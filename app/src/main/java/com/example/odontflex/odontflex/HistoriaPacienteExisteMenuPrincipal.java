package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HistoriaPacienteExisteMenuPrincipal extends AppCompatActivity {

    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    ListViewAdapterButton adaptador;
    int tipoCup=0;
    ListView lvCups;
    String[] opciones = new  String[]{
            "",
            ""
    };
    String[] menu = new  String[]{
            "Informacion Personal",
            "Anamnesis",
            "Alergias - Hábitos",
            "Exámen Estomatológico",
            "Examen Periodontal - Dental",
            "Odontograma",
            "Nota de evolucion"
    };

    int[] imgOpciones={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.iconoconsultorio

    };

    int[] img1={
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa

    };

    int[] img2={
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.adicionar

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_paciente_existe_menu_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);
        final ListView listaFr = (ListView) findViewById(R.id.listView);
        adaptador = new ListViewAdapterButton(this,menu,img1, img2);
        listaFr.setAdapter(adaptador);
        list.setAdapter(adapter);
        list.setBackgroundColor(Color.rgb(178, 223, 219));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent historiaclinica = new Intent(getApplicationContext(),
                                HistoriaClinicaPrincipal.class);
                        startActivity(historiaclinica);
                        finish();
                        break;
                    case 1:
                        Intent consultorio = new Intent(getApplicationContext(),
                                Consultorio.class);
                        startActivity(consultorio);
                        finish();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historia_paciente_existe_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
