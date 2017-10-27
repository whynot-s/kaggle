package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/10/8.
 */
public interface RelationGenDao {
    List<String> getCollaborationRecord();
    
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

    void insertMapIdToName(@Param("MAP") Map<Integer,String> MAP);
    
    Map<String, Object> getAllRelationTime(@Param("competitorId1") int competitorId1, @Param("competitorId2") int competitorId2);
}
