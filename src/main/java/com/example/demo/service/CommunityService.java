package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.CommunityMapper;
import com.example.demo.model.Pagenation;
import com.example.demo.model.Community;
import com.example.demo.model.CommunityReply;

@Service
public class CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    public int getCount(Community community) {
        return communityMapper.getCount(community);
    }

    public List<Community> getPagedList(Pagenation pgn) {
        return communityMapper.getPagedList(pgn);
    }

    public void updateReadCount(int cb_num) {
        communityMapper.updateReadCount(cb_num);
    }

    public Community getCommunity(int cb_num) {
        return communityMapper.getCommunity(cb_num);
    }

    public void insert(Community community) {
        communityMapper.insert(community);
    }

    public void update(Community community) {
        communityMapper.update(community);
    }

    public void delete(int cb_num) {
        communityMapper.delete(cb_num);
    }

    // 댓글 등록
    public int insertReply(CommunityReply reply) {
    	return communityMapper.insertReply(reply);
    }

    // 댓글 목록 가져오기
    public List<CommunityReply> getReplyList(int cb_num) {
        return communityMapper.getReplyList(cb_num);
    }

    // 댓글 삭제
    public void deleteReply(int cbr_num) {
        communityMapper.deleteReply(cbr_num);
    }
    
    //댓글 수정
	public int replyupdate(CommunityReply reply) {
		return communityMapper.replyupdate(reply);
	}
	
	// 게시글 상태 변경 (신고 상태 1로 변경)
	public void updateBoardState(int cb_num, int state) {
	    communityMapper.updateBoardState(cb_num, state);
	}



}
