package com.ez.herb.product.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductVO {
	private int productNo;
	private int categoryNo;
	private String productName;
	private int sellPrice;
	private String company;
	private String imageURL;
	private String explains;
	private String description;
	private Timestamp regDate;
	private int mileage;  
}
