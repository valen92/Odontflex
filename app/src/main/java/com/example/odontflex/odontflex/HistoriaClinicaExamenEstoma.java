package com.example.odontflex.odontflex;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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

public class HistoriaClinicaExamenEstoma extends AppCompatActivity {

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
            Oclusion, nomPaciente;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;

    TextView txtObservaciones;

    String observaciones ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_clinica_examen_estoma);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
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
                        final Dialog dialog = new Dialog(HistoriaClinicaExamenEstoma.this);
                        dialog.setContentView(R.layout.dialogo_dejar_actividad);
                        dialog.show();
                        btnSi = (Button) dialog.findViewById(R.id.btnSi);
                        btnNo = (Button) dialog.findViewById(R.id.btnNo);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        btnSi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new borrarDatos().execute();
                                //Toast.makeText(getApplicationContext(), "Salio", Toast.LENGTH_SHORT).show();

                                Intent consultorio = new Intent(getApplicationContext(),
                                        Consultorio.class);
                                consultorio.putExtra("idOdontologo", idOdontologo);
                                startActivity(consultorio);
                                finish();
                                dialog.cancel();

                            }
                        });

                        break;
                    case 1:
                        final Dialog dialog1 = new Dialog(HistoriaClinicaExamenEstoma.this);
                        dialog1.setContentView(R.layout.dialogo_dejar_actividad);
                        dialog1.show();
                        btnSi = (Button) dialog1.findViewById(R.id.btnSi);
                        btnNo = (Button) dialog1.findViewById(R.id.btnNo);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.cancel();
                            }
                        });
                        btnSi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new borrarDatos().execute();
                                //Toast.makeText(getApplicationContext(), "Salio", Toast.LENGTH_SHORT).show();
                                new borrarDatos().execute();
                                Intent infoGeneral = new Intent(getApplicationContext(),
                                        InfoGeneral.class);
                                infoGeneral.putExtra("idOdontologo", idOdontologo);
                                startActivity(infoGeneral);
                                finish();
                                dialog1.cancel();
                            }
                        });
                        break;
                }

            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historia_clinica_examen_estoma, menu);
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
        final Dialog dialog = new Dialog(HistoriaClinicaExamenEstoma.this);
        dialog.setContentView(R.layout.activity_dialogo_observaciones);
        dialog.show();
        btnCancelarObservacion = (Button) dialog.findViewById(R.id.btnCancelarObservacion);
        btnGuardarObservacion = (Button) dialog.findViewById(R.id.btnGuardarObservacion);
        txtObservaciones = (TextView) dialog.findViewById(R.id.txtObservacionesAnamnesis);
        txtObservaciones.setText(""+observaciones);
        btnCancelarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnGuardarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observaciones = txtObservaciones.getText().toString();
                dialog.cancel();
                //Toast.makeText(getApplicationContext(), "La observación se ha agregado con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        //dialogo.guardarObservacion

    }

    public void Siguiente(View v) {
        onCheckboxClicked();
        new insertar().execute();

    }


    private void onCheckboxClicked() {
        if (CheckATM.isChecked()) {
            ATM = "1";

        } else {
            ATM = "0";
        }


        if (CheckMusculos.isChecked()) {
            Musculos = "1";

        } else {
            Musculos = "0";
        }


        if (CheckPiel.isChecked()) {
            Piel = "1";
        } else {
            Piel = "0";
        }


        if (CheckLabios.isChecked()) {
            Labios = "1";
        } else {
            Labios = "0";
        }


        if (CheckGanglios.isChecked()) {
            Ganglios = "1";
        } else {
            Ganglios = "0";
        }


        if (CheckCarrillo.isChecked()) {
            Carrillo = "1";
        } else {
            Carrillo = "0";
        }


        if (CheckPisoboca.isChecked()) {
            Pisoboca = "1";
        } else {
            Pisoboca = "0";
        }


        if (CheckPaladar.isChecked()) {
            Paladar = "1";
        } else {
            Paladar = "0";
        }


        if (CheckGlandsali.isChecked()) {
            Glandsalivales = "1";
        } else {
            Glandsalivales = "0";
        }


        if (CheckFrenillos.isChecked()) {
            Frenillos = "1";
        } else {
            Frenillos = "0";
        }

        if (CheckLengua.isChecked()) {
            Lengua = "1";
        } else {
            Lengua = "0";
        }

        if (CheckEncias.isChecked()) {
            Encias = "1";
        } else {
            Encias = "0";
        }


        if (CheckMucosas.isChecked()) {
            Mucosas = "1";
        } else {
            Mucosas = "0";
        }


        if (CheckOclusion.isChecked()) {
            Oclusion = "1";
        } else {
            Oclusion = "0";
        }

    }

    class insertar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);


            datos.add(new BasicNameValuePair("op", "examenestoma"));
            datos.add(new BasicNameValuePair("txtatm", ATM));
            datos.add(new BasicNameValuePair("txtmusculos", Musculos));
            datos.add(new BasicNameValuePair("txtpiel", Piel));
            datos.add(new BasicNameValuePair("txtlabios", Labios));
            datos.add(new BasicNameValuePair("txtganglios", Ganglios));
            datos.add(new BasicNameValuePair("txtcarrillo", Carrillo));
            datos.add(new BasicNameValuePair("txtpisoboca", Pisoboca));
            datos.add(new BasicNameValuePair("txtpaladar", Paladar));
            datos.add(new BasicNameValuePair("txtglandsalivales", Glandsalivales));
            datos.add(new BasicNameValuePair("txtfrenillos", Frenillos));
            datos.add(new BasicNameValuePair("txtlengua", Lengua));
            datos.add(new BasicNameValuePair("txtencias", Encias));
            datos.add(new BasicNameValuePair("txtmucosas", Mucosas));
            datos.add(new BasicNameValuePair("txtoclusion", Oclusion));
            datos.add(new BasicNameValuePair("txtobs", observaciones));
            datos.add(new BasicNameValuePair("txtidpaciente", idPaciente));


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
            //Toast.makeText(getApplicationContext(), "NP: "+nomPaciente, Toast.LENGTH_LONG).show();
            Intent inicio = new Intent(getApplicationContext(),
                    HistoriaClinicaExamenPerioDent.class);
            inicio.putExtra("idOdontologo", idOdontologo);
            inicio.putExtra("idPaciente", idPaciente);
            inicio.putExtra("nomPaciente", nomPaciente);
            startActivity(inicio);
            finish();
        }
    }

    class borrarDatos extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "borradoDatos"));
            datos.add(new BasicNameValuePair("txtactividad", "estoma"));
            datos.add(new BasicNameValuePair("txtidpaciente", idPaciente));



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
           // Toast.makeText(getApplicationContext(), "eliminado con exito", Toast.LENGTH_LONG).show();

        }
    }

}
