/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

import java.time.LocalDateTime;

/**
 *
 * @author Dinis Pimpão e Gonçalo Gouveia
 */
public class Compra {

    private LocalDateTime data;
    private Pessoa funcionario;
    private Pessoa cliente;
    private double preçoFinal;
    private Menu menu;

    public Compra(LocalDateTime data, Pessoa funcionario, Pessoa cliente, double preçoFinal, Menu menu) {
        this.data = data;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.preçoFinal = preçoFinal;
        this.menu = menu;
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

    public double getPreçoFinal() {
        return preçoFinal;
    }

    public void setPreçoFinal(double preçoFinal) {
        this.preçoFinal = preçoFinal;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
