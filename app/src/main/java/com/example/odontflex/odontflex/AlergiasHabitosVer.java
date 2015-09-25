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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class AlergiasHabitosVer extends AppCompatActivity {

    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    String[] opciones = new String[]{
            "",
            ""
    };

    int[] imgOpciones = {
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info

    };

    private CheckBox CheckCepillado1vez,CheckCepillado2veces, CheckSeda1vez, CheckSeda2veces;

    Button btnSi,btnNo;
    TextView txtAlergias, txtOtros;

    String alergias="", otros="", Cepillado="", Seda="",idPaciente="",idOdontologo, habitos, nomPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergias_habitos_ver);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
        alergias = dato.getStringExtra("alergias");
        otros = dato.getStringExtra("otrosHabitos");
        habitos = dato.getStringExtra("habitos");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        CheckCepillado1vez = (CheckBox) findViewById(R.id.checkBoxCepillado1vez);
        CheckCepillado2veces = (CheckBox) findViewById(R.id.checkBoxCepillado2veces);
        CheckSeda1vez = (CheckBox) findViewById(R.id.checkBoxSeda1vez);
        CheckSeda2veces = (CheckBox) findViewById(R.id.checkBoxSeda2veces);

        CheckCepillado1vez.setEnabled(false);
        CheckCepillado2veces.setEnabled(false);
        CheckSeda1vez.setEnabled(false);
        CheckSeda2veces.setEnabled(false);

        txtAlergias = (TextView) findViewById(R.id.txtAlergias);
        txtOtros = (TextView) findViewById(R.id.txtOtrosHabitos);

        txtAlergias.setText(""+alergias);
        txtOtros.setText(""+otros);

        Valores();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout) findViewById(R.id.slidingPane);
        ListView list = (ListView) findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this, opciones, imgOpciones);
        list.setAdapter(adapter);
        list.setBackgroundColor(Color.rgb(178, 223, 219));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent consultorio = new Intent(getApplicationContext(),
                                Consultorio.class);
                        consultorio.putExtra("idOdontologo", idOdontologo);
                        startActivity(consultorio);
                        finish();
                        break;
                    case 1:
                        Intent infoGeneral = new Intent(getApplicationContext(),
                                InfoGeneral.class);
                        infoGeneral.putExtra("idOdontologo", idOdontologo);
                        startActivity(infoGeneral);
                        finish();
                        break;
                }

            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alergias_habitos_ver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.opciones:
                if (mPanes.closePane()) {
                    closePane();
                } else {
                    openPane();
                }
                break;
            case R.id.salir:
                Salir();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openPane() {
        mPanes.openPane();
    }

    private void closePane() {
        mPanes.closePane();
    }

    public void onBackPressed() {
    }

    public void Inicio(View v) {
        Intent inicio = new Intent(getApplicationContext(),
                Menu_principal.class);
        inicio.putExtra("idOdontologo", idOdontologo);
        startActivity(inicio);
        finish();
    }

    public void Siguiente(View v) {
        Intent volver = new Intent(getApplicationContext(),
                HistoriaPacienteExisteMenuPrincipal.class);
        volver.putExtra("idPaciente", idPaciente);
        volver.putExtra("nomPaciente", nomPaciente);
        volver.putExtra("idOdontologo", idOdontologo);
        startActivity(volver);
        finish();
    }

    public void Salir() {
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

    public void Valores(){
        switch (habitos.substring(0,1)){
            case "1":
                CheckCepillado1vez.setChecked(true);
                break;
            case "2":
                CheckCepillado2veces.setChecked(true);
                break;
        }
        switch (habitos.substring(1)){
            case "1":
                CheckSeda1vez.setChecked(true);
                break;
            case "2":
                CheckSeda2veces.setChecked(true);
                break;
        }
    }
}
