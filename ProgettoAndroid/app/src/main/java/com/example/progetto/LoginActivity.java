package com.example.progetto;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity  extends AppCompatActivity {

    String utente="";

    Button login;
    EditText user;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        login=(Button) findViewById(R.id.login);
        user=(EditText) findViewById(R.id.ute);
        password=(EditText) findViewById(R.id.pwd);




        login.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                LoginHttp login = new LoginHttp(user.getText().toString(),password.getText().toString(),getApplicationContext());
                try{
                    URL url = new URL("http://10.0.2.2:8088/ProgettoIUM/Servlet");
                    login.execute(url);
                }catch(MalformedURLException e){}


            }

        });


    }



}
