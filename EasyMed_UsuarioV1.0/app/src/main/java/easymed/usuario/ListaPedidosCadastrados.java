package easymed.usuario;

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
        Map<Integer,Integer> produtoQuantidadePorUsuario = new HashMap<Integer, Integer>();

        //povoamento de exemplo:
        listaProd = new Vector<>();
        listaProd.add(new ProdutoInfo(1,"Viagra", "Azulzinho", 15.90, "caixa", 10));
        listaProd.add(new ProdutoInfo(2,"Seringas", "BD", 3.50, "pacote", 20));
        listaProd.add(new ProdutoInfo(3,"Insulina", "Apidra", 25.99, "refil", 1));
        listaProd.add(new ProdutoInfo(4,"Curativos", "Band-Aid", 2.00, "caixa", 10));
        listaProd.add(new ProdutoInfo(5,"Mussum Ipsum, cacilds vidis litro abertis. Posuere libero varius", "Mussum", 10000000.00, "ipsum", 10000));

        produtoQuantidadePorUsuario.put(1, 3);
        produtoQuantidadePorUsuario.put(3, 5);
        produtoQuantidadePorUsuario.put(4, 1);
        //produtoQuantidadePorUsuario.put(5, 1);
        //fim do povoamento de exemplo

        Iterator it = produtoQuantidadePorUsuario.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();

            TableRow linha = new TableRow(this);

            TextView qtd = new TextView(this);
            TextView nomeProduto = new TextView(this);
            TextView preco = new TextView(this);

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

}
