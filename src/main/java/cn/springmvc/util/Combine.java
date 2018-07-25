package cn.springmvc.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLT on 2018/3/22.
 */
@Component
public class Combine {
    public static void main(String[] args){
        List<Integer> ia = new ArrayList<Integer>();
        ia.add(1);
        ia.add(2);
        ia.add(3);
        ia.add(4);
        ia.add(5);
        ia.add(6);

        combination(ia,4);
    }

    public static void combination(List<Integer> ia, int n) {
        List<String> CombineResult = new ArrayList<String>();
        combination("", ia, n,CombineResult);

        for (int i = 0; i < CombineResult.size();i ++){
            System.out.println(CombineResult.get(i));
        }
    }
    public static void combination(String s, List<Integer> ia, int n,List<String> CombineResult) {
        if(n == 1) {
            for(int i = 0; i < ia.size(); i++) {
                CombineResult.add(s+ia.get(i));
               // System.out.println(s+ia.get(i));
            }
        } else {
            for(int i = 0; i < ia.size()-(n-1); i++) {
                String ss = "";
                ss = s+ia.get(i)+", ";
                //建立从i开始的子数组
                List<Integer> ii = new ArrayList<Integer>();
                //int[] ii = new int[ia.size()-i-1];
                for(int j = 0; j < ia.size()-i-1; j++) {
                    ii.add(ia.get(i+j+1));
                }
                combination(ss, ii, n-1,CombineResult);
            }
        }
    }
}