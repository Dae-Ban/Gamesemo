
package com.example.demo.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {

	public int countById(@Param("id") String id);

	public int countByNickname(@Param("nickname") String nickname);

	public int insert(Member member);

	public Member findById(String id);

	public int updateMember(Member member);

	public Member selectMemberById(String id);

	public int updatePassword(Member member); // 일반 비번 변경

	public int deleteMember(String id);

	public Member selectByEmailForRegister(String email);

	public Member selectByEmailForFind(String email);
	
	public Member selectByIdAndEmail(@Param("id") String id, @Param("email") String email);

	public int updatePasswordForFind(Map<String, Object> paramMap); // 비번찾기 후 비번변경

	// 이메일인증 : 재원님꺼 추가함

	public Member findByEmail(@Param("email") String email);

	public int checkIdExists(String id);

	public Member findByEmailForRegister(String email);

	public boolean registerMember(Member member);
	
	public List<Member> selectAllEmailSubscribers();
	
	// 로그인 : 영교님꺼 추가	
	
	void socialInsert(Member member);

	public Member login(String id);
	
	Member findBySocialIdAndPlatform(@Param("socialId") String socialId, @Param ("platform") String platform);

	


}
