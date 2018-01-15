package cn.springmvc.dao;

import cn.springmvc.model.AbilityImprove;
import org.apache.ibatis.annotations.Param;

/**
 * Created by YLT on 2017/12/26.
 */
public interface AbilityImproveDao {
    void updateScoreByName(@Param("tagName") String tagName, @Param("score") double score, @Param("competitorId") int competitorId);
    void updateCompeteNumByName(@Param("tagName") String tagName, @Param("num") int num, @Param("competitorId") int competitorId);
    double getScoreByName(@Param("tagName") String tagName, @Param("competitorId") int competitorId);
    int getCompeteNumByName(@Param("tagName") String tagName, @Param("competitorId") int competitorId);
    int exitOrNot(@Param("competitorId") int competitorId);
    void insert(AbilityImprove abilityImprove);

}
