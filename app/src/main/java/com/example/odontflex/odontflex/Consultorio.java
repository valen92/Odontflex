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
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class Consultorio extends AppCompatActivity {

    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    ListViewAdapter adapter, adaptador;
    ListView lvConsultorio;
    String[] opciones = new  String[]{
            "",
            "",
    };

    int[] imgOInfo={
            10,
            10,
    };

    String[] info = new  String[]{
            "Directorio Pacientes",
            "Odontólogos",
    };

    int[] imgOpciones={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.glyphicons_195_circle_info,
    };

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;
    String [] nomPaciente, telPaciente, idOdontologo, nomOdontologo, apeOdontologo, tarProOdontologo, fechaNacOdontologo,
        dirOdontologo, telOdontologo;
    ProgressBar progressBar;
    String idOdo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultorio);
        Intent dato = getIntent();
        idOdo = dato.getStringExtra("idOdontologo");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mPanes.setParallaxDistance(PARALLAX_SIZE);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);
        list.setAdapter(adapter);
        list.setBackgroundColor(Color.rgb(178, 223, 219));


        lvConsultorio = (ListView)findViewById(R.id.listViewConsultorio);
        adaptador = new ListViewAdapter(this,info,imgOInfo);
        lvConsultorio.setAdapter(adaptador);
        lvConsultorio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        new paciente().execute();
                        break;
                    case 1:
                        new odontologo().execute();
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
                        historiaclinica.putExtra("idOdontologo", idOdo);
                        startActivity(historiaclinica);
                        finish();
                        break;
                    case 1:
                        Intent info = new Intent(getApplicationContext(),
                                InfoGeneral.class);
                        info.putExtra("idOdontologo", idOdo);
                        startActivity(info);
                        finish();
                        break;
                }

            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consultorio, menu);
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
        inicio.putExtra("idOdontologo", idOdo);
        startActivity(inicio);
        finish();
    }

    public void Salir (){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }



    class paciente extends AsyncTask<String, String, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "pacientes"));

            try {
                envio.setEntity(new UrlEncodedFormEntity(datos));
                try {
                    HttpResponse respuesta = peticion.execute(envio);
                    HttpEntity resEntity = respuesta.getEntity();

                    InputStream is = resEntity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
                    String dato = null;
                    StringBuilder sb = new StringBuilder();

                    while((dato = br.readLine()) != null){
                        sb.append(dato + "\n");
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
            nomPaciente = new String[jsonO.length()];
            telPaciente = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    nomPaciente[i] = jsonO.getJSONObject(i).getString("nomPaciente") + " " + jsonO.getJSONObject(i).getString("apePaciente");
                    telPaciente[i] = jsonO.getJSONObject(i).getString("telPaciente");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            progressBar.setVisibility(View.GONE);
            if (nomPaciente.length>0){
                Intent paciente = new Intent(getApplicationContext(),
                        Paciente.class);
                paciente.putExtra("nomPaciente", nomPaciente);
                paciente.putExtra("telPaciente", telPaciente);
                startActivity(paciente);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "No se han ingresado pacientes, " +
                        "al sistema", Toast.LENGTH_LONG).show();
            }
        }
    }



    class odontologo extends AsyncTask<String, String, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "odontologo"));

            try {
                envio.setEntity(new UrlEncodedFormEntity(datos));
                try {
                    HttpResponse respuesta = peticion.execute(envio);
                    HttpEntity resEntity = respuesta.getEntity();

                    InputStream is = resEntity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
                    String dato = null;
                    StringBuilder sb = new StringBuilder();

                    while((dato = br.readLine()) != null){
                        sb.append(dato + "\n");
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
            idOdontologo = new String[jsonO.length()];
            nomOdontologo = new String[jsonO.length()];
            apeOdontologo = new String[jsonO.length()];
            tarProOdontologo = new String[jsonO.length()];
            fechaNacOdontologo = new String[jsonO.length()];
            dirOdontologo = new String[jsonO.length()];
            telOdontologo = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    idOdontologo[i] = jsonO.getJSONObject(i).getString("idOdontologo");
                    nomOdontologo[i] = jsonO.getJSONObject(i).getString("nombreOdontologo");
                    apeOdontologo[i] = jsonO.getJSONObject(i).getString("apellidosOdontologo");
                    tarProOdontologo[i] = jsonO.getJSONObject(i).getString("tarjetaprofOdontologo");
                    fechaNacOdontologo[i] = jsonO.getJSONObject(i).getString("fechanacOdontologo");
                    dirOdontologo[i] = jsonO.getJSONObject(i).getString("direccionresOdontologo");
                    telOdontologo[i] = jsonO.getJSONObject(i).getString("telefonoOdontologo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            progressBar.setVisibility(View.GONE);
            if (idOdontologo.length>0){
                Intent odontologo = new Intent(getApplicationContext(),
                        Odontologo.class);
                odontologo.putExtra("idOdontologo", idOdontologo);
                odontologo.putExtra("idOdo", idOdo);
                odontologo.putExtra("nomOdontologo", nomOdontologo);
                odontologo.putExtra("apeOdontologo", apeOdontologo);
                odontologo.putExtra("tarProOdontologo", tarProOdontologo);
                odontologo.putExtra("fechaNacOdontologo", fechaNacOdontologo);
                odontologo.putExtra("dirOdontologo", dirOdontologo);
                odontologo.putExtra("telOdontologo", telOdontologo);
                startActivity(odontologo);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "No se han encontrado resultados, " +
                        "para la consulta", Toast.LENGTH_LONG).show();
            }
        }
    }
}
