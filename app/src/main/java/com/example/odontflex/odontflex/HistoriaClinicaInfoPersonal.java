package com.example.odontflex.odontflex;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
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
import java.util.Calendar;

public class HistoriaClinicaInfoPersonal extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;

    TextView txtIdPaciente, txtNomPaciente, txtApePaciente, txtFechaNacimiento, txtEdadPaciente,
            txtDirPaciente, txtOcupacionPaciente, txtTelPaciente;

    ImageView btnCalendario;

    String anio = "YYYY";
    String mes = "MM";
    String dia = "DD";

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

    String idPaciente, idOdontologo, nomPaciente, apePaciente, fechaNacimiento, edadPaciente, nomPaciente1,
    dirPaciente, ocupacionPaciente, telPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_clinica_info_personal);
        Intent dato = getIntent();
        idOdontologo = dato.getStringExtra("idOdontologo");
        nomPaciente1 = dato.getStringExtra("nomPaciente");
        idPaciente = dato.getStringExtra("idPaciente");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        txtIdPaciente = (TextView)findViewById(R.id.txtIdOdontologo);
        txtNomPaciente = (TextView)findViewById(R.id.txtNomOdontologo);
        txtApePaciente = (TextView)findViewById(R.id.txtApeOdontologo);
        txtFechaNacimiento = (TextView)findViewById(R.id.txtFechaNacimiento);
        txtEdadPaciente = (TextView)findViewById(R.id.txtEdadPOdontologo);
        txtDirPaciente = (TextView)findViewById(R.id.txtDirOdontologo);
        txtOcupacionPaciente = (TextView)findViewById(R.id.txtTarProOdontologo);
        txtTelPaciente = (TextView)findViewById(R.id.txtTelPaciente);
        btnCalendario = (ImageView)findViewById(R.id.btnCalendario);

        txtIdPaciente.setEnabled(false);
        txtIdPaciente.setText("" + idPaciente);
        txtFechaNacimiento.setEnabled(false);
        txtFechaNacimiento.setText("" + anio + "/" + mes + "/" + dia);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha();
            }
        });


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
        getMenuInflater().inflate(R.menu.menu_historia_clinica_info_personal, menu);
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
        inicio.putExtra("idOdontologo", idOdontologo);
        startActivity(inicio);
        finish();
    }

    public void Salir (){
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

    public void fecha() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog myDatePiccker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day2) {
                anio = Integer.toString(year1);
                mes = Integer.toString(month1 + 1);
                dia = Integer.toString(day2);
                txtFechaNacimiento.setText("" + anio + "/" + mes + "/" + dia);

            }
        },
                mYear, mMonth, mDay);
        myDatePiccker.show();
    }

    public void Siguiente (View v){
        nomPaciente = txtNomPaciente.getText().toString();
        apePaciente = txtApePaciente.getText().toString();
        fechaNacimiento = txtFechaNacimiento.getText().toString();
        edadPaciente = txtEdadPaciente.getText().toString();
        dirPaciente = txtDirPaciente.getText().toString();
        ocupacionPaciente = txtOcupacionPaciente.getText().toString();
        telPaciente = txtTelPaciente.getText().toString();

        new insertar().execute();

    }

    class insertar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "infoGeneral"));
            datos.add(new BasicNameValuePair("txtIdPaciente", idPaciente));
            datos.add(new BasicNameValuePair("txtIdOdontologo", idOdontologo));
            datos.add(new BasicNameValuePair("txtNomPaciente", nomPaciente));
            datos.add(new BasicNameValuePair("txtApePaciente", apePaciente));
            datos.add(new BasicNameValuePair("txtFechaNacimiento", fechaNacimiento));
            datos.add(new BasicNameValuePair("txtEdadPaciente", edadPaciente));
            datos.add(new BasicNameValuePair("txtDirPaciente", dirPaciente));
            datos.add(new BasicNameValuePair("txtOcupacionPaciente", ocupacionPaciente));
            datos.add(new BasicNameValuePair("txtTelPaciente", telPaciente));

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
                        inicio.putExtra("idPaciente", idPaciente);
            inicio.putExtra("idOdontologo", idOdontologo);
            inicio.putExtra("nomPaciente", nomPaciente1);
            startActivity(inicio);
            finish();
        }
    }

}
