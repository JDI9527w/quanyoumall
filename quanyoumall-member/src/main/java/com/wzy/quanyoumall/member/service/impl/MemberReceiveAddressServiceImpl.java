package com.wzy.quanyoumall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.member.entity.MemberReceiveAddressEntity;
import com.wzy.quanyoumall.member.mapper.MemberReceiveAddressMapper;
import com.wzy.quanyoumall.member.service.MemberReceiveAddressService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddressEntity> implements MemberReceiveAddressService {
    @Override
    public List<MemberReceiveAddressEntity> listGetMemberReceiveAddrByMemberId(Long memberId) {
        return baseMapper.selectList(new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));
    }
}