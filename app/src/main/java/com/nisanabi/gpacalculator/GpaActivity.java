package com.nisanabi.gpacalculator;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GpaActivity extends AppCompatActivity {

    ArrayList<GpaActivityFragment> modules = new ArrayList<GpaActivityFragment>();
    GpaActivityFragment fragGpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        try{
            displayData(getCurrentFocus());
        }catch(Exception e){

        }
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

    /**
     * Calculates the total points awarded by multiplying the credits by
     * each grade
     * @param grade list of grades with the same number of credits awarded
     * @param credit amount of credits awarded
     * @return the total number of points awarded
     */
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
     * Calculates the total number of credits stored in the map
     * @param grade the map that contains all the grades and credits
     * @return total number of credits awarded
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

    /**
     * Adds a GpaActivityFragment to the activity. Allows user add another grade and credit
     */
    public void addModule(){

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

    /**
     * Calculates the GPA.
     */
    private void calcGPA() {
       int i = 0;

        /*
           Find all the modules that have data filled for both grade and credit
         */
        for(GpaActivityFragment frag : modules){
            if(frag.check()) {
                //displayAlert("Missing some data in " + frag.check());
                //break
                frag.submit(i,frag.getGrade(),frag.getCredit());
                i++;

            }
        }

        GradeMapSingleton grademap = GradeMapSingleton.getInstance();

        Iterator entries = grademap.getGradeMap().entrySet().iterator();

        double gradepoints = 0;
        double totalcredit = 0;
        double gpa;

        for(Object key : grademap.getGradeMap().keySet()){
            int k = (Integer) key;
            HashMap<String, Integer> data = (HashMap) grademap.getGradeMap().get(k) ;

            int grade = data.get("Grade");
            int credit = data.get("Credit");


            int points = grade*credit;

            totalcredit += credit;
            gradepoints += points;
        }

        gpa = gradepoints/totalcredit;
        String answer = new DecimalFormat("##.##").format(gpa);
        //find the grade assiated with the points awarded
        ConvertGradePoint pointsToGrade= new ConvertGradePoint((int) Math.round(Double.parseDouble(answer)));
        displayAlert("Your GPA is: " + answer + "\n" + "Grade: " + pointsToGrade.getgradeConverted());
    }

    public void saveData(View view){

        SharedPreferences sharedPref = getSharedPreferences("gpaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //saves each item in grademap to the editor
        GradeMapSingleton grademap = GradeMapSingleton.getInstance();
        for(Object key: grademap.getGradeMap().keySet()){
            int k = (int) key;
            HashMap<String, Integer> data = (HashMap) grademap.getGradeMap().get(k);
            editor.putString(key.toString(), data.get("Grade") + "," + data.get("Credit"));
        }
        editor.apply();

        Toast.makeText(this, "Huzzah! Saved", Toast.LENGTH_LONG).show();
    }

    public void displayData(View view){

        SharedPreferences sharedPref = getSharedPreferences("gpaData", Context.MODE_PRIVATE);
        Map<String, Object> grademap =(Map) sharedPref.getAll();
        //create the modules to add the data to
        for(int i = 0; i<grademap.size(); i++) addModule();
        int i = 0;
        for(String key: grademap.keySet()){
            String[] gradecredit = getGradeCredit(grademap.get(key).toString());
            int grade = Integer.parseInt(gradecredit[0]);
            int credit = Integer.parseInt(gradecredit[1]);

            GpaActivityFragment frag = modules.get(i);
            frag.setGrade(grade);
            frag.setCredit(credit);

            i++;
        }
    }
    /**
     * Find the grade and credit couple
     * @param a Grade and credits values separated with a comma
     * @return An array with the grade at index 0 and credit at index 0
     */
    public String[] getGradeCredit(String a){
        String[] array = a.split(",");
        return array;
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