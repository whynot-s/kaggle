package cn.springmvc.service;

import cn.springmvc.dao.*;
import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorAbility;
import cn.springmvc.model.TeamRecordAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YLT on 2018/1/12.
 */
@Service
public class DataPreHandle {
    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    @Autowired
    private CompetitionLeaderboardDao competitionLeaderboardDao;


    /*
    * competitorRelation表中删去未参与过竞赛的人，即无能力值的用户
    * */
    public void relationTableDelete(){
        List<Integer> allCompetitorId1 = relationGenDao.getCompetitor1List();
        List<Integer> competitorWithAbility = competitorAbilityDao.getCompetitorIds();
        for (int competitorId1:allCompetitorId1) {
            if (competitorWithAbility.contains(competitorId1)){
                System.out.println(competitorId1);
                continue;
            }else {
                relationGenDao.deleteRelation(competitorId1);
                System.out.println(competitorId1 + "deleted");
            }
        }
    }

    public void getNumbet(){
        List<String> records = competitionLeaderboardDao.getAllLeaderBoardRecord();
        int count = 0;
        for (String record:records){
            if (record.split("&&").length > 1){
                count ++;
            }
        }
        System.out.println(count);
    }
}
