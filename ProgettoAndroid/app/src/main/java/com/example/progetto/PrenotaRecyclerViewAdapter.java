package com.example.progetto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

public class PrenotaRecyclerViewAdapter extends RecyclerView.Adapter<PrenotaRecyclerViewAdapter.ViewHolder> {

    private java.util. List<Ripetizione> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int cont=0;
    private Context context;
    private PrenotaRecyclerViewAdapter arrayAdapter;
    private PrenotaFrag pre;
    Activity a;

    public java.util. List<Ripetizione> getmData(){
        return this.mData;
    }
    // data is passed into the constructor
    PrenotaRecyclerViewAdapter(Activity a,Context context, List<Ripetizione> data, PrenotaFrag pre) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.arrayAdapter=this;
        this.pre=pre;
        this.a=a;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Ripetizione st = mData.get(position);
        holder.prof.setText("Professore: \t\t"+st.getProf());
        holder.mat.setText("Materia:    \t\t"+st.getMat());
        holder.gg.setText("Giorno:     \t\t"+st.getGG());
        holder.ora.setText("Ora:        \t\t"+st.getOra());
        holder.prenota.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //****************

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Cosa vuoi fare");
                builder.setMessage("Prenotare?");

                builder.setPositiveButton("no", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                final int positionS=position;

                builder.setNegativeButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringTokenizer tokenizer=new StringTokenizer(arrayAdapter.getItem(positionS).toString(),",");

                        String prof=tokenizer.nextToken();
                        String mat=tokenizer.nextToken();
                        String gg=tokenizer.nextToken();
                        String ora=tokenizer.nextToken();



                        PrenotaHttp prenota=new PrenotaHttp(a,prof,mat,ora,gg,positionS,arrayAdapter,context);
                        try{
                            URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
                            prenota.execute(url);

                            HttpJson json = new HttpJson(pre.profS, pre.matS,pre.oraS,pre.ggS,arrayAdapter,context);
                            try{
                                url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
                                json.execute(url);
                            }catch(MalformedURLException e){}

                            pre.setModify(true);

                        }catch(MalformedURLException e){
                            Toast.makeText(context,"Prenotazione non effettuata: qualcosa è andato storto", Toast.LENGTH_LONG).show();
                        }



                        dialog.dismiss();

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



            }
            //****************



        });
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prof;
        TextView mat;
        TextView gg;
        TextView ora;
        Button prenota;

        ViewHolder(View itemView) {
            super(itemView);
            this.prof = (TextView) itemView.findViewById(R.id.prof);
            this.mat = (TextView)itemView.findViewById(R.id.mat);
            this.gg = (TextView)itemView.findViewById(R.id.gg);
            this.ora = (TextView)itemView.findViewById(R.id.ora);
            this.prenota = (Button)itemView.findViewById(R.id.prenota);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Ripetizione getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }




}