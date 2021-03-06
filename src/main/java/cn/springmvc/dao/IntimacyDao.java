package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by whynot-s on 2017/10/25.
 */
public interface IntimacyDao {

    List<Map<String, Object>> getTeams();

    void truncateTeamMemberIntimacy();

    Double getPairIntimacy(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    void updateTeamPairIntimacy(@Param("intimacy") double intimacy, @Param("teamId") int teamId, @Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    void insertTeamPair(@Param("teamId") int teamId, @Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    List<Integer> getTeamIds();

    List<Map<String, Object>> getTeamMemberIntimacyById(@Param("teamId") int teamId);

    void insertTeamTotalIntimacy(@Param("teamId") int teamId, @Param("intimacy") double intimacy, @Param("numOfMembers") int numOfMembers);

    void truncateTeamIntimacy();
}
