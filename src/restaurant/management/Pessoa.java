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
public class Pessoa {

    private int codigo;
    private String nome;
    private double contribuinte;
    private String username;
    private String password;
    private double salarioBruto;
    private boolean isAdmin;
    private TipoPessoa tipoPessoa;

    public Pessoa() {
    }

    //Contrutor para cliente
    public Pessoa(int codigo, String nome, double contribuinte, TipoPessoa tipoPessoa) {
        this.codigo = codigo;
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.tipoPessoa = tipoPessoa;

        if (this.tipoPessoa.equals(TipoPessoa.Cliente)) {
            this.username = "Não aplicavel";
            this.password = "Não aplicavel";
            this.salarioBruto = 0;
            this.isAdmin = false;
        }
    }

    //Contrutor para funcionarios
    public Pessoa(int codigo, String nome, double contribuinte, String username, String password, double salarioBruto, boolean isAdmin, TipoPessoa tipoPessoa) {
        this.codigo = codigo;
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.username = username;
        this.password = password;
        this.salarioBruto = salarioBruto;
        this.isAdmin = isAdmin;
        this.tipoPessoa = tipoPessoa;
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

    public double getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(double contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getAllInfo() {
        if (tipoPessoa.equals(TipoPessoa.Cliente)) {
            return "-------------------------------" + "\n"
                    + "Codigo: " + this.codigo + "\n"
                    + "Nome: " + this.nome + "\n"
                    + "Nif: " + String.format("%.0f", this.contribuinte) + "\n"
                    + "Tipo de pessoa: " + this.tipoPessoa;

        } else {
            return "-------------------------------" + "\n"
                    + "Codigo: " + this.codigo + "\n"
                    + "Nome: " + this.nome + "\n"
                    + "Nif: " + String.format("%.0f", this.contribuinte) + "\n"
                    + "Username: " + this.username + "\n"
                    + "Password: " + this.password + "\n"
                    + "Salário Bruto: " + String.format("%.2f", this.salarioBruto) + "\n"
                    + "É administrador: " + this.isAdmin + "\n"
                    + "Tipo de pessoa: " + this.tipoPessoa;
        }
    }
}
