package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Admin;


@Mapper
public interface AdminMapper {

	Admin findByUsername(@Param("admin_id") String admin_id);
}
