package cn.springmvc.service;

import cn.springmvc.dao.RecommendResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/4/21.
 */
@Component
public class VarianceGet {
    @Autowired
    private RecommendResultDao recommendResultDao;

    @Autowired
    private TeamSuccessRate teamSuccessRate;
    public void getVariance(String tableName , int teamSize, double costpercent){
        double totalVar = 0;
        List<HashMap<String ,Object>> results = recommendResultDao.getRecommendResult(tableName);
        for (HashMap<String ,Object> result:results) {
            ArrayList<Integer> teammembers = new ArrayList<Integer>();
            for (int i = 1; i <= teamSize; i ++){
                teammembers.add((Integer) result.get("member"+i));
            }
            totalVar += varianceInArray(teammembers,teamSize,costpercent);
        }
        System.out.println(totalVar/results.size());
    }

    //得到一个团队中，各个开发者的组队意愿方差
    public double varianceInArray(ArrayList<Integer> teammembers, int teamsize,double costpercent){
        int [] teammember = new int[teamsize];
        for (int i =0; i < teamsize; i ++){
            teammember[i] = teammembers.get(i);
        }
        double[] willingness = teamSuccessRate.getTeamSuccessRateMutualEachPerson(teammember,teamsize,costpercent);
        double avg = 0;
        for (int i = 0; i < teamsize; i ++){
            avg += willingness[i];
        }
        avg = avg/teamsize;

        double var = 0;
        for (int i = 0; i < teamsize; i ++){
            var += (willingness[i] - avg) * (willingness[i] - avg);
        }
        return var/teamsize;
    }
}
