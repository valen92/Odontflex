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

public class EstomatologicoEditar extends AppCompatActivity {

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
            Oclusion, nomPaciente, estomatologicoInfo;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;

    TextView txtObservaciones;

    String observaciones ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estomatologico_editar);

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
        getMenuInflater().inflate(R.menu.menu_estomatologico_editar, menu);
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
        final Dialog dialog = new Dialog(EstomatologicoEditar.this);
        dialog.setContentView(R.layout.activity_dialogo_observaciones);
        dialog.show();
        btnCancelarObservacion = (Button) dialog.findViewById(R.id.btnCancelarObservacion);
        btnGuardarObservacion = (Button) dialog.findViewById(R.id.btnGuardarObservacion);
        txtObservaciones = (TextView) dialog.findViewById(R.id.txtObservacionesAnamnesis);
        txtObservaciones.setText("" + observaciones);
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
                Toast.makeText(getApplicationContext(), "La observación se ha editado con éxito", Toast.LENGTH_SHORT).show();
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


    public void Actualizar(View v) {
        onCheckboxClicked();
        Log.d("HOLA ACA", "entro correctamente");
        new actualizar().execute();


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


    class actualizar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);


            datos.add(new BasicNameValuePair("op", "actualizarexamenestoma"));
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
            Toast.makeText(getApplicationContext(), "actualizado con exito estomatologico", Toast.LENGTH_LONG).show();
            Intent inicio = new Intent(getApplicationContext(),
                    HistoriaPacienteExisteMenuPrincipal.class);
            inicio.putExtra("idOdontologo", idOdontologo);
            inicio.putExtra("idPaciente", idPaciente);
            startActivity(inicio);
            finish();
        }
    }

}
