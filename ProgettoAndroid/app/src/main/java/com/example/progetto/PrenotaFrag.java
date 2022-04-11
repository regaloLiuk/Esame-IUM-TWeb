package com.example.progetto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PrenotaFrag extends Fragment implements PrenotaRecyclerViewAdapter.ItemClickListener{
    Spinner prof;
    Spinner mat;
    Spinner gg;
    Spinner ora;

    TextView utente;
    boolean modify=false;

    String profS="";
    String matS="";
    String ggS="";
    String oraS="";
    PrenotaRecyclerViewAdapter  arrayAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prenota_fragment, container, false);

        Button b=(Button)rootView.findViewById(R.id.bottone);
        prof=(Spinner)rootView.findViewById(R.id.prof);
        mat=(Spinner)rootView.findViewById(R.id.mat);
        gg=(Spinner)rootView.findViewById(R.id.gg);
        ora=(Spinner)rootView.findViewById(R.id.ora);
        utente=(TextView) rootView.findViewById(R.id.utente);

        ArrayList<Ripetizione> list = new   ArrayList<Ripetizione>();



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ripetizioniList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayAdapter = new PrenotaRecyclerViewAdapter(getActivity(),getActivity(), list,this);
        arrayAdapter.setClickListener(this);
        recyclerView.setAdapter(arrayAdapter);


        ArrayList<String> arrayProf = new ArrayList<String>();
        ArrayList<String> arrayMat = new ArrayList<String>();



        SpinnerHttp spinProf=new SpinnerHttp(arrayProf,getContext(),prof,0);
        try{
            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
            spinProf.execute(url);
        }catch(MalformedURLException e){}

        SpinnerHttp spinMat=new SpinnerHttp(arrayMat,getContext(),mat,1);
        try{
            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
            spinMat.execute(url);
        }catch(MalformedURLException e){}




        b.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                profS=prof.getSelectedItem().toString();
                matS=mat.getSelectedItem().toString();
                ggS=gg.getSelectedItem().toString();
                oraS=ora.getSelectedItem().toString();

                getPrenotazioni();


            }

        });


        return rootView;
    }



    public void getPrenotazioni(){
        HttpJson json = new HttpJson(profS, matS,oraS,ggS,arrayAdapter,getContext());
        try{
            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
            json.execute(url);
        }catch(MalformedURLException e){}
    }

    public boolean getModify(){
        return this.modify;
    }

    public void setModify(boolean mod){
        this.modify=mod;
    }


    @Override
    public void onItemClick(final View view, int position) {
        StringTokenizer tokenizer=new StringTokenizer(arrayAdapter.getItem(position).toString(),",");

        String prof=tokenizer.nextToken();
        String mat=tokenizer.nextToken();
        String gg=tokenizer.nextToken();
        String ora=tokenizer.nextToken();

        Toast.makeText(getContext().getApplicationContext(),"Docente: "+prof+" Materia: "+mat+" Giorno: "+gg+" Ora: "+ora, Toast.LENGTH_LONG).show();

    }



}
