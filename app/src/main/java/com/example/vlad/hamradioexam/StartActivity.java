package com.example.vlad.hamradioexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;

    public static final String APP_PREFERENCES_QUESTION_COUNTER = "question_counter_preference";
    private static long back_pressed;
    android.support.v4.app.Fragment fragment = null;
    Class fragmentClass = StudyFragment.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = Objects.requireNonNull(this.getSharedPreferences(APP_PREFERENCES_QUESTION_COUNTER, Context.MODE_PRIVATE));
        //Активируем шторку управления
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Запускаем активити с обучение по умолчанию после запуска приложения
        try {
            fragment = (android.support.v4.app.Fragment) Objects.requireNonNull(fragmentClass).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        //--------------------------------------------------------------
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (fragmentClass.equals(StudyFragment.class)) {
            getMenuInflater().inflate(R.menu.study, menu);
        } else {
            getMenuInflater().inflate(R.menu.start, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Fragment fragment = null;
            Class fragmentClass = SettingsFragment.class;
            try {
                fragment = (Fragment) Objects.requireNonNull(fragmentClass).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        }/* else if (id == R.id.action_go_to_page) {
            final int old_question_counter = sharedPreferences.getInt(APP_PREFERENCES_LAST_QUESTION_STUDY, 1);
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this).
                    setTitle("Перейти на страницу").
                    setView(input).
                    setCancelable(false).
                    setPositiveButton(R.string.go_to_page, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(APP_PREFERENCES_LAST_QUESTION_STUDY, Integer.valueOf(input.getText().toString()));
                            String debug = input.getText().toString();
                            editor.apply();

                            Fragment fragment = null;
                            Class fragmentClass = StudyFragment.class;
                            try {
                                fragment = (Fragment) Objects.requireNonNull(fragmentClass).newInstance();
                            } catch (IllegalAccessException | InstantiationException e) {
                                e.printStackTrace();
                            }
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
                            dialog.dismiss();
                        }
                    }).
                    setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(APP_PREFERENCES_LAST_QUESTION_STUDY, old_question_counter);
                            editor.apply();

                            Fragment fragment = null;
                            Class fragmentClass = StudyFragment.class;
                            try {
                                fragment = (Fragment) Objects.requireNonNull(fragmentClass).newInstance();
                            } catch (IllegalAccessException | InstantiationException e) {
                                e.printStackTrace();
                            }
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
                            dialog.dismiss();
                        }
                    }).show();

        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (id == R.id.nav_exam) {
            fragmentClass = ExamFragment.class;
        } else if (id == R.id.nav_study) {
            fragmentClass = StudyFragment.class;
        } else if (id == R.id.nav_book) {
            fragmentClass = BookFragment.class;
        } else if (id == R.id.nav_stats) {
            fragmentClass = StatsFragment.class;
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutFragment.class;
        } else if (id == R.id.nav_settings) {
            fragmentClass = SettingsFragment.class;
        }

        try {
            fragment = (android.support.v4.app.Fragment) Objects.requireNonNull(fragmentClass).newInstance();
        } catch (IllegalAccessException | NullPointerException | InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exit(MenuItem item) {
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (back_pressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                Snackbar.make(Objects.requireNonNull(getCurrentFocus()), "Нажмите еще раз для выхода", Snackbar.LENGTH_SHORT).show();
            }
        }
        back_pressed = System.currentTimeMillis();
    }
}
