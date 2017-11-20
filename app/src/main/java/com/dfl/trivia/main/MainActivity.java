package com.dfl.trivia.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.dfl.trivia.R;
import com.dfl.trivia.networking.RequestFactory;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    MainActivityFragment mainActivityFragment =
        (MainActivityFragment) getFragmentManager().findFragmentById(R.id.content_main);

    if (mainActivityFragment == null) {
      mainActivityFragment = MainActivityFragment.newInstance();
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.add(R.id.content_main, mainActivityFragment);
      transaction.commit();
    }

    new MainPresenter(mainActivityFragment, new RequestFactory(),
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
  }
}
