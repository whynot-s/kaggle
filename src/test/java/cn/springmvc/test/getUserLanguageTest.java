package cn.springmvc.test;


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

    @Test
    public void testGetLanguages() throws Exception {
        getUserLanguage.getLanguages();
    }
}
