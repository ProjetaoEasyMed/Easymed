package easymed.usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                atualizandoListaGlobal();
                Intent it = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(it);
                finish();
            }
        }, 3000);

    }

    private void atualizandoListaGlobal()
    {
        /*EXCLUIR*/
        Vector<ProdutoInfo> medicamentos = new Vector<ProdutoInfo>();

        //Povoamento de Exemplo
        medicamentos.add(new ProdutoInfo(1,"Viagra", "Azulzinho", 15.90, "caixa", 10));
        medicamentos.add(new ProdutoInfo(2,"Seringa", "BD", 3.50, "pacote", 20));
        medicamentos.add(new ProdutoInfo(3,"Insulina", "Apidra", 25.99, "refil", 1));
        medicamentos.add(new ProdutoInfo(4,"Curativo", "Band-Aid", 2.00, "caixa", 10));
        medicamentos.add(new ProdutoInfo(5,"Mussum Ipsum", "Mussum", 10000000.00, "ipsum", 10000));
        medicamentos.add(new ProdutoInfo(6,"Licor de Cacau", "Xavier", 5.00, "ml", 50));
        medicamentos.add(new ProdutoInfo(7,"Jujuba", "Arco-Íris", 1.00, "unidade", 10));
        medicamentos.add(new ProdutoInfo(8,"Pastilha", "Valda", 5.00, "unidade", 1));
        medicamentos.add(new ProdutoInfo(9,"Paracetamol", "Bayer", 2.50, "cartela", 10));
        medicamentos.add(new ProdutoInfo(10,"Suporte para Mesa de Vidro", "Casa e Cozinha", 400.00, "unidade", 4));
        medicamentos.add(new ProdutoInfo(11,"Martelo", "Osso", 45.00, "unidade", 1));
        medicamentos.add(new ProdutoInfo(12,"Sofá Cama 3 lugares", "Couch", 525.35, "unidade", 1));
        //Fim do Povoamento
        /*EXCLUIR*/

        SharedPreferences listaGlobal = getSharedPreferences(GlobalValues.listaGlobal, MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = listaGlobal.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicamentos);
        prefsEditor.putString(GlobalValues.produtos, json);
        prefsEditor.apply();
    }
}
