package cn.springmvc.service;

import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YLT on 2018/3/22.
 */
@Component
public class TeamSuccessRate {
    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;
    /*
  * 加载团队成员的能力值
  * */
    public double[] loadTeamAbility(int[] team, int teamSize) {
        double memberAbility[] = new double[teamSize];
        for (int i = 0; i < teamSize; i++) {
            memberAbility[i] = DataPreLoad.competitorAbilityMap.get(team[i]);
        }
        return memberAbility;
    }

    /*
  * 组队成功率计算公式
  * */
    public double getTeamSuccessRate(int[] team, int teamSize, double p, double q,double r, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);

        double cost = teamCost.getTeamCost(team, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(memberAbility, teamSize);
        double grow = abilityGrow.getGrowSpace(memberAbility, teamSize);

        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;
        return resultRate;
    }

    /*
  * 组队成功率计算公式
  * */
    //x
    public double getTeamSuccessRateMutual(int[] team, int teamSize, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);
        double successRate = 0;

        for (int i = 0; i < teamSize; i ++){
            double cost = teamCost.getTeamCost2(team,teamSize,costPercent,i);
            double diff = abilityDiff.getAbilityDiff2(memberAbility,teamSize,i);
            double grow = abilityGrow.getGrowSpace2(memberAbility,teamSize,i);

            CoffiTable coffi = DataPreLoad.coffiMap.get(team[i]);
            successRate += cost * coffi.getCostPercent() + diff * coffi.getDiffPercent() + grow *  coffi.getGrowPercent();
        }

        return successRate;
    }

    //对
    /*
  * 组队成功率计算公式
  * */
    public double getTeamSuccessRateMutual2(int[] team, int teamSize, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);
        double successRate = 0;

        double [] memberCost = teamCost.getTeamCost3(team,teamSize,costPercent);
        double [] memberDiff = abilityDiff.getAbilityDiff6Array(memberAbility,teamSize);
        double [] memberGrow = abilityGrow.getGrowSpace3(memberAbility,teamSize);

        for (int i = 0; i < teamSize; i ++){
            CoffiTable coffi = DataPreLoad.coffiMap.get(team[i]);
            double tempSuccessRate = (1 - memberCost[i])*3.2 * coffi.getCostPercent() + (1-memberDiff[i]) * coffi.getDiffPercent() + 3.2*memberGrow[i] * coffi.getGrowPercent();
            successRate += tempSuccessRate;
        }
        return successRate;
    }

    /*
  * 组队成功率计算公式
  * */
    public double getTeamSuccessRateMutual2Intimacy(int[] team, int teamSize, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);
        double successRate = 0;

        double [] memberCost = teamCost.getTeamCost3(team,teamSize,costPercent);
        double [] memberDiff = abilityDiff.getAbilityDiff6Array(memberAbility,teamSize);
        double [] memberGrow = abilityGrow.getGrowSpace3(memberAbility,teamSize);


        for (int i = 0; i < teamSize; i ++){
            CoffiTable coffi = DataPreLoad.coffiMap.get(team[i]);
            double tempSuccessRate = 3.2*(1 - memberCost[i]) * coffi.getCostPercent() + (1-memberDiff[i]) * coffi.getDiffPercent() + 3.2*memberGrow[i] * coffi.getGrowPercent();
            successRate += tempSuccessRate;
        }
        return successRate;
    }

    //x
    /*
* 组队成功率计算公式
* 得到每个member的组队意愿，以便求得他们组队意愿的方差
* */
    public double[] getTeamSuccessRateMutualEachPerson(int[] team, int teamSize, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);
        double[] willingness = new double[teamSize];

        double [] memberCost = teamCost.getTeamCost3(team,teamSize,costPercent);
        double [] memberDiff = abilityDiff.getAbilityDiff6Array(memberAbility,teamSize);
        double [] memberGrow = abilityGrow.getGrowSpace3(memberAbility,teamSize);


        for (int i = 0; i < teamSize; i ++){
            CoffiTable coffi = DataPreLoad.coffiMap.get(team[i]);
            willingness[i] = 3.2*(1 - memberCost[i]) * coffi.getCostPercent() + (1-memberDiff[i]) * coffi.getDiffPercent() + 3.2*memberGrow[i] * coffi.getGrowPercent();
        }
        return willingness;
    }


    //x
    /*
* 组队成功率计算公式
* */
    public double getTeamSuccessRateMutual3(int[] team, int teamSize, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);
        double successRate = 1;

        double [] memberCost = teamCost.getTeamCost3(team,teamSize,costPercent);
        double [] memberDiff = abilityDiff.getAbilityDiff3(memberAbility,teamSize);
        double [] memberGrow = abilityGrow.getGrowSpace3(memberAbility,teamSize);


        for (int i = 0; i < teamSize; i ++){
            CoffiTable coffi = DataPreLoad.coffiMap.get(team[i]);
            double tempSuccessRate = (1 - memberCost[i]) * coffi.getCostPercent() + (1-memberDiff[i]) * coffi.getDiffPercent() + memberGrow[i] * coffi.getGrowPercent();
            successRate *= tempSuccessRate;
            /*if (tempSuccessRate < 0.45){
                 return -1;
            }*/
        }

        return Math.pow(successRate,(double)1/teamSize);
    }
}
