package easymed.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Aguarde extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aguarde);

        try {
            wait(3000);
            Intent it = new Intent(Aguarde.this,PedidoRealizado.class);
            startActivity(it);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
