package com.nisanabi.myapplication;

/**
 * Created by Nisa on 05/07/2015.
 */
public class GradetoPoints {

    String grade;
    int points = 0;

    public GradetoPoints(String grade){
        this.grade = grade;
    }

    public int getPoints(){
        switch (this.grade){
            case "A1" :
                points = 22;
                break;
            case "A2" :
                points = 21;
                break;
            case "A3" :
                points = 20;
                break;
            case "A4" :
                points = 19;
                break;
            case "A5" :
                points = 18;
                break;
            case "B1" :
                points = 17;
                break;
            case "B2" :
                points = 16;
                break;
            case "B3" :
                points = 15;
                break;
            case "C1" :
                points = 14;
                break;
            case "C2" :
                points = 13;
                break;
            case "C3" :
                points = 12;
                break;
            case "D1" :
                points = 11;
                break;
            case "D2" :
                points = 10;
                break;
            case "D3" :
                points = 9;
                break;
            case "E1" :
                points = 8;
                break;
            case "E2" :
                points = 7;
                break;
            case "E3" :
                points = 6;
                break;
            case "F1" :
                points = 5;
                break;
            case "F2" :
                points = 4;
                break;
            case "F3" :
                points = 3;
                break;
            case "G1" :
                points = 2;
                break;
            case "G2" :
                points = 1;
                break;
            case "H" :
                points = 0;
                break;
        }

        return points;
    }
}
