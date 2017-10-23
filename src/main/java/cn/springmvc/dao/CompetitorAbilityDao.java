package cn.springmvc.dao;

import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YLT on 2017/9/28.
 */
public interface CompetitorAbilityDao {
    List<String> getDescriptions();

    void updateAbility(@Param("competitorId") int competitorId, @Param("tagName") String tagName, @Param("score") double score);

    ArrayList<Competition> getCompetitions();

    Double getCompetitorAbility(@Param("competitorId") int competitorId, @Param("tagName") String tagName);

    ArrayList<CompetitionLeaderboard> getLeaderBoardByCompetitionId(@Param("competitionId") int competitionId);

    int userRecordExitOrNot(@Param("competitorId") int competitorId);

    void insertUserAbilityRecord(@Param("competitorId") int competitorId,@Param("tagName") String tagName, @Param("score") double score);
}
