package cn.springmvc.service;

import cn.springmvc.dao.*;
import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import cn.springmvc.model.CompetitorRecord;
import cn.springmvc.model.TeamRecordAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private Recomend1 recomend1;
    @Autowired
    private TeamRecordAnalysisDao teamRecordAnalysisDao;


    /*
    * 获取测试数据中参赛者的参赛记录-->competitorrecord_test表
    * */
    public void getTeamToTestNumber(){
        int [] testCompetitions = new int[]{6538,6539,6565, 6644,6649, 6768,6775,6841,7042,7043,7082,7115,7162,7163,7277,7299,7380, 7391, 7559,7634, 8011,8076, 8078};
        for (int competition:testCompetitions) {
            ArrayList<CompetitionLeaderboard> leaderboard = competitionLeaderboardDao.getLeaderBoardByCompetitionId(competition);
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

    /*
    * 测试集选取方式一：测试数据集中 存在能力评价 并且 参赛次数满足一定条件 的开发者
    * 测试数据集中 存在能力评价 并且 团队在测试集中团队参赛过的 开发者 ---》 995人
    * */
    public List<Integer> getTestSet1() {
        List<Integer> allTestCompetitors = competitorRecordDao.getTeamCompetitor("competitorrecord_test");//测试集中曾经团队参赛的开发者
        System.out.println(allTestCompetitors.size());
        for (int i = 0; i < allTestCompetitors.size();){
            if (DataPreLoad.competitorAbilityMap.containsKey(allTestCompetitors.get(i))){
                i++;
            }else{
                allTestCompetitors.remove(i);
            }
        }
       /* for (int i = 0; i < allTestCompetitors.size(); ) {
            if (competitorRecordDao.getRecordTimeByCompetitorIdTest(allTestCompetitors.get(i)) < 3) {
                allTestCompetitors.remove(i);
            } else {
                i++;
            }
        }*/
       System.out.println(allTestCompetitors.size());
        return allTestCompetitors;
    }


    /*
    * 测试集选取方式二：
    * 获得训练数据集中团队参赛次数大于等于3，且在测试数据中团队参赛过的人-158人
    * */
    public List<Integer> getTestSet2(){
        List<Integer> allTestCompetitor = competitorRecordDao.getTeamRecordMoreThan();
        List<Integer> command1 = competitorRecordDao.getTeamCompetitor("competitorrecord_test");

        for (int i = 0; i < allTestCompetitor.size();){
            if (!command1.contains(allTestCompetitor.get(i))){
                allTestCompetitor.remove(i);
            }else{
                i++;
            }
        }
        System.out.println(allTestCompetitor.size());
        return allTestCompetitor;
    }

    /*
   * 测试集选取方式三：
   * 在参赛者不同能力区间，按比例随机选取一定比例的参赛者进行推荐实验
   * */
    public List<Integer> getTestSet3(){
        int[] levels = new int[]{1, 24, 160,417,500,250,95,30,7,1};
        List<Integer> allTestCompetitors = new ArrayList<Integer>();
        for (int i = 0;i < 10; i ++) {
            double l1 = i * 0.1;
            double l2 = l1 + 0.1;
            List<Integer> comptitorsInLevel = competitorAbilityDao.getCompetitorsInEachLevel(l1,l2);
            int totalSize = comptitorsInLevel.size();
            System.out.println(totalSize);
            for (int j = 0; j < levels[i]; ) {
                Random random = new Random();
                int a = random.nextInt(totalSize);
                if (!allTestCompetitors.contains(comptitorsInLevel.get(a))) {
                    allTestCompetitors.add(comptitorsInLevel.get(a));
                    j++;
                }
            }
        }
        return allTestCompetitors;
    }

    /*
  * 测试集选取方式四：
  * 必须要有能力
  * 选择测试数据中，参赛次数>2、且至少团队参赛过一次的参赛者进行推荐实验334个参赛者
  * */
    public List<Integer> getTestSet4(){
        List<Integer> testSet4 = competitorRecordDao.getTeamCompetitor("competitorrecord_test");
        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        for (int i = 0; i < testSet4.size();) {
            if (competitorRecordDao.getRecordTimeByCompetitorId(testSet4.get(i)) > 2 && allCompetitorsSet.contains(testSet4.get(i))){
                i++;
            }else {
                testSet4.remove(i);
            }
        }
        System.out.println(testSet4.size());
        return testSet4;
    }

    /*
  * 测试集选取方式五：
  * 必须要有能力
  * 选择测试数据集中，曾团队参赛的参赛者、且在训练数据集中曾团队参赛过605个参赛者
  * */
    public List<Integer> getTestSet5(){
        List<Integer> testSet5 = competitorRecordDao.getTeamCompetitor("competitorrecord_test");
        List<Integer> command1 = competitorRecordDao.getTeamCompetitor("competitorrecord_train");
        System.out.println(testSet5.size());
        for (int i = 0; i < testSet5.size();) {
            if (command1.contains(testSet5.get(i)) ){
                i++;
            }else {
                testSet5.remove(i);
            }
        }
        System.out.println(testSet5.size());
        return testSet5;
    }

    /*
    * 在所有数据中，曾团队参赛的人的集合，即能学习到参数的人的集合
    * */
    public List<Integer> getTestSet6(){
        List<Integer> testSet6 = teamRecordAnalysisDao.getAllTestCompetitors();
        System.out.println(testSet6.size());
        return testSet6;
    }

    /*
  * 获得测试集中的用户的历史团队参赛记录（训练数据中）-->以便学习参数
  * */
    public void getTestSetTeamRecord(List<Integer> testSet){
        for (int competitor:testSet) {
            List<TeamRecordAnalysis> teamRecordOfCompetitor = teamRecordAnalysisDao.getTeamRecordByCompetitorId(competitor);
            for (TeamRecordAnalysis record : teamRecordOfCompetitor) {
                teamRecordAnalysisDao.insert(record);
            }
        }
    }

    /*
    * 获得测试集中的用户的历史团队参赛记录（训练数据中）-->以便学习参数
    * */
    public void getTestSet2TeamRecord(){
        List<Integer> testSet3 = getTestSet2();
        for (int competitor:testSet3) {
            List<TeamRecordAnalysis> teamRecordOfCompetitor = teamRecordAnalysisDao.getTeamRecordByCompetitorId(competitor);
            for (TeamRecordAnalysis record : teamRecordOfCompetitor) {
                teamRecordAnalysisDao.insert(record);
            }
        }
    }



    public void getTestPeopleTeamSum(){
        List<Integer> allTestPeople = teamRecordAnalysisDao.getTestPeople();
        for (Integer testPeople:allTestPeople){
            teamRecordAnalysisDao.setTeamSum(testPeople);
        }
    }
}
