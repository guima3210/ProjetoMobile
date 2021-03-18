package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Agenda de Contatos");
    }
        public void listaTelefone(View view){
            Intent i = new Intent(getApplicationContext(),ContatosTelefone.class);
            startActivity(i);
        }
    public void listaemail(View view){
        Intent i = new Intent(getApplicationContext(),ContatosEmail.class);
        startActivity(i);


    }
    public void sobre(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(i);
    }

}