package com.example.odontflex.odontflex;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogoObservaciones extends DialogFragment implements View.OnClickListener{
    Button guardar,cancelar;
    EditText observacion;
    String guardarObservacion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dialogo_observaciones, null);

        guardar = (Button) view.findViewById(R.id.buttonGuardarObservacion);
        cancelar = (Button) view.findViewById(R.id.buttonCancelarObservacion);
        observacion = (EditText) view.findViewById(R.id.EditTextObservaciones);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.buttonGuardarObservacion)
        {

            //guardarObservacion = observacion.getText().toString();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }


}
