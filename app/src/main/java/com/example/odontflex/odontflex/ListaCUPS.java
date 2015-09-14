package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ListaCUPS extends AppCompatActivity {

    String[] codigoCup, nomCup;
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
            R.drawable.iconoconsultorio,

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
        setContentView(R.layout.activity_lista_cups);
        Intent dato = getIntent();
        codigoCup = dato.getStringArrayExtra("codigoCups");
        nomCup = dato.getStringArrayExtra("nomCups");
        MAX_FILAS = codigoCup.length;
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

        tabla = (TableLayout)findViewById(R.id.tabla);
        cabecera = (TableLayout)findViewById(R.id.cabecera);
        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutId = new TableRow.LayoutParams(120,50);
        layoutTexto = new TableRow.LayoutParams(480, TableRow.LayoutParams.WRAP_CONTENT);
        agregarCabecera();
        agregarFilasTabla();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_cu, menu);
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
                CUPS.class);
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

        txtId.setText("Codigo");
        txtId.setGravity(Gravity.CENTER_HORIZONTAL);
        txtId.setTextAppearance(this, R.style.etiqueta);
        txtId.setLayoutParams(layoutId);

        txtNombre.setText("Nombre");
        txtNombre.setGravity(Gravity.CENTER_HORIZONTAL);
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

            txtId.setText(codigoCup[i]);
            txtId.setGravity(Gravity.CENTER_HORIZONTAL);
            txtId.setTextAppearance(this, R.style.etiqueta);
            txtId.setLayoutParams(layoutId);

            txtNombre.setText(nomCup[i]);
            txtId.setGravity(Gravity.CENTER_HORIZONTAL);
            txtNombre.setTextAppearance(this, R.style.etiqueta);
            txtNombre.setLayoutParams(layoutTexto);

            fila.addView(txtId);
            fila.addView(txtNombre);

            tabla.addView(fila);
        }
    }
}
