package cn.springmvc.service;

import cn.springmvc.dao.RelationGenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YLT on 2018/1/12.
 */
@Component
public class TeamCost {

    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private CompetitorIntimacy competitorIntimacy;

    /*
    * 得到团队成员的代价图
    * */
    public double[][] loadWeightGraph(int[] team, int teamSize,double costPercent){
        double[][] graphWeights = new double[teamSize][teamSize];
        for (int i = 0; i < teamSize; i++) {
            int competitorId1 = team[i];
            for (int j = 0; j < teamSize; j++) {
                if (i == j) {
                    //自己和自己的亲密度设置为0
                    graphWeights[i][j] = 0.0;
                } else {
                    int competitorId2 = team[j];
                    if(relationGenDao.recordExistOrNot(competitorId1,competitorId2) == 0){
                        graphWeights[i][j] = 1;
                    }else {
                        Double cost1 = relationGenDao.getCost(competitorId1,competitorId2,"cost1");
                        Double cost2 = relationGenDao.getCost(competitorId1,competitorId2,"cost2");
                        //团队沟通代价 越小越好
                        graphWeights[i][j] = costPercent * cost1 + (1 - costPercent) * cost2;
                    }
                }
            }
        }
        return graphWeights;
    }

    public double getTeamCost(int[] team,int teamSize, double costPercent){
        double[][] graphWeights = loadWeightGraph(team,teamSize,costPercent);
        double[][] teamIntimacy = new double[teamSize][teamSize];

        competitorIntimacy.calcTeamIntimacy(graphWeights,teamIntimacy);
        return competitorIntimacy.calcTeamTotalIntimacy(teamIntimacy);
    }
}
