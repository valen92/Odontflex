package com.example.odontflex.odontflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Menu_principal extends AppCompatActivity {

    ListViewAdapter adapter;
    String[] tituloMenuPpl = new  String[]{
            "Historia Clínica",
            "Consultorio",
            "Información General",
    };

    int[]imagenesMenuPpl={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info,

    };

    String idOdontologo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent dato = getIntent();
        idOdontologo = dato.getStringExtra("idOdontologo");
        final ListView listaFr = (ListView) findViewById(R.id.listViewMenuPrincipal);
        adapter = new ListViewAdapter(this,tituloMenuPpl,imagenesMenuPpl);
        listaFr.setAdapter(adapter);
        listaFr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: Intent inicio = new Intent(getApplicationContext(),
                             HistoriaClinicaPrincipal.class);
                        inicio.putExtra("idOdontologo", idOdontologo);
                        startActivity(inicio);
                        finish();
                        break;
                    case 1: Intent consultorio = new Intent(getApplicationContext(),
                            Consultorio.class);
                        startActivity(consultorio);
                        finish();
                        break;
                    case 2: Intent infoGeneral = new Intent(getApplicationContext(),
                            InfoGeneral.class);
                        startActivity(infoGeneral);
                        finish();
                        break;
                }

            }


        });
    }

    public void onBackPressed(){
    }

    public void Salir (View v){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

}
