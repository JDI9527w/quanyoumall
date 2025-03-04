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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
    public Boolean checkMember(MemberLoginVo memberLoginVo) {
        MemberEntity memberEntity = baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("email", memberLoginVo.getUsername())
                .or()
                .eq("username", memberLoginVo.getUsername())
                .or()
                .eq("mobile", memberLoginVo.getUsername()));
        if (ObjectUtils.isNotEmpty(memberEntity)) {
            String encodePassword = memberEntity.getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder.matches(memberLoginVo.getPassword(), encodePassword);
        }
        return false;
    }
}