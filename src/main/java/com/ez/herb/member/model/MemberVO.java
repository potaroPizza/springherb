package com.ez.herb.member.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
	private int no;
	private String userid;
	private String name;
	private String pwd;
	private String email1;
	private String email2;
	private String hp1;
	private String hp2;
	private String hp3;
	private String zipcode;
	private String address;
	private String addressDetail;
	private Timestamp regdate;
    private int mileage;
    private Timestamp outdate;
}