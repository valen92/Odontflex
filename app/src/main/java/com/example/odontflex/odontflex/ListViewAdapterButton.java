package com.example.odontflex.odontflex;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class ListViewAdapterButton extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] titulos;
    int[] imagenes1, imagenes2;
    LayoutInflater inflater;
    String idPaciente, idOdontologo, nomPaciente;
    String opcion = "";
    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;
    //Arrays info personal
    String [] paciente, nombre, apellido, nacimiento, direccion,
        ocupacion, telefono, edad;
    //Arrays anamnesis
    String [] anamnesisInfo, obsAnamnesis, alergias, habitos, otrosHabitos, estomatologicoInfo, obsEstomatologico,
        periodontal, dental, obsDental;

    public ListViewAdapterButton(Context context, String[] titulos, int[] imagenes1, int[] imagenes2,
                                 String idPaciente, String idOdontologo, String nomPaciente) {
        this.context = context;
        this.titulos = titulos;
        this.imagenes1 = imagenes1;
        this.imagenes2 = imagenes2;
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.nomPaciente = nomPaciente;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtTitle;
        ImageView imgButton1, imgButton2;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.layout_menu_button, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.layout_menu_text);
        imgButton1 = (ImageView) itemView.findViewById(R.id.imgButton1);
        imgButton2 = (ImageView) itemView.findViewById(R.id.imgbutton2);

        // Capture position and set to the TextViews
        txtTitle.setText(titulos[position]);
        imgButton1.setImageResource(imagenes1[position]);
        imgButton2.setImageResource(imagenes2[position]);

        imgButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        opcion="1";
                        new paciente().execute();
                        break;
                    case 1:
                        opcion="1";
                        new anamnesis().execute();
                        break;
                    case 2:
                        opcion="1";
                        new alergiashabitos().execute();
                        break;
                    case 3:
                        opcion="1";
                        new estomatologico().execute();
                        break;
                    case 4:
                        opcion="1";
                        new periodontaldental().execute();
                        break;
                }
            }
        });

        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        opcion="2";
                        new paciente().execute();
                        break;
                    case 1:
                        opcion="2";
                        new anamnesis().execute();
                        break;
                    case 2:
                        opcion="2";
                        new alergiashabitos().execute();
                        break;
                    case 3:
                        opcion="2";
                        new estomatologico().execute();
                        break;
                    case 4:
                        opcion="2";
                        new periodontaldental().execute();
                        break;
                }
            }
        });

        return itemView;
    }

    class paciente extends AsyncTask<String, String, String> {

        private Exception exception;

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
            nombre = new String[jsonO.length()];
            apellido = new String[jsonO.length()];
            nacimiento = new String[jsonO.length()];
            direccion = new String[jsonO.length()];
            ocupacion = new String[jsonO.length()];
            telefono = new String[jsonO.length()];
            edad = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    paciente[i] = jsonO.getJSONObject(i).getString("idPaciente");
                    nombre[i] = jsonO.getJSONObject(i).getString("nomPaciente");
                    apellido[i] = jsonO.getJSONObject(i).getString("apePaciente");
                    nacimiento[i] = jsonO.getJSONObject(i).getString("fechanacPaciente");
                    direccion[i] = jsonO.getJSONObject(i).getString("dirPaciente");
                    ocupacion[i] = jsonO.getJSONObject(i).getString("ocuPaciente");
                    telefono[i] = jsonO.getJSONObject(i).getString("telPaciente");
                    edad[i] = jsonO.getJSONObject(i).getString("edadPaciente");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (paciente.length>0){
                if(opcion=="1") {
                    Intent existe = new Intent(context,
                            InfoPersonalVer.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nombre[0]);
                    existe.putExtra("apePaciente", apellido[0]);
                    existe.putExtra("fechanacPaciente", nacimiento[0]);
                    existe.putExtra("dirPaciente", direccion[0]);
                    existe.putExtra("ocuPaciente", ocupacion[0]);
                    existe.putExtra("telPaciente", telefono[0]);
                    existe.putExtra("edadPaciente", edad[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }
                else {
                    Intent existe = new Intent(context,
                            PacienteInfoPersonal.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nombre[0]);
                    existe.putExtra("apePaciente", apellido[0]);
                    existe.putExtra("fechanacPaciente", nacimiento[0]);
                    existe.putExtra("dirPaciente", direccion[0]);
                    existe.putExtra("ocuPaciente", ocupacion[0]);
                    existe.putExtra("telPaciente", telefono[0]);
                    existe.putExtra("edadPaciente", edad[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }

            } else {
                Toast.makeText(context, "Se ha presentadoun error al hacer la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class anamnesis extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "veranamnesis"));
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
            anamnesisInfo = new String[jsonO.length()];
            obsAnamnesis = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    anamnesisInfo[i] = jsonO.getJSONObject(i).getString("ttomedicosAnamnesis") +
                            jsonO.getJSONObject(i).getString("ingesmedAnamnesis") +
                            jsonO.getJSONObject(i).getString("respiratoriasAnamnesis") +
                            jsonO.getJSONObject(i).getString("cardiacasAnamnesis") +
                            jsonO.getJSONObject(i).getString("hipertensionAnamnesis") +
                            jsonO.getJSONObject(i).getString("gastroAnamnesis") +
                            jsonO.getJSONObject(i).getString("diabetesAnamnesis") +
                            jsonO.getJSONObject(i).getString("hipotensionAnamnesis") +
                            jsonO.getJSONObject(i).getString("hepatitisAnamnesis") +
                            jsonO.getJSONObject(i).getString("reumaticaAnamnesis") +
                            jsonO.getJSONObject(i).getString("artritisAnamnesis") +
                            jsonO.getJSONObject(i).getString("infeccionesAnamnesis") +
                            jsonO.getJSONObject(i).getString("irradiacionesAnamnesis") +
                            jsonO.getJSONObject(i).getString("hemorragiasAnamnesis") +
                            jsonO.getJSONObject(i).getString("accidentesAnamnesis") +
                            jsonO.getJSONObject(i).getString("embarazoAnamnesis") +
                            jsonO.getJSONObject(i).getString("vihAnamnesis") +
                            jsonO.getJSONObject(i).getString("sinusitisAnamnesis");
                    obsAnamnesis[i] = jsonO.getJSONObject(i).getString("obsAnamnesis");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (anamnesisInfo.length>0){
                if(opcion=="1") {
                    Intent existe = new Intent(context,
                            AnamnesisVer.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("anamnesisInfo", anamnesisInfo[0]);
                    existe.putExtra("obsAnamnesis", obsAnamnesis[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }
                else {
                    Intent existe = new Intent(context,
                            AnamnesisEditar.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("anamnesisInfo", anamnesisInfo[0]);
                    existe.putExtra("obsAnamnesis", obsAnamnesis[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }

            } else {
                Toast.makeText(context, "Se ha presentadoun error al hacer la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class alergiashabitos extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "veralergiashabitos"));
            datos.add(new BasicNameValuePair("txtIdPaciente", idPaciente));

            try {
                envio.setEntity(new UrlEncodedFormEntity(datos));
                try {
                    HttpResponse respuesta = peticion.execute(envio);
                    HttpEntity resEntity = respuesta.getEntity();

                    InputStream is = resEntity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            is, "UTF-8"), 8);
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
            alergias = new String[jsonO.length()];
            habitos = new String[jsonO.length()];
            otrosHabitos = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    alergias[i] = jsonO.getJSONObject(i).getString("descripcionAlergias");
                    otrosHabitos[i] = jsonO.getJSONObject(i).getString("otrosHabitos");
                    habitos[i] = jsonO.getJSONObject(i).getString("cepilladoHabitos")+
                            jsonO.getJSONObject(i).getString("sedaHabitos");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (alergias.length>0){
                if(opcion=="1") {
                    Intent existe = new Intent(context,
                            AlergiasHabitosVer.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("alergias", alergias[0]);
                    existe.putExtra("otrosHabitos", otrosHabitos[0]);
                    existe.putExtra("habitos", habitos[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }
                else {
                    Intent existe = new Intent(context,
                            AlergiasHabitosEditar.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("alergias", alergias[0]);
                    existe.putExtra("otrosHabitos", otrosHabitos[0]);
                    existe.putExtra("habitos", habitos[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }

            } else {
                Toast.makeText(context, "Se ha presentadoun error al hacer la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class estomatologico extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "verestomatologico"));
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
            estomatologicoInfo = new String[jsonO.length()];
            obsEstomatologico = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {


                    estomatologicoInfo[i] = jsonO.getJSONObject(i).getString("atmEstomatologico") +
                            jsonO.getJSONObject(i).getString("musculosEstomatologico") +
                            jsonO.getJSONObject(i).getString("pielEstomatologico") +
                            jsonO.getJSONObject(i).getString("labiosEstomatologico") +
                            jsonO.getJSONObject(i).getString("gangliosEstomatologico") +
                            jsonO.getJSONObject(i).getString("carrillosEstomatologico") +
                            jsonO.getJSONObject(i).getString("pisoEstomatologico") +
                            jsonO.getJSONObject(i).getString("paladarEstomatologico") +
                            jsonO.getJSONObject(i).getString("salivalesEstomatologico") +
                            jsonO.getJSONObject(i).getString("frenillosEstomatologico") +
                            jsonO.getJSONObject(i).getString("lenguaEstomatologico") +
                            jsonO.getJSONObject(i).getString("enciasEstomatologico") +
                            jsonO.getJSONObject(i).getString("mucosasEstomatologico") +
                            jsonO.getJSONObject(i).getString("oclusionEstomatologico");
                            obsEstomatologico[i] = jsonO.getJSONObject(i).getString("obsEstomatologico");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (estomatologicoInfo.length>0){
                if(opcion=="1") {
                    Intent existe = new Intent(context,
                            EstomatologicoVer.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("EstomaInfo", estomatologicoInfo[0]);
                    existe.putExtra("obsEstoma", obsEstomatologico[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }
                else {
                    Intent existe = new Intent(context,
                            EstomatologicoEditar.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("EstomaInfo", estomatologicoInfo[0]);
                    existe.putExtra("obsEstoma", obsEstomatologico[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }

            } else {
                Toast.makeText(context, "Se ha presentadoun error al hacer la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class periodontaldental extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "verperiodontaldental"));
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
            periodontal = new String[jsonO.length()];
            dental = new String[jsonO.length()];
            obsDental = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {


                    dental[i] = jsonO.getJSONObject(i).getString("supermumerariosDental") +
                            jsonO.getJSONObject(i).getString("abrasionDental") +
                            jsonO.getJSONObject(i).getString("manchasDental") +
                            jsonO.getJSONObject(i).getString("patpulparDental") +
                            jsonO.getJSONObject(i).getString("fracturasDental") +
                            jsonO.getJSONObject(i).getString("atriccionDental") +
                            jsonO.getJSONObject(i).getString("erosionDental") +
                            jsonO.getJSONObject(i).getString("malformacionesDental") +
                            jsonO.getJSONObject(i).getString("traumaDental") +
                            jsonO.getJSONObject(i).getString("rotacionesDental");
                    periodontal[i] = jsonO.getJSONObject(i).getString("blandaPeriodontal") +
                            jsonO.getJSONObject(i).getString("calcificadaPeriodontal") +
                            jsonO.getJSONObject(i).getString("bolsasPeriodontal") +
                            jsonO.getJSONObject(i).getString("gingivalesPeriodontal");
                    obsDental[i] = jsonO.getJSONObject(i).getString("obsDental");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (dental.length>0){
                if(opcion=="1") {
                    Intent existe = new Intent(context,
                            PeriodontalDentalVer.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("periodontal", periodontal[0]);
                    existe.putExtra("dental", dental[0]);
                    existe.putExtra("obsDental", obsDental[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }
                else {
                    Intent existe = new Intent(context,
                            PeriodontalDentalEditar.class);
                    existe.putExtra("idPaciente", idPaciente);
                    existe.putExtra("nomPaciente", nomPaciente);
                    existe.putExtra("periodontal", periodontal[0]);
                    existe.putExtra("dental", dental[0]);
                    existe.putExtra("obsDental", obsDental[0]);
                    existe.putExtra("idOdontologo", idOdontologo);
                    context.startActivity(existe);
                }

            } else {
                Toast.makeText(context, "Se ha presentadoun error al hacer la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

}