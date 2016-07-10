package easymed.usuario;

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
                    filtrarLista(palavraBuscada);
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

        medicamentos = new Vector<ProdutoInfo>();

        //Povoamento de Exemplo
        medicamentos.add(new ProdutoInfo(1,"Viagra", "Azulzinho", 15.90, "caixa", 10));
        medicamentos.add(new ProdutoInfo(2,"Seringas", "BD", 3.50, "pacote", 20));
        medicamentos.add(new ProdutoInfo(3,"Insulina", "Apidra", 25.99, "refil", 1));
        medicamentos.add(new ProdutoInfo(4,"Curativos", "Band-Aid", 2.00, "caixa", 10));
        medicamentos.add(new ProdutoInfo(5,"Mussum Ipsum", "Mussum", 10000000.00, "ipsum", 10000));
        medicamentos.add(new ProdutoInfo(6,"Licor de Cacau", "Xavier", 5.00, "ml", 50));
        //Fim do Povoamento

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

    private void filtrarLista(String word)
    {
        listMedicines.removeViews(1, medicamentos.size()); //FODAAAAAO: Remove as views, mas deixa o Menu


    }

}
