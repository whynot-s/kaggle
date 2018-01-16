package cn.springmvc.service;

import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.RecommendResultDao;
import cn.springmvc.model.CompetitorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/1/15.
 */
@Service
public class ResultAnalysis {
    @Autowired
    private RecommendResultDao recommendResultDao;

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    public void getPRate(String tableName, int teamSize){
        List<HashMap<String, Object>> recommendResult = recommendResultDao.getRecommendResult(tableName);
        int allNum = recommendResult.size();
        int count = 0;
        boolean flag = false;

        for (HashMap<String,Object> result : recommendResult) {
            int competitor = Integer.parseInt(result.get("member1").toString());
            List<CompetitorRecord> records = competitorRecordDao.getCompetitorRecordByCompetitorId(competitor);
            for (int i = 2; i < teamSize + 1; i ++){
                String key = "member" + i;
                int member = Integer.parseInt(result.get(key).toString());
                for (CompetitorRecord record:records) {
                    if(competitorRecordDao.exist(record.getCompetitionId(),member,record.getRanking()) == 1){
                        System.out.println(competitor);
                        count ++;
                        flag = true;
                        break;
                    }
                }
                if (flag == true){
                    flag = false;
                    break;
                }
            }
        }
        System.out.println(count + "    " + allNum);
        System.out.println((double)(count)/allNum);
    }
}
