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

public class AnamnesisVer extends AppCompatActivity {
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

    private CheckBox CheckTratamientoMed, CheckIngesMedicamentos, CheckEnfRespiratorias, CheckEnfCardiacas,
            CheckEnfGastrointestinales, CheckDiabetes, CheckHipertension, CheckHipotension, CheckFiebreReumatica,
            CheckArtritis, CheckInfecciones, CheckIrradiaciones, CheckHemorragias, CheckSinusitis, CheckAccidentes,
            CheckEmbarazo, CheckHepatitis, CheckVih;

    String idPaciente, TratamientoMed, IngesMedicamentos, EnfRespiratorias, EnfCardiacas, EnfGastrointestinales,
            Diabetes, Hipertension, Hipotension, FiebreReumatica, Artritis, Infecciones, Irradiaciones, Hemorragias,
            Sinusitis, Accidentes, Embarazo, Hepatitis, Vih, idOdontologo, anamnesisInfo, nomPaciente;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;

    TextView txtObservaciones;

    String observaciones ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamnesis_ver);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
        anamnesisInfo = dato.getStringExtra("anamnesisInfo");
        observaciones = dato.getStringExtra("obsAnamnesis");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        CheckTratamientoMed = (CheckBox) findViewById(R.id.CheckTratamientoMed);
        CheckIngesMedicamentos = (CheckBox) findViewById(R.id.CheckIngesMedicamentos);
        CheckEnfRespiratorias = (CheckBox) findViewById(R.id.CheckEnfRespiratorias);
        CheckEnfCardiacas = (CheckBox) findViewById(R.id.CheckEnfCardiacas);
        CheckEnfGastrointestinales = (CheckBox) findViewById(R.id.CheckEnfGastrointestinales);
        CheckDiabetes = (CheckBox) findViewById(R.id.CheckDiabetes);
        CheckHipertension = (CheckBox) findViewById(R.id.CheckHipertension);
        CheckHipotension = (CheckBox) findViewById(R.id.CheckHipotension);
        CheckFiebreReumatica = (CheckBox) findViewById(R.id.CheckFiebreReumatica);
        CheckArtritis = (CheckBox) findViewById(R.id.CheckArtritis);
        CheckInfecciones = (CheckBox) findViewById(R.id.CheckInfecciones);
        CheckIrradiaciones = (CheckBox) findViewById(R.id.CheckIrradiaciones);
        CheckHemorragias = (CheckBox) findViewById(R.id.CheckHemorragias);
        CheckSinusitis = (CheckBox) findViewById(R.id.CheckSinusitis);
        CheckAccidentes = (CheckBox) findViewById(R.id.CheckAccidentes);
        CheckEmbarazo = (CheckBox) findViewById(R.id.CheckEmbarazo);
        CheckHepatitis = (CheckBox) findViewById(R.id.CheckHepatitis);
        CheckVih = (CheckBox) findViewById(R.id.CheckVih);

        CheckTratamientoMed.setEnabled(false);
        CheckIngesMedicamentos.setEnabled(false);
        CheckEnfRespiratorias.setEnabled(false);
        CheckEnfCardiacas.setEnabled(false);
        CheckEnfGastrointestinales.setEnabled(false);
        CheckDiabetes.setEnabled(false);
        CheckHipertension.setEnabled(false);
        CheckHipotension.setEnabled(false);
        CheckFiebreReumatica.setEnabled(false);
        CheckArtritis.setEnabled(false);
        CheckInfecciones.setEnabled(false);
        CheckIrradiaciones.setEnabled(false);
        CheckHemorragias.setEnabled(false);
        CheckSinusitis.setEnabled(false);
        CheckAccidentes.setEnabled(false);
        CheckEmbarazo.setEnabled(false);
        CheckHepatitis.setEnabled(false);
        CheckVih.setEnabled(false);

        TratamientoMed=anamnesisInfo.substring(0,1);
        IngesMedicamentos=anamnesisInfo.substring(1,2);
        EnfRespiratorias=anamnesisInfo.substring(2,3);
        EnfCardiacas=anamnesisInfo.substring(3,4);
        EnfGastrointestinales=anamnesisInfo.substring(5,6);
        Diabetes=anamnesisInfo.substring(6,7);
        Hipertension=anamnesisInfo.substring(4,5);
        Hipotension=anamnesisInfo.substring(7,8);
        FiebreReumatica=anamnesisInfo.substring(9,10);
        Artritis=anamnesisInfo.substring(10,11);
        Infecciones=anamnesisInfo.substring(11,12);
        Irradiaciones=anamnesisInfo.substring(12,13);
        Hemorragias=anamnesisInfo.substring(13,14);
        Sinusitis=anamnesisInfo.substring(17);
        Accidentes=anamnesisInfo.substring(14,15);
        Embarazo=anamnesisInfo.substring(15,16);
        Hepatitis=anamnesisInfo.substring(8,9);
        Vih=anamnesisInfo.substring(16,17);

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
                        startActivity(consultorio);
                        finish();
                        break;
                    case 1:
                        Intent infoGeneral = new Intent(getApplicationContext(),
                                  InfoGeneral.class);
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
        getMenuInflater().inflate(R.menu.menu_anamnesis_ver, menu);
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
        final Dialog dialog = new Dialog(AnamnesisVer.this);
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
        switch (TratamientoMed){
            case "0":
                CheckTratamientoMed.setChecked(false);
                break;
            case "1":
                CheckTratamientoMed.setChecked(true);
                break;
        }

        switch (IngesMedicamentos){
            case "0":
                CheckIngesMedicamentos.setChecked(false);
                break;
            case "1":
                CheckIngesMedicamentos.setChecked(true);
                break;
        }

        switch (EnfRespiratorias){
            case "0":
                CheckEnfRespiratorias.setChecked(false);
                break;
            case "1":
                CheckEnfRespiratorias.setChecked(true);
                break;
        }

        switch (EnfCardiacas){
            case "0":
                CheckEnfCardiacas.setChecked(false);
                break;
            case "1":
                CheckEnfCardiacas.setChecked(true);
                break;
        }

        switch (Hipertension){
            case "0":
                CheckHipertension.setChecked(false);
                break;
            case "1":
                CheckHipertension.setChecked(true);
                break;
        }

        switch (EnfGastrointestinales){
            case "0":
                CheckEnfGastrointestinales.setChecked(false);
                break;
            case "1":
                CheckEnfGastrointestinales.setChecked(true);
                break;
        }

        switch (Diabetes){
            case "0":
                CheckDiabetes.setChecked(false);
                break;
            case "1":
                CheckDiabetes.setChecked(true);
                break;
        }

        switch (Hipotension){
            case "0":
                CheckHipotension.setChecked(false);
                break;
            case "1":
                CheckHipotension.setChecked(true);
                break;
        }

        switch (Hepatitis){
            case "0":
                CheckHepatitis.setChecked(false);
                break;
            case "1":
                CheckHepatitis.setChecked(true);
                break;
        }

        switch (FiebreReumatica){
            case "0":
                CheckFiebreReumatica.setChecked(false);
                break;
            case "1":
                CheckFiebreReumatica.setChecked(true);
                break;
        }

        switch (Artritis){
            case "0":
                CheckArtritis.setChecked(false);
                break;
            case "1":
                CheckArtritis.setChecked(true);
                break;
        }

        switch (Infecciones){
            case "0":
                CheckInfecciones.setChecked(false);
                break;
            case "1":
                CheckInfecciones.setChecked(true);
                break;
        }

        switch (Irradiaciones){
            case "0":
                CheckIrradiaciones.setChecked(false);
                break;
            case "1":
                CheckIrradiaciones.setChecked(true);
                break;
        }

        switch (Hemorragias){
            case "0":
                CheckHemorragias.setChecked(false);
                break;
            case "1":
                CheckHemorragias.setChecked(true);
                break;
        }

        switch (Accidentes){
            case "0":
                CheckAccidentes.setChecked(false);
                break;
            case "1":
                CheckAccidentes.setChecked(true);
                break;
        }

        switch (Embarazo){
            case "0":
                CheckEmbarazo.setChecked(false);
                break;
            case "1":
                CheckEmbarazo.setChecked(true);
                break;
        }

        switch (Vih){
            case "0":
                CheckVih.setChecked(false);
                break;
            case "1":
                CheckVih.setChecked(true);
                break;
        }

        switch (Sinusitis){
            case "0":
                CheckSinusitis.setChecked(false);
                break;
            case "1":
                CheckSinusitis.setChecked(true);
                break;
        }
    }
}
