package cn.springmvc.service;
import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.dao.RecommendResultDao;
import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by YLT on 2018/1/15.
 */
@Component
public class IntimacyContrastExperiment {
    @Autowired
    private Recomend1 recomend1;
    @Autowired
    private TeamCost teamCost;
    @Autowired
    private RecommendResultDao recommendResultDao;
    @Autowired
    private TestDataGet testDataGet;
    @Autowired
    private AbilityGrow abilityGrow;
    @Autowired
    private AbilityDiff abilityDiff;
    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;
    @Autowired
    private CoffiTableDao coffiTableDao;
    @Autowired
    private TeamSuccessRate teamSuccessRate;
    @Autowired
    private RecommendSetGet recommendSetGet;

    public void experiment_2() {
        //DataPreLoad.dataLoad();
        List<Integer> testCompetitors = testDataGet.getTestSet2();
        for (int i = 0; i < testCompetitors.size(); i++) {
            System.out.println(testCompetitors.get(i));
           // compare(testCompetitors.get(i),4, 0.7);
        }
    }

    public void experimentWithCoffi(){
       // DataPreLoad.dataLoad();
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("");
        for (CoffiTable testCompetitor:allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            compare(member1,4,0.7,costCoffi,diffCoffi,growCoffi);
        }
    }

    public void compare(int competitorId, int teamSize, double costPercent,double costCoffi, double diffCoffi, double growCoffi) {
        /*Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        List<Integer> allCompetitors  = new ArrayList<Integer>();
        for (Integer competitor: allCompetitorsSet){
            allCompetitors.add(competitor);
        }*/
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        //List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = 1 - teamCost.getTeamCost(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }
        double[] memberAbility = teamSuccessRate.loadTeamAbility(teamMember, teamSize);
        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(memberAbility, teamSize);
        double grow = abilityGrow.getGrowSpace(memberAbility, teamSize);
        double resultRate = costCoffi * (1 - cost) + diffCoffi * (1 - diff) + growCoffi * grow;
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, diff, grow, resultRate,"recommendResult_2_test");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, diff, grow,resultRate, "recommendResult_3_test");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], cost, diff, grow,resultRate, "recommendResult_4_test");
        }
    }


    public void compareMutual(int competitorId, int teamSize, double costPercent) {
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = 1 - teamCost.getTeamCost(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }
        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2Intimacy(teamMember,teamSize,costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate,"recommendResult_2_test");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0,resultRate, "recommendResult_3_test");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0,resultRate, "recommendResult_4_test");
        }
    }


    //采用几何平均法
    public void compareMutual3(int competitorId, int teamSize, double costPercent) {
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = 1 - teamCost.getTeamCost(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }
        double resultRate = teamSuccessRate.getTeamSuccessRateMutual3(teamMember,teamSize,costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate,"recommendResult_2_test");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0,resultRate, "recommendResult_3_test");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0,resultRate, "recommendResult_4_test");
        }
    }
}
