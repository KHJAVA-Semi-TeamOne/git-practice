package com.netflix.dao;

import java.util.ArrayList;

import com.netflix.dto.Basic;
import com.netflix.dto.Membership;
import com.netflix.dto.Premium;
import com.netflix.dto.Standard;

public class MemberDAO {
	private ArrayList<Membership> list = new ArrayList<>();
	
	public void insertSample() {
		list.add(new Basic("aaa555","AAA", "210505", 3000));
		list.add(new Standard("eee666","E5E5", "211220", 5000));
		list.add(new Premium("cdd333","C6C6", "220605", 2000));
	}
	
	public void insert(Membership dto) {
		list.add(dto);
	}
	
	public ArrayList<Membership> selectAll(){
		return list;
	}
	
	public boolean doesIdExist(String id){
		for(Membership dto : list) {
			if(dto.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesNicknameExist(String nickname) {
		for(Membership dto : list) {
			if(dto.getNickname().equals(nickname)) {
				return true;
			}
		}
		return false;
	}
	
	public Membership selectById(String id){
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)) { 
				// 사용자가 입력한 아이디와 실제 일치하는 아이디가 있는지 검색
				return list.get(i);
			}
		}
		return null;
	}
	
	public Membership selectByNickname(String nickname){
		for(Membership dto : list) {
			if(dto.getNickname().equals(nickname)) {
				return dto;
			}
		}
		return null;
	}
	
	public int modify(String id, String nickname, int point){
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)) {
				// 수정할 회원의 정보 -> list.get(i)
				list.get(i).setNickname(nickname);
				list.get(i).setPoint(point);
				return 1;
			}
		}
		return -1;
	}
	
	public int delete(String id){
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)) {
				list.remove(i);
				return 1;
			}
		}		
		return -1;
	}
	
}
