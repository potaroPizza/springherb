package com.ez.herb.board.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private int no;
	private String name;
	private String pwd;
	private String title;
	private String email;
	private Timestamp regdate;
	private int readcount;
	private String content;
}
