package com.example.vlad.hamradioexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class StudyFragment extends Fragment {

    private int question_counter;

    TextView question_text;
    TextView question_number;
    ImageView question_image;
    TextView question_variant1;
    TextView question_variant2;
    TextView question_variant3;
    TextView question_variant4;
    Button last_question_button;
    Button next_question_button;

    SharedPreferences sharedPreferences;

    public static final String APP_PREFERENCES_LAST_QUESTION_STUDY = "last_checked_question_study";

    private void showQuestion(SQLiteDatabase DB) {
        Cursor cursor = DB.rawQuery("SELECT * FROM exam WHERE ID = " + question_counter, null);//Вывести билет с ID == question_counter
        cursor.moveToFirst();

        String textNumber = "Вопрос №" + cursor.getInt(0);
        question_number.setText(textNumber);
        question_text.setText(cursor.getString(1));
        question_variant1.setText(cursor.getString(2));
        question_variant2.setText(cursor.getString(3));//Выводим содержание билета
        question_variant3.setText(cursor.getString(4));
        question_variant4.setText(cursor.getString(5));

        question_variant1.setTextColor(getResources().getColor(R.color.colorTextSecondary));
        question_variant2.setTextColor(getResources().getColor(R.color.colorTextSecondary));//Обнуляем цвета вариантов ответов
        question_variant3.setTextColor(getResources().getColor(R.color.colorTextSecondary));
        question_variant4.setTextColor(getResources().getColor(R.color.colorTextSecondary));

        switch (cursor.getString(6)) {//Подсвечиваем правильный вариант ответа красным
            case "a":
                question_variant1.setTextColor(getResources().getColor(R.color.correct_answer_color));
                break;
            case "b":
                question_variant2.setTextColor(getResources().getColor(R.color.correct_answer_color));
                break;
            case "c":
                question_variant3.setTextColor(getResources().getColor(R.color.correct_answer_color));
                break;
            case "d":
                question_variant4.setTextColor(getResources().getColor(R.color.correct_answer_color));
                break;
        }
        cursor.close();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        DatabaseHelper DBHelper = new DatabaseHelper(getContext());
        DBHelper.updateDataBase();
        final SQLiteDatabase DB = DBHelper.getWritableDatabase();

        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(APP_PREFERENCES_LAST_QUESTION_STUDY, Context.MODE_PRIVATE);

        last_question_button = view.findViewById(R.id.last_question);
        next_question_button = view.findViewById(R.id.next_question);

        question_number = view.findViewById(R.id.study_question_number);
        question_text = view.findViewById(R.id.study_question_text);
        question_variant1 = view.findViewById(R.id.study_question_variant1);
        question_image = view.findViewById(R.id.study_question_image);
        question_variant2 = view.findViewById(R.id.study_question_variant2);
        question_variant3 = view.findViewById(R.id.study_question_variant3);
        question_variant4 = view.findViewById(R.id.study_question_variant4);

        if (!sharedPreferences.contains(APP_PREFERENCES_LAST_QUESTION_STUDY)){
            question_counter = 1;
        }else{
            question_counter = sharedPreferences.getInt(APP_PREFERENCES_LAST_QUESTION_STUDY, 1);
        }

        showQuestion(DB);//открываем при загрузке



        last_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter--;
                if (question_counter == 0) {
                    question_counter = 426;
                }
                showQuestion(DB);//включаем предыдущий
            }
        });
        next_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter++;
                if (question_counter == 427) {
                    question_counter = 1;
                }
                showQuestion(DB);//включаем следующий
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_PREFERENCES_LAST_QUESTION_STUDY, question_counter);
        editor.apply();
    }
}
