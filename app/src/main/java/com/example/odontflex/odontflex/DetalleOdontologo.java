package com.example.odontflex.odontflex;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class DetalleOdontologo extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;

    TextView txtIdOdontologo, txtNomOdontologo, txtApeOdontologo, txtFechaNacimiento,
            txtDirOdontologo, txtTarProOdontologo, txtTelOdontologo;

    String[] idOdontologo, nomOdontologo, apeOdontologo, tarProOdontologo, fechaNacOdontologo,
            dirOdontologo, telOdontologo;
    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 30;
    private String[] mListItems;
    ListViewAdapter adapter;
    ListView lvCups;
    final int contador = 0;
    String[] opciones = new String[]{
            "",
            "",
    };

    int[] imgOpciones = {
            R.drawable.glyphicons_029_notes_2,
            R.drawable.glyphicons_195_circle_info,

    };

    int posicion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_odontologo);
        Intent dato = getIntent();
        nomOdontologo = dato.getStringArrayExtra("nomOdontologo");
        idOdontologo = dato.getStringArrayExtra("idOdontologo");
        apeOdontologo = dato.getStringArrayExtra("apeOdontologo");
        tarProOdontologo = dato.getStringArrayExtra("tarProOdontologo");
        fechaNacOdontologo = dato.getStringArrayExtra("fechaNacOdontologo");
        dirOdontologo = dato.getStringArrayExtra("dirOdontologo");
        telOdontologo = dato.getStringArrayExtra("telOdontologo");
        posicion = Integer.parseInt(dato.getStringExtra("posicion"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        txtIdOdontologo = (TextView)findViewById(R.id.txtIdOdontologo);
        txtNomOdontologo = (TextView)findViewById(R.id.txtNomOdontologo);
        txtApeOdontologo = (TextView)findViewById(R.id.txtApeOdontologo);
        txtFechaNacimiento = (TextView)findViewById(R.id.txtFechaNacimiento);
        txtDirOdontologo = (TextView)findViewById(R.id.txtDirOdontologo);
        txtTarProOdontologo = (TextView)findViewById(R.id.txtTarProOdontologo);
        txtTelOdontologo = (TextView)findViewById(R.id.txtTelOdontologo);

        txtIdOdontologo.setText(""+idOdontologo[posicion]);
        txtNomOdontologo.setText(""+nomOdontologo[posicion]);
        txtApeOdontologo.setText(""+apeOdontologo[posicion]);
        txtFechaNacimiento.setText(""+fechaNacOdontologo[posicion]);
        txtDirOdontologo.setText(""+dirOdontologo[posicion]);
        txtTarProOdontologo.setText(""+tarProOdontologo[posicion]);
        txtTelOdontologo.setText("" + telOdontologo[posicion]);

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
                        Intent historiaclinica = new Intent(getApplicationContext(),
                                HistoriaClinicaPrincipal.class);
                        startActivity(historiaclinica);
                        finish();
                        break;
                    case 1:
                        Intent info = new Intent(getApplicationContext(),
                                InfoGeneral.class);
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
        getMenuInflater().inflate(R.menu.menu_detalle_odontologo, menu);
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
        startActivity(inicio);
        finish();
    }

    public void Salir() {
        Intent salir = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(salir);
        finish();
    }

    public void Atras(View v) {
        Intent odontologo = new Intent(getApplicationContext(),
                Odontologo.class);
        odontologo.putExtra("idOdontologo", idOdontologo);
        odontologo.putExtra("nomOdontologo", nomOdontologo);
        odontologo.putExtra("apeOdontologo", apeOdontologo);
        odontologo.putExtra("tarProOdontologo", tarProOdontologo);
        odontologo.putExtra("fechaNacOdontologo", fechaNacOdontologo);
        odontologo.putExtra("dirOdontologo", dirOdontologo);
        odontologo.putExtra("telOdontologo", telOdontologo);
        startActivity(odontologo);
        finish();
    }
}
