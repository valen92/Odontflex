package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InfoGeneral extends ActionBarActivity {

    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    ListViewAdapter adapter, adaptador;
    ListView lvInfoGeneral;
    String[] opciones = new  String[]{
            "",
            "",
    };

    int[] imgOInfo={
            10,
            10,
    };

    String[] info = new  String[]{
            "CUPS",
            "Acerca de Odontflex",
    };

    int[] imgOpciones={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.iconoconsultorio,
    };

    String idOdontologo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_general);
        Intent dato = getIntent();
        idOdontologo = dato.getStringExtra("idOdontologo");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        mPanes.setParallaxDistance(PARALLAX_SIZE);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);
        list.setAdapter(adapter);
        list.setBackgroundColor(Color.rgb(178, 223, 219));


        lvInfoGeneral = (ListView)findViewById(R.id.listViewInfoGeneral);
        adaptador = new ListViewAdapter(this,info,imgOInfo);
        lvInfoGeneral.setAdapter(adaptador);
        lvInfoGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent cups = new Intent(getApplicationContext(),
                                CUPS.class);
                        cups.putExtra("idOdontologo", idOdontologo);
                        startActivity(cups);
                        finish();
                        break;
                    case 1:
                        Intent acercade = new Intent(getApplicationContext(),
                                Acercade.class);
                        acercade.putExtra("idOdontologo", idOdontologo);
                        startActivity(acercade);
                        finish();
                        break;
                }

            }


        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent historiaclinica = new Intent(getApplicationContext(),
                                HistoriaClinicaPrincipal.class);
                        historiaclinica.putExtra("idOdontologo", idOdontologo);
                        startActivity(historiaclinica);
                        finish();
                        break;
                    case 1:
                        Intent consultorio = new Intent(getApplicationContext(),
                                Consultorio.class);
                        consultorio.putExtra("idOdontologo", idOdontologo);
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
        getMenuInflater().inflate(R.menu.menu_info_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.opciones:
                if (mPanes.closePane()){
                    closePane();
                } else {
                    openPane();
                }
                break;
            case  R.id.salir:
                Salir();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPane(){
        mPanes.openPane();
    }

    private void closePane(){
        mPanes.closePane();
    }

    public void onBackPressed(){
    }

    public void Inicio (View v){
        Intent inicio = new Intent(getApplicationContext(),
                Menu_principal.class);
        inicio.putExtra("idOdontologo", idOdontologo);
        startActivity(inicio);
        finish();
    }

    public void Salir (){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }
}
