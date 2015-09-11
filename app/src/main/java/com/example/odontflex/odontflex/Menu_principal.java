package com.example.odontflex.odontflex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Menu_principal extends AppCompatActivity {

    ListViewAdapter adapter;
    String[] tituloMenuPpl = new  String[]{
            "Historia Clínica",
            "Consultorio",
            "Información General",
    };

    int[]imagenesMenuPpl={
            R.drawable.glyphicons_029_notes_2,
            R.drawable.iconoconsultorio,
            R.drawable.glyphicons_195_circle_info,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        final ListView listaFr = (ListView) findViewById(R.id.listViewMenuPrincipal);
        adapter = new ListViewAdapter(this,tituloMenuPpl,imagenesMenuPpl);
        listaFr.setAdapter(adapter);
        listaFr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
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
