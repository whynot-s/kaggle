package cn.springmvc.service;

import cn.springmvc.dao.RelationGenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YLT on 2018/1/12.
 */
@Component
public class TeamCost {

    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private CompetitorIntimacy competitorIntimacy;

    @Autowired
    private Recomend1 recomend1;

    /*
    输入参数：团队成员id，团队人数，cost系数占比，
    cost表示组队沟通代价的系数，1-cost表示社交沟通代价的系数
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
                    if(! DataPreLoad.relationMap.containsKey(competitorId1) || ! DataPreLoad.relationMap.get(competitorId1).containsKey(competitorId2)){
                        graphWeights[i][j] = 1;
                    }else {
                        String cost = DataPreLoad.relationMap.get(competitorId1).get(competitorId2);
                        String []costString = cost.split(";");
                        Double cost1 = Double.valueOf(costString[0]);
                        Double cost2 = Double.valueOf(costString[1]);
                        //团队沟通代价 越小越好
                        graphWeights[i][j] = costPercent * cost1 + (1 - costPercent) * cost2;
                    }
                }
            }
        }
        return graphWeights;
    }

    // 两人团队运行10s，可接受（对两人组队场景需运行3ms）
    public double[][] loadWeightGraph2(int[] team, int teamSize,double costPercent){
        double[][] graphWeights = new double[teamSize][teamSize];
        for (int i = 0; i < teamSize; i++) {
            int competitorId1 = team[i];
            for (int j = i; j < teamSize; j++) {
                if (i == j) {
                    //自己和自己的亲密度设置为0
                    graphWeights[i][j] = 0.0;
                } else {
                    int competitorId2 = team[j];
                    Map<Integer,String> map1 = DataPreLoad.relationMap.get(competitorId1);
                    if(map1==null){
                        graphWeights[i][j] = 1;
                    }
                    else{
                        String cost = map1.get(competitorId2);
                        if(cost==null)
                            graphWeights[i][j] = 1;
                        else{
                            String []costString = cost.split(";");
                            double cost1 = Double.parseDouble(costString[0]);
                            double cost2 = Double.parseDouble(costString[1]);
                                    //团队沟通代价 越小越好
                            graphWeights[i][j] = costPercent * cost1 + (1 - costPercent) * cost2;
                        }
                    }

                }
            }
        }
        for(int i = 0; i < teamSize; i++){
            for (int j = 0; j < i; j++) {
                graphWeights[i][j] = graphWeights[j][i];
            }
        }
        return graphWeights;
    }

    /*计算某团队的沟通代价，二人组队场景运行21ms
    * */
    public double getTeamCost(int[] team, int teamSize, double costPercent) {
        double[][] graphWeights = loadWeightGraph(team, teamSize, costPercent); //二人组队场景运行3ms，与calcTeamIntimacy2同时则要21ms
        double[][] teamIntimacy = new double[teamSize][teamSize];
        competitorIntimacy.calcTeamIntimacy(graphWeights, teamIntimacy); //5ms，与loadWeightGraph2同时运行则要21ms
        return competitorIntimacy.calcTeamTotalIntimacy(teamIntimacy);
    }

    public double getTeamCost2(int[] team, int teamSize, double costPercent, int index) {
        double[][] graphWeights = loadWeightGraph(team, teamSize, costPercent); //二人组队场景运行3ms，与calcTeamIntimacy2同时则要21ms
        double[][] teamIntimacy = new double[teamSize][teamSize];
        competitorIntimacy.calcTeamIntimacy(graphWeights, teamIntimacy); //5ms，与loadWeightGraph2同时运行则要21ms
        return competitorIntimacy.calcMemberTotalIntimacy(teamIntimacy,index);
    }

    public double[] getTeamCost3(int[] team, int teamSize, double costPercent) {
        double[][] graphWeights = loadWeightGraph(team, teamSize, costPercent); //二人组队场景运行3ms，与calcTeamIntimacy2同时则要21ms
        double[][] teamIntimacy = new double[teamSize][teamSize];
        competitorIntimacy.calcTeamIntimacy(graphWeights, teamIntimacy); //5ms，与loadWeightGraph2同时运行则要21ms
        return competitorIntimacy.calcMemberTotalIntimacy2(teamIntimacy);

    }
}
