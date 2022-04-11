package com.example.progetto;

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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.StringTokenizer;

public class StoricoRecyclerViewAdapter extends RecyclerView.Adapter<StoricoRecyclerViewAdapter.ViewHolder> {

    private List<Storico> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int cont=0;
    private StoricoRecyclerViewAdapter arrayAdapter;
    private Context context;
    private StoricoFrag storico;


    public List<Storico> getmData(){
        return this.mData;
    }
    // data is passed into the constructor
    StoricoRecyclerViewAdapter(Context context, List<Storico> data,StoricoFrag storico) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.arrayAdapter=this;
        this.context=context;
        this.storico=storico;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_storico, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Storico st = mData.get(position);
        holder.prof.setText("Professore: \t\t"+st.getProf());
        holder.mat.setText("Materia:    \t\t"+st.getMat());
        holder.gg.setText("Giorno:     \t\t"+st.getGG());
        holder.ora.setText("Ora:        \t\t"+st.getOra());

        if(st.stato.equals("0")){
            holder.stato.setText("Stato: \t\tPrenotata");
            holder.stato.setTextColor(Color.parseColor("#c57b18"));
            holder.modifica.setVisibility(View.VISIBLE);
        }
        else if(st.stato.equals("1")){
            holder.stato.setText("Stato: \t\tEffettuata");
            holder.stato.setTextColor(Color.parseColor("#3e9831"));
            holder.modifica.setVisibility(View.GONE);
        }
        else{
            holder.stato.setText("Stato: \t\tDisdetta");
            holder.stato.setTextColor(Color.rgb(255,0,0));
            holder.modifica.setVisibility(View.GONE);
        }

        holder.modifica.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                StringTokenizer tokenizer = new StringTokenizer(arrayAdapter.getItem(position).toString(),",");

                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();

                String stato=tokenizer.nextToken();

                if(stato.equals("0")) {

                    final int positionItem = position;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Cosa vuoi fare");
                    builder.setMessage("Disdire o effettuata?");

                    builder.setPositiveButton("Disdici", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            storico.action("disdici", v, positionItem, arrayAdapter);
                            storico.setModify(true);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Effettuata", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            storico.action("effettuata", v, positionItem, arrayAdapter);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Messaggio");
                    builder.setMessage("Ripetizione gia effettuata");


                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }


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
        TextView stato;
        Button modifica;

        ViewHolder(View itemView) {
            super(itemView);
            this.prof = (TextView) itemView.findViewById(R.id.prof);
            this.mat = (TextView)itemView.findViewById(R.id.mat);
            this.gg = (TextView)itemView.findViewById(R.id.gg);
            this.ora = (TextView)itemView.findViewById(R.id.ora);
            this.stato =(TextView) itemView.findViewById(R.id.stato);
            this.modifica= (Button)itemView.findViewById(R.id.modifica);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Storico getItem(int id) {
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