package com.nisanabi.myapplication;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GpaActivity extends ActionBarActivity {

    ArrayList<GpaActivityFragment> modules = new ArrayList<GpaActivityFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
    }

    public int findGPA(HashMap<Integer, ArrayList<Integer>> grade){
        int totalPoints = 0 ;
        int totalCredit = findTotalCredit(grade);

        for(HashMap.Entry<Integer, ArrayList<Integer>> entry: grade.entrySet()){
            int points = totalPoints(entry.getValue(), entry.getKey());
            totalPoints += points;
        }

        return totalPoints/totalCredit;
    }

    public int totalPoints(ArrayList<Integer> grade, int credit){
        int points = 0;
        int totalPoints = 0;

        for(Integer g: grade){
            points += g*credit;
            totalPoints += points;
        }

        return totalPoints;
    }
    /**
     * findTotalCredit calculates the total number of credits stored in the map
     * @param grade
     * @return
     */
    public int findTotalCredit(HashMap<Integer, ArrayList<Integer>> grade){
        int totalCredit = 0;

        //calculate number of grades with key num of credits and add to total credits
        for(HashMap.Entry<Integer, ArrayList<Integer>> entry: grade.entrySet()){
            int credit = entry.getKey()*entry.getValue().size();
            totalCredit += credit;
        }

        return totalCredit;
    }

    public void addModule(){
        //if(modules.isEmpty()/* || modules.get(lastModuleID).getGradeBox().getText() != "Add"
               // || modules.get(lastModuleID).getCreditBox().getText() != "Add"*/) {



            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GpaActivityFragment fragGpa = new GpaActivityFragment();
            fragmentTransaction.add(R.id.gpa_fragment_container, fragGpa, "");
            fragmentTransaction.commit();

            modules.add(fragGpa);
    }

    public void displayAlert(String alert){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(alert)
                .setNeutralButton("ok", null)
                .create()
                .show();
    }


    private void calcGPA() {
        int i = 0;
        for(GpaActivityFragment frag : modules){
            if(frag.check()) {
                //displayAlert("Missing some data in " + frag.check());
                //break;
                System.out.println("FRAG no: " + i + "  Frag : " + frag);
                frag.submit(i,frag.getGrade(),frag.getCredit());
                i++;

            }
        }

        GradeMapSingleton grademap = GradeMapSingleton.getInstance();

        Iterator entries = grademap.getGradeMap().entrySet().iterator();

        double gradepoints = 0;
        double totalcredit = 0;
        double gpa = 0;

        for(Object key : grademap.getGradeMap().keySet()){
            int k = (Integer) key;
            HashMap<String, Integer> data = (HashMap) grademap.getGradeMap().get(k) ;

            int grade = data.get("Grade");
            int credit = data.get("Credit");
            System.out.println("bla credit: " + totalcredit + "+" + credit + " keyyyy : " + k);
            System.out.println("bla points:" + grade + " x " + credit + " = " + grade*credit);

            int points = grade*credit;

            totalcredit += credit;
            gradepoints += points;
        }

        System.out.println("total points:" + gradepoints);
        System.out.println("total credit:" + totalcredit);
        System.out.println("gpa:" + gradepoints/totalcredit);


        gpa = gradepoints/totalcredit;
        String answer = new DecimalFormat("##.##").format(gpa);
        displayAlert("Your GPA is: " + answer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_add_module:
                addModule();
                break;
            case R.id.action_calculate_gpa:
                calcGPA();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}