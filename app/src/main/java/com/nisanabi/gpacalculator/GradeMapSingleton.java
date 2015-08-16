package com.nisanabi.gpacalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nisa on 03/07/2015.
 */
public class GradeMapSingleton {

    private static GradeMapSingleton instance = null;

    private HashMap<Integer,HashMap<String,Integer>> grademap;

    private int grade, credit;

    private GradeMapSingleton(){
        grademap = new HashMap<Integer,HashMap<String, Integer>>();
    }

    public Map getGradeMap(){
        return grademap;
    }


    public void add(int i, int grade, int credit){
        HashMap<String,Integer> currentgrade = new HashMap<String, Integer>();
        currentgrade.put("Grade", grade);
        currentgrade.put("Credit",credit);
        grademap.put(i, currentgrade);
    }
    public static GradeMapSingleton getInstance(){

        if(instance == null) {
            instance = new GradeMapSingleton();
        }
        return instance;
    }
}
