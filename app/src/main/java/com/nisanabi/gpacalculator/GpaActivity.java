package com.nisanabi.gpacalculator;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        //displayData(getCurrentFocus());
        //}catch(Exception e){
         //  Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        //}
    }

    /*@Override
    public void onResume(){
        super.onResume();
        storeModules();
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println(GradeMapSingleton.getInstance().getGradeMap().size() + " +++++");
        saveData(getCurrentFocus());
    }*/

    /**
     * Calculates the GPA.
     */
    private void calcGPA() {

        storeModules();
        //List<Fragment> allFragments = getFragmentManager().getFragments();
        //GradeMapSingleton grademap = GradeMapSingleton.getInstance();

        double gradepoints = 0;
        double totalcredit = 0;
        double gpa = 0;

        for(GpaActivityFragment frag : modules){
            int grade = frag.getGrade();
            int credit = frag.getCredit();

            int points = grade*credit;

            totalcredit += credit;
            gradepoints += points;
        }

        gpa = gradepoints/totalcredit;
        String answer = new DecimalFormat("##.##").format(gpa);
        if(Double.isNaN(gpa)) answer = "0";
        //find the grade assiated with the points awarded
        ConvertGradePoint pointsToGrade= new ConvertGradePoint((int) Math.round(Double.parseDouble(answer)));
        displayAlert("Your GPA is: " + answer + "\n" + "Grade: " + pointsToGrade.getgradeConverted());
    }

    /**
     * Adds a GpaActivityFragment to the activity. Allows user add another grade and credit
     */
    public void addModule(){

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GpaActivityFragment fragGpa = new GpaActivityFragment();
            String id = (modules.isEmpty()) ? "0":modules.size()+"";
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
        for(GpaActivityFragment frag: modules){
            if(frag.check()) {
                //displayAlert("Missing some data in " + frag.check());
                //break
                frag.submit(i, frag.getGrade(), frag.getCredit());
                i++;
            }
        }
    }

    public void saveData(View view){
        storeModules();

        SharedPreferences sharedPref = getSharedPreferences("gpaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //saves each item in grademap to the editor
        GradeMapSingleton grademap = GradeMapSingleton.getInstance();
        System.out.println("SAVING");
        for(HashMap<String, Integer> item: grademap.getGradeMap()){

            editor.putString(item.toString(), item.get("Grade") + "," + item.get("Credit"));
        }
        editor.apply();
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

                frag.setPrefGrade(grade);
                frag.setPrefCredit(credit);
                i++;
            }
        }
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