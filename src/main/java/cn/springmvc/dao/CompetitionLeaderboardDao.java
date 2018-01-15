package cn.springmvc.dao;

import cn.springmvc.model.CompetitionLeaderboard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YLT on 2018/1/13.
 */
public interface CompetitionLeaderboardDao {
    List<String > getLeaderBoardByCompetitionId(@Param("competitionId") int competitionId);

    List<CompetitionLeaderboard>  getLeaderBoardEachCompetition(@Param("competitionId") int competitionId);

    void update(@Param("competitionId") int competitionId, @Param("ranking")int ranking,@Param("teamMemberId")String teamMemberId);

}
