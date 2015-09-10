package com.example.odontflex.odontflex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;
    String [] usuarios;
    TextView txtUsuario, txtPassword;
    String usuario, password;

    private static final String TAG_SUCCESS = "OK";
    private static final String TAG_MESSAGE = "estado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (TextView)findViewById(R.id.txtUsuario);
        txtPassword = (TextView)findViewById(R.id.txtPassword);

    }

    public void Login(View v){
        usuario = txtUsuario.getText().toString();
        password = txtPassword.getText().toString();
        new validar().execute();
    }

    class validar extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            HttpClient peticion = new DefaultHttpClient();
            HttpPost envio = new HttpPost(SERVER_URL);
            ArrayList<NameValuePair> datos = new ArrayList<NameValuePair>(0);

            datos.add(new BasicNameValuePair("op", "login"));
            datos.add(new BasicNameValuePair("txtUsuario", usuario));
            datos.add(new BasicNameValuePair("txtPassword", password));

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
            usuarios = new String[jsonO.length()];

            for(int i = 0; i < jsonO.length(); i++){
                try {
                    usuarios[i] = jsonO.getJSONObject(i).getString("idOdontologo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonO.toString();
        }

        protected void onPostExecute(String feed) {
            if (usuarios.length>0){
                Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_LONG).show();
                Intent intMenuPpl = new Intent(getApplicationContext(), Menu_principal.class);
                startActivity(intMenuPpl);
            } else {
                Toast.makeText(getApplicationContext(), "Usuario y/o Password incorrecta, " +
                        "intentelo de nuevo", Toast.LENGTH_LONG).show();
            }
        }
    }

}
