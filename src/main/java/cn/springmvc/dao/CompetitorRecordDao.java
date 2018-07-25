package cn.springmvc.dao;

import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorRecord;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YLT on 2017/12/19.
 */
public interface CompetitorRecordDao {

    void insertCompetitorRecord(CompetitorRecord competitorRecord);

    List<Integer> getCompetitorIds();

    List<CompetitorRecord> getCompetitorRecordByCompetitorId(@Param("competitorId") int competitorId, @Param("tableName")String tableName);

    List<CompetitorRecord> getLimitCompetitorRecordByCompetitorId(@Param("competitorId") int competitorId);

    List<CompetitorRecord> getCompetitorRecordByCompetitionId(@Param("competitionId") int competitionId);

    //是否存在用户的某条参赛记录
    int existOrNot(@Param("competitionId") int competitionId, @Param("competitorId") int competitorId);

    //查询用户的某次竞赛参与方式
    int getSoloOrTeam(@Param("competitionId") int competitionId, @Param("competitorId") int competitorId);

    void updateRankPercent(@Param("competitionId") int competitionId, @Param("competitorId") int competitorId, @Param("rankPercent") double rankPercent);

    //查询某个用户的竞赛参与数,训练集
    Integer getRecordTimeByCompetitorId(@Param("competitorId") int competitorId);

    ////查询某个用户的竞赛参与数,测试集
    Integer getRecordTimeByCompetitorIdTest(@Param("competitorId") int competitorId);

    //
    //List<Integer> getTeamCompetitors(@Param("tableName") String tableName);

    List<HashMap> teamOrNot(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    Integer exist(@Param("tableName") String tableName, @Param("competitionId") int competitionId, @Param("competitorId") int competitorId,@Param("ranking") int ranking);

    List<Integer> getTeamRecordMoreThan();

    List<Integer> getTeamCompetitor(@Param("tableName") String tableName);

    List<Integer> getLeaderBoardRecordMember(@Param("competitionId") int competitionId, @Param("ranking") int ranking);

}
