package easymed.usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class ConfirmarPedido extends AppCompatActivity {

    private Button back_button;
    private Button confirm_button;

    private ListView listaProdutosPagar;
    private SharedPreferences listaLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ConfirmarPedido.this, MainActivity.class);
//                it.putExtra("fbJsonObj", jsonObj.toString());
//                it.putExtra("jsonObjDados",jsonObjDados.toString());
                startActivity(it);
                finish();
            }
        });

        confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ConfirmarPedido.this, Aguarde.class);
//                it.putExtra("fbJsonObj", jsonObj.toString());
//                it.putExtra("jsonObjDados",jsonObjDados.toString());
                startActivity(it);
                finish();
            }
        });


        /* Ordináaaaaaaria TCHU PÁ */
        listaProdutosPagar = (ListView) findViewById(R.id.listaProdutosPagar);

        imprimeListaNaTela(getProdutoListLocal());

    }

    @Override
    public void onBackPressed(){
            ConfirmarPedido.super.onBackPressed();
            this.finish();
    }

    private void imprimeListaNaTela(Vector<ProdutoInfo> medicamentos)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        for(int i = 0; i < medicamentos.size(); i++)
        {
            arrayAdapter.add(medicamentos.get(i).getNome());
        }
        listaProdutosPagar.setAdapter(arrayAdapter);
    }

    public Vector<ProdutoInfo> getProdutoListLocal()
    {
        SharedPreferences listaLocal = getSharedPreferences(GlobalValues.listaLocal, MODE_PRIVATE);

        Gson gson = new Gson();
        String keyJson = listaLocal.getString(GlobalValues.produtos, "");
        Type typeOfT = new TypeToken<Vector<ProdutoInfo>>(){}.getType();
        Vector<ProdutoInfo> medicamentos = gson.fromJson(keyJson, typeOfT);

        return medicamentos;
    }

}
