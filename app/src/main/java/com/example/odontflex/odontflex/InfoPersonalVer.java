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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class InfoPersonalVer extends AppCompatActivity {

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
            dirPaciente, ocupacionPaciente, telPaciente, idOdontologo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personal_ver);Intent dato = getIntent();
        idPaciente = dato.getStringExtra("idPaciente");
        nomPaciente = dato.getStringExtra("nomPaciente");
        apePaciente = dato.getStringExtra("apePaciente");
        fechaNacimiento = dato.getStringExtra("fechanacPaciente");
        dirPaciente = dato.getStringExtra("dirPaciente");
        edadPaciente = dato.getStringExtra("edadPaciente");
        telPaciente = dato.getStringExtra("telPaciente");
        ocupacionPaciente = dato.getStringExtra("ocuPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        //Toast.makeText(getApplicationContext(), nomPaciente, Toast.LENGTH_SHORT).show();
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


        txtIdPaciente.setText("" + idPaciente);
        txtFechaNacimiento.setText("" + fechaNacimiento);
        txtApePaciente.setText("" + apePaciente);
        txtNomPaciente.setText("" + nomPaciente);
        txtDirPaciente.setText("" + dirPaciente);
        txtOcupacionPaciente.setText("" + ocupacionPaciente);
        txtTelPaciente.setText("" + telPaciente);
        txtEdadPaciente.setText("" + edadPaciente);

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
                        consultorio.putExtra("idOdontologo", idOdontologo);
                        startActivity(consultorio);
                        finish();
                        break;
                    case 1:
                        Intent info = new Intent(getApplicationContext(),
                                InfoGeneral.class);
                        info.putExtra("idOdontologo", idOdontologo);
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
        getMenuInflater().inflate(R.menu.menu_info_personal_ver, menu);
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

    public void Volver (View v){
        Intent atras = new Intent(getApplicationContext(),
                HistoriaPacienteExisteMenuPrincipal.class);
        atras.putExtra("idPaciente", idPaciente);
        atras.putExtra("idOdontologo", idOdontologo);
        atras.putExtra("nomPaciente", nomPaciente + " " + apePaciente);
        startActivity(atras);
        finish();
    }
}
