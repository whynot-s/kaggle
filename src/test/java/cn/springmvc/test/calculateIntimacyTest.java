package cn.springmvc.test;

import cn.springmvc.service.CompetitorIntimacy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/applicationContext.xml")

public class calculateIntimacyTest {

    @Autowired
    private CompetitorIntimacy competitorIntimacy;

    @Test
    public void test() {
//		competitorIntimacy.pairIntimacy();
//		competitorIntimacy.genTeamIntimacy();
        competitorIntimacy.allTeamTotalIntimacy();
    }

    //	测试最短路径计算正确性
    @Test
    public void ShortestWeightedPathTest() {
        double[][] weights = new double[4][4];
        String W = "0.0;0.01;0.08;0.18;0.01;0.0;0.06;0.02;0.08;0.06;0.0;0.09;0.18;0.02;0.09;0.0";
        double[][] intimacy = new double[4][4];
        String K = "0.0;0.01;0.07;0.03;0.01;0.0;0.06;0.02;0.07;0.06;0.0;0.08;0.03;0.02;0.08;0.0";
        String[] W_s = W.split(";");
        String[] K_s = K.split(";");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                weights[i][j] = Double.parseDouble(W_s[i * 4 + j]);
            }
        }
        competitorIntimacy.calcTeamIntimacy(weights, intimacy);
        for (int i = 0; i < intimacy.length; i++) {
            for (int j = 0; j < intimacy[i].length; j++) {
                assertEquals(Double.parseDouble(K_s[i * 4 + j]), intimacy[i][j], 0.00000000001);
            }
        }
    }

}
