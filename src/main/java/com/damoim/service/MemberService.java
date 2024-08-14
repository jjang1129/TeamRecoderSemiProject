package com.damoim.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mapper.MemberMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//xml -> Mapper -> service -> controller

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	private BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	
	
	public Member login(Member vo) {
		
		Member mem = mapper.login(vo.getId());
		System.out.println("로그인할때 확인" + mem);
		
		if(mem != null && bcpe.matches(vo.getPwd(),mem.getPwd())) {
			System.out.println("세션에 mem 리턴?" + mem);
		return mem;
		}
		return null;
	}
	
	public ArrayList<MemberListDTO> loginMemberMembership(Member member){
		return mapper.loginMemberMembership(member);
		
	}
	
	@Transactional
	public void signUp(Member member) {
		// 비밀번호 암호화
		member.setPwd(bcpe.encode(member.getPwd()));
		
			mapper.signUp(member);
		
	}
	
	public Member idCheck(Member member) {
		return mapper.idCheck(member);
		
	}
	
	public Member nicknameCheck(Member member) {
		return mapper.nicknameCheck(member);
		
	}

	public Member pwdCheck(Member member) {
		
		return mapper.pwdCheck(member);
	}
	
	
	// 업데이트 관련 서비스 =====================
	public boolean updateCheck(Member vo,String pwdCheck) {
		
//		System.out.println("암호화한 비밀번호 : " + vo.getPwd());
//		System.out.println("입력된 비밀번호 : " + pwdCheck);
		
		if(bcpe.matches(pwdCheck,vo.getPwd())) {
			System.out.println("서비스에서 true 리턴!!!");
			return true;
		} else {
			System.out.println("서비스에서 false 리턴!@!");
			return false;
		}
	}
	
	public void update(Member member) {
		member.setPwd(bcpe.encode(member.getPwd()));
		 mapper.update(member);
	}
	public void addrUpdate(Member member) {
		 mapper.addrUpdate(member);
	}
	// ======================================
	
	
	
	
	/*
	// 카카오 로그인
	public User loginWithKakao(User user) {
		User savedUser = userMapper.getUserByEmail(user.getEmail());
		if(savedUser == null) {
			userMapper.addUser(user);
		}
		return savedUser;
	}
	*/
	
	
	public ArrayList<Member> dummyMember(){
		return mapper.dummyMember();
		
	}
	public void dummyUpdate() {
		ArrayList<Member> list = dummyMember();
		System.out.println(list);
		for(Member m : list) {
			m.setPwd(bcpe.encode(m.getPwd()));
		mapper.dummyUpdate(m);
		
		}
	}
}
