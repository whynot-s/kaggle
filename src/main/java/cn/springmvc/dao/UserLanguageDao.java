package cn.springmvc.dao;

import cn.springmvc.model.Kernel;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YLT on 2017/8/29.
 */
public interface UserLanguageDao {
    //kernel表中发布者使用的语言以及使用次数
    List<HashMap> userLanguage();

    void insertCompetitorLanguage(@Param("kernelAuthorId") int kernelAuthorId);

    HashSet<Integer> getCompetitorId2();

    HashSet<Integer> getCompetitorId();

    void updateCompetitor(@Param("competitorId") int competitorId, @Param("languages") String languages);

    void updateCompetitor2(@Param("competitorId") int competitorId, @Param("languages") String languages);

    Kernel getKernel(@Param("kernelId") int kernelId);
}
