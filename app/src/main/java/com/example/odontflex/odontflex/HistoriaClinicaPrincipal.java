package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class HistoriaClinicaPrincipal extends ActionBarActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;
    String [] paciente;

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

    String idOdontologo, idPaciente;
    TextView txtCedulaPaciente;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_clinica_principal);
        Intent dato = getIntent();
        idOdontologo = dato.getStringExtra("idOdontologo");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        txtCedulaPaciente = (TextView)findViewById(R.id.txtCedulaPaciente);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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
        getMenuInflater().inflate(R.menu.menu_historia_clinica_principal, menu);
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

    public void Consultar (View v){
        idPaciente = txtCedulaPaciente.getText().toString();

        new consultar().execute();

    }

    class consultar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "paciente"));
            datos.add(new BasicNameValuePair("txtIdPaciente", idPaciente));

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
            paciente = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    paciente[i] = jsonO.getJSONObject(i).getString("idPaciente");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            progressBar.setVisibility(View.GONE);
            if (paciente.length>0){
                Toast.makeText(getApplicationContext(), "Paciente encontrado ", Toast.LENGTH_LONG).show();

            } else {
                Intent infoPersonal = new Intent(getApplicationContext(),
                        HistoriaClinicaInfoPersonal.class);
                infoPersonal.putExtra("idOdontologo", idOdontologo);
                infoPersonal.putExtra("idPaciente", idPaciente);
                startActivity(infoPersonal);
                finish();
            }
        }
    }
}
