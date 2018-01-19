package com.example.cesar.bmi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {

    private EditText nome, peso, statura;
    private Spinner sesso;
    private String sessoSelezionato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Link the view with the code
        nome = (EditText) findViewById(R.id.nome);
        peso = (EditText) findViewById(R.id.peso);
        statura = (EditText) findViewById(R.id.statura);
        sesso = (Spinner) findViewById(R.id.sesso);

        // Array for the spinner
        final String[] sessi = {"Mujer", "Hombre"};
        // ArrayAdapter to give data to the spinner, it is a ModelViewController
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sessi);
        // How to go through the array
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Listener for the selected item of the spinner
        sesso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sessoSelezionato = sessi[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Set the adapter to the spinner
        sesso.setAdapter(adapter);
    }

    // For the button Calcolare IMC, method declared from the view
    public void imc(View view){
        Persona persona = new Persona();
        persona.nome = nome.getText().toString();
        persona.peso = Double.parseDouble(peso.getText().toString());
        persona.statura = Double.parseDouble(statura.getText().toString());
        persona.sesso = sessoSelezionato;
        Intent intent = new Intent(MainActivity.this, RisultatiActivity.class);
        intent.putExtra("persona", persona);
        startActivity(intent);
    }
}
