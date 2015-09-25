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

import org.json.JSONArray;

public class EstomatologicoVer extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;


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

    private CheckBox CheckATM, CheckMusculos, CheckPiel, CheckLabios,
            CheckGanglios, CheckCarrillo, CheckPisoboca, CheckPaladar, CheckGlandsali,
            CheckFrenillos, CheckLengua, CheckEncias, CheckMucosas, CheckOclusion;

    String idPaciente,idOdontologo, ATM, Musculos, Piel, Labios, Ganglios,
            Carrillo, Pisoboca, Paladar, Glandsalivales, Frenillos, Lengua, Encias, Mucosas,
            Oclusion, estomatologicoInfo, nomPaciente;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;

    TextView txtObservaciones;

    String observaciones ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estomatologico_ver);

        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
        estomatologicoInfo = dato.getStringExtra("EstomaInfo");
        observaciones = dato.getStringExtra("obsEstoma");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        CheckATM = (CheckBox) findViewById(R.id.checkBoxATM);
        CheckMusculos = (CheckBox) findViewById(R.id.checkBoxMusculos);
        CheckPiel = (CheckBox) findViewById(R.id.checkBoxPiel);
        CheckLabios = (CheckBox) findViewById(R.id.checkBoxLabios);
        CheckGanglios = (CheckBox) findViewById(R.id.checkBoxGanglios);
        CheckCarrillo = (CheckBox) findViewById(R.id.checkBoxCarrillos);
        CheckPisoboca = (CheckBox) findViewById(R.id.checkBoxPisodeboca);
        CheckPaladar = (CheckBox) findViewById(R.id.checkBoxPaladar);
        CheckGlandsali = (CheckBox) findViewById(R.id.checkBoxGlanSaliv);
        CheckFrenillos = (CheckBox) findViewById(R.id.checkBoxFrenillos);
        CheckLengua = (CheckBox) findViewById(R.id.checkBoxLengua);
        CheckEncias = (CheckBox) findViewById(R.id.checkBoxEncias);
        CheckMucosas = (CheckBox) findViewById(R.id.checkBoxMucosas);
        CheckOclusion = (CheckBox) findViewById(R.id.checkBoxOclusion);

        CheckATM.setEnabled(false);
        CheckMusculos.setEnabled(false);
        CheckPiel.setEnabled(false);
        CheckLabios.setEnabled(false);
        CheckGanglios.setEnabled(false);
        CheckCarrillo.setEnabled(false);
        CheckPisoboca.setEnabled(false);
        CheckPaladar.setEnabled(false);
        CheckGlandsali.setEnabled(false);
        CheckFrenillos.setEnabled(false);
        CheckLengua.setEnabled(false);
        CheckEncias.setEnabled(false);
        CheckMucosas.setEnabled(false);
        CheckOclusion.setEnabled(false);

        ATM=estomatologicoInfo.substring(0,1);
        Musculos=estomatologicoInfo.substring(1,2);
        Piel=estomatologicoInfo.substring(2,3);
        Labios=estomatologicoInfo.substring(3,4);
        Ganglios=estomatologicoInfo.substring(4,5);
        Carrillo=estomatologicoInfo.substring(5,6);
        Pisoboca=estomatologicoInfo.substring(6,7);
        Paladar=estomatologicoInfo.substring(7,8);
        Glandsalivales=estomatologicoInfo.substring(8,9);
        Frenillos=estomatologicoInfo.substring(9,10);
        Lengua=estomatologicoInfo.substring(10,11);
        Encias=estomatologicoInfo.substring(11,12);
        Mucosas=estomatologicoInfo.substring(12,13);
        Oclusion=estomatologicoInfo.substring(13,14);

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
        getMenuInflater().inflate(R.menu.menu_estomatologico_ver, menu);
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

    public void mostrarDialogo(View v){
        final Dialog dialog = new Dialog(EstomatologicoVer.this);
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

    public void Salir() {
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }


    public void Valores (){
        switch (ATM){
            case "0":
                CheckATM.setChecked(false);
                break;
            case "1":
                CheckATM.setChecked(true);
                break;
        }

        switch (Musculos){
            case "0":
                CheckMucosas.setChecked(false);
                break;
            case "1":
                CheckMucosas.setChecked(true);
                break;
        }

        switch (Piel){
            case "0":
                CheckPiel.setChecked(false);
                break;
            case "1":
                CheckPiel.setChecked(true);
                break;
        }

        switch (Labios){
            case "0":
                CheckLabios.setChecked(false);
                break;
            case "1":
                CheckLabios.setChecked(true);
                break;
        }

        switch (Ganglios){
            case "0":
                CheckGanglios.setChecked(false);
                break;
            case "1":
                CheckGanglios.setChecked(true);
                break;
        }

        switch (Carrillo){
            case "0":
                CheckCarrillo.setChecked(false);
                break;
            case "1":
                CheckCarrillo.setChecked(true);
                break;
        }

        switch (Pisoboca){
            case "0":
                CheckPisoboca.setChecked(false);
                break;
            case "1":
                CheckPisoboca.setChecked(true);
                break;
        }

        switch (Paladar){
            case "0":
                CheckPaladar.setChecked(false);
                break;
            case "1":
                CheckPaladar.setChecked(true);
                break;
        }

        switch (Glandsalivales){
            case "0":
                CheckGlandsali.setChecked(false);
                break;
            case "1":
                CheckGlandsali.setChecked(true);
                break;
        }

        switch (Frenillos){
            case "0":
                CheckFrenillos.setChecked(false);
                break;
            case "1":
                CheckFrenillos.setChecked(true);
                break;
        }

        switch (Lengua){
            case "0":
                CheckLengua.setChecked(false);
                break;
            case "1":
                CheckLengua.setChecked(true);
                break;
        }

        switch (Encias){
            case "0":
                CheckEncias.setChecked(false);
                break;
            case "1":
                CheckEncias.setChecked(true);
                break;
        }

        switch (Mucosas){
            case "0":
                CheckMucosas.setChecked(false);
                break;
            case "1":
                CheckMucosas.setChecked(true);
                break;
        }

        switch (Oclusion){
            case "0":
                CheckOclusion.setChecked(false);
                break;
            case "1":
                CheckOclusion.setChecked(true);
                break;
        }


    }


}
