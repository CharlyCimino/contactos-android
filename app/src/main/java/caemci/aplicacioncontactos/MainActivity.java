package caemci.aplicacioncontactos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A8C1"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
    }

    public void lanzarAgregarContacto(View v) {
        Intent t= new Intent(this,ActivityAgregar.class);
        startActivity(t);
    }

    public void lanzarListarContactos(View v) {
        Intent t= new Intent(this,ActivityListar.class);
        startActivity(t);
    }

}
