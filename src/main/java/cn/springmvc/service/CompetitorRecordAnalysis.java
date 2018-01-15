package cn.springmvc.service;

import cn.springmvc.dao.CompetitionDao;
import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.CompetitorSoloAndTeamDao;
import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorRecord;
import cn.springmvc.model.CompetitorSoloAndTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2017/12/19.
 */
@Service
/*
* 生成每个参赛者的参赛记录
* 分析参赛者团队参赛和个人参赛的成绩情况
* */
public class CompetitorRecordAnalysis {

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Autowired
    private CompetitorSoloAndTeamDao  competitorSoloAndTeamDao;

    /*
    * 所有的参赛记录
    * 包括练习性质的竞赛
    * competitorRecord表中为所有竞赛的参赛记录
    * */
    public void getCompetitorRecord() {
        List<Integer> allCompetitionIds = competitionDao.getCompetitionIds();
        for (Integer competitionId : allCompetitionIds) {
            System.out.println("competition:" + competitionId);
            ArrayList<CompetitionLeaderboard> leaderboard = competitorRecordDao.getLeaderBoardByCompetitionId(competitionId);
            for (CompetitionLeaderboard it : leaderboard) {
                System.out.print("ranking:" + it.getRanking() +"\t");
                if(!it.getTeamMemberId().equals("")) {
                    String[] ids = it.getTeamMemberId().split("&");
                    String[] names = it.getTeamMember().split("&&");
                    int team = (names.length > 1 ? 1 : 0);
                    System.out.println(names.length + "\t" + ids.length);
                    for (String id : ids) {
                        CompetitorRecord record = new CompetitorRecord(competitionId, Integer.parseInt(id), it.getRanking(), team);
                        competitorRecordDao.insertCompetitorRecord(record);
                    }
                }
            }
        }
    }

    /*
    * 参与评分的参赛记录
    * 不包括练习性质的竞赛
    * 不记得在哪使用了
    * */
    public void getCompetitorRecordForScore() {
        List<Integer> allCompetitionIds = competitionDao.getCompetitionIds();
        for (Integer competitionId : allCompetitionIds) {
            System.out.println("competition:" + competitionId);
            Competition competition = competitionDao.getCompetitionById(competitionId);
            if (competition.getCompetitionType().equals("playground") || competition.getCompetitionType().equals("getting started") || !competition.getCompetitionStatus().equals("closed")){
                continue;
            }
            ArrayList<CompetitionLeaderboard> leaderboard = competitorRecordDao.getLeaderBoardByCompetitionId(competitionId);
            for (CompetitionLeaderboard it : leaderboard) {
                System.out.print("ranking:" + it.getRanking() +"\t");
                if(!it.getTeamMemberId().equals("")) {
                    String[] ids = it.getTeamMemberId().split("&");
                    String[] names = it.getTeamMember().split("&&");
                    int team = (names.length > 1 ? 1 : 0);
                    System.out.println(names.length + "\t" + ids.length);
                    for (String id : ids) {
                        CompetitorRecord record = new CompetitorRecord(competitionId, Integer.parseInt(id), it.getRanking(), team);
                        competitorRecordDao.insertCompetitorRecord(record);
                    }
                }
            }
        }
    }

    //参赛者参赛结果： 排行榜的前？？百分比
    public void getRankPercent(){
        List<HashMap<String,Object>> temp = competitionDao.getCompetitionLeaderboardLen();
        for (HashMap<String,Object> len: temp) {
            int competitionId = Integer.parseInt(len.get("competitionId").toString());
            int competitionLen = Integer.parseInt(len.get("leaderboardLength").toString());
            System.out.println(competitionId);
            List<CompetitorRecord> competitorRecordByCompetitionId = competitorRecordDao.getCompetitorRecordByCompetitionId(competitionId);
            for (CompetitorRecord record:competitorRecordByCompetitionId) {
                int curRank = record.getRanking();
                int curCompetitor = record.getCompetitorId();
                competitorRecordDao.updateRankPercent(competitionId,curCompetitor,curRank/(double)competitionLen);
            }
        }
    }

    public void competitorAnalysis(){
        List<Integer> allCompetitorIds = competitorRecordDao.getCompetitorIds();
        for (Integer competitorId: allCompetitorIds) {
            System.out.println("competitorId:" + competitorId);
            List<CompetitorRecord> records = competitorRecordDao.getCompetitorRecordByCompetitorId(competitorId);
            int soloNum = 0;
            int teamNum = 0;
            int soloRank = 0;
            int teamRank = 0;
            double soloPercent = 0.0;
            double teamPercent = 0.0;
            for (CompetitorRecord record:records) {
                if (record.getTeamOrNot() == 1){
                    teamNum ++;
                    teamRank += record.getRanking();
                    teamPercent += record.getRankPercent();
                }else{
                    soloNum ++;
                    soloRank += record.getRanking();
                    soloPercent += record.getRankPercent();
                }
            }
            if (soloNum != 0) {
                soloRank = soloRank/soloNum;
                soloPercent = soloPercent/soloNum;
            }
            if (teamNum != 0){
                teamRank = teamRank/teamNum;
                teamPercent = teamPercent/teamNum;
            }
            int soloWin = 0;
            int teamWin = 0;
            records = competitorRecordDao.getLimitCompetitorRecordByCompetitorId(competitorId);
            System.out.println("size:" + records.size());
            if (records.size() == 10) {
                for (CompetitorRecord record : records) {
                    if (record.getTeamOrNot() == 1) {
                        teamWin++;
                    } else {
                        soloWin++;
                    }
                }
            }else
            {
                soloWin = -1;
                teamWin = -1;
            }
            competitorSoloAndTeamDao.insertCompetitorSoloAndTeamDao(new CompetitorSoloAndTeam(competitorId,teamRank,soloRank,teamPercent,soloPercent,teamNum,soloNum,teamWin,soloWin));
        }
    }


}
