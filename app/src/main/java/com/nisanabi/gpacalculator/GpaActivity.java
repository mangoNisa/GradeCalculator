package com.nisanabi.gpacalculator;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GpaActivity extends AppCompatActivity {

    ArrayList<GpaActivityFragment> modules = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        //for ad
        AdBuddiz.setPublisherKey("9dfbf393-97a9-4ee8-a2fe-904d48c81cba");
        AdBuddiz.cacheAds(this);

       // try{
        displayData(getCurrentFocus());
        //}catch(Exception e){
         //  Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        //}
    }
    /**
     * Adds a GpaActivityFragment to the activity. Allows user add another grade and credit
     */
    public void addModule(){

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GpaActivityFragment fragGpa = new GpaActivityFragment();
            fragmentTransaction.add(R.id.gpa_fragment_container, fragGpa, modules.size()+"");
            fragmentTransaction.commit();

            modules.add(fragGpa);
    }



    public void displayAlert(String alert){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPA Result")
                .setMessage(alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AdBuddiz.showAd(GpaActivity.this);
                    }
                })
                .create()
                .show();
    }

    private void storeModules(){
        int i = 0;

        // Find all the modules that have data filled for both grade and credit

        for(GpaActivityFragment frag : modules){
            if(frag.check()) {

                //displayAlert("Missing some data in " + frag.check());
                //break
                System.out.println(frag.getGrade() + " " + frag.getCredit());
                frag.submit(i, frag.getGrade(), frag.getCredit());
                i++;

            }
        }
    }

    /**
     * Calculates the GPA.
     */
    private void calcGPA() {

        storeModules();

        GradeMapSingleton grademap = GradeMapSingleton.getInstance();

        double gradepoints = 0;
        double totalcredit = 0;
        double gpa;

        for(HashMap<String, Integer> item: grademap.getGradeMap()){

            int grade = item.get("Grade");
            int credit = item.get("Credit");

            System.out.println(grade+" "+credit);
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
        storeModules();

        SharedPreferences sharedPref = getSharedPreferences("gpaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //saves each item in grademap to the editor
        GradeMapSingleton grademap = GradeMapSingleton.getInstance();
        for(HashMap<String, Integer> item: grademap.getGradeMap()){

            editor.putString(item.toString(), item.get("Grade") + "," + item.get("Credit"));
        }
        editor.apply();

        Toast.makeText(this, "Huzzah! Saved", Toast.LENGTH_LONG).show();
    }

    public void displayData(View view){

        SharedPreferences sharedPref = getSharedPreferences("gpaData", Context.MODE_PRIVATE);
        Map<String, Object> grademap =(Map) sharedPref.getAll();

        //create the modules to add the data to
        for(int i = 0; i<grademap.size(); i++)addModule();

        int i = 0;
        if(grademap.size()>0) {
            for (String key : grademap.keySet()) {
                GpaActivityFragment frag = modules.get(i);
                String grade = grademap.get(key).toString().split(",")[0];
                String credit = grademap.get(key).toString().split(",")[1];
                System.out.println(" im here");

                frag.setPrefGrade(grade);
                frag.setPrefCredit(credit);
                i++;
            }
        }
    }
    /**
     * Find the grade and credit couple
     * @param a Grade and credits values separated with a comma
     * @return An array with the grade at index 0 and credit at index 0
     */
    public String[] getGradeCredit(String a){
        return a.split(",");
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
            case R.id.action_save_data:
                saveData(getCurrentFocus());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}