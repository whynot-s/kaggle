package cn.springmvc.service;

import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.model.Competition;
import cn.springmvc.model.CompetitionLeaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by YLT on 2017/9/28.
 */

@Service
public class CompetitorAbilityGen {

    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    //往competition的tag1标签中添加graph标签
    public void containsGraph() {
        ArrayList<String> descriptions = (ArrayList<String>) competitorAbilityDao.getDescriptions();
        for (String description : descriptions
                ) {
            /*if(description.contains("social")||description.contains("network")){*/
            if (description.contains("network")) {
                System.out.println(descriptions.indexOf(description));
            }
        }
    }

    public void totalAbilityGen() {
        ArrayList<Competition> allCompetitions = competitorAbilityDao.getCompetitions();
        for (Competition competition : allCompetitions
                ) {
            if (competition.getCompetitionStatus().equals("closed") && !competition.getCompetitionType().equals("playground") && !competition.getCompetitionType().equals("getting started") && !competition.getTag1().equals("")) {
                System.out.println(competition.getCompetitionId());
                eachCompetitionAbility(competition);
            }
        }
    }

    //一个竞赛对参赛者的技能增长
    public void eachCompetitionAbility(Competition competition) {
        //获取一个竞赛的排名记录
        ArrayList<CompetitionLeaderboard> leaderboards = competitorAbilityDao.getLeaderBoardByCompetitionId(competition.getCompetitionId());
        //获取一个竞赛的所有标签集合
        List<String> tag1sMid = Arrays.asList(competition.getTag1().split(";"));
        List<String> tag1s = new ArrayList<String>();
        for (String tag1 : tag1sMid
                ) {
            if (tag1.contains(" ")) {
                tag1 = "`" + tag1 + "`";
            }
            System.out.println(tag1);
            tag1s.add(tag1);
        }
        List<String> tag2sMid = Arrays.asList(competition.getTag2().split(";"));
        List<String> tag2s = new ArrayList<String>();
        for (String tag2 : tag2sMid
                ) {
            if (tag2.contains(" ")) {
                tag2 = "`" + tag2 + "`";
            }
            System.out.println(tag2);
            tag2s.add(tag2);
        }

        //截止到2017.7.30,已经测试正确
        Date closeDate = competition.getCloseTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String stringToDate = "7-30-2017";
        int duration = 0;
        try {
            Date nowDate = dateFormat.parse(stringToDate);
            duration = (int) ((nowDate.getTime() - closeDate.getTime()) / 1000 / 60 / 60 / 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int Nteams = competition.getTeamsNum();

        for (CompetitionLeaderboard leaderboardEach : leaderboards) {
            if (leaderboardEach.getTeamMemberId().equals("")) {
                continue;
            }
            String[] members = leaderboardEach.getTeamMemberId().split("&");
            int memberNumber = members.length;
            int rank = leaderboardEach.getRanking();
            System.out.print(rank);
            //得到参与这个竞赛获得名次后，组队中每一个成员在每一个tag下提高的分数
            double score = (100000.0 / Math.sqrt(memberNumber)) * Math.pow(rank, -0.75) * Math.log10(1 + Math.log10(Nteams)) * Math.pow(Math.E, -duration / 500.0);

            for (String member : members
                    ) {
                int memberId = Integer.parseInt(member);
                for (String tag : tag1s
                        ) {
                    double score1 = score / Math.sqrt(tag1s.size());
                    updateCompetitorAbilityScore(memberId, tag, score1);
                }
                for (String tag : tag2s
                        ) {
                    double score2 = score / Math.sqrt(tag2s.size());
                    updateCompetitorAbilityScore(memberId, tag, score2);
                }
                updateCompetitorAbilityScore(memberId, "totalScore", score);
            }
        }
        System.out.println(competition.getCompetitionId() + "end");
    }

    public void updateCompetitorAbilityScore(int competitorId, String tagName, double score) {
        if (competitorAbilityDao.userRecordExitOrNot(competitorId) == 0) {
            competitorAbilityDao.insertUserAbilityRecord(competitorId, tagName, score);
        } else {
            Double preScore = competitorAbilityDao.getCompetitorAbility(competitorId, tagName);
            if (preScore == null) {
                competitorAbilityDao.updateAbility(competitorId, tagName, score);
            } else {
                competitorAbilityDao.updateAbility(competitorId, tagName, score + preScore);
            }
        }
    }


}
