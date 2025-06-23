package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AnnMapper;
import com.example.demo.model.Announcement;

@Service
public class AnnouncementService {

    @Autowired
    private AnnMapper annMapper;

    public int count() {
        return annMapper.count();
    }

    public int countFiltered(String search, String keyword) {
        return annMapper.countFiltered(search, keyword);
    }

    public List<Announcement> getAnnList(int start, int end, String search, String keyword) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("start", start);
        paramMap.put("end", end);
        paramMap.put("search", search);
        paramMap.put("keyword", keyword);
        
        return annMapper.getAnnList(paramMap); // ✅ Map 하나로 넘겨줌
    }


    public Announcement getContent(int no) {
        return annMapper.getContent(no);
    }
}