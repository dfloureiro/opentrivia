package com.dfl.trivia.main;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dfl.trivia.R;
import com.dfl.trivia.networking.RequestFactory;

public class MainActivity extends AppCompatActivity {

    //https://opentdb.com/api_config.php
    //https://github.com/googlesamples/android-architecture/blob/todo-mvp/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/addedittask/AddEditTaskActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainActivityFragment mainActivityFragment = (MainActivityFragment) getFragmentManager()
                .findFragmentById(R.id.content_main);

        if (mainActivityFragment == null) {
            mainActivityFragment = MainActivityFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.content_main, mainActivityFragment);
            transaction.commit();
        }

        new MainPresenter(mainActivityFragment, new RequestFactory());
    }

}
