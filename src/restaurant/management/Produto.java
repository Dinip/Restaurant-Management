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

    private String nome;
    private TipoProduto tipoProduto;
    private String description;
    private double price;
    private int stock;
    
    //Construtor vazio
    public Produto(){  
    };

    //Contrutor com atributos
    public Produto(String nome, TipoProduto tipoProduto, String description, double price, int stock) {
        this.nome = nome;
        this.tipoProduto = tipoProduto;
        this.description = description;
        this.price = price;
        this.stock = stock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
