package cn.springmvc.service;

import cn.springmvc.dao.*;
import cn.springmvc.model.Discussion;
import cn.springmvc.model.WhoWantCompete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/12/23.
 */
@Service
public class TeamFailed {
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private WhoWantCompeteDao whoWantToCompeteDao;
    @Autowired
    private CompetitionDao competitionDao;
    @Autowired
    private DiscussionCommentDao discussionCommentDao;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    @Autowired
    private CompetitorRecordDao competitorRecordDao;
    @Autowired
    private DiscussionForTeamDao discussionForTeamDao;

    /*
    * 找出目的为请求组队teamup的discussion
    * */
    public void getDiscussionForTeamUp(){
        List<Map<String ,Object>> allDiscussionName = discussionDao.getDiscussionName();
        for (Map<String,Object> discussion:allDiscussionName
             ) {
            String discussionName = (String) discussion.get("discussionName");
            int discussionId = Integer.parseInt(discussion.get("discussionId").toString());
            if(discussionName.toLowerCase().contains("team")) {
                System.out.println(discussionId + ":" + discussionName);
                Discussion discussionForTeam = discussionDao.getDiscussionById(discussionId);
                discussionDao.insertToDiscussionForTeam(discussionForTeam);
            }
        }
    }

    /*
    * 在目的为请求组队的discussion中，找出存在组队意愿的用户
    * */
    public void getWhoWantToCompete(){
        List<Discussion> discussionForTeam = discussionDao.getDiscussionForTeam();
        for (Discussion record:discussionForTeam) {
            if(record.getDiscussionId() == 13708)
            {
                continue;
            }
            System.out.println("discussionId:" + record.getDiscussionId());

            String competitionName = record.getSourceName();
            Integer competitionId = competitionDao.getCompetitionIdByName(competitionName);
            if(competitionId == null ){
                continue;
            }
            int discussionId = record.getDiscussionId();
            int sponsor = record.getDiscussionAuthorId();

            if (whoWantToCompeteDao.existOrNot(sponsor,competitionId) != 1) {
                WhoWantCompete who = new WhoWantCompete();
                who.setCompetitionId(competitionId);
                who.setCompetitorId(sponsor);
                who.setDiscussionId(discussionId);
                who.setTeamOrNot(-1);
                who.setSponsor(1);
                whoWantToCompeteDao.insert(who);
            }

            List<Integer> commenters = discussionCommentDao.getCommentersByDiscussionId(discussionId);
            if(commenters.size() > 0) {
                for (Integer commenter: commenters) {
                    if(whoWantToCompeteDao.existOrNot(commenter,competitionId) != 1){
                        WhoWantCompete who = new WhoWantCompete();
                        who.setCompetitionId(competitionId);
                        who.setCompetitorId(commenter);
                        who.setDiscussionId(discussionId);
                        who.setSponsor(0);
                        who.setTeamOrNot(-1);
                        whoWantToCompeteDao.insert(who);
                    }
                }
            }

            List<Integer> repliers = discussionReplyDao.getRepliersByDissionId(discussionId);
            if(repliers.size() > 0){
                for (Integer replier:repliers) {
                    if(whoWantToCompeteDao.existOrNot(replier,competitionId) != 1){
                        WhoWantCompete who = new WhoWantCompete();
                        who.setCompetitionId(competitionId);
                        who.setCompetitorId(replier);
                        who.setDiscussionId(discussionId);
                        who.setSponsor(0);
                        who.setTeamOrNot(-1);
                        whoWantToCompeteDao.insert(who);
                    }
                }
            }
        }
    }
/*
* 存在组队意愿的参赛者是否真正参赛
* */
    public void partInOrNot(){
        List<WhoWantCompete> allWhos = whoWantToCompeteDao.getAllWhoWantCompete();

        for (WhoWantCompete who:allWhos) {
            int competitor = who.getCompetitorId();
            int competition = who.getCompetitionId();
            int exist = competitorRecordDao.existOrNot(competition,competitor);
            if (exist == 1) {
                int soloOrNot = competitorRecordDao.getSoloOrTeam(competition,competitor);
                whoWantToCompeteDao.update("inOrNot",1,competition,competitor);
                whoWantToCompeteDao.update("teamOrNot",soloOrNot,competition,competitor);
            }
        }
    }

    public void discussionMotivationAnalysis(){
        List<String> motivations = discussionForTeamDao.getMotivations();
        System.out.println(motivations.size());
        int totalCount = 0;
        int growCount = 0;
        int highLevelCount = 0;
        int levelCount = 0;
        for (String motivation:motivations) {
            if (motivation == null){
                continue;
            }
            totalCount ++;
            String[] strs = motivation.split(";");
            HashSet<String> motivationSet = new HashSet<String>();
            for (String str :strs) {
                motivationSet.add(str);
            }
            if (motivationSet.contains("3")) {
                growCount++;
            }
            if (motivationSet.contains("2")) {
                highLevelCount ++;
                if (motivationSet.contains("5")){
                    levelCount ++;
                }
            }
        }
        System.out.println("totalCount:" + "\t" + totalCount );
        System.out.println("growCount:" + "\t" + growCount );
        System.out.println("highLevelCount:" + "\t" + highLevelCount );
        System.out.println("levelCount:" + "\t" + levelCount );
    }

}
