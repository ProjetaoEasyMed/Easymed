package easymed.usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
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

    private EditText type_rua;
    private EditText type_bairro;
    private EditText type_cidade;
    private EditText type_complemento;
    private EditText type_estado;
    private EditText type_numero;

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
        type_rua = (EditText) findViewById(R.id.type_rua);
        type_bairro = (EditText) findViewById(R.id.type_bairro);
        type_cidade = (EditText) findViewById(R.id.type_cidade);
        type_complemento = (EditText) findViewById(R.id.type_complemento);
        type_estado = (EditText) findViewById(R.id.type_estado);
        type_numero = (EditText) findViewById(R.id.type_numero);

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
                finish();
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

            Address endereco = obtendoEndereco(location.getLatitude(), location.getLongitude());
            if(endereco != null)
            {
                type_rua.setText(endereco.getThoroughfare());
                type_numero.setText(endereco.getSubThoroughfare());
                //type_complemento.setText();
                type_bairro.setText(endereco.getSubLocality());
                type_cidade.setText(endereco.getLocality());
                type_estado.setText(endereco.getAdminArea());

            }
        }
        else
        {
            System.out.println("ai caramba!!!");
            //permissão negada!
        }
    }

    private Address obtendoEndereco(double latitude, double longitude)
    {
        Address address = null;
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address>listaEnderecos = null;

        try
        {
            listaEnderecos = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listaEnderecos != null)
            address = listaEnderecos.get(0);

        return address;
    }

}
