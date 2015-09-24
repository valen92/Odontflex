package com.example.odontflex.odontflex;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class HistoriaClinicaNotasEvo extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;

    TextView txthora, txtcalendario, txtdetalle;

    String anio = "YYYY";
    String mes = "MM";
    String dia = "DD";

    int  Horas;
    int Minutos;

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

    Button btnSi, btnNo;

    String minutos = "";

    ImageView btnCalendario, btnHora;

    String idPaciente,idOdontologo, strdetalle, strhora, strfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_clinica_notas_evo);
        Intent dato = getIntent();


        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        txthora  = (TextView)findViewById(R.id.editTextHora);
        txtcalendario  = (TextView)findViewById(R.id.editTextfecha);
        txtdetalle  = (TextView)findViewById(R.id.txtDetalleNotas);

        btnCalendario = (ImageView)findViewById(R.id.btncalendarionotas);
        btnHora = (ImageView)findViewById(R.id.btnhoranotas);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora();
            }
        });

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

                        final Dialog dialog = new Dialog(HistoriaClinicaNotasEvo.this);
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
                                Toast.makeText(getApplicationContext(), "Salio", Toast.LENGTH_SHORT).show();

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

                        final Dialog dialog1 = new Dialog(HistoriaClinicaNotasEvo.this);
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
                                Toast.makeText(getApplicationContext(), "Salio", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_historia_clinica_notas_evo, menu);
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

    private void openPane() {
        mPanes.openPane();
    }

    private void closePane() {
        mPanes.closePane();
    }

    public void onBackPressed() {
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

    public void obtenerFecha() {
        DatePickerDialog myDatePiccker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day2) {
                anio = Integer.toString(year1);
                mes = Integer.toString(month1 + 1);
                dia = Integer.toString(day2);
                txtcalendario.setText("" + anio + "/" + mes + "/" + dia);

            }
        },
                2015, 04, 13);
        myDatePiccker.show();
    }

    public void obtenerHora(){

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                minutos = Integer.toString(selectedMinute);
                if (minutos.length()==1){
                    minutos = "0"+minutos;
                }

                txthora.setText( selectedHour + ":" + minutos);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
    }


    public void Siguiente (View v){

        strhora = txthora.getText().toString();
        strfecha = txtcalendario.getText().toString();
        strdetalle = txtdetalle.getText().toString();


        new insertar().execute();

    }

    class insertar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "notasevo"));
            datos.add(new BasicNameValuePair("txtfechanotas", strfecha));
            datos.add(new BasicNameValuePair("txtsedahoranotas", strhora));
            datos.add(new BasicNameValuePair("txtdetalleNotas", strdetalle));
            datos.add(new BasicNameValuePair("txtodontologoNotas", "Odontologo Firma"));
            datos.add(new BasicNameValuePair("txtpacienteNotas", "Paciente Firma"));
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


            return null;
        }

        protected void onPostExecute(String feed) {
            Toast.makeText(getApplicationContext(), "Insertado con exito", Toast.LENGTH_LONG).show();

        }
    }

    class borrarDatos extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "borradoDatos"));
            datos.add(new BasicNameValuePair("txtactividad", "notasEvolucion"));
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
            Toast.makeText(getApplicationContext(), "eliminado con exito", Toast.LENGTH_LONG).show();

        }
    }


}
