package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.dao.RelationGenDao;
import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/3/4.
 */
@Component
public class DataPreLoad {
    @Autowired
    private  CompetitorAbilityDao competitorAbilityDao;

    @Autowired
    private  RelationGenDao relationGenDao;

    @Autowired
    private CoffiTableDao coffiTableDao;

    public static HashMap<Integer,Double> competitorAbilityMap = new HashMap<Integer, Double>();
    public static HashMap<Integer,HashMap<Integer,String>> relationMap = new HashMap<Integer, HashMap<Integer, String>>();
    public static HashMap<Integer,CoffiTable> coffiMap = new HashMap<Integer, CoffiTable>();

    public void dataLoad(){
        List<HashMap<String,Object>> allAbilityRecord = competitorAbilityDao.getCompetitorIdsWithScore();
        for (HashMap<String,Object> record: allAbilityRecord){
            competitorAbilityMap.put(Integer.parseInt(record.get("competitorId").toString()),Double.parseDouble(record.get("totalToLog").toString()));
        }

        List<HashMap<String ,Object>> allRelationRecord = relationGenDao.allCostBetween();
        for (HashMap<String ,Object> record : allRelationRecord) {
            int key = Integer.parseInt(record.get("competitorId1").toString());
            if (relationMap.containsKey(key)){
                relationMap.get(key).put(Integer.parseInt(record.get("competitorId2").toString()),record.get("cost1").toString() + ";" + record.get("cost2").toString());
            }else{
                HashMap<Integer,String> temp = new HashMap<Integer, String>();
                temp.put(Integer.parseInt(record.get("competitorId2").toString()),record.get("cost1").toString()+ ";" + record.get("cost2").toString());
                relationMap.put(key,temp);
            }
        }
    }

    public void coffiMapLoad(){
        List<CoffiTable> allCoffiTable = coffiTableDao.getTestCompetitors("coffiTable_end2_0_6");
        for (CoffiTable coffi:allCoffiTable) {
            int member1 = coffi.getMember1();
            coffiMap.put(member1,coffi);
        }
    }
}
