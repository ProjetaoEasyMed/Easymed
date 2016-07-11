package easymed.usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView serverSyncText;

    private TableLayout mainTable;

    private Vector<ProdutoInfo> listaProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(it);
//                finish();
            }
        });

        FloatingActionButton buy = (FloatingActionButton) findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ConfirmarPedido.class);
                startActivity(it);
//                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        /* Arriba CABRÓN!!! */
        serverSyncText = (TextView) findViewById(R.id.serverSyncText);
        animandoTexto();


        //DELAY: "Agora PARE, sacanagem, pegue no compasso..."

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainTable = (TableLayout) findViewById(R.id.mainTable);
                plotarTabela();
            }
        }, 3000);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent it = new Intent(MainActivity.this, ListaPedidosCadastrados.class);
            startActivity(it);
        } else if (id == R.id.nav_gallery) {
            finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent it = new Intent(MainActivity.this, DefinirEndereco.class);
            startActivity(it);
            finish();
        }


//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /* "Toma gostosa, lapada na rachada! Você pede e eu te dou, lapada na rachada!" (variável animada agora) */
    public void animandoTexto()
    {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sync_animation);

        if(serverSyncText.isEnabled())
        {
            serverSyncText.clearAnimation();
            serverSyncText.startAnimation(animation);
        }
        else
        {
            serverSyncText.clearAnimation();
        }
    }

    private void plotarTabela()
    {
        Map<Integer,Integer> produtoQuantidadePorUsuario = new HashMap<Integer, Integer>();

        listaProd = getProdutoListLocal();

        //povoamento de exemplo:
        produtoQuantidadePorUsuario.put(1, 3);
        produtoQuantidadePorUsuario.put(3, 5);
        produtoQuantidadePorUsuario.put(4, 1);
        //produtoQuantidadePorUsuario.put(5, 1);
        //fim do povoamento de exemplo

        Iterator it = produtoQuantidadePorUsuario.entrySet().iterator();
        if(it.hasNext())
        {
            serverSyncText.setEnabled(false);
            serverSyncText.clearAnimation();
            mainTable.removeView(serverSyncText);
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

            mainTable.addView(linha, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
