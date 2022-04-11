package com.example.progetto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;


@SuppressLint("NewApi")
public class PrenotaHttp extends AsyncTask<URL, Integer, Long> {

    String response = "";
    String professore;
    String materia;
    String orario;
    String giorno;
    String tmp="";
    Context context;
    int position;
    Activity a ;
    PrenotaRecyclerViewAdapter arrayAdapter;


    PrenotaHttp(Activity a,String professore, String materia, String orario, String giorno, int position, PrenotaRecyclerViewAdapter arrayAdapter, Context context) {
        this.professore = professore;
        this.materia = materia;
        this.orario=orario;
        this.giorno=giorno;
        this.context=context;
        this.arrayAdapter=arrayAdapter;
        this.position=position;
        this.a=a;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder feedback = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                feedback.append("&");

            feedback.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            feedback.append("=");
            feedback.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return feedback.toString();
    }

    public void getData() throws IOException {

        HashMap<String, String> params = new HashMap<>();
        params.put("var", "prenota");
        params.put("prof",professore);
        params.put("mat", materia );
        params.put("ora", orario );
        params.put("gg", giorno );
        params.put("ute", a.getTitle().toString());



        URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            // You need to specify the context-type.  In this case it is a
            // form submission, so use "multipart/form-data"
            client.setRequestProperty("Authorization", "http://10.0.2.2:8088/ProgettoIUM/Servlet");
            client.setDoInput(true);
            client.setDoOutput(true);

            OutputStream os = client.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = client.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }
            else {
                response = "#ERR#";
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        finally {
            if(client != null) // Make sure the connection is not null.
                client.disconnect();
        }
    }

    @Override
    protected Long doInBackground(URL... params) {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] result = response.getBytes();
        Long numOfBytes = Long.valueOf(result.length);
        return numOfBytes;
    }

    protected void onPostExecute(Long result) {
        System.out.println("Downloaded " + result + " bytes");

        if(response.equals("ok")){
            Toast.makeText(context,"Prenotazione effettuata", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context,"Prenotazione già occupata", Toast.LENGTH_LONG).show();
        }

    }
}