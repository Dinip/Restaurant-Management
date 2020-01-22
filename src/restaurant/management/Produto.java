/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

/**
 *
 * @author Dinis Pimpão e Gonçalo Gouveia
 */
public class Produto {

    private int codigo;
    private String nome;
    private TipoProduto tipoProduto;
    private double preço;
    private int stock;

    //Construtor vazio
    public Produto() {
    }

    //Contrutor com atributos
    public Produto(int codigo, String nome, TipoProduto tipoProduto, double preço, int stock) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipoProduto = tipoProduto;
        this.preço = preço;
        this.stock = stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public double getPreço() {
        return preço;
    }

    public void setPreço(double preço) {
        this.preço = preço;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getAllInfo() {
        return "-------------------------------" + "\n"
                + "Codigo: " + this.codigo + "\n"
                + "Nome: " + this.nome + "\n"
                + "Preço: " + String.format("%.2f", this.preço) + "€\n"
                + "Stock: " + this.stock + "\n"
                + "Tipo de produto: " + this.tipoProduto;
    }

    public String getInfoVenda() {
        return "-------------------------------" + "\n"
                + "Tipo de produto: " + this.tipoProduto + "\n"
                + "Nome: " + this.nome + "   Preço: " + String.format("%.2f", this.preço) + "€";
    }
}
