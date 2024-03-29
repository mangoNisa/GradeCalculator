package com.nisanabi.gpacalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class GpaActivityFragment extends Fragment {

    TextView add_grade, add_credit;
    int gradepoint, credit;
    String prefGrade = "";
    String prefCredit = "";
    GradeMapSingleton grademap;
    ImageButton btn_remove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gpa, container, false);
        grademap = GradeMapSingleton.getInstance();

        add_grade = (TextView) view.findViewById(R.id.text_grade);
        add_credit = (TextView) view.findViewById(R.id.text_credit);
        //btn_remove = (ImageButton) view.findViewById(R.id.btn_remove);

        if(!prefGrade.equals("") && !prefCredit.equals("")){
            add_grade.setText(prefGrade);
            add_credit.setText(prefCredit);
        }

        add_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int grade_list = R.array.list_grade;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_grade)
                        .setItems(grade_list, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String grade = getResources().getStringArray(R.array.list_grade)[which] + "";
                                add_grade.setText(grade);
                                ConvertGradePoint points = new ConvertGradePoint(grade);
                                gradepoint = points.getpointsConverted();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        add_credit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Credits")
                        .setMessage("How many credits were you awarded?");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value;
                                if (input.getText().toString().equals("")) {
                                    value = "0";
                                    Toast.makeText(getActivity(), "Credit set as 0", Toast.LENGTH_SHORT).show();
                                } else {
                                    value = input.getText().toString();
                                }
                                add_credit.setText(value);
                                credit = Integer.parseInt(value);
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        /*btn_remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPref = GpaActivityFragment.this.getActivity().getSharedPreferences("gpaData", Context.MODE_PRIVATE);
                Map<String, Object> prefMap =(Map) sharedPref.getAll();

                for(String key : prefMap.keySet()){
                    String grade = prefMap.get(key).toString().split(",")[0];
                    String credit = prefMap.get(key).toString().split(",")[1];
                    System.out.println(credit + " " + getCredit());
                    System.out.println(credit + " " + getCredit());

                    if(grade.equals(getGrade()+"") && credit.equals(getCredit()+"")){
                        System.out.println(grade + " " + getGrade()+ " delete");
                        sharedPref.edit().remove(key).apply();
                    }
                }
                System.out.println(prefMap.size());
                grademap.remove(getGrade(), getCredit());

                //remove the fragment from disply
                getActivity().getFragmentManager().beginTransaction().remove(GpaActivityFragment.this).commit();
            }
        });
*/
        return view;
    }

    public int getGrade(){
        return this.gradepoint;
    }

    public int getCredit(){
        return this.credit;
    }

    public void setPrefGrade(String grade){
        int g = Integer.parseInt(grade);
        ConvertGradePoint pointsToGrade= new ConvertGradePoint(g);
        prefGrade = pointsToGrade.getgradeConverted();
        gradepoint = g;
    }

    public void setPrefCredit(String credit){
        this.credit = Integer.parseInt(credit);
        prefCredit = credit;
    }


    /**
     * Checks if the credit and grade text boxes have values
     * @return true if textboxes contain data
     */
    public boolean check(){
        return !(add_credit.getText().toString().equals("Add")|| add_grade.getText().toString().equals("Add"));
    }

    public void submit(int i, int a, int b){
        grademap.add(i, a, b);
    }

}