package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
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

public class HistoriaClinicaAnamnesis extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;


    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    String[] opciones = new  String[]{
            "",
            ""
    };

    int[] imgOpciones={
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info

    };

    private CheckBox CheckTratamientoMed, CheckIngesMedicamentos, CheckEnfRespiratorias, CheckEnfCardiacas,
            CheckEnfGastrointestinales, CheckDiabetes, CheckHipertension, CheckHipotension, CheckFiebreReumatica,
            CheckArtritis, CheckInfecciones, CheckIrradiaciones, CheckHemorragias, CheckSinusitis, CheckAccidentes,
            CheckEmbarazo, CheckHepatitis, CheckVih;

    String idPaciente, TratamientoMed, IngesMedicamentos, EnfRespiratorias, EnfCardiacas, EnfGastrointestinales,
            Diabetes, Hipertension, Hipotension, FiebreReumatica, Artritis, Infecciones, Irradiaciones, Hemorragias,
            Sinusitis, Accidentes, Embarazo, Hepatitis, Vih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_clinica_anamnesis);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        CheckTratamientoMed = (CheckBox)findViewById(R.id.CheckTratamientoMed);
        CheckIngesMedicamentos = (CheckBox)findViewById(R.id.CheckIngesMedicamentos);
        CheckEnfRespiratorias = (CheckBox)findViewById(R.id.CheckEnfRespiratorias);
        CheckEnfCardiacas = (CheckBox)findViewById(R.id.CheckEnfCardiacas);
        CheckEnfGastrointestinales = (CheckBox)findViewById(R.id.CheckEnfGastrointestinales);
        CheckDiabetes = (CheckBox)findViewById(R.id.CheckDiabetes);
        CheckHipertension = (CheckBox)findViewById(R.id.CheckHipertension);
        CheckHipotension = (CheckBox)findViewById(R.id.CheckHipotension);
        CheckFiebreReumatica = (CheckBox)findViewById(R.id.CheckFiebreReumatica);
        CheckArtritis = (CheckBox)findViewById(R.id.CheckArtritis);
        CheckInfecciones = (CheckBox)findViewById(R.id.CheckInfecciones);
        CheckIrradiaciones = (CheckBox)findViewById(R.id.CheckIrradiaciones);
        CheckHemorragias = (CheckBox)findViewById(R.id.CheckHemorragias);
        CheckSinusitis = (CheckBox)findViewById(R.id.CheckSinusitis);
        CheckAccidentes = (CheckBox)findViewById(R.id.CheckAccidentes);
        CheckEmbarazo = (CheckBox)findViewById(R.id.CheckEmbarazo);
        CheckHepatitis = (CheckBox)findViewById(R.id.CheckHepatitis);
        CheckVih = (CheckBox)findViewById(R.id.CheckVih);



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
        getMenuInflater().inflate(R.menu.menu_historia_clinica_anamnesis, menu);
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

    public void Salir (){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }


    public void Siguiente (View v){


        onCheckboxClicked();
        new insertar().execute();

    }



    private void onCheckboxClicked() {


                        if (CheckTratamientoMed.isChecked()) {
                            TratamientoMed = "1";

                        }
                        else {
                            TratamientoMed = "0";
                        }



                        if (CheckIngesMedicamentos.isChecked()) {
                            IngesMedicamentos = "1";

                        }
                        else {
                            IngesMedicamentos = "0";
                        }


                        if (CheckEnfRespiratorias.isChecked()) {
                            EnfRespiratorias = "1";
                        }
                        else {
                            EnfRespiratorias = "0";
                        }



                        if (CheckEnfCardiacas.isChecked()) {
                            EnfCardiacas = "1";
                        }
                        else {
                            EnfCardiacas = "0";
                        }


                        if (CheckEnfGastrointestinales.isChecked()) {
                            EnfGastrointestinales = "1";
                        }
                        else {
                            EnfGastrointestinales = "0";
                        }


                        if (CheckDiabetes.isChecked()) {
                            Diabetes = "1";
                        }
                        else {
                            Diabetes = "0";
                        }




                        if (CheckHipertension.isChecked()) {
                            Hipertension = "1";
                        }
                        else {
                            Hipertension = "0";
                        }




                        if (CheckHipotension.isChecked()) {
                            Hipotension = "1";
                        }
                        else {
                            Hipotension = "0";
                        }



                        if (CheckFiebreReumatica.isChecked()) {
                            FiebreReumatica = "1";
                        }
                        else {
                            FiebreReumatica = "0";
                        }


                        if (CheckArtritis.isChecked()) {
                            Artritis = "1";
                        }
                        else {
                            Artritis = "0";
                        }

                        if (CheckInfecciones.isChecked()) {
                            Infecciones = "1";
                        }
                        else {
                            Infecciones = "0";
                        }

                        if (CheckIrradiaciones.isChecked()) {
                            Irradiaciones = "1";
                        }
                        else {
                            Irradiaciones = "0";
                        }


                        if (CheckHemorragias.isChecked()) {
                            Hemorragias = "1";
                        }
                        else {
                            Hemorragias = "0";
                        }


                        if (CheckSinusitis.isChecked()) {
                            Sinusitis = "1";
                        }
                        else {
                            Sinusitis = "0";
                        }


                       if (CheckAccidentes.isChecked()) {
                            Accidentes = "1";
                        }
                        else {
                            Accidentes = "0";
                        }

                        if (CheckEmbarazo.isChecked()) {
                            Embarazo = "1";
                        }
                        else {
                            Embarazo = "0";
                        }


                        if (CheckHepatitis.isChecked()) {
                            Hepatitis = "1";
                        }
                        else {
                            Hepatitis = "0";
                        }

                        if (CheckVih.isChecked()) {
                            Vih = "1";
                        }
                        else {
                            Vih = "0";
                        }

    }

    class insertar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "anamnesis"));
            datos.add(new BasicNameValuePair("txtttomedicos", TratamientoMed));
            datos.add(new BasicNameValuePair("txtIdingesmed", IngesMedicamentos));
            datos.add(new BasicNameValuePair("txtrespiratorias", EnfRespiratorias));
            datos.add(new BasicNameValuePair("txtcardiacas", EnfCardiacas));
            datos.add(new BasicNameValuePair("txthipertension", Hipertension));
            datos.add(new BasicNameValuePair("txtgastro", EnfGastrointestinales));
            datos.add(new BasicNameValuePair("txtdiabetes", Diabetes));
            datos.add(new BasicNameValuePair("txthipotension", Hipotension));
            datos.add(new BasicNameValuePair("txthepatitis", Hepatitis));
            datos.add(new BasicNameValuePair("txtreumatica", FiebreReumatica));
            datos.add(new BasicNameValuePair("txtartritis", Artritis));
            datos.add(new BasicNameValuePair("txtinfecciones", Infecciones));
            datos.add(new BasicNameValuePair("txtirradiaciones", Irradiaciones));
            datos.add(new BasicNameValuePair("txthemorragias", Hemorragias));
            datos.add(new BasicNameValuePair("txtaccidentes", Accidentes));
            datos.add(new BasicNameValuePair("txtembarazo", Embarazo));
            datos.add(new BasicNameValuePair("txtvih", Vih));
            datos.add(new BasicNameValuePair("txtobs", ""));//observaciones vacio por ahora
            datos.add(new BasicNameValuePair("txtidpaciente", idPaciente));
            datos.add(new BasicNameValuePair("txtsinusitis", Sinusitis));

            try {
                envio.setEntity(new UrlEncodedFormEntity(datos));
                try {
                    HttpResponse respuesta = peticion.execute(envio);
                    HttpEntity resEntity = respuesta.getEntity();

                    InputStream is = resEntity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String dato = null;
                    StringBuilder sb = new StringBuilder();

                    while((dato = br.readLine()) != null){
                        sb.append(dato);
                    }

                    is.close();

                    json = sb.toString();

                    Log.d("d", json);

                } catch (ClientProtocolException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                jsonO = new JSONArray(json);
                Log.d("ddd","Hola");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String feed) {
            Toast.makeText(getApplicationContext(), "Insertado con exito", Toast.LENGTH_LONG).show();
            Intent inicio = new Intent(getApplicationContext(),
                    HistoriaClinicaAnamnesis.class);
            //inicio.putExtra("idOdontologo", usuarios[0]);
            startActivity(inicio);
            finish();
        }
    }




}


