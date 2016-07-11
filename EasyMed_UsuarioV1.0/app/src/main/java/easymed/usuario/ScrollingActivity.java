package easymed.usuario;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class ScrollingActivity extends AppCompatActivity {

    private TableLayout listMedicines;
    private EditText barraPesquisa;

    private Vector<ProdutoInfo> medicamentos;

    private HashMap<Integer, Integer> idVSqtd;

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


        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.confirm_list_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < listMedicines.getChildCount(); i++)
                {
                    TableRow row = (TableRow) listMedicines.getChildAt(i);

                    if(row.getChildAt(0) instanceof EditText)
                    {
                        int qtd;
                        try {
                            qtd = Integer.parseInt(((EditText) row.getChildAt(0)).getText().toString());
                        }
                        catch (Exception e)
                        {
                            qtd = 0;
                        }

                        if (qtd > 0) {
                            idVSqtd.put(row.getId(), qtd);
                        }
                    }
                }

                atualizandoNumeroPedidos();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* O MONSTRO SAIU DA JAULA!!! */
        idVSqtd = new HashMap<Integer, Integer>();
        listMedicines = (TableLayout) findViewById(R.id.listMedicines);
        barraPesquisa = (EditText) findViewById(R.id.barraPesquisa);

        medicamentos = getProdutoListGlobal();

        if(medicamentos != null)
            imprimeListaNaTela(medicamentos);
        else
        {
            Toast.makeText(ScrollingActivity.this, "A lista de medicamentos da farmácia está vazia. Tente mais tarde...", Toast.LENGTH_SHORT).show();
        }
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
            linha.setId(medicamentos.get(i).getId());

            EditText num = new EditText(this);
            num.setText("0");
            num.setInputType(InputType.TYPE_CLASS_NUMBER);
            num.setTextAppearance(this, android.R.style.TextAppearance_Large);

            TextView nome = new TextView(this);
            nome.setTextAppearance(this, android.R.style.TextAppearance_Large);
            TextView marca = new TextView(this);
            marca.setTextAppearance(this, android.R.style.TextAppearance_Large);
            TextView preco = new TextView(this);
            preco.setTextAppearance(this, android.R.style.TextAppearance_Large);

            nome.setText(medicamentos.get(i).getNome());
            marca.setText(medicamentos.get(i).getMarca());
            String p = "RS " + String.format("%.2f", medicamentos.get(i).getPreco());
            preco.setText(p);

            linha.addView(num);
            linha.addView(new Space(this));
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

    public Vector<ProdutoInfo> getProdutoListGlobal()
    {
        SharedPreferences listaGlobal = getSharedPreferences(GlobalValues.listaGlobal, MODE_PRIVATE);

        Gson gson = new Gson();
        String keyJson = listaGlobal.getString(GlobalValues.produtos, "");
        Type typeOfT = new TypeToken<Vector<ProdutoInfo>>(){}.getType();
        Vector<ProdutoInfo> medicamentos = gson.fromJson(keyJson, typeOfT);

        return medicamentos;
    }

    private void atualizandoNumeroPedidos()
    {
        SharedPreferences listaLocal = getSharedPreferences(GlobalValues.listaLocal, MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = listaLocal.edit();
        Gson gson = new Gson();
        String json = gson.toJson(idVSqtd);
        prefsEditor.putString(GlobalValues.quantidade, json);
        prefsEditor.apply();
    }
}
