package com.nisanabi.gpacalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nisa on 03/07/2015.
 */
public class GradeMapSingleton {

    private static GradeMapSingleton instance = null;

    private ArrayList<HashMap<String,Integer>> grademap;

    private int grade, credit;

    private GradeMapSingleton(){
        grademap = new ArrayList<HashMap<String, Integer>>();
    }

    public ArrayList<HashMap<String,Integer>> getGradeMap(){
        return grademap;
    }


    public void add(int i, int gradepoint, int credit){
        HashMap<String,Integer> currentgrade = new HashMap<String, Integer>();
        currentgrade.put("Grade", gradepoint);
        currentgrade.put("Credit",credit);
        grademap.add(currentgrade);
    }

    public void remove(int gradepoint, int credit) {
        System.out.println(grademap.size());
        for(HashMap<String,Integer> item: grademap) {
            System.out.println(item.get("Grade") + " " +item.get("Credit") );
            if(item.get("Grade").equals(gradepoint) && item.get("Credit").equals(credit)){
                System.out.println("REMOVIEDDDDD");
                grademap.remove(item);
            }
        }
    }
    public static GradeMapSingleton getInstance(){

        if(instance == null) {
            instance = new GradeMapSingleton();
        }
        return instance;
    }




}
