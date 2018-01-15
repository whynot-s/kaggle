package cn.springmvc.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by YLT on 2018/1/10.
 */
@Component
public class AbilityDiff {

    /*
    * 获得团队能力差异
    */
    public double getAbilityDiff(double [] memberAbility, int teamSize){
        if (teamSize < 2) {
            return 0.0;
        }else {
            double max = Double.MIN_VALUE;
            double min = Double.MAX_VALUE;
            for (int i = 0; i < teamSize; i ++){
                if (max < memberAbility[i]){
                    max = memberAbility[i];
                }
                if(min > memberAbility[i]){
                    min = memberAbility[i];
                }
            }
            return max - min;
        }
    }

}
