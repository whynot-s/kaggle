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
    一维能力下的能力成长模型
    *获得能力成长空间模型
    * 团队能力成长
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
            //growSpace += teamAbility - memberAbility[i];
            growSpace += memberAbility[i];
        }
        growSpace = teamAbility * teamSize - growSpace;
        return growSpace / teamSize;
    }

    //个人能力成长
    //team ability - personal
    public double getGrowSpace2(double[] memberAbility, int teamSize, int index) {
        double teamAbility;
        double cant = 1;
        for (int i = 0; i < teamSize; i++) {
            cant = cant * (1 - memberAbility[i]);
        }
        teamAbility = 1 - cant;

        double growSpace = teamAbility - memberAbility[index];
        return growSpace;
    }

    //个人能力成长
    //team ability - personal
    public double[] getGrowSpace3(double[] memberAbility, int teamSize) {
        double teamAbility;
        double cant = 1;
        for (int i = 0; i < teamSize; i++) {
            cant = cant * (1 - memberAbility[i]);
        }
        teamAbility = 1 - cant;

        double[] growSpace = new double[teamSize];
        for (int index = 0; index < teamSize;index ++){
            growSpace[index] = teamAbility - memberAbility[index];
        }
        return growSpace;
    }
}
