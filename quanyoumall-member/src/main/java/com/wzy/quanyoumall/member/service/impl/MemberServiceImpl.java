package com.wzy.quanyoumall.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.member.entity.MemberEntity;
import com.wzy.quanyoumall.member.mapper.MemberMapper;
import com.wzy.quanyoumall.member.service.MemberService;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {


}