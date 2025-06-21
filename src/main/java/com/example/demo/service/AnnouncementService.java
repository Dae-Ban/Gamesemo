package com.example.demo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AnnMapper;
import com.example.demo.model.Announcement;

@Service
public class AnnouncementService {

	@Autowired
	AnnMapper annMap;
	
	@Transactional
	public List<Announcement> getAnnList(int page, String search, String keyword) throws SQLException {
		return annMap.getAnnList(page, search, keyword);
	}

	@Transactional
	public int count() throws SQLException {
		return annMap.count();
	}
	
	@Transactional
	public Announcement getContent(int no) {
		Announcement ann =  annMap.getContent(no);
		return ann;
	}

}
