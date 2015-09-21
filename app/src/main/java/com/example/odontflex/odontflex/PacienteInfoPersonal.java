package com.example.odontflex.odontflex;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class PacienteInfoPersonal extends AppCompatActivity {

    String SERVER_URL = "http://www.mustflex.com/Odontflex/login.php";
    static String json;
    JSONArray jsonO;

    TextView txtIdPaciente, txtNomPaciente, txtApePaciente, txtFechaNacimiento, txtEdadPaciente,
            txtDirPaciente, txtOcupacionPaciente, txtTelPaciente;

    private SlidingPaneLayout mPanes;
    ListViewAdapter adapter;

    String[] opciones = new  String[]{
            "",
            ""
    };

    int[] imgOpciones={
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info

    };

    String idPaciente, nomPaciente, apePaciente, fechaNacimiento, edadPaciente,
            dirPaciente, ocupacionPaciente, telPaciente;

    ImageView btnCalendario;

    String anio = "YYYY";
    String mes = "MM";
    String dia = "DD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_info_personal);
        Intent dato = getIntent();
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

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);list.setAdapter(adapter);
        list.setBackgroundColor(Color.rgb(178, 223, 219));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent consultorio = new Intent(getApplicationContext(),
                                Consultorio.class);
                        startActivity(consultorio);
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

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paciente_info_personal, menu);
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

    public void Atras (View v){
        Intent atras = new Intent(getApplicationContext(),
                HistoriaClinicaPrincipal.class);
        startActivity(atras);
        finish();
    }

    public void fecha() {
        DatePickerDialog myDatePiccker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day2) {
                anio = Integer.toString(year1);
                mes = Integer.toString(month1 + 1);
                dia = Integer.toString(day2);
                txtFechaNacimiento.setText("" + anio + "/" + mes + "/" + dia);

            }
        },
                2015, 04, 13);
        myDatePiccker.show();
    }
}
