package cn.springmvc.service;

import cn.springmvc.dao.RelationGenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/10/8.
 */
@Service
public class CompetitorRelation {

    @Autowired
    private RelationGenDao relationGenDao;

    public void insertCollaborationRelation() {

        List<String> allCollaborationRecord = relationGenDao.getCollaborationRecord();

        for (String record : allCollaborationRecord
                ) {
            //这里需要注意
            String[] members = record.split("&");
            if(members.length > 1) {
                for (int i = 0; i < members.length - 1; i++) {
                    for (int j = i + 1; j < members.length; j++) {
                        updateRelation(Integer.parseInt(members[i]), Integer.parseInt(members[j]), "collaborationTime");
                    }
                }
            }
        }
    }


    public void updateRelation(int competitorId1, int competitorId2, String relationName) {
        //忽略了同一个人之间的关系
        if (competitorId1 != competitorId2) {
            Integer Time = relationGenDao.getRelationTime(competitorId1, competitorId2, relationName);
            if (relationGenDao.recordExistOrNot(competitorId1, competitorId2) == 1) {
                if (Time == null) {
                    relationGenDao.updateRelationTime(competitorId1, competitorId2, 1, relationName);
                    relationGenDao.updateRelationTime(competitorId2, competitorId1, 1, relationName);
                } else {
                    relationGenDao.updateRelationTime(competitorId1, competitorId2, Time + 1, relationName);
                    relationGenDao.updateRelationTime(competitorId2, competitorId1, Time + 1, relationName);
                }
            } else {
                relationGenDao.insertRelation(competitorId1, competitorId2, relationName);
                relationGenDao.insertRelation(competitorId2, competitorId1, relationName);
            }
        }
    }


    public void insertOtherRelationTime(String tableName) {
        //筛去了discussionAuthorId和commentId相同的情况，discussion与kernel重合的情况
        if (tableName.equals("discussionComment")) {
            List<Map<String, Integer>> discussionCommentList = relationGenDao.getDiscussionComment();
            List<Integer> kernelIdInDiscussion = relationGenDao.discussionKernelOrNot();
            for (Map<String, Integer> discussionComment : discussionCommentList
                    ) {
                int discussionId = discussionComment.get("discussionId");
                if (kernelIdInDiscussion.contains(discussionId)) {
                    continue;
                }
                int discussionAuthorId = discussionComment.get("discussionAuthorId");
                int commenterId = discussionComment.get("commenterId");
                updateRelation(discussionAuthorId, commenterId, "discussionCommentTime");
            }
        } else if (tableName.equals("discussionReply")) {
            List<Map<String, Integer>> discussionReplyList = relationGenDao.getDiscussionReply();
            List<Integer> kernelIdInDiscussion = relationGenDao.discussionKernelOrNot();
            for (Map<String, Integer> discussionReply : discussionReplyList
                    ) {
                int discussionId = discussionReply.get("discussionId");
                if (kernelIdInDiscussion.contains(discussionId)) {
                    continue;
                }
                int discussionAuthorId = discussionReply.get("discussionAuthorId");
                int commentNo = discussionReply.get("commentNo");
                int replierId = discussionReply.get("replierId");
                int commenterId = relationGenDao.findCommenterId(discussionId, commentNo, "discussioncomment", "discussionId");
                updateRelation(discussionAuthorId, replierId, "discussionCommentTime");
                updateRelation(commenterId, replierId, "discussionCommentTime");
            }
        } else if (tableName.equals("kernelComment")) {
            List<Map<String, Integer>> kernelCommentList = relationGenDao.getKernelComment();
            for (Map<String, Integer> kernelComment : kernelCommentList) {
                int kernelAuthorId = kernelComment.get("kernelAuthorId");
                int commenterId = kernelComment.get("commenterId");
                updateRelation(kernelAuthorId, commenterId, "kernelCommentTime");
            }
        } else if (tableName.equals("kernelReply")) {
            List<Map<String, Integer>> kernelReplyList = relationGenDao.getKernelReply();
            for (Map<String, Integer> kernelReply : kernelReplyList
                    ) {
                int kernelId = kernelReply.get("kernelId");
                int kernelAuthorId = kernelReply.get("kernelAuthorId");
                int commentNo = kernelReply.get("commentNo");
                int replierId = kernelReply.get("replierId");
                int commenterId = relationGenDao.findCommenterId(kernelId, commentNo, "kernelcomment", "kernelId");
                updateRelation(kernelAuthorId, replierId, "kernelCommentTime");
                updateRelation(commenterId, replierId, "kernelCommentTime");
            }
        } else if (tableName.equals("discussionVote")) {
            List<Map<String, Object>> discussionVoteList = relationGenDao.getRelationMap("discussionAuthorId", "voterId", "discussionvote");
            for (Map<String, Object> discussionVote : discussionVoteList
                    ) {
                int discussionAuthorId = Integer.parseInt(discussionVote.get("discussionAuthorId").toString());
                int voterId = Integer.parseInt(discussionVote.get("voterId").toString());
                updateRelation(discussionAuthorId, voterId, "discussionVoteTime");
            }
        } else if (tableName.equals("kernelVote")) {
            List<Map<String, Object>> kernelVoteList = relationGenDao.getRelationMap("kernelAuthorId", "voterId", "kernelvote");
            for (Map<String, Object> kernelVote : kernelVoteList
                    ) {
                int kernelAuthorId = Integer.parseInt(kernelVote.get("kernelAuthorId").toString());
                int voterId = Integer.parseInt(kernelVote.get("voterId").toString());
                updateRelation(kernelAuthorId, voterId, "kernelVoteTime");
            }
        } else if (tableName.equals("kernelFork")) {
            List<Map<String, Object>> kernelForkList = relationGenDao.getRelationMap("kernelAuthorId", "forkId", "kernelfork");
            for (Map<String, Object> kernelFork : kernelForkList
                    ) {
                int kernelAuthorId = Integer.parseInt(kernelFork.get("kernelAuthorId").toString());
                int forkId = Integer.parseInt(kernelFork.get("forkId").toString());
                updateRelation(kernelAuthorId, forkId, "kernelForkTime");
            }
        } else if (tableName.equals("organization")) {
            for (int organizationId = 1; organizationId < 498; organizationId++) {
                Integer[] members = relationGenDao.getOrganizationMember(organizationId);
                if (members.length < 2) {
                    continue;
                }
                for (int i = 0; i < members.length - 1; i++) {
                    for (int j = i + 1; j < members.length; j++) {
                        updateRelation(members[i], members[j], "organizationTime");
                    }
                }
            }
        } else if (tableName.equals("followRelation")) {
            List<Map<String, Object>> followList = relationGenDao.getRelationMap("competitorId", "followerId", "followrelation");
            System.out.println(followList.size());
            for (Map<String, Object> follow : followList
                    ) {
                int competitorId = Integer.parseInt(follow.get("competitorId").toString());
                int followerId = Integer.parseInt(follow.get("followerId").toString());
                updateRelation(competitorId, followerId, "followTime");
            }
        }
    }


    public void testes() {

    }


    //完善leaderboard表时，为了获得人名与id之间的对应关系
    public void mapToId(){

        List<Map<String, Object>> competitor_2 = relationGenDao.getRelationMap("competitorId", "competitorName", "competitor_2");
        List<Map<String, Object>> competitor = relationGenDao.getRelationMap("competitorId", "competitorName", "competitor");
        List<Map<String, Object>> discussion = relationGenDao.getRelationMap("discussionAuthorId", "discussionAuthorName", "discussion");
        List<Map<String, Object>> kernel = relationGenDao.getRelationMap("kernelAuthorId", "kernelAuthorName", "kernel");
        List<Map<String, Object>> discussionComment = relationGenDao.getRelationMap("commenterId", "commenterName", "discussioncomment");
        List<Map<String, Object>> kernelComment = relationGenDao.getRelationMap("commenterId", "commenterName", "kernelcomment");
        List<Map<String, Object>> discussionReply = relationGenDao.getRelationMap("replierId", "replierName", "discussionreply");
        List<Map<String, Object>> kernelReply = relationGenDao.getRelationMap("replierId", "replierName", "kernelreply");
        List<Map<String, Object>> discussionVote = relationGenDao.getRelationMap("discussionAuthorId", "voterId", "discussionvote");
        List<Map<String, Object>> kernelVote = relationGenDao.getRelationMap("kernelAuthorId", "voterId", "kernelvote");
        List<Map<String, Object>> kernelFork = relationGenDao.getRelationMap("kernelAuthorId", "forkId", "kernelfork");

        Map<Integer, String> MAP = new HashMap<Integer, String>();
        merge(MAP, competitor_2, "competitorId", "competitorName");
        merge(MAP, competitor, "competitorId", "competitorName");
        merge(MAP, discussion, "discussionAuthorId", "discussionAuthorName");
        merge(MAP, kernel, "kernelAuthorId", "kernelAuthorName");
        merge(MAP, discussionComment, "commenterId", "commenterName");
        merge(MAP, kernelComment, "commenterId", "commenterName");
        merge(MAP, discussionReply, "replierId", "replierName");
        merge(MAP, kernelReply, "replierId", "replierName");
        merge(MAP, discussionVote, "discussionAuthorId", "voterId");
        merge(MAP, kernelVote, "kernelAuthorId", "voterId");
        merge(MAP, kernelFork, "kernelAuthorId", "forkId");
        relationGenDao.insertMapIdToName(MAP);
    }

    public void merge(Map<Integer, String> MAP, List<Map<String, Object>> map, String column1, String column2) {
        for (Map<String, Object> map1 : map
                ) {
            int id = Integer.parseInt(map1.get(column1).toString());
            if (MAP.containsKey(id)) {
                continue;
            }
            MAP.put(id, (String) map1.get(column2));
        }
    }


}
