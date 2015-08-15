package com.nisanabi.gpacalculator;

/**
 * Created by Nisa on 05/07/2015.
 */
public class ConvertGradePoint {

    String grade;
    int point;
    int pointsConverted= 0;
    String gradeConverted = "";

    public ConvertGradePoint(String grade){
        this.grade = grade;
    }

    public ConvertGradePoint(int point){
        this.point = point;
    }

    public int getpointsConverted(){
        switch (this.grade){
            case "A1" :
                pointsConverted = 22;
                break;
            case "A2" :
                pointsConverted = 21;
                break;
            case "A3" :
                pointsConverted = 20;
                break;
            case "A4" :
                pointsConverted = 19;
                break;
            case "A5" :
                pointsConverted = 18;
                break;
            case "B1" :
                pointsConverted = 17;
                break;
            case "B2" :
                pointsConverted = 16;
                break;
            case "B3" :
                pointsConverted = 15;
                break;
            case "C1" :
                pointsConverted = 14;
                break;
            case "C2" :
                pointsConverted = 13;
                break;
            case "C3" :
                pointsConverted = 12;
                break;
            case "D1" :
                pointsConverted = 11;
                break;
            case "D2" :
                pointsConverted = 10;
                break;
            case "D3" :
                pointsConverted = 9;
                break;
            case "E1" :
                pointsConverted = 8;
                break;
            case "E2" :
                pointsConverted = 7;
                break;
            case "E3" :
                pointsConverted = 6;
                break;
            case "F1" :
                pointsConverted = 5;
                break;
            case "F2" :
                pointsConverted = 4;
                break;
            case "F3" :
                pointsConverted = 3;
                break;
            case "G1" :
                pointsConverted = 2;
                break;
            case "G2" :
                pointsConverted = 1;
                break;
            case "H" :
                pointsConverted = 0;
                break;
        }

        return pointsConverted;
    }

    public String getgradeConverted(){
        switch (this.point){
            case 22 :
                gradeConverted = "A1";
                break;
            case 21 :
                gradeConverted = "A2";
                break;
            case 20 :
                gradeConverted = "A3";
                break;
            case 19 :
                gradeConverted = "A4";
                break;
            case 18 :
                gradeConverted = "A5";
                break;
            case 17 :
                gradeConverted = "B1";
                break;
            case 16 :
                gradeConverted = "B2";
                break;
            case 15 :
                gradeConverted = "B3";
                break;
            case 14 :
                gradeConverted = "C1";
                break;
            case 13 :
                gradeConverted = "C2";
                break;
            case  12 :
                gradeConverted = "C3";
                break;
            case 11 :
                gradeConverted = "D1";
                break;
            case 10 :
                gradeConverted = "D2";
                break;
            case 9 :
                gradeConverted = "D3";
                break;
            case 8 :
                gradeConverted = "E1";
                break;
            case 7 :
                gradeConverted = "E2";
                break;
            case 6 :
                gradeConverted = "E3";
                break;
            case 5 :
                gradeConverted = "F1";
                break;
            case 4 :
                gradeConverted = "F2";
                break;
            case 3 :
                gradeConverted = "F3";
                break;
            case 2 :
                gradeConverted = "G2";
                break;
            case 1 :
                gradeConverted = "G2";
                break;
            case 0 :
                gradeConverted = "H";
                break;
        }

        return gradeConverted;
    }
}
