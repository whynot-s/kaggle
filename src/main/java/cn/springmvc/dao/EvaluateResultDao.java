package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EvaluateResultDao {

    List<Map<String, Integer>> getResult2ById(@Param("competitorId") int competitorId);

    List<Map<String, Integer>> getResult3ById(@Param("competitorId") int competitorId);

    void insertEvaResult(@Param("competitorId") int competitorId, @Param("size") int size
        , @Param("accuracy") double accuracy, @Param("coverage") double coverage);
}
