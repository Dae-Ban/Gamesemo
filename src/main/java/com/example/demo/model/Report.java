package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Report {
    private int rp_num;
    private String id;
    private String rp_reason;
    private Timestamp rp_date;
    private String rp_table;
    private int board_num;
    private String rp_status;
     
}