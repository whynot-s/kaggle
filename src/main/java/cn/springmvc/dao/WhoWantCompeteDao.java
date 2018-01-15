package cn.springmvc.dao;

import cn.springmvc.model.WhoWantCompete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YLT on 2017/12/23.
 */
public interface WhoWantCompeteDao {
    int existOrNot(@Param("competitorId")int competitorId,@Param("competitionId") int competitionId);
    void insert(WhoWantCompete whoWantCompete);
    void update(@Param("columnName") String columnName, @Param("value") int value,@Param("competitionId") int competitionId,@Param("competitorId")int competitorId);
    List<WhoWantCompete> getAllWhoWantCompete();

}
