package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AdminAnno {
	private int anNum;
	private String anTitle;
	private String anContent;
	private Timestamp anDate;
	private int anState;
}
