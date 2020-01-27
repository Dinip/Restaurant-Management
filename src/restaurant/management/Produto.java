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
    private double preco;

    //Contrutor com atributos
    public Produto(int codigo, String nome, TipoProduto tipoProduto, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipoProduto = tipoProduto;
        this.preco = preco;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getAllInfo() {
        return "-------------------------------" + "\n"
                + "Codigo: " + this.codigo + "\n"
                + "Nome: " + this.nome + "\n"
                + "Preço: " + String.format("%.2f", this.preco) + "€\n"
                + "Tipo de produto: " + this.tipoProduto;
    }

    public String getInfoVenda() {
        return "-------------------------------" + "\n"
                + "Tipo de produto: " + this.tipoProduto + "\n"
                + "Nome: " + this.nome + "   Preço: " + String.format("%.2f", this.preco) + "€";
    }
}
