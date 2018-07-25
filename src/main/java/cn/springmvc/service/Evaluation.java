/*
package cn.springmvc.service;

import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.EvaluateResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class Evaluation {

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private EvaluateResultDao evaluateResultDao;

    public void Evaluate(){
        //初始化测试数据
        Map<Integer, Map<Integer, ArrayList<ArrayList<Integer>>>> TestData
                = new HashMap<Integer, Map<Integer, ArrayList<ArrayList<Integer>>>>();
        GetTestData(TestData);
        //根据competitorId遍历测试数据
        for(Entry<Integer, Map<Integer, ArrayList<ArrayList<Integer>>>> competitor_size : TestData.entrySet()) {
            int competitorId = competitor_size.getKey();
            //在每个competitorId下遍历每其测试数据中的size
            for(Entry<Integer, ArrayList<ArrayList<Integer>>> teams : competitor_size.getValue().entrySet()){
                //获得对应competitor，size大小的推荐结果
                int size = teams.getKey();
                Map<Integer, Boolean> RecommendData = new HashMap<Integer, Boolean>();
                GetRecommendData(competitorId, size, RecommendData);
                //计算查全率
                double coverage = -1;
                for(ArrayList<Integer> team : teams.getValue()){
                    double count = 0.0;
                    for(Integer member : team)
                        if(RecommendData.get(member) != null)
                            count += 1.0;
                    count /= (team.size() - 1);//去掉自己
                    if(count > coverage)
                        coverage = count;
                }
                //计算查准率
                double accuracy = coverage > 0.1 ? 1 : 0;
                evaluateResultDao.insertEvaResult(competitorId, size, accuracy, coverage);
            }
        }
    }

    //初始化测试数据集
    private void GetTestData(Map<Integer, Map<Integer, ArrayList<ArrayList<Integer>>>> TestData){
        List<Map<String, Object>> TeamData = competitorRecordDao.getTeamIDs();
        for(Map<String, Object> team : TeamData){
            List<Integer> teamMembers = competitorRecordDao.getTeamMembers(
                    team.get("competitionId"), team.get("ranking"));
            int size = Integer.parseInt(team.get("size").toString());
            ArrayList<Integer> currentTeam = new ArrayList<Integer>();
            for(Integer member : teamMembers)
                currentTeam.add(member);
            for(Integer member : teamMembers){
                Map<Integer, ArrayList<ArrayList<Integer>>> memberHistory = TestData.get(member);
                if(memberHistory == null)
                    memberHistory = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
                ArrayList<ArrayList<Integer>> teams = memberHistory.get(size);
                if(teams == null)
                    teams = new ArrayList<ArrayList<Integer>>();
                teams.add(currentTeam);
                memberHistory.put(size, teams);
                TestData.put(member, memberHistory);
            }
        }
    }

    //根据competitorId和size获取
    private boolean GetRecommendData(int competitorId, int size, Map<Integer, Boolean> Check){
        List<Map<String, Integer>> RecData = null;
        switch(size){
            case 2:RecData = evaluateResultDao.getResult2ById(competitorId);break;
            case 3:RecData = evaluateResultDao.getResult3ById(competitorId);break;
            case 4:break;
            case 5:break;
            default:break;
        }
        if(RecData == null)
            return false;
        ArrayList<String> label = new ArrayList<String>();
        for(int i = 1; i <= size; i++)
            label.add(String.format("member%d", i));
        for(Map<String, Integer> RecD : RecData){
            for(int i = 0; i < label.size(); i++) {
                Integer cId = RecD.get(label.get(i));
                if(cId == null || cId == competitorId || Check.get(cId) != null)continue;
                Check.put(cId, true);
            }
        }
        return true;
    }

}
*/
