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

public class CUPS extends AppCompatActivity {

    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    int tipoCup=0;
    ListView lvCups;
    String[] opciones = new  String[]{
            "",
            "",
    };

    int[] imgOpciones={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.iconoconsultorio,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cups);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);
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

                        break;
                }

            }
        });

        lvCups = (ListView)findViewById(R.id.lvCups);
        lvCups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipoCup = 1 + position;
                Intent listaCups = new Intent(getApplicationContext(),
                        ListaCUPS.class);
                listaCups.putExtra("tipoCup", Integer.toString(tipoCup));
                startActivity(listaCups);
                finish();

            }
        });
        tipoCup=0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cu, menu);
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
        startActivity(inicio);
        finish();
    }

    public void Salir (){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

    public void Atras (View v){
        Intent atras = new Intent(getApplicationContext(),
                InfoGeneral.class);
        startActivity(atras);
        finish();
    }
}
