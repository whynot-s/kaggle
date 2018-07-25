package cn.springmvc.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by YLT on 2018/1/10.
 */
@Component
public class AbilityDiff {

    /*
    一维能力下的能力差异模型
    * 获得团队能力差异:团队能力差异模型
    * max - min
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

    //((A-B+A-C)/2 + 1)/2
    public double getAbilityDiff2(double [] memberAbility, int teamSize, int index){
       /* double abilityDiff = 0;

        for (int i = 0; i < teamSize; i ++){
            abilityDiff += memberAbility[i];
            //abilityDiff += memberAbility[index] - memberAbility[i];
        }
        abilityDiff = memberAbility[index]*teamSize - abilityDiff;

        return abilityDiff > 0 ? abilityDiff/teamSize : 0;*/

        double abilityDiff = 0;

        for (int i = 0; i < teamSize; i ++){
            abilityDiff += memberAbility[i];
            //abilityDiff += memberAbility[index] - memberAbility[i];
        }
        abilityDiff = (memberAbility[index] * teamSize - abilityDiff) / (teamSize - 1);

        return (abilityDiff + 1) / 2;
    }

    //((A-B+A-C)/2 + 1)/2
    public double[] getAbilityDiff3(double [] memberAbility, int teamSize){
        double[] abilityDiff = new double[teamSize];
        for (int index = 0; index < teamSize; index++){
            for (int i = 0; i < teamSize; i ++){
                abilityDiff[index] += memberAbility[i];
            }
            abilityDiff[index] = (memberAbility[index] * teamSize - abilityDiff[index])/(teamSize - 1);
            abilityDiff[index] = (abilityDiff[index] + 1)/2;
        }

        return abilityDiff;
    }

    //(A-B+A-C)/2 > 0? (A-B+A-C)/2:-(A-B+A-C)/2
    public double getAbilityDiff4(double [] memberAbility, int teamSize, int index){
        double abilityDiff = 0;

        for (int i = 0; i < teamSize; i ++){
            abilityDiff += memberAbility[i];
        }
        abilityDiff = (memberAbility[index] * teamSize - abilityDiff) / (teamSize - 1);
        if (abilityDiff < 0){
            abilityDiff = -abilityDiff;
        }
        return abilityDiff;
    }

    //(A-B+A-C)/2 > 0? (A-B+A-C)/2:0
    public double[] getAbilityDiff5(double [] memberAbility, int teamSize){
        double[] abilityDiff = new double[teamSize];
        for (int index = 0; index < teamSize; index++){
            for (int i = 0; i < teamSize; i ++){
                abilityDiff[index] += memberAbility[i];
            }
            abilityDiff[index] = (memberAbility[index] * teamSize - abilityDiff[index])/(teamSize - 1);
            abilityDiff[index] = abilityDiff[index] < 0 ? 0:abilityDiff[index];
        }

        return abilityDiff;
    }

    //(|A-B|+|A-C|)/2
    public double getAbilityDiff6(double [] memberAbility, int teamSize, int index){
        double abilityDiff = 0;
        for (int i = 0; i < teamSize; i ++){
            abilityDiff += Math.abs(memberAbility[index]-memberAbility[i]);
        }
        return abilityDiff/(teamSize - 1);
    }

    //(|A-B|+|A-C|)/2
    public double[] getAbilityDiff6Array(double [] memberAbility, int teamSize){
        double[] abilityDiff = new double[teamSize];

        for (int index = 0; index < teamSize; index ++){
            for (int j = 0; j < teamSize; j ++) {
                abilityDiff[index] += Math.abs(memberAbility[index] - memberAbility[j]);
            }
            abilityDiff[index] = abilityDiff[index]/(teamSize-1);
        }
        return abilityDiff;
    }
}
