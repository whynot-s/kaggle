package cn.springmvc.dao;

import cn.springmvc.model.CoffiTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YLT on 2018/1/27.
 */
public interface CoffiTableDao {
    void insertCoffiTable(@Param("member1")int member1, @Param("costPercent")double costPercent, @Param("diffPercent") double diffPercent, @Param("growPercent")double growPercent);
    List<CoffiTable> getTestCompetitors(@Param("tableName") String tableName);
    CoffiTable getCoffiByCompetitorId(@Param("tableName") String tableName,@Param("competitorId")int competitorId);
    List<Integer> getCoffiMemberIds(@Param("tableName") String tableName);

}
