package cn.springmvc.dao;

import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorRecord;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/12/19.
 */
public interface CompetitorRecordDao {

    void insertCompetitorRecord(CompetitorRecord competitorRecord);

    ArrayList<CompetitionLeaderboard> getLeaderBoardByCompetitionId(@Param("competitionId") int competitionId);

    List<Integer> getCompetitorIds();

    List<CompetitorRecord> getCompetitorRecordByCompetitorId(@Param("competitorId") int competitorId);

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
    List<Integer> getTestCompetitors();

    //获取测试数据中各个team的比赛、排名、队伍的size
    List<Map<String, Object>> getTeamIDs();

    //根据比赛与排名获得各个team成员的Id
    List<Integer> getTeamMembers(@Param("competitionId") Object competitionId, @Param("ranking") Object ranking);

}
