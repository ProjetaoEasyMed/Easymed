package easymed.usuario;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

import easymed.usuario.produtos.ProdutoInfo;

/**
 * Created by Wilson on 10/07/2016.
 */
public class GlobalValues
{
    public static final String listaLocal = "ListaLocal";
    public static final String listaGlobal = "ListaGlobal";

    public static final String produtos = "Produtos";
    public static final String quantidade = "Quantidade";

}
