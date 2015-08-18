package com.nisanabi.gpacalculator;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GpaActivityFragment extends Fragment {

    TextView add_grade, add_credit;
    int gradepoint, credit;
    View  view;
    GradeMapSingleton grademap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gpa, container, false);
        System.out.println(view.toString()+"doing it oncreateview");
        grademap = GradeMapSingleton.getInstance();

        add_grade = (TextView) view.findViewById(R.id.text_grade);
        add_credit = (TextView) view.findViewById(R.id.text_credit);


        add_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int grade_list = R.array.list_grade;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_grade)
                        .setItems(grade_list, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String grade = getResources().getStringArray(R.array.list_grade)[which]+"";
                                setGrade(grade);
                                /*add_grade.setText(grade);
                                ConvertGradePoint points = new ConvertGradePoint(grade);
                                gradepoint = points.getpointsConverted();*/
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
                                    Toast.makeText(getActivity(),"Credit set as 0", Toast.LENGTH_SHORT).show();
                                } else {
                                    value = input.getText().toString();
                                }
                                setCredit(value);
                                /*add_credit.setText(value);
                                credit = Integer.parseInt(value);*/                            }
                        });
                builder.create();
                builder.show();
            }
        });

        return view;
    }

    public int getGrade(){
        return this.gradepoint;
    }

    public int getCredit(){
        return this.credit;
    }

    public void setGrade(String grade){

        ConvertGradePoint points = new ConvertGradePoint(grade);
        add_grade = (TextView) view.findViewById(R.id.text_grade);
        add_grade.setText(grade);
        System.out.println("blo??????????");

        this.gradepoint = points.getpointsConverted();
        System.out.println("BLAYYYYYYYYYYYYYYYYYYYYY");


    }

    public void setCredit(String credit){
        add_credit = (TextView) view.findViewById(R.id.text_credit);
        add_credit.setText(credit);
        this.credit = Integer.parseInt(credit);

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