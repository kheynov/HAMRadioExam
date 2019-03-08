package com.example.vlad.hamradioexam;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class ExamFragment extends Fragment {
    private int question_counter;

    TextView question_text;
    TextView question_number;
    ImageView question_image;
    TextView question_variant1;
    TextView question_variant2;
    TextView question_variant3;
    TextView question_variant4;
    ImageButton last_question_button;
    ImageButton next_question_button;

    int[] category_1_questions = {};
    int[] category_2_questions = {};
    int[] category_3_questions = {};
    int[] category_4_questions = {};

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

        int image_id = cursor.getInt(7);
        Log.i("INFO", String.valueOf(image_id));

        if (image_id >= 1 && image_id <= 18) {
            question_image.setVisibility(View.VISIBLE);

            switch (image_id) {
                case 1:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam1", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 2:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam2", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 3:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam3", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 4:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam4", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 5:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam5", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 6:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam6", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 7:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam7", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 8:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam8", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 9:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam9", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 10:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam10", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 11:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam11", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 12:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam12", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 13:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam13", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 14:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam14", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 15:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam15", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 16:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam16", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 17:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam17", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
                case 18:
                    question_image.setImageResource(getResources().getIdentifier("@drawable/exam18", "drawable", Objects.requireNonNull(getActivity()).getPackageName()));
                    break;
            }

        } else {
            question_image.setVisibility(View.INVISIBLE);
            question_image.setImageResource(R.color.transparent);
        }
        cursor.close();
    }

    @SuppressLint("ClickableViewAccessibility")
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
        if (!sharedPreferences.contains(APP_PREFERENCES_LAST_QUESTION_STUDY)) {
            question_counter = 1;
        } else {
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

        question_variant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter++;
                if (question_counter == 427) {
                    question_counter = 1;
                }
                showQuestion(DB);//включаем следующий
            }
        });
        question_variant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter++;
                if (question_counter == 427) {
                    question_counter = 1;
                }
                showQuestion(DB);//включаем следующий
            }
        });
        question_variant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_counter++;
                if (question_counter == 427) {
                    question_counter = 1;
                }
                showQuestion(DB);//включаем следующий
            }
        });
        question_variant4.setOnClickListener(new View.OnClickListener() {
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
}
