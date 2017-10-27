package cn.springmvc.test;


import cn.springmvc.service.CompetitorAbility;
import cn.springmvc.service.CompetitorRelation;
import cn.springmvc.service.GetUserLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/applicationContext.xml")

public class getUserLanguageTest {

    @Autowired
    private GetUserLanguage getUserLanguage;

    @Autowired
    private CompetitorAbility competitorAbility;

    @Autowired
    private CompetitorRelation competitiorRelation;

    @Test
    public void testGetLanguages() throws Exception {
        //  getUserLanguage.getLanguages();
        //   competitorAbility.containsGraph();
        //competitorAbility.totalAbilityGen();
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String stringToDate1 = "7-30-2017";
        String stringToDate2 = "8-30-2017";
        Date date1 = dateFormat.parse(stringToDate1);
        Date date2 = dateFormat.parse(stringToDate2);
        int duration = (int) ((date2.getTime() - date1.getTime())/1000/60/60/24);
        System.out.println(duration);*/
        //System.out.println(50/500.0);
       // competitiorRelation.testes();
        System.out.println();
       // competitiorRelation.insertCollaborationRelation();
      //  competitorAbility.totalAbilityGen();
      //  competitorAbility.test();
    }
}
