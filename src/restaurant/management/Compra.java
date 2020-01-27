/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Dinis Pimpão e Gonçalo Gouveia
 */
public class Compra {

    private LocalDateTime data;
    private Pessoa funcionario;
    private Pessoa cliente;
    private double precoFinal;
    private ArrayList<Produto> produtos;

    public Compra(LocalDateTime data, Pessoa funcionario, Pessoa cliente, double precoFinal, ArrayList<Produto> produtos) {
        this.data = data;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.precoFinal = precoFinal;
        this.produtos = produtos;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Pessoa getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Pessoa funcionario) {
        this.funcionario = funcionario;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getAllInfo() {
        return "-------------------------------" + "\n"
                + "Data: " + this.data + "\n"
                + "Vendido por: " + this.funcionario.getNome() + "\n"
                + "Cliente: " + this.cliente.getNome() + "\n"
                + "Nif: " + this.cliente.getContribuinte() + "\n"
                + "Preço Final: " + String.format("%.2f", this.precoFinal) + "€\n"
                + "Produtos:";                
    }
}
