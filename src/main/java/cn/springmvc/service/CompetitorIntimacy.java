package cn.springmvc.service;

import cn.springmvc.dao.RelationGenDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitorIntimacy {

	@Autowired
	private RelationGenDao relationGenDao;
	
	//计算两用户间亲密度并加入数据库中
	public void pairIntimacy() {
		List<Map<String, Object>> pairUsers = relationGenDao.getRelationMap("competitorId1", "competitorId2", "competitorRelation");
		for(Map<String, Object> pairUser : pairUsers) {
			int competitorId1 = (Integer)(pairUser.get("competitorId1"));
			int competitorId2 = (Integer)(pairUser.get("competitorId2"));
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
		for(int i = 0; i < relationName.length; i++) {
			String name = relationName[i];
			Integer relationTime = relationTimes.get(name) == null ? 0 : ((Integer)(relationTimes.get(name)));
			if(i == 0) {
				sum += coefficient[i] * relationTime;
			}else {
				sum += coefficient[i] * Math.sqrt(relationTime);
			}
		}
		return 1.0/sum;
	}

	
	
}
