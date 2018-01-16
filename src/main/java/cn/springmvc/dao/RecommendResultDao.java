package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/1/14.
 */
public interface RecommendResultDao {
    void insert3(@Param("member1")int member1,@Param("member2")int member2,@Param("member3")int member3,@Param("cost")double cost,@Param("diff") double diff,@Param("grow") double grow,@Param("tableName") String tableName);
    void insert2(@Param("member1")int member1,@Param("member2")int member2,@Param("cost")double cost,@Param("diff") double diff,@Param("grow") double grow,@Param("tableName") String tableName);
    void insert4(@Param("member1")int member1,@Param("member2")int member2,@Param("member3")int member3,@Param("member4")int member4,@Param("cost")double cost,@Param("diff") double diff,@Param("grow") double grow,@Param("tableName") String tableName);

    List<HashMap<String,Object>> getRecommendResult(@Param("tableName") String tableName);

}
