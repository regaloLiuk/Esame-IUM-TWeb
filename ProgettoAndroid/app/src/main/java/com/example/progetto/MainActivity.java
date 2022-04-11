package com.example.progetto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private  TabLayout tabLayout;
    StoricoFrag storicoFrag=null;
    PrenotaFrag prenotaFrag=null;

   TextView utente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PrenotaFrag prenota=new PrenotaFrag();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, prenota).commit();
            Intent i=getIntent();
            String username=i.getStringExtra("utente");
            utente=(TextView)findViewById(R.id.utente);
            setTitle(username);
            utente.setText("");
        }






        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout= (TabLayout) findViewById(R.id.toolbar);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {



                storicoFrag = (StoricoFrag) mSectionsPagerAdapter.getCurrentFrag(1);
                prenotaFrag =(PrenotaFrag) mSectionsPagerAdapter.getCurrentFrag(0);

                if(tab.getPosition()==1){

                    if(prenotaFrag.getModify()){
                        storicoFrag.getStorico();
                        prenotaFrag.setModify(false);
                        System.out.println("Riscaricato lo storico");
                    }else
                        System.out.println("niente storico");

                }else{

                    storicoFrag = (StoricoFrag) mSectionsPagerAdapter.getCurrentFrag(1);
                    prenotaFrag =(PrenotaFrag) mSectionsPagerAdapter.getCurrentFrag(0);
                    if(storicoFrag.getModify()){
                         prenotaFrag.getPrenotazioni();
                        storicoFrag.setModify(false);
                        System.out.println("Riscaricato le prenotazioni");
                    }else
                        System.out.println("niente prenotazioni");


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }





    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        StoricoFrag storicoFrag = null;
        PrenotaFrag prenotaFrag = null;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment =null;
            switch (position) {
                case 0:
                    prenotaFrag = new PrenotaFrag();
                    fragment = prenotaFrag;
                    break;
                case 1:
                    storicoFrag = new StoricoFrag();
                    fragment = storicoFrag;
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Prenota";
                case 1:
                    return "Storico";

            }
            return null;
        }

        public Fragment getCurrentFrag(int i){
            if(i==0)
                return prenotaFrag;
            else
                return storicoFrag;
        }

    }


}



