package com.example.odontflex.odontflex;

import android.app.Dialog;
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
import android.widget.Toast;

import org.json.JSONArray;

public class PeriodontalDentalVer extends AppCompatActivity {

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

    private CheckBox Checkplacablanda, Checkbolsas, CheckPlacacalcificada, Checkretraccionesgingivales,
            Checksupenumerarios, Checkabrasion, Checkmanchas, Checkpatologiapulpar, Checkfracturas, Checkatriccion,
            Checkerosion, Checkmalformaciones, Checktrauma, Checkrotaciones;

    String idPaciente, idOdontologo, placablanda, bolsas, placacalcificada, retraccionesgingivales,
            supenumerarios, abrasion, manchas, patologiapulpar, fracturas, atriccion,
            erosion, malformaciones, trauma, rotaciones, nomPaciente, dental, periodontal;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;
    ;

    TextView txtObservaciones;

    String observaciones = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodontal_dental_ver);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
        periodontal = dato.getStringExtra("periodontal");
        dental = dato.getStringExtra("dental");
        observaciones = dato.getStringExtra("obsDental");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();


        Checkplacablanda = (CheckBox) findViewById(R.id.checkBoxPlacablanda);
        Checkbolsas = (CheckBox) findViewById(R.id.checkBoxBolsas);
        CheckPlacacalcificada = (CheckBox) findViewById(R.id.checkBoxPlacacalcificada);
        Checkretraccionesgingivales = (CheckBox) findViewById(R.id.checkBoxRetracgin);
        Checksupenumerarios = (CheckBox) findViewById(R.id.checkBoxSupenum);
        Checkabrasion = (CheckBox) findViewById(R.id.checkBoxAbrasion);
        Checkmanchas = (CheckBox) findViewById(R.id.checkBoxManchas);
        Checkpatologiapulpar = (CheckBox) findViewById(R.id.checkBoxPatolopulpar);
        Checkfracturas = (CheckBox) findViewById(R.id.checkBoxFracturas);
        Checkatriccion = (CheckBox) findViewById(R.id.checkBoxAtriccion);
        Checkerosion = (CheckBox) findViewById(R.id.checkBoxErosion);
        Checkmalformaciones = (CheckBox) findViewById(R.id.checkBoxMalformaciones);
        Checktrauma = (CheckBox) findViewById(R.id.checkBoxTrauma);
        Checkrotaciones = (CheckBox) findViewById(R.id.checkBoxRotaciones);

        Checkplacablanda.setEnabled(false);
        Checkbolsas.setEnabled(false);
        CheckPlacacalcificada.setEnabled(false);
        Checkretraccionesgingivales.setEnabled(false);
        Checksupenumerarios.setEnabled(false);
        Checkabrasion.setEnabled(false);
        Checkmanchas.setEnabled(false);
        Checkpatologiapulpar.setEnabled(false);
        Checkfracturas.setEnabled(false);
        Checkatriccion.setEnabled(false);
        Checkerosion.setEnabled(false);
        Checkmalformaciones.setEnabled(false);
        Checktrauma.setEnabled(false);
        Checkrotaciones.setEnabled(false);

        Asignacion();
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
                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_periodontal_dental_ver, menu);
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

    public void Salir() {
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

    public void mostrarDialogo(View v){
        final Dialog dialog = new Dialog(PeriodontalDentalVer.this);
        dialog.setContentView(R.layout.activity_dialogo_verobservaciones);
        dialog.show();
        btnCancelarObservacion = (Button) dialog.findViewById(R.id.btnCancelarObservacion);
        txtObservaciones = (TextView) dialog.findViewById(R.id.txtObservacionesAnamnesis);
        txtObservaciones.setText("" + observaciones);
        btnCancelarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

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

    public void Asignacion(){
        supenumerarios=dental.substring(0,1);
        abrasion=dental.substring(1,2);
        manchas=dental.substring(2,3);
        patologiapulpar=dental.substring(3,4);
        fracturas=dental.substring(4,5);
        atriccion=dental.substring(5,6);
        erosion=dental.substring(6,7);
        malformaciones=dental.substring(7,8);
        trauma=dental.substring(8,9);
        rotaciones=dental.substring(9);
        placablanda=periodontal.substring(0,1);
        placacalcificada=periodontal.substring(1,2);
        bolsas=periodontal.substring(2,3);
        retraccionesgingivales=periodontal.substring(3);
    }

    public void Valores (){
        switch (supenumerarios){
            case "0":
                Checksupenumerarios.setChecked(false);
                break;
            case "1":
                Checksupenumerarios.setChecked(true);
                break;
        }

        switch (abrasion){
            case "0":
                Checkabrasion.setChecked(false);
                break;
            case "1":
                Checkabrasion.setChecked(true);
                break;
        }

        switch (manchas){
            case "0":
                Checkmanchas.setChecked(false);
                break;
            case "1":
                Checkmanchas.setChecked(true);
                break;
        }

        switch (patologiapulpar){
            case "0":
                Checkpatologiapulpar.setChecked(false);
                break;
            case "1":
                Checkpatologiapulpar.setChecked(true);
                break;
        }

        switch (fracturas){
            case "0":
                Checkfracturas.setChecked(false);
                break;
            case "1":
                Checkfracturas.setChecked(true);
                break;
        }

        switch (atriccion){
            case "0":
                Checkatriccion.setChecked(false);
                break;
            case "1":
                Checkatriccion.setChecked(true);
                break;
        }

        switch (erosion){
            case "0":
                Checkerosion.setChecked(false);
                break;
            case "1":
                Checkerosion.setChecked(true);
                break;
        }

        switch (malformaciones){
            case "0":
                Checkmalformaciones.setChecked(false);
                break;
            case "1":
                Checkmalformaciones.setChecked(true);
                break;
        }

        switch (trauma){
            case "0":
                Checktrauma.setChecked(false);
                break;
            case "1":
                Checktrauma.setChecked(true);
                break;
        }

        switch (rotaciones){
            case "0":
                Checkrotaciones.setChecked(false);
                break;
            case "1":
                Checkrotaciones.setChecked(true);
                break;
        }

        switch (placablanda){
            case "0":
                Checkplacablanda.setChecked(false);
                break;
            case "1":
                Checkplacablanda.setChecked(true);
                break;
        }

        switch (placacalcificada){
            case "0":
                CheckPlacacalcificada.setChecked(false);
                break;
            case "1":
                CheckPlacacalcificada.setChecked(true);
                break;
        }

        switch (bolsas){
            case "0":
                Checkbolsas.setChecked(false);
                break;
            case "1":
                Checkbolsas.setChecked(true);
                break;
        }

        switch (retraccionesgingivales){
            case "0":
                Checkretraccionesgingivales.setChecked(false);
                break;
            case "1":
                Checkretraccionesgingivales.setChecked(true);
                break;
        }
    }
}
