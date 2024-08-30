package com.damoim.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.vo.Image;
import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;


@Controller
public class MembershipMeetingController {

	
	@Autowired
	private  MembershipMeetingService service;
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("/write")
	public String write(int membershipCode, Model model) {
		System.out.println("모임 컨트롤러 매핑 " + membershipCode);
		model.addAttribute("membershipCode",membershipCode );
		return "membershipMeeting/meetingWrite";
	}
	
	@PostMapping("/write")
	public String write1(int membershipCode, MembershipMeetings meeting) throws IOException {

		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		
		
		meeting.setId(authentication.getName());

		service.addMeeting(meeting);
		
		List<MembershipUserList> list = membershipService.MembershipAllInfo(membershipCode);
		MeetingsAgree ma = new MeetingsAgree();
		List<String> ides = new ArrayList<String>();
		
		for(MembershipUserList membership : list) {
			if(!membership.getListGrade().equals("guest")) {
				ides.add(membership.getMember().getId());
			}
		}
		ma.setIdes(ides);
		ma.setMeetCode(meeting.getMeetCode());
		service.yesOrNo(ma);


		System.out.println(meeting);
	   
	  
	   
		return "redirect:/club/"+membershipCode;
	}
	
	@GetMapping("/meetingDetail")
	public String meetingDetail(int meetCode , Model model) {
		System.out.println("디테일 컨트롤러 연결 : " + meetCode);
		model.addAttribute("meet", service.meetSelect(meetCode));
		model.addAttribute("list", service.meetMember(meetCode));


		List<MeetingsAgree> agree = new ArrayList<>();
		
	  for(int i=0; i<service.meetMember(meetCode).size(); i++) {
		  String name = service.meetMember(meetCode).get(i).getMember().getId();
		  MeetingsAgree asd =(MeetingsAgree) service.meetMember(meetCode).get(i);
		  asd.setId(name);
		  agree.add(asd);
		  System.out.println("바뀐 애들" + asd);
	  }
	  
	  System.out.println(agree);
	  
	
	 
	 
	  
	
	  
	
			
		return "membershipMeeting/meetingDetail";
	}
	
	@ResponseBody
	@PostMapping("/go")
	public String participation(MeetingsAgree ma) {
		System.out.println("참가 컨트롤러 접속 ");
		service.participation(ma);
		
		return "redirect:/meetingDetail";
	}
		
	
	
	
	
  public String fileUpload(MultipartFile file,int membershipCode,int meetCode) throws IllegalStateException, IOException {
		
		UUID uuid= UUID.randomUUID();
		
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
			
		File copyFile = new File("\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\"+ membershipCode+"\\"+meetCode+"\\"+fileName);  
		
		file.transferTo(copyFile); 
		
	
		
	
		
		return fileName;
		
		
	}
		
  
  @GetMapping("/meetingUpdate")
  public String update(int no, Model model) {
		

		
		return "/update";
	}
	

	
	@GetMapping("/meetingDelete")
	public String remove(int meetCode) throws IllegalStateException, IOException {
		
		
		return null;

	
	}
	
	public void fileDelete(String file, int membershipCode, int meetCode) throws IllegalStateException, IOException {
		if(file == null) {
			System.out.println("삭제할 파일이 없습니다");
		}
		else {
			System.out.println("삭제될 URL : "  + file);
		File f = new File("\\\\192.168.10.51\\damoim\\membership\\"+ Integer.toString(membershipCode) + "\\"+Integer.toString(meetCode) + "\\" + file);
		f.delete();
		
		}
	
	}
	
}
