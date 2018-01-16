package cn.springmvc.service;

import cn.springmvc.dao.RecommendResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void experiment_2() {
        List<Integer> testCompetitors = testDataGet.getCompetitorToTest();
        boolean flag = false;
        for (int i = 0; i < testCompetitors.size(); i++) {
            if (flag == true) {
                System.out.println(testCompetitors.get(i));
                compare(testCompetitors.get(i), 2, 0.7);
            }
            if (testCompetitors.get(i) == 1124185) {
                flag = true;
            }
        }
    }

    public void experiment_3() {
        List<Integer> testCompetitors = testDataGet.getCompetitorToTest();
        boolean flag = false;
        for (int i = 0; i < testCompetitors.size(); i++) {
            if (testCompetitors.get(i) == 1124185) {
                flag = true;
            }
            if (flag == true) {
                System.out.println(testCompetitors.get(i));
                compare(testCompetitors.get(i), 3, 0.7);
            }
        }
    }

    public void experiment_4() {
        List<Integer> testCompetitors = testDataGet.getCompetitorToTest();
        for (int i = 0; i < testCompetitors.size(); i++) {
            System.out.println(testCompetitors.get(i));
            compare(testCompetitors.get(i), 4, 0.7);
        }
    }

    public void compare(int competitorId, int teamSize, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recomend1.getRecommendSet(competitorId, 0.4);

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
        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, 0, 0, "recommendResult_2_test");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, 0, 0, "recommendResult_3_test");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], cost, 0, 0, "recommendResult_4_test");
        }
    }


    public void bestRole() {

    }

}
