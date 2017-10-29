package cn.springmvc.service;

import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.model.Id_Tag;
import cn.springmvc.model.competitorAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wutiange on 2017/10/24.
 */

//编写过程中从第二次运行开始就不必再切回到Test文件里了,Shitf+F10
@Service
public class Recommend
{
    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    private String[] tagName = {"`image`","`text`","tabular","`waft`","`audio`","`time series`","`graph`","`adversarial learning`","`binary classification`","`forecasting`","`multiclass classification`","`object identification`","`object detection`","`regression`","`duplicate detection`","`artificial intelligence`","`object segmentation`","`object labeling`","`optimization`","`ranking`","`totalScore`"};
    private int [] book = new int[10];                                          //组合数标记数组
    private int stuId;
    private Double maxSum = new Double(0);
    private String t = "";                                                      //推荐结果
    private ArrayList<competitorAbility> allList = null;
    private HashMap<Integer,competitorAbility> map = new HashMap<Integer, competitorAbility>();         //竞赛ID-->竞赛对象
    private HashMap<String,Integer> tag = new HashMap<String, Integer>();      //标记竞赛是否需要包含
    private Double tag1rate = 0.0;
    private Double tag2rate = 0.0;
    private Double totalrate = 0.0;
    //测试性能
    private Double time = 0.0;
    private int count = 0;

    //range能力相近的上下选择的比例，range>0 && range<=1
    //要求传入的tags的length和tag的个数相同，也就是用split来生成tags，不要new
    public String getRecommendTeam(int userId,int teamsize,String [] tags,Double range,Double tag1Rate,Double tag2Rate,Double totalScoreRate)
    {
        allList = competitorAbilityDao.getAllCompetitorAbility();
        for (int i=0;i<allList.size();i++){
            map.put(allList.get(i).getCompetitorId(),allList.get(i));
        }
        for (int i=0;i<tags.length;i++){
            tag.put(tags[i],1);
        }
        tag1rate = tag1Rate;
        tag2rate = tag2Rate;
        totalrate = totalScoreRate;
        stuId = userId;

        HashSet<Integer> result = new HashSet<Integer>();       //能力相近的人的集合

        for (int i=0;i<tagName.length;i++) {
            if (tag.containsKey(tagName[i])){
                System.out.println(tagName[i]);
                Double score = competitorAbilityDao.getCompetitorAbility(userId, tagName[i]);
                if (score != null){
                    ArrayList<Id_Tag> list = competitorAbilityDao.getAllNotNullAbility(tagName[i]);
                    int len = list.size();
                    Id_Tag flesh = new Id_Tag();
                    flesh.setCompetitorId(userId);
                    flesh.setTagScore(score);

                    Collections.sort(list,new cmp());
                    //这里可以改成二分查找
                    int loc = -1;
                    for (int j=0;j<len;j++){
                        if (Math.abs(list.get(j).getTagScore()-score)<0.00000001)
                            loc = j;
                    }
                    for (int j=loc+1;j<len&&j<loc+(int)(len*range);j++){
                        result.add(list.get(j).getCompetitorId());
                    }
                    for (int j=loc-1;j>=0&&j>loc-(int)(len*range);j--){
                        result.add(list.get(j).getCompetitorId());
                    }
                }
            }
        }
        System.out.println(result.size());
        int [] results = new int[result.size()+10];
        int i=0;
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()){
            results[i++] = iter.next();
        }
        Long start = System.currentTimeMillis();
        System.out.println("init: 1s到1.5s (相近人在11980的耗时)");
        dfs(results,teamsize-1,0,0,result.size());
        System.out.println("dfs:"+(System.currentTimeMillis()-start)/1000.0);
        System.out.println("getTeamAbility:30s (相近人在11980的耗时)");
        System.out.println("res.add(map.get(Integer.parseInt(str[i]))) :"+time);
        System.out.println("count:"+count);
        //综上，结论：没有耗时的单步但是遍历的量太大（近亿级）
        System.out.println("result is "+t);
        return t;
    }

    //这里的teamsize是组队人数-1，有一个人已经定了
    //生成组合数序列
    private void dfs(int[] result,int teamsize,int level,int cur,int n)
    {
        if (level==teamsize){
            String team = "(";
            for (int i=0;i<teamsize;i++){
                team+=result[book[i]-1]+",";
            }
            team+=stuId;
            team+=")";
            count++;
            ArrayList<competitorAbility> list = getTeamAbility(team);
            if (Qinmi(book)){
//                Long start = System.currentTimeMillis();
                UpdateProfit(team,teamsize,list);
//                time += (System.currentTimeMillis()-start)/1000.0;
            }
            return;
        }
        for (int i=cur+1;i<=n;i++){
            book[level] = i;
            dfs(result,teamsize,level+1,i,n);
        }
    }

    //亲密度
    private boolean Qinmi(int [] book)
    {
        return true;
    }

    //这里的teamsize没有包括自己,list就是这几个人的能力
    private void UpdateProfit(String team,int teamsize,ArrayList<competitorAbility> list)
    {
        Double sum = new Double(0);
        PersonAbility[] persons = new PersonAbility[teamsize+2];
        for (int i=0;i<teamsize+1;i++){
            persons[i] = new PersonAbility(list.get(i));
        }

        for (int i=0;i<tagName.length;i++){
            if (tag.containsKey(tagName[i])){
                //找最大值
                Double max = new Double(0);
                for (int j=0;j<teamsize+1;j++){
                    if (persons[j].tag[i]==null)
                        continue;
                    if (max<persons[j].tag[i]){
                        max = persons[j].tag[i];
                    }
                }
                //循环作和
                for (int j=0;j<teamsize+1;j++){
                    Double flesh = persons[j].tag[i];
                    if (flesh==null)
                        continue;
                    if (i<7){
                        sum+=(max-flesh)*flesh*tag1rate;         //tag1
                    }
                    else if (i==tagName.length-1)
                        sum+=(max-flesh)*flesh*totalrate;         //totalScore
                    else
                        sum+=(max-flesh)*flesh+tag2rate;             //tag2
                }
            }
        }
        //更新最大值和团队
        if (sum>maxSum){
            maxSum = sum;
            t = team;
            System.out.println(t);
        }
    }

    //获取几个人的能力，team形如(381,389,userId)
    private ArrayList<competitorAbility> getTeamAbility(String team)
    {
        ArrayList<competitorAbility> res = new ArrayList<competitorAbility>();
        String [] str = team.substring(1,team.length()-1).split(",");
        Long start = System.currentTimeMillis();
        for (int i=0;i<str.length;i++){
            res.add(map.get(Integer.parseInt(str[i])));
        }
        time += (System.currentTimeMillis()-start)/1000.0;
        return res;
    }

}

class cmp implements Comparator<Id_Tag>
{
    public int compare(Id_Tag o1, Id_Tag o2) {
        return o1.getTagScore()>=o2.getTagScore()?1:-1;
    }
}

class PersonAbility
{
    Double [] tag = new Double [21];
    PersonAbility(competitorAbility t)
    {
        tag[7] = t.getAdversarial_learning();
        tag[15] = t.getArtificial_intelligence();
        tag[4] = t.getAudio();
        tag[8] = t.getBinary_classification();
        tag[14] = t.getDuplicate_detection();
        tag[9] = t.getForecasting();
        tag[6] = t.getGraph();
        tag[0] = t.getImage();
        tag[10] = t.getMulticlass_classification();
        tag[12] = t.getObject_detection();
        tag[11] = t.getObject_identification();
        tag[17] = t.getObject_labeling();
        tag[16] = t.getObject_segmentation();
        tag[18] = t.getOptimization();
        tag[19] = t.getRanking();
        tag[13] = t.getRegression();
        tag[2] = t.getTabular();
        tag[1] = t.getText();
        tag[5] = t.getTime_series();
        tag[20] = t.getTotalScore();
        tag[3] = t.getWaft();
    }

}


