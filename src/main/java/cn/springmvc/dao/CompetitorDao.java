package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by YLT on 2018/7/25.
 */
public interface CompetitorDao {
    Integer getCompetitorId(@Param("name") String name);
    String getCompetitorName(@Param("id") int id);

}
