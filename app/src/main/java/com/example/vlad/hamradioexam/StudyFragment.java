package com.example.vlad.hamradioexam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class StudyFragment extends Fragment {

    private int question_counter = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        DatabaseHelper DBHelper = new DatabaseHelper(getContext());
        DBHelper.updateDataBase();
        final SQLiteDatabase DB = DBHelper.getWritableDatabase();
        Button last_question_button = view.findViewById(R.id.last_question);
        Button next_question_button = view.findViewById(R.id.next_question);
        final TextView question_number = view.findViewById(R.id.study_question_number);
        final TextView question_text = view.findViewById(R.id.study_question_text);
        final TextView question_variant1 = view.findViewById(R.id.study_question_variant1);
        final ImageView question_image = view.findViewById(R.id.study_question_image);
        final TextView question_variant2 = view.findViewById(R.id.study_question_variant2);
        final TextView question_variant3 = view.findViewById(R.id.study_question_variant3);
        final TextView question_variant4 = view.findViewById(R.id.study_question_variant4);

        Cursor cursor = DB.rawQuery("SELECT * FROM exam WHERE ID = " + question_counter, null);
        cursor.moveToFirst();
        String textNumber = "Вопрос №" + cursor.getInt(0);
        question_number.setText(textNumber);
        question_text.setText(cursor.getString(1));
        question_variant1.setText(cursor.getString(2));
        question_variant2.setText(cursor.getString(3));
        question_variant3.setText(cursor.getString(4));
        question_variant4.setText(cursor.getString(5));

        question_variant1.setTextColor(Color.BLACK);
        question_variant2.setTextColor(Color.BLACK);
        question_variant3.setTextColor(Color.BLACK);
        question_variant4.setTextColor(Color.BLACK);
        switch (cursor.getString(6)){
            case "a":
                question_variant1.setTextColor(Color.RED);
                break;
            case "b":
                question_variant2.setTextColor(Color.RED);
                break;
            case "c":
                question_variant3.setTextColor(Color.RED);
                break;
            case "d":
                question_variant4.setTextColor(Color.RED);
                break;
        }
        cursor.close();
        last_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter--;
                if (question_counter == 0) {
                    question_counter = 426;
                }
                Cursor cursor = DB.rawQuery("SELECT * FROM exam WHERE ID = " + question_counter, null);
                cursor.moveToFirst();
                String textNumber = "Вопрос №" + cursor.getInt(0);
                question_number.setText(textNumber);
                question_text.setText(cursor.getString(1));
                question_variant1.setText(cursor.getString(2));
                question_variant2.setText(cursor.getString(3));
                question_variant3.setText(cursor.getString(4));
                question_variant4.setText(cursor.getString(5));
                question_variant1.setTextColor(Color.BLACK);
                question_variant2.setTextColor(Color.BLACK);
                question_variant3.setTextColor(Color.BLACK);
                question_variant4.setTextColor(Color.BLACK);
                switch (cursor.getString(6)){
                    case "a":
                        question_variant1.setTextColor(Color.RED);
                        break;
                    case "b":
                        question_variant2.setTextColor(Color.RED);
                        break;
                    case "c":
                        question_variant3.setTextColor(Color.RED);
                        break;
                    case "d":
                        question_variant4.setTextColor(Color.RED);
                        break;
                    default:
                        question_variant1.setTextColor(Color.BLACK);
                        question_variant2.setTextColor(Color.BLACK);
                        question_variant3.setTextColor(Color.BLACK);
                        question_variant4.setTextColor(Color.BLACK);
                        break;
                }
                cursor.close();
            }
        });
        next_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter++;
                if (question_counter == 427){
                    question_counter = 1;
                }
                Cursor cursor = DB.rawQuery("SELECT * FROM exam WHERE ID = " + question_counter, null);
                cursor.moveToFirst();
                String textNumber = "Вопрос №" + cursor.getInt(0);
                question_number.setText(textNumber);
                question_text.setText(cursor.getString(1));
                question_variant1.setText(cursor.getString(2));
                question_variant2.setText(cursor.getString(3));
                question_variant3.setText(cursor.getString(4));
                question_variant4.setText(cursor.getString(5));

                question_variant1.setTextColor(Color.BLACK);
                question_variant2.setTextColor(Color.BLACK);
                question_variant3.setTextColor(Color.BLACK);
                question_variant4.setTextColor(Color.BLACK);
                switch (cursor.getString(6)){
                    case "a":
                        question_variant1.setTextColor(Color.RED);
                        break;
                    case "b":
                        question_variant2.setTextColor(Color.RED);
                        break;
                    case "c":
                        question_variant3.setTextColor(Color.RED);
                        break;
                    case "d":
                        question_variant4.setTextColor(Color.RED);
                        break;
                }
                cursor.close();
            }
        });

        return view;
    }


}
