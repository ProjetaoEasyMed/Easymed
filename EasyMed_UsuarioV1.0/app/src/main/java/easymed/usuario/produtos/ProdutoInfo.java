package easymed.usuario.produtos;

import java.util.Currency;

/**
 * Created by Wilson on 08/07/2016.
 */
public class ProdutoInfo
{
    private int id;
    private String nome;
    private String marca;
    private Double preco;
    private String tipo; //ex: caixa, cartela, mililitros, etc
    private int qtdPorTipo; //ex: 10 comp por caixa

    //add mais coisa??


    public ProdutoInfo(int id, String nome, String marca, Double preco, String tipo, int qtdPorTipo)
    {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.tipo = tipo;
        this.qtdPorTipo = qtdPorTipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public Double getPreco() {
        return preco;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQtdPorTipo() {
        return qtdPorTipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQtdPorTipo(int qtdPorTipo) {
        this.qtdPorTipo = qtdPorTipo;
    }
}