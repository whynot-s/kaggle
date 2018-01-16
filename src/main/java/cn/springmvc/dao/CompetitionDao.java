package cn.springmvc.dao;

import cn.springmvc.model.Competition;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2017/9/28.
 */
public interface CompetitionDao {

    //ok
    ArrayList<Competition> getCompetitions();


    List<Integer> getCompetitionIds();
    Integer getCompetitionIdByName(@Param("competitionName") String competitionName);
    List<HashMap<String ,Object>> getCompetitionLeaderboardLen();
    Competition getCompetitionById(@Param("competitionId") int competitionId);
}
