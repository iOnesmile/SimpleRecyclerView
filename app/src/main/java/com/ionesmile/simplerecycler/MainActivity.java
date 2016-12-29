package com.ionesmile.simplerecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ionesmile.simplerecycler.fragment.MusicListFragment;

/**
 * Created by iOnesmile on 2016/12/28 0020.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, new MusicListFragment()).commit();
    }
}
