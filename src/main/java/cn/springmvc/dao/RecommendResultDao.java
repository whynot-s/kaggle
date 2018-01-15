package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by YLT on 2018/1/14.
 */
public interface RecommendResultDao {
    void insert(@Param("member1")int member1,@Param("member2")int member2,@Param("member3")int member3,@Param("cost")double cost,@Param("diff") double diff,@Param("grow") double grow);
    void insert2(@Param("member1")int member1,@Param("member2")int member2,@Param("cost")double cost,@Param("diff") double diff,@Param("grow") double grow);
}
