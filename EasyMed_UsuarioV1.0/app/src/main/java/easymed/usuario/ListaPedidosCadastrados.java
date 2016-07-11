package easymed.usuario;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_cadastrados);
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


        /*
            Hasta La Vista, Baby!!! (Terminator)
         */

        TableLayout screenList = (TableLayout) findViewById(R.id.screenList);

        //será preenchido com a quantidade de medicamento (vindo de algum canto) pelo paciente logado
        //padrão: <id do produto, quantidade que o usuário compra>
        Map<Integer,Integer> produtoQuantidadePorUsuario = getQuantidadePorUsuario();

        //povoamento de exemplo:
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
