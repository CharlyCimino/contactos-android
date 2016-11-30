package caemci.aplicacioncontactos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ActivityListar extends AppCompatActivity {

    private ListView listViewListadoDeContactos;
    ArrayList<Contacto> contactos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A8C1"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        listViewListadoDeContactos = (ListView) findViewById(R.id.lvContactos);

        BaseDeDatos gestorDeContacto = new BaseDeDatos(getApplicationContext());
        this.contactos = gestorDeContacto.obtenerContactos();

        if (this.contactos == null)
        {
            Toast.makeText(this,"No hay contactos para mostrar", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final ArrayAdapter<Contacto> adapter = new ArrayAdapter<Contacto>(this,android.R.layout.simple_list_item_1,android.R.id.text1,this.contactos);
            listViewListadoDeContactos.setAdapter(adapter);


            listViewListadoDeContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Contacto c = adapter.getItem(position);
                    lanzarVerContacto(null,c);
                }
            });
        }
    }

    public void lanzarVerContacto(View v, Contacto c) {
        Intent t= new Intent(this,ActivityVer.class);
        t.putExtra("id", c.getId());
        t.putExtra("nombre", c.getNombre());
        t.putExtra("apellido", c.getApellido());
        t.putExtra("mail", c.getMail());
        t.putExtra("telefono", c.getTelefono());
        startActivity(t);
        this.finish();
    }

    public void vaciarBD (View v)
    {
        BaseDeDatos bd = new BaseDeDatos(getApplicationContext());
        bd.deleteAll();
        Toast.makeText(this,"Se han borrado todos los contactos", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
