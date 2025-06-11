package com.example.demo.mapper;

import com.example.demo.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin findByUsername(@Param("adminId") String adminId);
}