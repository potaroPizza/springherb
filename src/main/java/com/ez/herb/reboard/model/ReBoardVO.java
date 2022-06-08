package com.ez.herb.reboard.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReBoardVO {
	private int no;
	private String name;	
	private String pwd;
	private String title;
	private String email;	
	private Timestamp regdate;	
	private int readcount;
	private String content;
	
	//답변형 추가
	private int groupNo;
	private int step; 
	private int sortNo;
	private String delFlag;
	
	//자료실 추가
	private String fileName;
	private long fileSize; 
	private int downCount;
    private String originalFileName; 
	
    //등록일로 부터 지난날 체크하는 멤버변수
    private int dateTerm;
	
}





