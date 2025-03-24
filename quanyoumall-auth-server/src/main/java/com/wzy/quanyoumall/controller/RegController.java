package com.wzy.quanyoumall.controller;

import com.wzy.quanyoumall.common.constant.AuthServerConstant;
import com.wzy.quanyoumall.common.exception.BizCodeEnum;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.feign.MemberFeignService;
import com.wzy.quanyoumall.feign.ThirdPartyFeignService;
import com.wzy.quanyoumall.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth/reg")
public class RegController {

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MemberFeignService memberFeignService;

    @GetMapping("/reg.html")
    public String reg() {
        return "register";
    }

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {
        Long expire = stringRedisTemplate.getExpire(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, TimeUnit.SECONDS);
        if (expire != null) {
            if (expire > 9 * 60L) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        // 生成一个0到99999之间的随机数
        int randomNumber = ThreadLocalRandom.current().nextInt(100000);
        // 格式化输出为5位数字，不足的前面补0
        String code = String.format("%05d", randomNumber);
        thirdPartyFeignService.sendCode(phone, code);
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);
        return R.ok();
    }

    @PostMapping("/regist")
    public String register(@Valid UserRegisterVo registerVo, BindingResult result, Model model) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            model.addAttribute("errors", errors);
            return "register";
        }
        String checkCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
        if (!registerVo.getCode().equals(checkCode)) {
            errors.put("code", "验证码错误");
            model.addAttribute("errors", errors);
            return "register";
        } else {
            stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
            R r = memberFeignService.registerMember(registerVo);
            int resCode = r.getCode();
            String msg = (String) r.get("msg");
            if (resCode != 0) {
                if (resCode == 10003) {
                    errors.put("username", msg);
                }
                if (resCode == 10004) {
                    errors.put("phone", msg);
                }
                model.addAttribute("errors", errors);
                return "register";
            }
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }
}
