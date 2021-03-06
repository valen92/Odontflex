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
import android.widget.CompoundButton;
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

public class AlergiasHabitosEditar extends AppCompatActivity {

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

    private CheckBox CheckCepillado1vez, CheckCepillado2veces, CheckSeda1vez, CheckSeda2veces;

    Button btnSi, btnNo;
    TextView txtAlergias, txtOtros;

    String alergias = "", otros = "", Cepillado = "", Seda = "", idPaciente = "", idOdontologo, habitos, nomPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergias_habitos_editar);
        Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente = dato.getStringExtra("nomPaciente");
        alergias = dato.getStringExtra("alergias");
        otros = dato.getStringExtra("otrosHabitos");
        habitos = dato.getStringExtra("habitos");
        Cepillado=habitos.substring(0,1);
        Seda=habitos.substring(1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        CheckCepillado1vez = (CheckBox) findViewById(R.id.checkBoxCepillado1vez);
        CheckCepillado2veces = (CheckBox) findViewById(R.id.checkBoxCepillado2veces);
        CheckSeda1vez = (CheckBox) findViewById(R.id.checkBoxSeda1vez);
        CheckSeda2veces = (CheckBox) findViewById(R.id.checkBoxSeda2veces);

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

        CheckCepillado1vez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    CheckCepillado2veces.setEnabled(false);
                    CheckCepillado2veces.setChecked(false);
                    Cepillado = "1";
                    //Toast.makeText(getApplicationContext(), "1 vez" + Cepillado, Toast.LENGTH_LONG).show();

                } else {
                    if (b == false) {
                        CheckCepillado2veces.setEnabled(true);
                        Cepillado = "0";
                        //Toast.makeText(getApplicationContext(), "1 vez" + Cepillado, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        CheckCepillado2veces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    CheckCepillado1vez.setEnabled(false);
                    CheckCepillado1vez.setChecked(false);
                    Cepillado = "2";


                } else if (b == false) {
                    CheckCepillado1vez.setEnabled(true);
                    Cepillado = "0";


                }
            }
        });

        CheckSeda1vez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    CheckSeda2veces.setEnabled(false);
                    CheckSeda2veces.setChecked(false);
                    Seda = "1";


                } else {
                    if (b == false) {
                        CheckSeda2veces.setEnabled(true);
                        Seda = "0";

                    }
                }
            }
        });


        CheckSeda2veces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    CheckSeda1vez.setEnabled(false);
                    CheckSeda1vez.setChecked(false);
                    Seda = "2";


                } else if (b == false) {
                    CheckSeda1vez.setEnabled(true);
                    Seda = "0";


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alergias_habitos_editar, menu);
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


    public void Actualizar(View v) {
        alergias = txtAlergias.getText().toString();
        otros = txtOtros.getText().toString();

        new actualizar().execute();

    }


    class actualizar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "actualizaralergiashabitos"));
            datos.add(new BasicNameValuePair("txtcepillado", Cepillado));
            datos.add(new BasicNameValuePair("txtseda", Seda));
            datos.add(new BasicNameValuePair("txtotros", otros));
            datos.add(new BasicNameValuePair("txtalergias", alergias));
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
            Toast.makeText(getApplicationContext(), "Actualizado con exito", Toast.LENGTH_LONG).show();

        }
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
}
