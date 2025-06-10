package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Admin;


@Mapper
public interface AdminMapper {

	Admin findByUsername(String admin_id);
}
