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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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

    private RequestQueue requestQueue;
    JSONObject jsonObj = new JSONObject();
    JSONObject jsonObj2 = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_endereco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsManager();
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



                try {
                    jsonObj2.put("rua","caxanga");
                    jsonObj2.put("numero","200");
                    jsonObj2.put("complemento","apto. 2001, bloco c");
                    jsonObj2.put("referencia","em frente a igreja do Carmo");
                    jsonObj2.put("bairro","Madalena");
                    jsonObj2.put("cidade","Recife");

                    jsonObj.put("id_usuario","16ad2cfc769321c8128a743ef668f209");
                    jsonObj.putOpt("endereco",jsonObj2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST,
                        "https://projetao-easymed.herokuapp.com/atualizaEndereco",
                        jsonObj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(DefinirEndereco.this, "Enviando endereço para o servidor   /atualizaEndereco" + response.toString(), Toast.LENGTH_LONG).show();
                                System.out.println("responde.to string ]===============================   " + response.toString());

//                                try {
////                                    jsonObj.put("itens",response.getJSONArray("itens"));
////                            data = jsonObj.getJSONArray()
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest2);

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


//        gpsButton = (Button) findViewById(R.id.GPSbutton);
//        gpsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gpsManager();
//            }
//        });
    }

    public void onBackPressed(){
        Intent it = new Intent(DefinirEndereco.this, MainActivity.class);
        startActivity(it);
        finish();
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
