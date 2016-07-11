package easymed.usuario;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class ListaPedidosCadastrados extends AppCompatActivity {

    /**
     *
     * @author Arnold Schwarzenegger
     */

    private Vector<ProdutoInfo> listaProd;

    private RequestQueue requestQueue;
    JSONObject jsonObj = new JSONObject();
    JSONObject response2 = new JSONObject();

    JSONObject jsonObj2 = new JSONObject();
    JSONArray jsonList = new JSONArray();

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_cadastrados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);


        try {
            jsonObj.put("id_usuario","16ad2cfc769321c8128a743ef668f209");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST,
                "https://projetao-easymed.herokuapp.com/listaPadrao",
                jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ListaPedidosCadastrados.this, "Pedindo lista para o servidor   /listaPadrao" + response.toString(), Toast.LENGTH_LONG).show();
                        response2 = response;
                        try {
                            data = response.get("data").toString();
                            jsonObj.put("itens",response.getJSONArray("itens"));
//                            data = jsonObj.getJSONArray()
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("responde.to string ]===============================   " + data);


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        /*
            Hasta La Vista, Baby!!! (Terminator)
         */

        TableLayout screenList = (TableLayout) findViewById(R.id.screenList);

        //será preenchido com a quantidade de medicamento (vindo de algum canto) pelo paciente logado
        //padrão: <id do produto, quantidade que o usuário compra>
        Map<Integer,Integer> produtoQuantidadePorUsuario = getQuantidadePorUsuario();

        //povoamento de exemplo:
        listaProd = new Vector<>();
        listaProd.add(new ProdutoInfo(1, "Viagra", "Azulzinho", 15.90, "caixa", 10));
        listaProd.add(new ProdutoInfo(2,"Seringas", "BD", 3.50, "pacote", 20));
        listaProd.add(new ProdutoInfo(3,"Insulina", "Apidra", 25.99, "refil", 1));
        listaProd.add(new ProdutoInfo(4,"Curativos", "Band-Aid", 2.00, "caixa", 10));
        listaProd.add(new ProdutoInfo(5,"Mussum Ipsum, cacilds vidis litro abertis. Posuere libero varius", "Mussum", 10000000.00, "ipsum", 10000));

        produtoQuantidadePorUsuario.put(1, 3);
        produtoQuantidadePorUsuario.put(3, 5);
        produtoQuantidadePorUsuario.put(4, 1);
        //produtoQuantidadePorUsuario.put(5, 1);
        //fim do povoamento de exemplo
        listaProd = getProdutoListGlobal();

        Iterator it = produtoQuantidadePorUsuario.entrySet().iterator();
        if(it.hasNext())
        {
            TableRow listaVaziaRow = (TableRow) findViewById(R.id.listaVaziaRow);
            screenList.removeView(listaVaziaRow);
        }

        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();

            TableRow linha = new TableRow(this);

            TextView qtd = new TextView(this);
            qtd.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            TextView nomeProduto = new TextView(this);
            nomeProduto.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            TextView preco = new TextView(this);
            preco.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            ProdutoInfo product = findProduct((int)pair.getKey());

            qtd.setText(produtoQuantidadePorUsuario.get(product.getId()).toString());

            nomeProduto.setText(product.getNome());

            String p = "RS " + String.format("%.2f", product.getPreco());
            preco.setText(p);

            linha.addView(qtd);
            linha.addView(nomeProduto);
            linha.addView(preco);

            screenList.addView(linha, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
