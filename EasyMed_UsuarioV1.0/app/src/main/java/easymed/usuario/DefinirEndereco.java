package easymed.usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Vector;
import java.util.jar.Manifest;

public class DefinirEndereco extends AppCompatActivity {

    /**
     @author: Wilson Limeira, o Safadão
     */

    private Button cadastrar;
    private Button gpsButton;

    private EditText rua;
    private EditText numero;
    private EditText complemento;
    private EditText bairro;
    private EditText cidade;
    private EditText estado;

    private Location location;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_endereco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        /* Let the carnage begin!!! (Rock and Roll Racing) */
        final EditText type_rua = (EditText) findViewById(R.id.type_rua);
        final EditText type_bairro = (EditText) findViewById(R.id.type_bairro);
        final EditText type_cidade = (EditText) findViewById(R.id.type_cidade);
        final EditText type_complemento = (EditText) findViewById(R.id.type_complemento);
        final EditText type_estado = (EditText) findViewById(R.id.type_estado);
        final EditText type_numero = (EditText) findViewById(R.id.type_numero);

        cadastrar = (Button) findViewById(R.id.cadastrar_endereco);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefinirEndereco.this, MainActivity.class);

                Bundle correios = new Bundle();
                correios.putString("rua", type_rua.getText().toString());
                correios.putString("bairro", type_bairro.getText().toString());
                correios.putString("cidade", type_cidade.getText().toString());
                correios.putString("complemento", type_complemento.getText().toString());
                correios.putString("estado", type_estado.getText().toString());
                correios.putString("numero", type_numero.getText().toString());

                intent.putExtra("endereco", correios);

                startActivity(intent);
            }
        });


        gpsButton = (Button) findViewById(R.id.GPSbutton);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsManager();
            }
        });
    }

    /* "I don't know who you are, but I will find you and I will kill you!" (Taken) */
    private void gpsManager()
    {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            System.out.println("llatitude: "+location.getLatitude());
            System.out.println("llongitude: "+location.getLongitude());

            
        }
        else
        {
            System.out.println("ai caramba!!!");
            //permissão negada!
        }
    }

}
