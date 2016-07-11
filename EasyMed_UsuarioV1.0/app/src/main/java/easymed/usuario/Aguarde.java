package easymed.usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

public class Aguarde extends AppCompatActivity {

    ImageView imagemAguarde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aguarde);

        imagemAguarde = (ImageView) findViewById(R.id.imageView2);
        animandoBotao();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                atualizandoArquivosLocais();
                Intent it = new Intent(Aguarde.this,PedidoRealizado.class);
                startActivity(it);
                finish();
            }
        }, 3000);
    }

    /* Dança da manivelaaaaaaa */
    private void animandoBotao()
    {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sync_animation);
        imagemAguarde.startAnimation(animation);
    }

    private void atualizandoArquivosLocais()
    {
        /*EXCLUIR*/
        Vector<ProdutoInfo> medicamentos = new Vector<ProdutoInfo>();

        //Povoamento de Exemplo
        medicamentos.add(new ProdutoInfo(1,"Viagra", "Azulzinho", 15.90, "caixa", 10));
        medicamentos.add(new ProdutoInfo(2,"Seringas", "BD", 3.50, "pacote", 20));
        medicamentos.add(new ProdutoInfo(3,"Insulina", "Apidra", 25.99, "refil", 1));
        medicamentos.add(new ProdutoInfo(4,"Curativos", "Band-Aid", 2.00, "caixa", 10));
        medicamentos.add(new ProdutoInfo(5,"Mussum Ipsum", "Mussum", 10000000.00, "ipsum", 10000));
        medicamentos.add(new ProdutoInfo(6,"Licor de Cacau", "Xavier", 5.00, "ml", 50));
        //Fim do Povoamento
        /*EXCLUIR*/

        SharedPreferences listaLocal = getSharedPreferences(GlobalValues.listaLocal, MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = listaLocal.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicamentos);
        prefsEditor.putString(GlobalValues.produtos, json);
        prefsEditor.apply();
    }
}
