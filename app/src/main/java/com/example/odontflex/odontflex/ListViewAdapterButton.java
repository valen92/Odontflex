package com.example.odontflex.odontflex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapterButton extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] titulos;
    int[] imagenes1, imagenes2;
    LayoutInflater inflater;
    String idPaciente;

    public ListViewAdapterButton(Context context, String[] titulos, int[] imagenes1, int[] imagenes2, String idPaciente) {
        this.context = context;
        this.titulos = titulos;
        this.imagenes1 = imagenes1;
        this.imagenes2 = imagenes2;
        this.idPaciente = idPaciente;
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

    public View getView(final int position, View convertView, ViewGroup parent) {

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
                Toast.makeText(context, "boton 1: "+ position + " " + idPaciente, Toast.LENGTH_LONG).show();
            }
        });

        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "boton 2: " + position + " " + idPaciente, Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }
}