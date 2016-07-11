package easymed.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PedidoRealizado extends AppCompatActivity {

    private Button definir_padrao;
    private Button home_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_realizado);

        home_screen = (Button) findViewById(R.id.home_screen);
        home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PedidoRealizado.this, MainActivity.class);
//                it.putExtra("fbJsonObj", jsonObj.toString());
//                it.putExtra("jsonObjDados",jsonObjDados.toString());
                startActivity(it);
                finish();
            }
        });


    }
}
