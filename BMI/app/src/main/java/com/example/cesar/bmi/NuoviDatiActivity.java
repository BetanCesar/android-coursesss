package com.example.cesar.bmi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class NuoviDatiActivity extends Activity {

    private EditText editPesso, editStatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovi_dati);
        editPesso = (EditText) findViewById(R.id.editPesso);
        editStatura = (EditText) findViewById(R.id.editStatura);
    }

    public void ricalcolare(View view){
        Persona persona = new Persona();
        String pessoStr = editPesso.getText().toString();
        String staturaStr = editStatura.getText().toString();
        if(TextUtils.isEmpty(pessoStr)){
            editPesso.setError("Cattura un valore!");
            return;
        }if(TextUtils.isEmpty(staturaStr)){
            editStatura.setError("Cattura un valore");
            return;
        }

        persona.peso = Double.parseDouble(pessoStr);
        persona.statura = Double.parseDouble(staturaStr);

        Intent intent = new Intent();
        intent.putExtra("editPersona", persona);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
