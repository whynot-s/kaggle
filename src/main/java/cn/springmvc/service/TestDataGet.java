package cn.springmvc.service;

import cn.springmvc.dao.*;
import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorRecord;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2018/1/13.
 */
@Service
public class TestDataGet {
    @Autowired
    private CompetitorRecordDao competitorRecordDao;
    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;
    @Autowired
    private CompetitionDao competitionDao;
    @Autowired
    private RelationGenDao relationGenDao;
    @Autowired
    private CompetitionLeaderboardDao competitionLeaderboardDao;


    /*
    * 获取测试数据中参赛者的参赛记录
    * */
    public void getTeamToTestNumber(){
        int [] testCompetitions = new int[]{6538,6539,6565, 6644,6649, 6768,6775,6841,7042,7043,7082,7115,7162,7163,7277,7299,7380, 7391, 7559,7634, 8011,8076, 8078};
        for (int competition:testCompetitions) {
            ArrayList<CompetitionLeaderboard> leaderboard = competitorRecordDao.getLeaderBoardByCompetitionId(competition);
            for (CompetitionLeaderboard it : leaderboard) {
                if(!it.getTeamMemberId().equals("")) {
                    String[] ids = it.getTeamMemberId().split("&");
                    String[] names = it.getTeamMember().split("&&");
                    int team = (names.length > 1 ? 1 : 0);
                    System.out.println(names.length + "\t" + ids.length);
                    for (String id : ids) {
                        CompetitorRecord record = new CompetitorRecord(competition, Integer.parseInt(id), it.getRanking(), team);
                        competitorRecordDao.insertCompetitorRecord(record);
                    }
                }
            }
        }
    }

    public List<Integer> getCompetitorToTest() {
        List<Integer> allTestCompetitors = competitorRecordDao.getTestCompetitors();
        List<Integer> allCompetitors = competitorAbilityDao.getCompetitorIds();
        for (int i = 0; i < allTestCompetitors.size();){
            if (allCompetitors.contains(allTestCompetitors.get(i))){
                i++;
            }else{
                allTestCompetitors.remove(i);
            }
        }
        for (int i = 0; i < allTestCompetitors.size(); ) {
            if (competitorRecordDao.getRecordTimeByCompetitorIdTest(allTestCompetitors.get(i)) < 3) {
                allTestCompetitors.remove(i);
            } else {
                i++;
            }
        }

       // System.out.println(allTestCompetitors.size());
        return allTestCompetitors;
    }


    /*
    * 取消该竞赛对用户能力的影响
    * */
    public void removeImpactInAbility(int competitionId) {
        //获取一个竞赛的排名记录
        ArrayList<CompetitionLeaderboard> leaderboards = competitorAbilityDao.getLeaderBoardByCompetitionId(competitionId);
        Competition competition = competitionDao.getCompetitionById(competitionId);

        String tag = "`" + competition.getTag4() + "`";

        //截止到2017.7.30,已经测试正确
        Date closeDate = competition.getCloseTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String stringToDate = "7-30-2017";
        int duration = 0;
        try {
            Date nowDate = dateFormat.parse(stringToDate);
            duration = (int) ((nowDate.getTime() - closeDate.getTime()) / 1000 / 60 / 60 / 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int Nteams = competition.getTeamsNum();

        for (CompetitionLeaderboard leaderboardEach : leaderboards) {
            if (leaderboardEach.getTeamMemberId().equals("")) {
                continue;
            }
            String[] members = leaderboardEach.getTeamMemberId().split("&");
            String[] membersName = leaderboardEach.getTeamMember().split("&&");
            int memberNumber = membersName.length;
            int rank = leaderboardEach.getRanking();
            System.out.print(rank);
            //得到参与这个竞赛获得名次后，组队中每一个成员在每一个tag下提高的分数
            double score = (100000.0 / Math.sqrt(memberNumber)) * Math.pow(rank, -0.75) * Math.log10(1 + Math.log10(Nteams)) * Math.pow(Math.E, -duration / 500.0);

            for (String member : members) {
                int memberId = Integer.parseInt(member);
                deleteCompetitorAbilityScore(memberId, tag, score);
                deleteCompetitorAbilityScore(memberId, "totalScore", score);
            }
        }
        System.out.println(competition.getCompetitionId() + "end");
    }

    public void deleteCompetitorAbilityScore(int competitorId, String tagName, double score) {
        Double preScore = competitorAbilityDao.getCompetitorAbility(competitorId, tagName);
        competitorAbilityDao.updateAbility(competitorId, tagName, preScore - score);
    }

    public void deleteCollaborationRelationBycompetitionId(int competitionId) {
        List<String> collaborationRecord = competitionLeaderboardDao.getLeaderBoardByCompetitionId(competitionId);
        for (String record : collaborationRecord) {
            //这里需要注意
            String[] members = record.split("&");
            if (members.length > 1) {
                for (int i = 0; i < members.length - 1; i++) {
                    for (int j = i + 1; j < members.length; j++) {
                        deleteCollaborationRelation(Integer.parseInt(members[i]), Integer.parseInt(members[j]), "collaborationTime");
                        calCollCost(Integer.parseInt(members[i]), Integer.parseInt(members[j]));
                        calCollCost( Integer.parseInt(members[j]),Integer.parseInt(members[i]));
                    }
                }
            }
        }
    }

    public void deleteCollaborationRelation(int competitorId1, int competitorId2, String relationName) {
        if (competitorId1 != competitorId2) {
            if (relationGenDao.recordExistOrNot(competitorId1, competitorId2) == 1) {
                Integer Time = relationGenDao.getRelationTime(competitorId1, competitorId2, relationName);

                relationGenDao.updateRelationTime(competitorId1, competitorId2, Time - 1, relationName);
                relationGenDao.updateRelationTime(competitorId2, competitorId1, Time - 1, relationName);
            }
        }
    }

    public void calCollCost(int competitorId1, int competitorId2) {
        int recordTime1 = competitorRecordDao.getRecordTimeByCompetitorId(competitorId1);
        double cost1;
        Integer collaborationTime = relationGenDao.getRelationTime(competitorId1, competitorId2, "collaboration");
        if (collaborationTime == 0 || collaborationTime == null) {
            cost1 = 1;
        } else {
            int recordTime2 = competitorRecordDao.getRecordTimeByCompetitorId(competitorId2);
            cost1 = 1 - ((double) collaborationTime) / (recordTime1 + recordTime2 - collaborationTime);
        }
        relationGenDao.updateCost(competitorId1, competitorId2, "cost1", cost1);
    }
}
