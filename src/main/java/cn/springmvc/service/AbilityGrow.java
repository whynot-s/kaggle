package cn.springmvc.service;

import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.model.CompetitorAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YLT on 2018/1/12.
 */
@Component
public class AbilityGrow {

    /*
    *获得能力成长空间模型
    */
    public double getGrowSpace(double[] memberAbility, int teamSize) {
        //团队能力值
        double teamAbility;
        double cant = 1;
        for (int i = 0; i < teamSize; i++) {
            cant = cant * (1 - memberAbility[i]);
        }
        teamAbility = 1 - cant;

        //能力成长空间
        double growSpace = 0;
        for (int i = 0; i < teamSize; i++) {
            growSpace += teamAbility - memberAbility[i];
        }
        return growSpace / teamSize;
    }
}
