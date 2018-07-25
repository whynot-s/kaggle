package cn.springmvc.service;

import cn.springmvc.dao.*;
import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YLT on 2018/1/12.
 */
@Service
public class Recomend1 {
    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private TestDataGet testDataGet;

    @Autowired
    private RecommendResultDao recommendResultDao;

    @Autowired
    private CoffiTableDao coffiTableDao;

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private RecommendSetGet recommendSetGet;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    /*
    *推荐流程
    * 被推荐者、团队大小、团队沟通代价系数、团队能力差异系数、cost中协作关系系数
    */
    public int[] recommendForCompetitor(int competitorId, int teamSize, double Diff, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        //List<Integer> allCompetitors = recommendSetGet.getRecommendSet1(competitorId, Diff);
        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, i + 1, p, q, r, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }

        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, diff, grow, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, diff, grow, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], cost, diff, grow, resultRate, "recommendResult_4");
        }
        return teamMember;
    }


    /*
    *推荐流程
    * 被推荐者、团队大小、团队沟通代价系数、团队能力差异系数、cost中协作关系系数
    * 推荐限制：不能推荐cost = 1的团队，因为团队cost = 1，团队成员间互不相识，无法验证准确性
    */
    public int[] recommendForCompetitorWithCloseness(int competitorId, int teamSize, double Diff, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        //List<Integer> allCompetitors = recommendSetGet.getRecommendSet0(competitorId, Diff,"");
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 1; i < teamSize; i++) {
            boolean flag = false;
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, i + 1, p, q, r, costPercent);
                if (tempSuccessRate > successRate) {
                    for (int j = 0; j < i; j++) {
                        if (DataPreLoad.relationMap.get(teamMember[j]).containsKey(competitor)) {
                            successRate = tempSuccessRate;
                            toChose = competitor;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if (flag == false) {
                System.out.println("recommend failed:" + competitorId);
                return null;
            } else {
                teamMember[i] = toChose;
                allCompetitors.remove(allCompetitors.indexOf(toChose));
            }
        }

        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, diff, grow, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, diff, grow, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], cost, diff, grow, resultRate, "recommendResult_4");
        }
        return teamMember;
    }


    /*
    * 推荐流程:考虑双方的组队意愿
    * 被推荐者、团队大小、团队沟通代价系数、团队能力差异系数、cost中协作关系系数
    * 推荐限制：不能推荐cost = 1的团队，因为团队cost = 1，团队成员间互不相识，无法验证准确性
    * 未改变建模方式；考虑双方意愿
    */
    public int[] recommendForCompetitorWithClosenessMutual(int competitorId, int teamSize, double Diff, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 1; i < teamSize; i++) {
            boolean flag = false;
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double p1 = 0, q1 = 0, r1 = 0;
                for (int k = 0; k <= i; k++) {
                    CoffiTable coffiTable = DataPreLoad.coffiMap.get(teamMember[k]);
                    p1 = p1 + coffiTable.getCostPercent();
                    q1 = q1 + coffiTable.getDiffPercent();
                    r1 = r1 + coffiTable.getGrowPercent();
                }
                p1 = p1 * 4.1;
                q1 = q1 * 1.1;
                r1 = r1 * 12.1;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, i + 1, p1, q1, r1, costPercent);
                if (tempSuccessRate > successRate) {
                    for (int j = 0; j < i; j++) {
                        if (DataPreLoad.relationMap.get(teamMember[j]).containsKey(competitor)) {
                            successRate = tempSuccessRate;
                            toChose = competitor;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if (flag == false) {
                System.out.println("recommend failed:" + competitorId);
                return null;
            } else {
                teamMember[i] = toChose;
                allCompetitors.remove(allCompetitors.indexOf(toChose));
            }
        }

        for (int k = 0; k < teamSize; k++) {
            CoffiTable coffiTable = DataPreLoad.coffiMap.get(teamMember[k]);
            p = p + coffiTable.getCostPercent();
            q = q + coffiTable.getDiffPercent();
            r = r + coffiTable.getGrowPercent();
        }
        p = p * 4.1;
        q = q * 1.1;
        r = r * 12.1;
        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(teamMember, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, diff, grow, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, diff, grow, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], cost, diff, grow, resultRate, "recommendResult_4");
        }
        return teamMember;
    }

    /*改变建模方式；考虑双方意愿
    不推荐cost=1的团队
    * */
    public int[] recommendForCompetitorMutual(int competitorId, int teamSize, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet4();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 1; i < teamSize; i++) {
            boolean flag = false;
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    for (int j = 0; j < i; j++) {
                        if (DataPreLoad.relationMap.get(teamMember[j]).containsKey(competitor)) {
                            successRate = tempSuccessRate;
                            toChose = competitor;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if (flag == false) {
                System.out.println("recommend failed:" + competitorId);
                return null;
            } else {
                teamMember[i] = toChose;
                allCompetitors.remove(allCompetitors.indexOf(toChose));
            }
        }

        double resultRate = teamSuccessRate.getTeamSuccessRateMutual(teamMember, teamSize, costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0, resultRate, "recommendResult_4");
        }
        return teamMember;
    }

    /*改变建模方式；考虑双方意愿
    不考虑团队成员是否存在社交关系
   * */
    public int[] recommendForCompetitorMutual2(int competitorId, int teamSize, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual2(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            if (toChose != -1) {
                teamMember[i] = toChose;
                allCompetitors.remove(allCompetitors.indexOf(toChose));
            }
            else {
                System.out.println("recommend failed");
                return new int[teamSize];
            }
        }

        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2(teamMember, teamSize, costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0, resultRate, "recommendResult_4");
        }
        return teamMember;
    }

    /*改变建模方式；考虑双方意愿
    不考虑团队成员是否存在社交关系
    采用几何平均法
   * */
    public int[] recommendForCompetitorMutual3(int competitorId, int teamSize, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual3(teamMember, i + 1, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            /*if (successRate == -1){
                System.out.println(competitorId);
                return new int[teamSize];
            }*/
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }

        double resultRate = teamSuccessRate.getTeamSuccessRateMutual3(teamMember, teamSize, costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate, "recommendResult_2");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0, resultRate, "recommendResult_3");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0, resultRate, "recommendResult_4");
        }
        return teamMember;
    }


    public double[] getPRRate(int[] team, int teamSize) {
        int competitor1 = team[0];
        double[] result = new double[2];
        int Time = 0;
        for (int i = 1; i < teamSize; i++) {
            int competitor2 = team[i];
            if (relationGenDao.recordExistOrNot(competitor1, competitor2) == 1) {
                Time++;
            }
        }
        if (Time != 0) {
            result[0] = 1.0;
        }
        result[1] = Time / ((double) (teamSize - 1));
        return result;
    }
}
