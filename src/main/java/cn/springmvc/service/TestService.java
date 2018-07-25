package cn.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by YLT on 2018/4/3.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/applicationContext.xml")*/
 @Component
public class TestService {
    @Autowired
    private DataPreLoad dataPreLoad;
    @Autowired
    private Experiment experiment;

    public static void main(String[] args){
        String paths[] = {"conf/applicationContext.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        TestService myBean = (TestService)ctx.getBean("TestService");
        //TestService service = new TestService();
        myBean.dataPreLoad.dataLoad();
        myBean.experiment.exp10();
    }
}
