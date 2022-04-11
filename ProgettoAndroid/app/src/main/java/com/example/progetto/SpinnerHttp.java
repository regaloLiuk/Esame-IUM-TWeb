package com.example.progetto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


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


import javax.net.ssl.HttpsURLConnection;




public class SpinnerHttp extends AsyncTask<URL, Integer, Long> {
    Context context;
    Spinner spinner;
    String response = "";
    ArrayList<String> arrayAdapter;
    int action;



    SpinnerHttp(ArrayList<String> arrayAdapter, Context context, Spinner spinner,int action) {
        this.arrayAdapter=arrayAdapter;
        this.context=context;
        this.spinner=spinner;
        this.action=action;

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
        if(action==0)
            params.put("var", "prof");
        else
            params.put("var", "mat");


        URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
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
        // This counts how many bytes were downloaded
        final byte[] result = response.getBytes();
        Long numOfBytes = Long.valueOf(result.length);
        return numOfBytes;
    }

    protected void onPostExecute(Long result) {
        System.out.println("Downloaded " + result + " bytes");

        if(action==0){
            try{

                final JSONObject obj = new JSONObject(response);
                    final JSONArray storico = obj.getJSONArray("Professore");
                final int n = storico.length();
                System.out.println("Professore----------------"+n);
                arrayAdapter.add("professore");
                for (int i = 0; i < n; ++i) {
                    final JSONObject person = storico.getJSONObject(i);

                    String nome=person.getString("NOME");
                    String cognome=person.getString("COGNOME");
                    int id=person.getInt("ID");

                    arrayAdapter.add(nome+" "+cognome);
                    System.out.println("...........");
                }

                ArrayAdapter<String> adpProf = new ArrayAdapter<String> (context,android.R.layout.simple_spinner_dropdown_item,arrayAdapter);
                spinner.setAdapter(adpProf);
                adpProf.notifyDataSetChanged();

                spinner.setVisibility(View.VISIBLE);

            }catch(JSONException e){System.out.println(e);}



        }
    else{
        try{

            final JSONObject obj = new JSONObject(response);
            final JSONArray storico = obj.getJSONArray("Materia");
            final int n = storico.length();
            System.out.println("Materia----------------"+n);
            arrayAdapter.add("materia");
            for (int i = 0; i < n; ++i) {
                final JSONObject person = storico.getJSONObject(i);

                String materia=person.getString("materia");

                arrayAdapter.add(materia);
                System.out.println("...........");
            }

            ArrayAdapter<String> adpProf = new ArrayAdapter<String> (context,android.R.layout.simple_spinner_dropdown_item,arrayAdapter);
            spinner.setAdapter(adpProf);
            adpProf.notifyDataSetChanged();

            spinner.setVisibility(View.VISIBLE);

        }catch(JSONException e){System.out.println(e);}

    }

    }
}