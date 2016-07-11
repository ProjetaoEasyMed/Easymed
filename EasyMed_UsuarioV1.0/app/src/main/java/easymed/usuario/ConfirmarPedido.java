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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class ConfirmarPedido extends AppCompatActivity {

    private Button back_button;
    private Button confirm_button;

    private ListView listaProdutosPagar;

    private Vector<ProdutoInfo> listaProd;

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

        imprimeListaNaTela(getProdutoListGlobal());

    }

    @Override
    public void onBackPressed(){
            ConfirmarPedido.super.onBackPressed();
            this.finish();
    }

    private void imprimeListaNaTela(Vector<ProdutoInfo> medicamentos)
    {
        Map<Integer,Integer> produtoQuantidadePorUsuario = getQuantidadePorUsuario();

        listaProd = getProdutoListGlobal();

        if(produtoQuantidadePorUsuario != null) {
            Iterator it = produtoQuantidadePorUsuario.entrySet().iterator();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                ProdutoInfo product = findProduct((int) pair.getKey());



                arrayAdapter.add(product.getNome());


            }
            listaProdutosPagar.setAdapter(arrayAdapter);
        }
    }

    private ProdutoInfo findProduct(int id)
    {
        ProdutoInfo element = null;

        for(int i = 0; i < this.listaProd.size(); i++)
        {
            if(listaProd.get(i).getId() == id)
                element = listaProd.get(i);
        }

        return element;
    }

    public Vector<ProdutoInfo> getProdutoListGlobal()
    {
        SharedPreferences listaGlobal = getSharedPreferences(GlobalValues.listaGlobal, MODE_PRIVATE);

        Gson gson = new Gson();
        String keyJson = listaGlobal.getString(GlobalValues.produtos, "");
        Type typeOfT = new TypeToken<Vector<ProdutoInfo>>(){}.getType();
        Vector<ProdutoInfo> medicamentos = gson.fromJson(keyJson, typeOfT);

        return medicamentos;
    }

    public HashMap<Integer, Integer> getQuantidadePorUsuario()
    {
        SharedPreferences listaLocal = getSharedPreferences(GlobalValues.listaLocal, MODE_PRIVATE);

        Gson gson = new Gson();
        String keyJson = listaLocal.getString(GlobalValues.quantidade, "");
        Type typeOfT = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
        HashMap<Integer,Integer> qtdUser = gson.fromJson(keyJson, typeOfT);

        return qtdUser;
    }

}
