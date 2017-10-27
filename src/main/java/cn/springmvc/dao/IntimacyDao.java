package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by whynot-s on 2017/10/25.
 */
public interface IntimacyDao {

	List<Map<String, Object>> getTeams();
	
	void truncateTeamIntimacy();
	
	Double getPairIntimacy(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);
	
	void updateTeamPairIntimacy(@Param("intimacy") double intimacy, @Param("teamId") int teamId, @Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);
	
	void insertTeamPair(@Param("teamId") int teamId, @Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);
	
}
