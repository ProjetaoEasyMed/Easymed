package easymed.usuario;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
}
