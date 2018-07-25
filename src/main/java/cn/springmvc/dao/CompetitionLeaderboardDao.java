package cn.springmvc.dao;

import cn.springmvc.model.CompetitionLeaderboard;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLT on 2018/1/13.
 */
public interface CompetitionLeaderboardDao {
    List<String > getMemberIdsByCompetitionId(@Param("competitionId") int competitionId);

    ArrayList<CompetitionLeaderboard> getLeaderBoardByCompetitionId(@Param("competitionId") int competitionId);

    void update(@Param("competitionId") int competitionId, @Param("ranking")int ranking,@Param("teamMemberId")String teamMemberId);

    ArrayList<String> getAllLeaderBoardRecord();

    ArrayList<String > getTopRankTeam(@Param("ranking") int ranking);
}
