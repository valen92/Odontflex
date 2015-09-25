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
import android.widget.Toast;

public class HistoriaPacienteExisteMenuPrincipal extends AppCompatActivity {

    private SlidingPaneLayout mPanes;
    ListViewAdapter adapter;
    ListViewAdapterButton adaptador;
    String[] opciones = new  String[]{
            "",
            ""
    };
    String[] menu = new  String[]{
            "Informacion Personal",
            "Anamnesis",
            "Alergias - Hábitos",
            "Exámen Estomatológico",
            "Examen Periodontal - Dental",
            "Odontograma",
            "Nota de evolucion"
    };

    int[] imgOpciones={
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info

    };

    int[] img1={
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa

    };

    int[] img2={
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.adicionar

    };

    String idPaciente, nomPaciente, idOdontologo;

    TextView txtNomPaciente, txtIdPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_paciente_existe_menu_principal);
        Intent dato = getIntent();
        nomPaciente = dato.getStringExtra("nomPaciente");
        idPaciente = dato.getStringExtra("idPaciente");
        idOdontologo = dato.getStringExtra("idOdontologo");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        txtNomPaciente = (TextView)findViewById(R.id.txtNomPaciente);
        txtIdPaciente = (TextView)findViewById(R.id.txtIdPaciente);
        txtNomPaciente.setText("" + nomPaciente);
        txtIdPaciente.setText("" + idPaciente);

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mPanes = (SlidingPaneLayout)findViewById(R.id.slidingPane);
        ListView list = (ListView)findViewById(R.id.animalList);
        adapter = new ListViewAdapter(this,opciones,imgOpciones);
        final ListView listaFr = (ListView) findViewById(R.id.listView);
        adaptador = new ListViewAdapterButton(this,menu,img1, img2, idPaciente, idOdontologo, nomPaciente);
        listaFr.setAdapter(adaptador);
        listaFr.setItemsCanFocus(false);
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
        getMenuInflater().inflate(R.menu.menu_historia_paciente_existe_menu_principal, menu);
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

    public void Atras (View v){
        Intent atras = new Intent(getApplicationContext(),
                HistoriaClinicaPrincipal.class);
        atras.putExtra("idOdontologo", idOdontologo);
        startActivity(atras);
        finish();
    }
}
