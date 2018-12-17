package com.example.soyeonlee.myapplication10;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        Fragment[] arrFragments = new Fragment[2];
        arrFragments[0] = new TabFragment1();
        arrFragments[1] = new TabFragment2();


        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),arrFragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
