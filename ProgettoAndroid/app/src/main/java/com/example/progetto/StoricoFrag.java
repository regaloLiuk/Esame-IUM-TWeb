package com.example.progetto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;



public class StoricoFrag extends Fragment implements StoricoRecyclerViewAdapter.ItemClickListener {

    //  private ListView listView;

    private boolean modify = false;


    // private  CustomAdapterStorico arrayAdapter;
    private StoricoRecyclerViewAdapter arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.storico_fragment, container, false);

        //listView = (ListView)rootView.findViewById(R.id.storicoList);
        ArrayList<Storico> list = new ArrayList<Storico>();


        //    arrayAdapter = new CustomAdapterStorico(getContext(),R.layout.recyclerview_row,list);
        //  listView.setAdapter(arrayAdapter);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.storicoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayAdapter = new StoricoRecyclerViewAdapter(getActivity(), list, this);
        arrayAdapter.setClickListener(this);
        recyclerView.setAdapter(arrayAdapter);


        // recyclerView.addItemDecoration(new StoricoRecyclerViewAdapter.SeparatorDecoration(Color.rgb(0,0,0),10));


        getStorico();


        return rootView;
    }

    public void getStorico() {
        StoricoHttp storico = new StoricoHttp(getActivity().getTitle().toString(), getActivity().getApplicationContext(), arrayAdapter);
        try {
            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
            storico.execute(url);
        } catch (MalformedURLException e) {
        }
    }

    public void action(String action, View parentItem, int positionItem, StoricoRecyclerViewAdapter arrayAdapter) {
        StringTokenizer tokenizer = new StringTokenizer(arrayAdapter.getItem(positionItem).toString(), ",");

        System.out.println(arrayAdapter.getItem(positionItem).toString());
        tokenizer.nextToken();

        String prof = tokenizer.nextToken();
        String mat = tokenizer.nextToken();
        String gg = tokenizer.nextToken();
        String ora = tokenizer.nextToken();

        System.out.println(getActivity().getTitle().toString() + " " + prof + " " + mat + " " + ora + " " + gg);

        Effettua_DisdiciHttp prenota = new Effettua_DisdiciHttp(getActivity().getTitle().toString(), mat, prof, gg, ora, action, getActivity().getApplicationContext(), arrayAdapter, positionItem);
        try {
            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
            prenota.execute(url);
        } catch (MalformedURLException e) {
        }

    }

    public boolean getModify() {
        return this.modify;
    }

    public void setModify(boolean mod) {
        this.modify = mod;
    }


    @Override
    public void onItemClick(final View view, int position) {

        StringTokenizer tokenizer=new StringTokenizer(arrayAdapter.getItem(position).toString(),",");
        tokenizer.nextToken();
        String prof=tokenizer.nextToken();
        String mat=tokenizer.nextToken();
        String gg=tokenizer.nextToken();
        String ora=tokenizer.nextToken();

        Toast.makeText(getContext().getApplicationContext(),"Docente: "+prof+" Materia: "+mat+" Giorno: "+gg+" Ora: "+ora, Toast.LENGTH_LONG).show();


    }

}