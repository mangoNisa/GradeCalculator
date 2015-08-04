package com.nisanabi.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class select_calc extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_calc, menu);
        return true;
    }

    public void click_grade(View v){
        Log.d("MYMESSAGE!!!!!", "Grade opened" );
        //Intent intent = new Intent(this, GradeActivity.class);
    }
    public void click_gpa(View v){
        Intent intent = new Intent(this, GpaActivity.class);
        startActivity(intent);
    }


}
