package easymed.usuario;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class ScrollingActivity extends AppCompatActivity {

    private TableLayout listMedicines;
    private EditText barraPesquisa;

    private Vector<ProdutoInfo> medicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palavraBuscada = barraPesquisa.getText().toString();

                if(palavraBuscada.length() > 0)
                {
                    Vector<ProdutoInfo> newList = filtrarLista(palavraBuscada);
                    imprimeListaNaTela(newList);
                }
                else
                {
                    Snackbar.make(view, "Campo de busca vazio", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* O MONSTRO SAIU DA JAULA!!! */
        listMedicines = (TableLayout) findViewById(R.id.listMedicines);
        barraPesquisa = (EditText) findViewById(R.id.barraPesquisa);

        medicamentos = getProdutoListLocal();

        imprimeListaNaTela(medicamentos);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }

    private void imprimeListaNaTela(Vector<ProdutoInfo> medicamentos)
    {
        for(int i = 0; i < medicamentos.size(); i++)
        {
            TableRow linha = new TableRow(this);

            TextView nome = new TextView(this);
            nome.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            TextView marca = new TextView(this);
            marca.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            TextView preco = new TextView(this);
            preco.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            nome.setText(medicamentos.get(i).getNome());
            marca.setText(medicamentos.get(i).getMarca());
            String p = "RS " + String.format("%.2f", medicamentos.get(i).getPreco());
            preco.setText(p);

            linha.addView(nome);
            linha.addView(new Space(this));
            linha.addView(marca);
            linha.addView(new Space(this));
            linha.addView(preco);

            listMedicines.addView(linha, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private Vector<ProdutoInfo> filtrarLista(String word)
    {
        listMedicines.removeViews(1, medicamentos.size()); //FODAAAAAO: Remove as views, mas deixa o Menu

        Vector<ProdutoInfo> produtosEncontrados = new Vector<ProdutoInfo>();
        for (int i = 0; i < medicamentos.size(); i++)
        {
            //passa ambos os lados pra lower case pra buscar, assim acha tudo
            if(medicamentos.get(i).getNome().toLowerCase().contains(word.toLowerCase()) || medicamentos.get(i).getMarca().toLowerCase().contains(word.toLowerCase()))
            {
                produtosEncontrados.add(medicamentos.get(i));
            }
        }

        return produtosEncontrados;
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
