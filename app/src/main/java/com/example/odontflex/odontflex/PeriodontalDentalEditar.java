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

public class PeriodontalDentalEditar extends AppCompatActivity {

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

    private CheckBox Checkplacablanda, Checkbolsas, CheckPlacacalcificada, Checkretraccionesgingivales,
            Checksupenumerarios, Checkabrasion, Checkmanchas, Checkpatologiapulpar, Checkfracturas, Checkatriccion,
            Checkerosion, Checkmalformaciones, Checktrauma, Checkrotaciones;

    String idPaciente,idOdontologo,placablanda, bolsas, placacalcificada, retraccionesgingivales,
            supenumerarios, abrasion, manchas, patologiapulpar, fracturas, atriccion,
            erosion, malformaciones, trauma, rotaciones, nomPaciente, dental, periodontal;

    Button btnCancelarObservacion, btnGuardarObservacion, btnSi, btnNo;;

    TextView txtObservaciones;

    String observaciones ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodontal_dental_editar);
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
        getMenuInflater().inflate(R.menu.menu_periodontal_dental_editar, menu);
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
        final Dialog dialog = new Dialog(PeriodontalDentalEditar.this);
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

    public void Actualizar(View v) {
        onCheckboxClicked();
        new actualizar().execute();

    }


    private void onCheckboxClicked() {
        if (Checkplacablanda.isChecked()) {
            placablanda = "1";

        } else {
            placablanda = "0";
        }


        if (Checkbolsas.isChecked()) {
            bolsas = "1";

        } else {
            bolsas = "0";
        }


        if (CheckPlacacalcificada.isChecked()) {
            placacalcificada = "1";
        } else {
            placacalcificada = "0";
        }


        if (Checkretraccionesgingivales.isChecked()) {
            retraccionesgingivales = "1";
        } else {
            retraccionesgingivales = "0";
        }


        if (Checksupenumerarios.isChecked()) {
            supenumerarios = "1";
        } else {
            supenumerarios = "0";
        }


        if (Checkabrasion.isChecked()) {
            abrasion = "1";
        } else {
            abrasion = "0";
        }


        if (Checkmanchas.isChecked()) {
            manchas = "1";
        } else {
            manchas = "0";
        }


        if (Checkpatologiapulpar.isChecked()) {
            patologiapulpar = "1";
        } else {
            patologiapulpar = "0";
        }


        if (Checkfracturas.isChecked()) {
            fracturas = "1";
        } else {
            fracturas = "0";
        }


        if (Checkatriccion.isChecked()) {
            atriccion = "1";
        } else {
            atriccion = "0";
        }

        if (Checkmalformaciones.isChecked()) {
            malformaciones = "1";
        } else {
            malformaciones = "0";
        }

        if (Checktrauma.isChecked()) {
            trauma = "1";
        } else {
            trauma = "0";
        }


        if (Checkrotaciones.isChecked()) {
            rotaciones = "1";
        } else {
            rotaciones = "0";
        }

        if (Checkerosion.isChecked()) {
            erosion = "1";
        } else {
            erosion = "0";
        }
    }

    class actualizar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);


            datos.add(new BasicNameValuePair("op", "actualizarperiodontaldental"));
            datos.add(new BasicNameValuePair("txtplacablanda", placablanda));
            datos.add(new BasicNameValuePair("txtplacacalcificada", placacalcificada));
            datos.add(new BasicNameValuePair("txtbolsas", bolsas));
            datos.add(new BasicNameValuePair("txtretraccionesgin", retraccionesgingivales));
            datos.add(new BasicNameValuePair("txtobs", observaciones));
            datos.add(new BasicNameValuePair("txtsupenumerario", supenumerarios));
            datos.add(new BasicNameValuePair("txtabrasion", abrasion));
            datos.add(new BasicNameValuePair("txtmanchas", manchas));
            datos.add(new BasicNameValuePair("txtpatpulpar", patologiapulpar));
            datos.add(new BasicNameValuePair("txtfracturas", fracturas));
            datos.add(new BasicNameValuePair("txtatriccion", atriccion));
            datos.add(new BasicNameValuePair("txterosion", erosion));
            datos.add(new BasicNameValuePair("txtmalformaciones", malformaciones));
            datos.add(new BasicNameValuePair("txttrauma", trauma));
            datos.add(new BasicNameValuePair("txtrotaciones", rotaciones));
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
            Toast.makeText(getApplicationContext(), "Actualizado con éxito", Toast.LENGTH_LONG).show();

        }
    }

}
