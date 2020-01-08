/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

import java.util.Scanner;

/**
 *
 * @author Dinis Pimpão e Gonçalo Gouveia
 */
public class RestaurantManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a clientes
    //Função para apresentar lista de clientes
    private static void listarClientes() {

    }

    //Função para criar novos clientes
    private static void criarCliente() {
        Scanner input = new Scanner(System.in);

        //Precisa de incrementar baseado no ultimo numero existente
        int codigo = 0;

        System.out.print("Indique o nome do cliente: ");
        String nome = input.nextLine();
        System.out.print("Indique o número de contribuinte do cliente: ");
        double nif = input.nextDouble();
        Pessoa cliente = new Pessoa(codigo, nome, nif, null, null, 0, false, TipoPessoa.Cliente);
    }

    //Função para editar informações de clientes
    private static void editarCliente() {

    }

    //Função para remover clientes do sistema
    private static void removerCliente() {

    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a funcionários
    //Função para apresentar lista de clientes
    private static void listarFuncionarios() {

    }

    //Função para criar novos funcionários
    private static void criarFuncionario() {
        Scanner input = new Scanner(System.in);

        //Precisa de incrementar baseado no ultimo numero existente
        int codigo = 0;

        System.out.print("Indique o nome do funcionário: ");
        String nome = input.nextLine();

        System.out.print("Indique o número de contribuinte do funcionário: ");
        double nif = Double.parseDouble(input.nextLine());

        System.out.print("Indique o username do funcionário: ");
        String username = input.nextLine();

        System.out.print("Indique a password do funcionário: ");
        String password = input.nextLine();

        System.out.print("Indique o salário bruto do funcionário: ");
        double salarioBruto = Double.parseDouble(input.nextLine());

        boolean isAdmin = false;
        System.out.print("O funcionário é administrador? [Sim/(default Não)]: ");
        String perguntaAdmin = input.nextLine();
        // Converte o texto introduzido para letra minuscula, para poder verificar
        // se contem uma parte do texto. Neste caso apenas procura pela letra "s"
        // que por sua vez se encontra na palavra "Sim" introduzida. Caso seja
        // introduzido outro valor, o programa por default assume como um "não".
        if (perguntaAdmin.toLowerCase().contains("s")) {
            isAdmin = true;
        }
        Pessoa funcionario = new Pessoa(codigo, nome, nif, username, password, salarioBruto, isAdmin, TipoPessoa.Funcionário);
    }

    //Função para editar informações de funcionários
    private static void editarFuncionario() {

    }

    //Função para remover funcionarios do sistema
    private static void removerFuncionario() {

    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a produtos
    //Função para apresentar lista de menus
    private static void listarProdutos() {

    }

    //Função para criar novo menu
    private static void criarProduto() {
        Scanner input = new Scanner(System.in);

        System.out.print("Indique o nome do produto: ");
        String nome = input.nextLine();

        System.out.print("Indique o tipo de produto [Refeição/Bebida/Sobremesa]: ");
        String tipo = input.nextLine();
        // Converte o texto introduzido todo para letra minuscula, para poder verificar
        // se contem uma parte do texto, de forma a facilitar a leitura do que é inserido
        if (tipo.toLowerCase().contains("ref")) {
            tipo = "Refeiçao";
        } else if (tipo.toLowerCase().contains("be")) {
            tipo = "Bebida";
        } else if (tipo.toLowerCase().contains("sob")) {
            tipo = "Sobremesa";
        }

        System.out.print("Indique a decrição do produto: ");
        String descriçao = input.nextLine();

        System.out.print("Indique o preço do produto: ");
        double preço = Double.parseDouble(input.nextLine());

        System.out.print("Indique o stock do produto: ");
        int stock = input.nextInt();

        Produto novoProduto = new Produto(nome, TipoProduto.valueOf(tipo), descriçao, preço, stock);
        System.out.println(novoProduto.getTipoProduto());
    }

    //Função para editar informações de menus
    private static void editarProduto() {

    }

    //Função para remover produtos do sistema
    private static void removerProduto() {

    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a menus
    //Função para apresentar lista de menus
    private static void listarMenus() {

    }

    //Função para criar novo menu
    private static void criarMenu() {

    }

    //Função para editar informações de menus
    private static void editarMenu() {

    }

    //Função para remover menus do sistema
    private static void removerMenu() {

    }

}
