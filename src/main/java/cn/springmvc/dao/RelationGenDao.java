package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/10/8.
 */
public interface RelationGenDao {
    Integer getRelationTime(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2, @Param("relationName") String relationName);

    void insertRelation(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2, @Param("relationName") String relationName);

    void updateRelationTime(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2, @Param("times") int times, @Param("relationName") String relationName);

    void updateIntimacy(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2, @Param("times") double times, @Param("relationName") String relationName);

    int recordExistOrNot(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    List<Map<String, Integer>> getDiscussionComment();

    List<Integer> discussionKernelOrNot();

    List<Map<String, Integer>> getDiscussionReply();

    int findCommenterId(@Param("Id") int Id, @Param("commentNo") int commentNo, @Param("tableName") String tableName, @Param("columnName") String columnName);

    List<Map<String, Integer>> getKernelComment();

    List<Map<String, Integer>> getKernelReply();

    List<Map<String, Object>> getRelationMap(@Param("column1") String column1, @Param("column2") String column2, @Param("tableName") String tableName);

    Integer[] getOrganizationMember(@Param("organizationId") int organizationId);

    Integer[] getOrganizationMemberNumber();

    void insertMapIdToName(@Param("MAP") Map<Integer, String> MAP);

    Map<String, Object> getAllRelationTime(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);

    List<Integer> getCompetitor1List();

    List<Map<String,Integer>> getRelationByCompetitorId1(@Param("competitorId1") int competitorId1);

    //更新参赛者的沟通代价
    void updateCost(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2, @Param("tagName") String tagName, @Param("cost") double cost);

    Integer getSocialRelationByCompetitorId(@Param("competitorId1") int competitorId1);

    //查询与competitorId1有关系的用户
    List<Integer> getCompetitorFriend(@Param("competitorId1") int competitorId1);

    //计算competitorId1和competitorId2之间的社交关系代价
    Integer calCost2(@Param("competitorId1") int competitorId1,@Param("competitorId2") int competitorId2);

    //删除competitorId1的关系
    void deleteRelation(@Param("competitorId1") int competitorId1);

    Double getCost(@Param("competitorId1") int competitorId1,@Param("competitorId2") int competitorId2,@Param("tagName") String tagName);
}
