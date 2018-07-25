package cn.springmvc.dao;

import cn.springmvc.model.TeamRecordAnalysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YLT on 2018/1/24.
 */
public interface TeamRecordAnalysisDao {
    void insert(TeamRecordAnalysis teamRecordAnalysis);
    List<Integer> getCompetitorsTeamMoreThan();
    List<TeamRecordAnalysis> getTeamRecordByCompetitorId(@Param("competitorId") int competitorId);
    List<Integer> getAllTestCompetitors();
    double getAvgColumn(@Param("tagName")String tagName);
    double getAllColumnOfCompetitor(@Param("tagName")String tagName, @Param("member1")int member1, @Param("tableName") String tableName);
    double getAvgColumnOfCompetitor(@Param("tagName")String tagName, @Param("member1")int member1, @Param("tableName") String tableName);

    int exist(@Param("member1") int member1);
    void setTeamSum(@Param("member1") int member1);
    List<Integer> getTestPeople();
    List<TeamRecordAnalysis> getAllTeamRecord(@Param("tableName") String tableName);
    void updateColumnString(@Param("columnName")String columnName, @Param("member1") int member1, @Param("result") String result);
    void updateSuccessRate(@Param("member1") int member1,@Param("successRate") double successRate);
    void updateFeature(@Param("cost")double cost, @Param("diff")double diff, @Param("grow")double grow,@Param("member1") int member1,@Param("teamNo")int teamNo);

}
