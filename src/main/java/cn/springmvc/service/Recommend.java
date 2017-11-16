package cn.springmvc.service;

import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.model.CompetitorAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wutiange on 2017/10/24.
 */

//编写过程中从第二次运行开始就不必再切回到Test文件里了,Shitf+F10
@Service
public class Recommend {
    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    @Autowired
    private CompetitorIntimacy intimacy;

    private String[] tagName = {"image", "text", "tabular", "waft", "audio", "time series", "graph", "adversarial learning",
            "binary classification", "forecasting", "multiclass classification", "object identification",
            "object detection", "regression", "duplicate detection", "artificial intelligence",
            "object segmentation", "object labeling", "optimization", "ranking", "totalScore"};
    private int[] book = new int[10];                                          //存储 组合数 数组
    private int stuId;                                                         //被推荐的人
    private Double maxSum = new Double(0);                                     // 最优的团队收益度的和
    private String t = "";                                                      //推荐结果
    private ArrayList<CompetitorAbility> allList = null;
    private HashMap<Integer, CompetitorAbility> map = new HashMap<Integer, CompetitorAbility>();   //竞赛ID-->竞赛对象
    private HashMap<String, Integer> tag = new HashMap<String, Integer>();      //标记竞赛是否需要包含
    private Double tag1rate = 0.0;
    private Double tag2rate = 0.0;
    private Double totalrate = 0.0;
    private Double intimacyLimits = 0.0;                          //亲密度下限

    //测试性能
    private Double time = 0.0;
    private int count = 0;

    //range>0 && range<=1
    //userId 被推荐的人
    //teamsize 团队规模
    public String getRecommendTeam(int userId, int teamsize, String[] tags, Double range, Double tag1Rate, Double tag2Rate, Double totalScoreRate,Double intimacyLimit) {
        allList = competitorAbilityDao.getAllCompetitorAbility();
        for (int i = 0; i < allList.size(); i++) {
            map.put(allList.get(i).getCompetitorId(), allList.get(i));
        }
        for (int i = 0; i < tags.length; i++) {
            tag.put(tags[i], 1);
        }
        tag1rate = tag1Rate;
        tag2rate = tag2Rate;
        totalrate = totalScoreRate;
        stuId = userId;
        intimacyLimits = intimacyLimit;

        int [] similarAbility = getSimilarAbility(tags,userId,range);

//        Long start = System.currentTimeMillis();
        dfs(similarAbility, teamsize - 1, 0, 0, similarAbility.length-10);
//        System.out.println("dfs:" + (System.currentTimeMillis() - start) / 1000.0);
//        System.out.println("res.add(map.get(Integer.parseInt(str[i]))) :" + time);
//        System.out.println("count:" + count);
        //综上，结论：没有耗时的单步但是遍历的量太大（近亿级）
        System.out.println("result is " + t);
        return t;
    }

    //获取能力相近的人的数组
    //tags需要推荐的标签数组
    //userId 被推荐者
    //range 相近的范围
    int [] getSimilarAbility(String [] tags,int userId,Double range)
    {
        HashSet<Integer> result = new HashSet<Integer>();       //能力相近的人的集合

        for (int i=0;i<tags.length;i++){
            Double score = competitorAbilityDao.getCompetitorAbility(userId, "`"+tags[i]+"`");
            if (score != null) {
                List<Map<String,Object>> list = competitorAbilityDao.getAllNotNullAbility2("`"+tags[i]+"`");
                int len = list.size();
                Collections.sort(list, new cmp());
                //这里可以改成二分查找
                int loc = -1;
                for (int j = 0; j < len; j++) {
                    if (Math.abs((Double)list.get(j).get("tagScore") - score) < 0.00000001)
                        loc = j;
                }
                for (int j = loc + 1; j < len && j < loc + (int) (len * range); j++) {
                    result.add((Integer) list.get(j).get("competitorId"));
                }
                for (int j = loc - 1; j >= 0 && j > loc - (int) (len * range); j--) {
                    result.add((Integer) list.get(j).get("competitorId"));
                }
            }
        }

        int[] results = new int[result.size() + 10];        //把能力相近的集合转为数组，方便后面处理
        int i = 0;
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            results[i++] = iter.next();
        }
        return results;
    }

    int step = 0;

    //result 能力相近的人的集合
    //这里的teamsize是团队规模-1，被推荐者确定在结果中
    //生成组合数序列
    private void dfs(int[] result, int teamsize, int level, int cur, int n) {
        if (level == teamsize) {
            String team = "(";                          //表示一种可能的团队组合情况
            String [] teams = new String[teamsize+1];
            for (int i = 0; i < teamsize; i++) {
                team += result[book[i] - 1] + ",";
                teams[i] = String.valueOf(result[book[i] - 1]);
            }
            team += stuId;
            team += ")";
            teams[teamsize] = String.valueOf(stuId);
            ArrayList<CompetitorAbility> list = getTeamAbility(team);
            if (Qinmi(teams)) {
                count++;
                UpdateProfit(team, teamsize, list);
            }
            step++;
            if (step%100000==0){
                System.out.println(step);
            }
            return;
        }
        for (int i = cur + 1; i <= n; i++) {
            book[level] = i;
            dfs(result, teamsize, level + 1, i, n);
        }
    }

    //亲密度
    private boolean Qinmi(String [] teams) {
        //函数返回值越小，亲密度越高
        return true;
//        return intimacy.getTeamTotalIntimacy(teams) <= intimacyLimits;
    }

    //team 团队成员
    // 这里的teamsize是团队规模-1，被推荐者确定在结果中
    // list存储这几个人的能力模型
    private void UpdateProfit(String team, int teamsize, ArrayList<CompetitorAbility> teamlist) {
        Double sum = new Double(0);

        for (int i = 0; i < tagName.length; i++) {
            if (tag.containsKey(tagName[i])) {
                //找某个tag的最大值
                Double max = new Double(0);
                for (int j = 0; j < teamsize + 1; j++) {
                    if (teamlist.get(j).getScoreByTagName(tagName[i]) == null)
                        continue;
                    if (max < teamlist.get(j).getScoreByTagName(tagName[i])) {
                        max = teamlist.get(j).getScoreByTagName(tagName[i]);
                    }
                }
                //循环作和
                for (int j = 0; j < teamsize + 1; j++) {
                    Double flesh = teamlist.get(j).getScoreByTagName(tagName[i]);
                    if (flesh == null)
                        continue;
                    if (i < 7) {
                        sum += (max - flesh) * flesh * tag1rate;         //tag1
                    } else if (i == tagName.length - 1)
                        sum += (max - flesh) * flesh * totalrate;         //totalScore
                    else
                        sum += (max - flesh) * flesh + tag2rate;             //tag2
                }
            }
        }
        //更新最大值和团队
        if (sum > maxSum) {
            maxSum = sum;
            t = team;                       //更新team结果
            System.out.println(t);          //中间结果输出
        }
    }

    //获取几个人的能力，team形如(381,389,userId)
    private ArrayList<CompetitorAbility> getTeamAbility(String team) {
        ArrayList<CompetitorAbility> res = new ArrayList<CompetitorAbility>();
        String[] str = team.substring(1, team.length() - 1).split(",");
        Long start = System.currentTimeMillis();
        for (int i = 0; i < str.length; i++) {
            res.add(map.get(Integer.parseInt(str[i])));                     //map的唯一使用，其实这个也可以转化为在mapper那里返回Map
        }
        time += (System.currentTimeMillis() - start) / 1000.0;
        return res;
    }

}

class cmp implements Comparator<Map<String,Object>>{

    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        return (Double)o1.get("tagScore") >= (Double)o2.get("tagScore")?1:-1;
    }
}
