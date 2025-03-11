package com.wzy.quanyoumall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.exception.PhoneExistException;
import com.wzy.quanyoumall.common.exception.UsernameExistException;
import com.wzy.quanyoumall.member.entity.MemberEntity;
import com.wzy.quanyoumall.member.mapper.MemberMapper;
import com.wzy.quanyoumall.member.service.MemberService;
import com.wzy.quanyoumall.member.vo.MemberLoginVo;
import com.wzy.quanyoumall.member.vo.MemberRegisterVo;
import com.wzy.quanyoumall.member.vo.SocialAccountVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Override
    public void register(MemberRegisterVo memberRegisterVo) throws UsernameExistException, PhoneExistException {
        String userName = memberRegisterVo.getUserName();
        String phone = memberRegisterVo.getPhone();
        MemberEntity checkUserNameExist = this.checkUserNameExist(userName);
        MemberEntity checkPhoneExist = this.checkPhoneExist(phone);
        if (ObjectUtils.isNotEmpty(checkUserNameExist)) {
            throw new UsernameExistException();
        }
        if (ObjectUtils.isNotEmpty(checkPhoneExist)) {
            throw new PhoneExistException();
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUsername(userName);
        memberEntity.setNickname(userName);
        memberEntity.setMobile(phone);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(memberRegisterVo.getPassword());
        memberEntity.setPassword(password);
        // 用户等级
        baseMapper.insert(memberEntity);
    }

    @Override
    public MemberEntity checkUserNameExist(String username) {
        QueryWrapper<MemberEntity> qw = new QueryWrapper<>();
        return baseMapper.selectOne(qw.eq("username", username).last("limit 1"));
    }

    @Override
    public MemberEntity checkPhoneExist(String phone) {
        QueryWrapper<MemberEntity> qw = new QueryWrapper<>();
        return baseMapper.selectOne(qw.eq("mobile", phone).last("limit 1"));
    }

    @Override
    public MemberEntity checkEmailExist(String email) {
        QueryWrapper<MemberEntity> qw = new QueryWrapper<>();
        return baseMapper.selectOne(qw.eq("email", email).last("limit 1"));
    }

    @Override
    public MemberEntity login(MemberLoginVo memberLoginVo) {
        MemberEntity memberEntity = baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("email", memberLoginVo.getUsername())
                .or()
                .eq("username", memberLoginVo.getUsername())
                .or()
                .eq("mobile", memberLoginVo.getUsername()));
        if (ObjectUtils.isNotEmpty(memberEntity)) {
            String encodePassword = memberEntity.getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean flag = bCryptPasswordEncoder.matches(memberLoginVo.getPassword(), encodePassword);
            if (flag) {
                return memberEntity;
            }
        }
        return null;
    }

    @Override
    public MemberEntity loginBySocialAccount(SocialAccountVo socialAccountVo) {
        String socialId = socialAccountVo.getId();
        MemberEntity matchEntity = baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("third_account_id1", socialId));
        if (ObjectUtils.isNotEmpty(matchEntity)) {
            // 直接登录。
            return matchEntity;
        } else {
            // 邮箱已注册,将三方id绑定.
            if (StringUtils.isNotEmpty(socialAccountVo.getEmail())) {
                MemberEntity memberEntity = checkEmailExist(socialAccountVo.getEmail());
                memberEntity.setThirdAccountId1(socialAccountVo.getId());
                baseMapper.updateById(memberEntity);
                return memberEntity;
            }
            // 注册
            MemberEntity memberEntity = new MemberEntity();
            MemberEntity nameExist = checkUserNameExist(socialAccountVo.getName());
            if (nameExist != null) {
//                用户名与已有用户重复
                // 用户量到达一定规模后,此方式无法避免用户名出现重复的情况.应在用户注册或其他时机做对用户名的限制.
                memberEntity.setUsername(socialAccountVo.getName() + UUID.randomUUID().toString().substring(0, 6));
                memberEntity.setNickname(socialAccountVo.getName() + UUID.randomUUID().toString().substring(0, 6));
            } else {
                memberEntity.setUsername(socialAccountVo.getName());
                memberEntity.setNickname(socialAccountVo.getName());
            }
            memberEntity.setEmail(socialAccountVo.getEmail());
            memberEntity.setThirdAccountId1(socialAccountVo.getId());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String randomPwd = bCryptPasswordEncoder.encode(UUID.randomUUID().toString().substring(0, 8));
            memberEntity.setPassword(randomPwd);
            baseMapper.insert(memberEntity);
            return memberEntity;
        }
    }

    /**
     * 查看社交账户的用户名和邮箱等属性是否与现有用户冲突
     *
     * @param socialAccountVo
     * @return
     */
    public Boolean checkClash(SocialAccountVo socialAccountVo) {
        MemberEntity nameCheck = checkUserNameExist(socialAccountVo.getName());
        MemberEntity emailCheck = null;
        if (StringUtils.isNotEmpty(socialAccountVo.getEmail())) {
            emailCheck = checkEmailExist(socialAccountVo.getEmail());
        }
        // 其他属性....
        if (ObjectUtils.isNotEmpty(nameCheck)) {
            return true;
        }
        return false;
    }
}