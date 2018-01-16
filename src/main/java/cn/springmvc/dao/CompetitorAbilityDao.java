package cn.springmvc.dao;

import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorAbility;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by YLT on 2017/9/28.
 */
public interface CompetitorAbilityDao
{
    List<String> getDescriptions();

    void updateAbility(@Param("competitorId") int competitorId, @Param("tagName") String tagName, @Param("score") double score);


    //通过Id获取到某个tag的能力值
    Double getCompetitorAbility(@Param("competitorId") int competitorId, @Param("tagName") String tagName);


    int userRecordExitOrNot(@Param("competitorId") int competitorId);

    void insertUserAbilityRecord(@Param("competitorId") int competitorId, @Param("tagName") String tagName, @Param("score") double score);

    //获取某个team下所有人的技能的值
    ArrayList<CompetitorAbility> getTeamAbility(@Param("list") String list);

    Double getMaxTag(@Param("list") String list, @Param("tagName") String tagName);


    //获取对应技能非空的所有人和能力值的集合
    List<Map<String, Object>> getAllNotNullAbility2(@Param("tagName") String tagName);

    //获取某个人的所有技能
    CompetitorAbility getCompetitorAbilityById(@Param("competitorId") int competitorId);

    //获取所有人的所有技能
    ArrayList<CompetitorAbility> getAllCompetitorAbility();

    //获取某技能下的最大值
    double getMaxScore(@Param("tagName") String tagName);

    //得到能力榜上所有参赛者id
    List<Integer> getCompetitorIds();

    List<Integer> getTopKCompetitor(@Param("tagName") String tagName);

}
