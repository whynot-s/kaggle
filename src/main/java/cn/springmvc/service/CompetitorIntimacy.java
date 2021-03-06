package cn.springmvc.service;

import cn.springmvc.dao.IntimacyDao;
import cn.springmvc.dao.RelationGenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
/*
* 亲密度相关函数
* */
public class CompetitorIntimacy {

    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private IntimacyDao intimacyDao;

    //计算两用户间亲密度并加入数据库中
    public void pairIntimacy() {
        List<Map<String, Object>> pairUsers = relationGenDao.getRelationMap("competitorId1", "competitorId2", "competitorRelation");
        for (Map<String, Object> pairUser : pairUsers) {
            int competitorId1 = (Integer) (pairUser.get("competitorId1"));
            int competitorId2 = (Integer) (pairUser.get("competitorId2"));
            //根据Id关系获得两者之间的各项关系数据
            Map<String, Object> relationTimes = relationGenDao.getAllRelationTime(competitorId1, competitorId2);
            double intimacy = calcIntimacy(relationTimes);
            relationGenDao.updateIntimacy(competitorId1, competitorId2, intimacy, "Intimacy");
        }
    }

    //根据Map中各项参数计算亲密度
    public double calcIntimacy(Map<String, Object> relationTimes) {
        String[] relationName = {
                "collaborationTime", "discussionCommentTime", "discussionVoteTime",
                "followTime", "kernelCommentTime", "kernelForkTime",
                "kernelVoteTime", "organizationTime"
        };
        //约定系数，与上方对应
        double[] coefficient = {
                1.0, 0.3, 0.3, 1.0, 0.4, 0.4, 0.4, 1.0
        };
        double sum = 0.0;
        for (int i = 0; i < relationName.length; i++) {
            String name = relationName[i];
            Integer relationTime = relationTimes.get(name) == null ? 0 : ((Integer) (relationTimes.get(name)));
            if (i == 0) {
                sum += coefficient[i] * relationTime;
            } else {
                sum += coefficient[i] * Math.sqrt(relationTime);
            }
        }
        return 1.0 / sum;
    }

    //初始化操作时将TeamMemberIntimacy清空(表需要存在)，并根据CompetitionLeaderBoard中的队伍信息按队伍每两个人加入记录，并加入两人团队中亲密度
    public void genTeamIntimacy() {
        intimacyDao.truncateTeamMemberIntimacy();
        List<Map<String, Object>> teams = intimacyDao.getTeams();
        for (Map<String, Object> team : teams) {
            int teamId = (Integer) (team.get("teamId"));
            String[] teamMembers = ((String) team.get("memberIds")).split("&");
            if (teamMembers.length > 1) {
                double[][] teamIntimacy = new double[teamMembers.length][teamMembers.length];
                teamMembersIntimacy(teamMembers, teamIntimacy);
                //将Team中任意两人亲密度加入TeamMemberIntimacy中
                for (int i = 0; i < teamMembers.length; i++) {
                    int competitorId1 = Integer.parseInt(teamMembers[i]);
                    for (int j = i + 1; j < teamMembers.length; j++) {
                        int competitorId2 = Integer.parseInt(teamMembers[j]);
                        intimacyDao.insertTeamPair(teamId, competitorId1, competitorId2);
                        intimacyDao.insertTeamPair(teamId, competitorId2, competitorId1);
                        intimacyDao.updateTeamPairIntimacy(teamIntimacy[i][j], teamId, competitorId1, competitorId2);
                        intimacyDao.updateTeamPairIntimacy(teamIntimacy[j][i], teamId, competitorId2, competitorId1);
                    }
                }
            } else {
                continue;
            }
        }
    }

    //团队亲密度计算函数，teamMembers为Id的字符串数组，teamIntimacy为求得亲密度方阵
    //须保证teamMembers.length == teamIntimacy.length
    public void teamMembersIntimacy(String[] teamMembers, double[][] teamIntimacy) {
        if (teamMembers.length != teamIntimacy.length) {
            //错误，不等长
            return;
        }
        //初始化Team矩阵
        double[][] graphWeights = new double[teamMembers.length][teamMembers.length];
        loadGraphWeights(teamMembers, graphWeights);
        //计算Team中任意两个人的亲密度
        calcTeamIntimacy(graphWeights, teamIntimacy);
    }

    //根据TeamemberIds将CompetitorIntimacy中的两者亲密度取出并存入GraphWeights中
    private void loadGraphWeights(String[] teamMembers, double[][] graphWeights) {
        for (int i = 0; i < teamMembers.length; i++) {
            graphWeights[i] = new double[teamMembers.length];
            int competitorId1 = Integer.parseInt(teamMembers[i]);
            for (int j = 0; j < teamMembers.length; j++) {
                if (i == j) {
                    //自己和自己的亲密度设置为0
                    graphWeights[i][j] = 0.0;
                } else {
                    int competitorId2 = Integer.parseInt(teamMembers[j]);
                    Double weights = intimacyDao.getPairIntimacy(competitorId1, competitorId2);
                    //若两个人亲密度在表中无记录，则设置为1
                    graphWeights[i][j] = weights == null ? 1 : weights;
                }
            }
        }
    }

    //根据已读入的带权图计算每个Team中任意两个人的亲密度，并存入TeamIntimacy中
    public void calcTeamIntimacy(double[][] graphWeights, double[][] TeamIntimacy) {
        for (int i = 0; i < graphWeights.length; i++) {
            TeamIntimacy[i] = new double[graphWeights.length];
            for (int j = 0; j < graphWeights.length; j++) {
                //遍历任意两个Team members
                if (i == j) {
                    TeamIntimacy[i][j] = 0.0;
                } else {
                    TeamIntimacy[i][j] = shortestWeightedPath(i, j, graphWeights);
                }
            }
        }
    }

    //根据已读入的带权图计算每个Team中任意两个人的亲密度，并存入TeamIntimacy中，对称方法,对二人组队场景需运行5ms
    public void calcTeamIntimacy2(double[][] graphWeights, double[][] TeamIntimacy) {
        for (int i = 0; i < graphWeights.length; i++) {
            TeamIntimacy[i] = new double[graphWeights.length];
            for (int j = i; j < graphWeights.length; j++) {
                //遍历任意两个Team members
                if (i == j) {
                    TeamIntimacy[i][j] = 0.0;
                } else {
                    TeamIntimacy[i][j] = shortestWeightedPath(i, j, graphWeights);
                }
            }
        }
        for (int i = 0; i < graphWeights.length; i++)
            for (int j = 0; j < i; j++)
                TeamIntimacy[i][j] = TeamIntimacy[j][i];
    }

    //广度优先计算start-->end的最短加权路径，权值为方阵表示
    private double shortestWeightedPath(int start, int end, double[][] weights) {
        double minWeight = Double.MAX_VALUE;
        Queue<Integer> subNodes = new LinkedList<Integer>();
        Queue<Double> minWeights = new LinkedList<Double>();
        Queue<boolean[]> passed = new LinkedList<boolean[]>();
        subNodes.offer(start);
        minWeights.offer(0.0);
        boolean[] pass = new boolean[weights.length];
        pass[start] = true;
        passed.offer(pass);
        while (!subNodes.isEmpty()) {
            int currentNode = subNodes.poll();
            double currentWeight = minWeights.poll();
            boolean[] currentPassed = passed.poll();
            for (int i = 0; i < weights[currentNode].length; i++) {
                //回头路不走，不通的路不走，已经大于等于现有最小权值的路不走（不入队）
                if (i == currentNode || currentWeight >= minWeight || currentPassed[i]) {
                    continue;
                    //遇终点判断权值大小，不入队
                } else if (i == end) {
                    if (currentWeight + weights[currentNode][i] < minWeight) {
                        minWeight = currentWeight + weights[currentNode][i];
                    }
                    //入队
                } else {
                    subNodes.offer(i);
                    minWeights.offer(currentWeight + weights[currentNode][i]);
                    boolean[] tempass = new boolean[currentPassed.length];
                    for (int j = 0; j < currentPassed.length; j++)
                        tempass[j] = currentPassed[j];
                    tempass[i] = true;
                    passed.offer(tempass);
                }
            }
        }
        return minWeight;
    }

    //初始化操作时将TeamIntimacy清空(表需要存在)，计算每个Team的总亲密度（Team来自teamMemberIntimacy表）
    public void allTeamTotalIntimacy() {
        intimacyDao.truncateTeamIntimacy();
        List<Integer> teamIds = intimacyDao.getTeamIds();
        for (Integer teamId : teamIds) {
            List<Map<String, Object>> team = intimacyDao.getTeamMemberIntimacyById(teamId);
            int numberOfMembers = (int) Math.sqrt(team.size()) + 1;
            double teamIntimacy = 0.0;
            for (Map<String, Object> relation : team) {
                teamIntimacy += (Double) (relation.get("Intimacy"));
            }
            teamIntimacy /= numberOfMembers * (numberOfMembers - 1);
            intimacyDao.insertTeamTotalIntimacy(teamId, teamIntimacy, numberOfMembers);
        }
    }

    //计算一个Team的总亲密度: N阶方阵的各项和/(N*(N-1))
    public double calcTeamTotalIntimacy(double[][] teamIntimacy) {
        double result = 0.0;
        for (int i = 0; i < teamIntimacy.length; i++) {
            for (int j = 0; j < teamIntimacy.length; j++) {
                if (teamIntimacy[i][j] > 1) {
                    teamIntimacy[i][j] = 1;
                }
                result += teamIntimacy[i][j];
            }
        }
        return result / (teamIntimacy.length * (teamIntimacy.length - 1));
    }

    //计算某个member在Team中与他人的亲密度: (N-1)
    public double calcMemberTotalIntimacy(double[][] teamIntimacy, int index) {
        double result = 0.0;
        int teamSize = teamIntimacy[index].length;
        for (int i = 0; i < teamSize; i++) {
            if (teamIntimacy[index][i] > 1) {
                teamIntimacy[index][i] = 1;
            }
            result += teamIntimacy[index][i];
        }
        return result / (teamSize - 1);
    }

    //计算某个member在Team中与他人的亲密度: (N-1)
    public double[] calcMemberTotalIntimacy2(double[][] teamIntimacy) {
        int length = teamIntimacy.length;
        double []result = new double[length];

        for (int index = 0; index < length; index ++){
            for (int i = 0; i < length; i ++){
                if (teamIntimacy[index][i] > 1){
                    teamIntimacy[index][i] = 1;
                }
                result[index] += teamIntimacy[index][i];
            }
            result[index] = result[index]/(length - 1);
        }
        return result;
    }

    //根据被推荐团队成员的id，得到团队的亲密度
    public double getTeamTotalIntimacy(String[] team) {
        int length = team.length;
        double[][] graphWeights = new double[length][length];
        double[][] teamIntimacy = new double[length][length];
        loadGraphWeights(team, graphWeights);
        calcTeamIntimacy(graphWeights, teamIntimacy);
        return calcTeamTotalIntimacy(teamIntimacy);
    }
}