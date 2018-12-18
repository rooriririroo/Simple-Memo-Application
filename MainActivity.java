package com.example.soyeonlee.myapplication10;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long time= 0;

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

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }
}
