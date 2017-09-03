package cn.springmvc.service;

import cn.springmvc.dao.UserLanguageDao;
import cn.springmvc.model.Kernel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by YLT on 2017/8/29.
 */
@Service
public class GetUserLanguage {

    @Autowired
    private UserLanguageDao userLanguageDao;

    public void getKernel() {
        Kernel kernel = userLanguageDao.getKernel(42);
        System.out.println(kernel.getKernelName());
    }

    public void getLanguages() {
        List<HashMap> languageList = userLanguageDao.userLanguage();
        HashMap<Integer, JSONObject> LANGUAGEMAP = new HashMap<Integer, JSONObject>();
        //kernelAuthorId":1078316,"COUNT(*)":1,"languages":"Python"}
        for (HashMap map : languageList
                ) {
            int competiorId = Integer.parseInt(String.valueOf(map.get("kernelAuthorId")));
            JSONObject competitorLanguage;
            if (LANGUAGEMAP.containsKey(competiorId)) {
                competitorLanguage = LANGUAGEMAP.get(competiorId);
            } else {
                competitorLanguage = new JSONObject();
            }
            competitorLanguage.put((String) map.get("languages"), map.get("COUNT(*)"));
            LANGUAGEMAP.put(competiorId, competitorLanguage);
        }

        HashSet<Integer> competitorId_2 = userLanguageDao.getCompetitorId2();
        HashSet<Integer> competitorId = userLanguageDao.getCompetitorId();
        Iterator<Map.Entry<Integer, JSONObject>> iter = LANGUAGEMAP.entrySet().iterator();
        Map.Entry<Integer, JSONObject> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            int id = entry.getKey();
            Object value = entry.getValue();
            if(competitorId.contains(id)){
                userLanguageDao.updateCompetitor(id,value.toString());
            }
            if(competitorId_2.contains(id)){
                userLanguageDao.updateCompetitor2(id,value.toString());
            }
            System.out.println(id + value.toString());
        }
    }
}
