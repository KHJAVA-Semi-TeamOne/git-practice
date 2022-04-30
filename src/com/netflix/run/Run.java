package com.netflix.run;

import java.util.ArrayList;
import java.util.Scanner;

import com.netflix.dao.MemberDAO;
import com.netflix.dto.Basic;
import com.netflix.dto.Membership;
import com.netflix.dto.Premium;
import com.netflix.dto.Standard;

public class Run {
	static Scanner sc = new Scanner(System.in);
	
	public static int getNumberInput() {
		while(true) {
			try {
				System.out.print(">> ");
				int menu = Integer.parseInt(sc.nextLine());
				return menu;
			}catch(Exception e) {
				System.out.println("숫자가 아닌 값을 입력할 수 없습니다.");
				e.printStackTrace();
			}
		}
	}
	
	public static void printMembership(ArrayList<Membership> list ) {
		System.out.println("ID\t등급\t닉네임\t가입일\t포인트");
		for(Membership dto : list) {
			System.out.println(dto.toString());
		}		
	}
	public static void printMembership(Membership dto) {
		System.out.println("ID\t등급\t닉네임\t가입일\t포인트");
		System.out.println( dto.toString() );
	}
	
	public static void main(String[] args) {		
		MemberDAO dao = new MemberDAO();
		
		String id = null;
		String nickname = null;
		int menu = 0;
		
		while(true) {
			System.out.println("\n*** Netflix 회원 관리 시스템 ***");
			System.out.println("1.신규 회원 등록");
			System.out.println("2.회원 목록 출력");
			System.out.println("3.회원 정보 검색");
			System.out.println("4.회원 정보 수정");
			System.out.println("5.회원 정보 삭제");
			System.out.println("6.시스템 종료");			
			menu = getNumberInput();
			
			if(menu == 1) { // 회원 등록
				dao.insertSample();
				System.out.println("\n회원 등급 선택");
				System.out.println("1.Basic");
				System.out.println("2.Standard");
				System.out.println("3.Premium");
				System.out.print(">> ");
				int grade = getNumberInput();
				
				while(true) {
					System.out.print("신규 회원 ID(7자 이내) : ");
					id = sc.nextLine();
					if(dao.doesIdExist(id)) {
						System.out.println("중복된 아이디입니다.");
					}else {
						break;
					}
				}				
				
				while(true) {
					System.out.print("신규 회원 닉네임(4자 이내) : ");
					nickname = sc.nextLine();
					if(!dao.doesNicknameExist(nickname)) {
						break;
					}
					System.out.println("중복된 닉네임입니다.");
				}				
				
				System.out.print("신규 회원 가입일(210505 형식) : ");
				String signup_date = sc.nextLine();
				
				System.out.print("신규 회원 포인트 : ");
				int point = getNumberInput();
				
				if(grade == 1) {// Basic 등급
					dao.insert(new Basic(id, nickname, signup_date, point));
				}else if(grade == 2) { // Standard 등급
					dao.insert(new Standard(id, nickname, signup_date, point));
				}else if(grade == 3) { // Premium 등급
					dao.insert(new Premium(id, nickname, signup_date, point));
				}			
			}else if(menu == 2) { // 목록 출력
				ArrayList<Membership> list = dao.selectAll();
				printMembership(list);
				
			}else if(menu == 3) { // 정보 검색
				System.out.println("\n1. ID로 검색");
				System.out.println("2. 닉네임으로 검색");
				System.out.print(">> ");
				int search = getNumberInput();
				
				if(search == 1) {//ID검색
					System.out.print("검색할 ID 입력 : ");
					id = sc.nextLine();
									
					Membership dto = dao.selectById(id);
					if(dto != null) {
						printMembership(dto);
					}else {
						System.out.println("일치하는 회원의 정보가 없습니다.");
					}
					
				}else if(search == 2) {//닉네임 검색
					System.out.print("검색할 닉네임 입력 : ");
					nickname = sc.nextLine();
					Membership dto = dao.selectByNickname(nickname);
						
					if(dto != null) {
						printMembership(dto);
					}else {
						System.out.println("일치하는 회원의 정보가 없습니다.");
					}
				}
				
			}else if(menu == 4) { // 정보 수정				
				ArrayList<Membership> list = dao.selectAll();
				printMembership(list);
				
				System.out.print("\n수정할 회원의 ID : ");
				id = sc.nextLine();
				System.out.print("수정할 닉네임 : ");
				nickname = sc.nextLine();
				System.out.print("수정할 포인트 : ");
				int point = getNumberInput();
				
				int rs = dao.modify(id, nickname, point);				
				if(rs == 1) {
					System.out.println("수정이 완료됐습니다.");
				}else if(rs == -1) {
					System.out.println("수정할 수 없는 아이디입니다.");
				}				
			}else if(menu == 5) { // 정보 삭제
				ArrayList<Membership> list = dao.selectAll();
				printMembership(list);				
				
				System.out.print("\n삭제할 회원의 ID 입력 : ");
				id = sc.nextLine();
				int rs = dao.delete(id);
				if(rs == 1) {
					System.out.println("삭제가 완료되었습니다.");
				}else if(rs == -1) {
					System.out.println("삭제할 수 없는 아이디입니다.");
				}			
			}else if(menu == 6) { // 시스템 종료 
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}		
	}
}
