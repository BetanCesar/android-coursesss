package com.example.cesar.bmi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RisultatiActivity extends Activity {

    private TextView nome, imc, ideale, energia, descrizioneIMC;
    private ImageView imageView;
    private Persona persona;
    private final int REQUEST_CODE = 7007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);

        // Assign the TextViews from the view to the variables
        nome = (TextView) findViewById(R.id.nome);
        imc = (TextView) findViewById(R.id.imc);
        ideale = (TextView) findViewById(R.id.ideale);
        energia = (TextView) findViewById(R.id.energia);
        imageView = (ImageView) findViewById(R.id.immagine);
        descrizioneIMC = (TextView) findViewById(R.id.descrizioneIMC);

        // Get the object from the intent
        persona = (Persona) getIntent().getSerializableExtra("persona");

        // Setting data
        settingData();

    }

    public void settingData(){

        // Calculate data
        Double imcDouble = persona.peso / Math.pow(persona.statura, 2.0);
        Double idealDouble = Math.pow(persona.statura, 2.0) * 22;
        Double energiaDouble = idealDouble * 30;

        // Set data
        nome.setText("Nome: " + persona.nome);
        imc.setText("IMC: " + String.valueOf(doubleTwoDecimals(imcDouble)));
        ideale.setText("Ideale: " + String.valueOf(doubleTwoDecimals(idealDouble)) + " Kg");
        energia.setText("Energia: " + String.valueOf(doubleTwoDecimals(energiaDouble)) + " kCal");

        // Set image and description text
        int color = Integer.parseInt("a4cf31", 16)+0xFF000000;
        descrizioneIMC.setTextColor(color);
        if(persona.sesso.equalsIgnoreCase("Donna")){
            if(imcDouble <= 17.5){
                imageView.setImageResource(R.drawable.woman_bmi_17_5);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Anoressia");
            }else if(imcDouble <= 18.5){
                imageView.setImageResource(R.drawable.woman_bmi_18_5);
                descrizioneIMC.setText("Basso normale");
            }else if(imcDouble <= 22.0){
                imageView.setImageResource(R.drawable.woman_bmi_22);
                descrizioneIMC.setText("Medio normale");
            }else if(imcDouble <= 24.9){
                imageView.setImageResource(R.drawable.woman_bmi_24_9);
                descrizioneIMC.setText("Alto normale");
            }else if(imcDouble <= 30){
                imageView.setImageResource(R.drawable.woman_bmi_30);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Obesità");
            }else if(imcDouble <= 40){
                imageView.setImageResource(R.drawable.woman_bmi_40);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Obesità patologica");
            }
        }else if(persona.sesso.equalsIgnoreCase("Uomo")){
            if(imcDouble <= 17.5){
                imageView.setImageResource(R.drawable.men_bmi_17_5);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Anoressia");
            }else if(imcDouble <= 18.5){
                imageView.setImageResource(R.drawable.men_bmi_18_5);
                descrizioneIMC.setText("Basso normale");
            }else if(imcDouble <= 22.0){
                imageView.setImageResource(R.drawable.men_bmi_22_0);
                descrizioneIMC.setText("Medio normale");
            }else if(imcDouble <= 24.9){
                imageView.setImageResource(R.drawable.men_bmi_24_9);
                descrizioneIMC.setText("Alto normale");
            }else if(imcDouble <= 30){
                imageView.setImageResource(R.drawable.men_bmi_30);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Obesità");
            }else if(imcDouble <= 40){
                imageView.setImageResource(R.drawable.men_bmi_40);
                color = Integer.parseInt("e50000", 16)+0xFF000000;
                descrizioneIMC.setTextColor(color);
                descrizioneIMC.setText("Obesità patologica");
            }
        }
    }

    public double doubleTwoDecimals(double num){
        return (double)Math.round(num * 100) / 100;
    }

    // For the button Ricalcolare, method declared from the view
    public void ricalcolare(View view){

        // Intent for go to NuoviDatiActivity from RisultatiActivity
        Intent intent = new Intent(RisultatiActivity.this, NuoviDatiActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // For the button Apri la pagina IMC, method declared from the view
    public void apriWikiIMC(View view){

        String url = "https://it.wikipedia.org/wiki/Indice_di_massa_corporea";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void share(View view){
        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Messaggio");
        textShareIntent.putExtra(Intent.EXTRA_TEXT, "Il mio IMC è: " + imc.getText().toString());
        textShareIntent.setType("text/plain");
        startActivity(Intent.createChooser(textShareIntent, "Share text with..."));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if (data.hasExtra("editPersona")){
                Persona p = (Persona)data.getSerializableExtra("editPersona");
                persona.peso = p.peso;
                persona.statura = p.statura;
                settingData();
            }
        }
    }
}
