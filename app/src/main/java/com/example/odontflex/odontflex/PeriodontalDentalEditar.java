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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

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
            erosion, malformaciones, trauma, rotaciones;

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
