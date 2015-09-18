package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Odontologo extends AppCompatActivity {

    String[] idOdontologo, nomOdontologo;
    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    ListView lvCups;
    String[] opciones = new  String[]{
            "",
            "",
    };

    int[] imgOpciones={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.glyphicons_195_circle_info,

    };

    TableLayout tabla;
    TableLayout cabecera;
    TableRow.LayoutParams layoutFila;
    TableRow.LayoutParams layoutId;
    TableRow.LayoutParams layoutTexto;

    private int MAX_FILAS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odontologo);
        Intent dato = getIntent();
        nomOdontologo = dato.getStringArrayExtra("nomOdontologo");
        idOdontologo = dato.getStringArrayExtra("idOdontologo");
        MAX_FILAS = nomOdontologo.length;
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
                        Intent info = new Intent(getApplicationContext(),
                                InfoGeneral.class);
                        startActivity(info);
                        finish();
                        break;
                }

            }
        });

        tabla = (TableLayout)findViewById(R.id.tabla);
        cabecera = (TableLayout)findViewById(R.id.cabecera);
        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutId = new TableRow.LayoutParams(220,TableRow.LayoutParams.WRAP_CONTENT);
        layoutTexto = new TableRow.LayoutParams(420, TableRow.LayoutParams.WRAP_CONTENT);
        agregarCabecera();
        agregarFilasTabla();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_odontologo, menu);
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
                Consultorio.class);
        startActivity(atras);
        finish();
    }

    public void agregarCabecera(){
        TableRow fila;
        TextView txtId;
        TextView txtNombre;

        fila = new TableRow(this);
        fila.setLayoutParams(layoutFila);

        txtId = new TextView(this);
        txtNombre = new TextView(this);

        txtId.setText("Identificacion");
        txtId.setGravity(Gravity.CENTER_HORIZONTAL);
        txtId.setTextAppearance(this, R.style.etiqueta);
        txtId.setLayoutParams(layoutId);

        txtNombre.setText("Nombre");
        txtNombre.setGravity(Gravity.CENTER_HORIZONTAL);
        txtNombre.setPadding(10,0,0,0);
        txtNombre.setTextAppearance(this, R.style.etiqueta);
        txtNombre.setLayoutParams(layoutTexto);

        fila.addView(txtId);
        fila.addView(txtNombre);
        cabecera.addView(fila);
    }

    public void agregarFilasTabla(){

        TableRow fila;
        TextView txtId;
        TextView txtNombre;

        for(int i = 0;i<MAX_FILAS;i++){
            int posicion = i + 1;
            fila = new TableRow(this);
            fila.setLayoutParams(layoutFila);

            txtId = new TextView(this);
            txtNombre = new TextView(this);

            txtId.setText(idOdontologo[i]);
            txtId.setGravity(Gravity.CENTER_VERTICAL);
            txtId.setTextAppearance(this, R.style.etiqueta);
            txtId.setLayoutParams(layoutId);

            txtNombre.setText(nomOdontologo[i]);
            txtNombre.setGravity(Gravity.CENTER_VERTICAL);
            txtNombre.setPadding(10,0,0,0);
            txtNombre.setTextAppearance(this, R.style.etiqueta);
            txtNombre.setLayoutParams(layoutTexto);

            fila.addView(txtId);
            fila.addView(txtNombre);

            tabla.addView(fila);
        }
    }
}
