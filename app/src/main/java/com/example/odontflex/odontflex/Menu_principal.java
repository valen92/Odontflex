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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        final ListView listaFr = (ListView) findViewById(R.id.listViewMenuPrincipal);
        adapter = new ListViewAdapter(this,tituloMenuPpl,imagenesMenuPpl);
        listaFr.setAdapter(adapter);
        listaFr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: Intent inicio = new Intent(getApplicationContext(),
                             HistoriaClinicaPrincipal.class);
                        startActivity(inicio);
                        finish();
                        break;
                }
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

            }


        });
    }

}
