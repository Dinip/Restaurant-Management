/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Dinis Pimpão e Gonçalo Gouveia
 */
public class RestaurantManagement {

    /**
     * @param args the command line arguments
     */
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Pessoa> pessoaStorage = new ArrayList<Pessoa>();
    public static ArrayList<Produto> produtoStorage = new ArrayList<Produto>();
    public static ArrayList<Compra> compraStorage = new ArrayList<Compra>();

    public static void main(String[] args) {
        runAtStart();
        menuInicial();
    }

    //----------------------------------------------------------------------------------------------//
    //Stuff to run at start (temp vars)
    public static void runAtStart() {

        Pessoa admin = new Pessoa(1, "Administrador Geral", 123456789, "admin", "admin", 1000, true, TipoPessoa.Funcionário);
        pessoaStorage.add(admin);

        Pessoa test1 = new Pessoa(2, "test1", 123230, TipoPessoa.Cliente);
        Pessoa test2 = new Pessoa(5, "test2", 123121, TipoPessoa.Cliente);
        Pessoa test3 = new Pessoa(6, "test3", 0, "test3", "test3", 0, false, TipoPessoa.Funcionário);
        Pessoa test4 = new Pessoa(7, "test4", 0, "test4", "test4", 0, false, TipoPessoa.Funcionário);
        
        pessoaStorage.add(test1);
        pessoaStorage.add(test2);
        pessoaStorage.add(test3);
        pessoaStorage.add(test4);
    }

    //Menu inicial
    private static void menuInicial() {
        int opçao = 0;
        do {
            System.out.println("1.Login");
            System.out.println("0.Exit");
            System.out.print("Escolha a opção: ");
            opçao = Integer.parseInt(input.nextLine());
        } while ((opçao > 1) || (opçao < 0));

        switch (opçao) {
            case 1:
                auth();
            case 0:
                System.exit(0);
        }
    }

    //Menu de autenticaçao
    private static void auth() {
        boolean valido = false;
        do {
            System.out.print("Introduza o username: ");
            String user = input.nextLine();
            System.out.print("Introduza a password: ");
            String pwd = input.nextLine();

            for (int contador = 0; contador < pessoaStorage.size(); contador++) {
                if (pessoaStorage.get(contador).getUsername().contentEquals(user) && pessoaStorage.get(contador).getPassword().contentEquals(pwd)) {
                    valido = true;
                    if (pessoaStorage.get(contador).isIsAdmin()) {
                        menusAdmin();
                        break;
                    } else {
                        menusFuncionario();
                        break;
                    }
                }
            }
            if (!valido) {
                System.out.println("Utilizador Inválido! Tente novamente.");
            }
        } while (!valido);
    }

    //Menu exclusivo para funcionarios
    private static void menusFuncionario() {
        System.out.println("Menu de funcionarios");
    }

    //Menu exclusivo para administradores
    private static void menusAdmin() {
        System.out.println("Menus de admins");

        editarFuncionario();
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a clientes
    //Função para apresentar lista de clientes
    private static void listarClientes() {
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                System.out.println(cliente.getAllInfo());
            }
        }
        System.out.println("-------------------------------");
    }

    //Função para criar novos clientes
    private static void criarCliente() {
        //Precisa de incrementar baseado no ultimo numero existente
        //Codigo da ultima pessoa + 1
        int codigo = pessoaStorage.get(pessoaStorage.size() - 1).getCodigo() + 1;
        System.out.print("Indique o nome do cliente: ");
        String nome = input.nextLine();
        System.out.print("Indique o número de contribuinte do cliente: ");
        double nif = Double.parseDouble(input.nextLine());
        Pessoa cliente = new Pessoa(codigo, nome, nif, TipoPessoa.Cliente);
        pessoaStorage.add(cliente);
    }

    //Função para editar informações de clientes
    private static void editarCliente() {
        int contadorClientes = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos clientes existem no sistema
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                contadorClientes++;
            }
        }

        //Corre se existirem clientes no sistema
        if (contadorClientes > 0) {
            Integer[] idsClientes = new Integer[contadorClientes];
            for (Pessoa cliente : pessoaStorage) {
                if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                    System.out.println(cliente.getAllInfo());
                    idsClientes[contador] = cliente.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");
            do {
                System.out.print("Indique o id do cliente que quer editar: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            boolean editar = false;
            int index = 0;
            for (Pessoa cliente : pessoaStorage) {
                index = pessoaStorage.indexOf(cliente);
                if ((cliente.getCodigo() == valorLido) && (index >= 0) && (index <= pessoaStorage.size())) {
                    editar = true;
                    break;
                }
            }
            if (editar) {
                System.out.print("Indique o novo nome do cliente: ");
                String nome = input.nextLine();

                System.out.print("Indique o novo nif do cliente: ");
                double nif = Double.parseDouble(input.nextLine());

                Pessoa cliente = new Pessoa(valorLido, nome, nif, TipoPessoa.Cliente);

                pessoaStorage.set(index, cliente);
                System.out.println("Cliente com id " + valorLido + " editado.");
            }
            listarClientes();

        } else {
            System.out.println("Não existem clientes.");
        }
    }

    //Função para remover clientes do sistema
    private static void removerCliente() {
        int contadorClientes = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos clientes existem no sistema
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                contadorClientes++;
            }
        }

        //Corre se existirem clientes no sistema
        if (contadorClientes > 0) {
            Integer[] idsClientes = new Integer[contadorClientes];
            for (Pessoa cliente : pessoaStorage) {
                if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                    System.out.println(cliente.getAllInfo());
                    idsClientes[contador] = cliente.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");

            //Loop para pedir id de cliente
            do {
                System.out.print("Indique o id do cliente que quer remover: ");
                valorLido = input.nextInt();
                if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            boolean remover = false;
            int index = 0;
            for (Pessoa cliente : pessoaStorage) {
                index = pessoaStorage.indexOf(cliente);
                if ((cliente.getCodigo() == valorLido) && (index >= 0) && (index <= pessoaStorage.size())) {
                    remover = true;
                    break;
                }
            }
            if (remover) {
                pessoaStorage.remove(index);
                System.out.println("Cliente com id " + valorLido + " removido.");
            }
            listarClientes();

        } else {
            System.out.println("Não existem clientes para remover.");
        }
    }

//----------------------------------------------------------------------------------------------//
//Codigo referente a funcionários
//Função para apresentar lista de clientes
    private static void listarFuncionarios() {
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                System.out.println(funcionario.getAllInfo());
            }
        }
        System.out.println("-------------------------------");
    }

    //Função para criar novos funcionários
    private static void criarFuncionario() {
        //Precisa de incrementar baseado no ultimo numero existente
        //Codigo da ultima pessoa + 1
        int codigo = pessoaStorage.get(pessoaStorage.size() - 1).getCodigo() + 1;

        System.out.print("Indique o nome do funcionário: ");
        String nome = input.nextLine();

        System.out.print("Indique o número de contribuinte do funcionário: ");
        double nif = Double.parseDouble(input.nextLine());

        //Verificar se o username ainda nao existe no sistema
        String username = "";
        boolean userExiste = false;
        do {
            System.out.print("Indique o username do funcionário: ");
            username = input.nextLine();
            //Verificaçao
            for (int contador = 0; contador < pessoaStorage.size(); contador++) {
                if (pessoaStorage.get(contador).getUsername().contentEquals(username)) {
                    System.out.println(pessoaStorage.get(contador).getUsername().contentEquals(username));
                    userExiste = true;
                    break;
                } else {
                    userExiste = false;
                }
            }
            if (userExiste) {
                System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
            }
        } while (userExiste);

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
        if (perguntaAdmin.toLowerCase().startsWith("s") || perguntaAdmin.toLowerCase().startsWith("y")) {
            isAdmin = true;
        }
        Pessoa funcionario = new Pessoa(codigo, nome, nif, username, password, salarioBruto, isAdmin, TipoPessoa.Funcionário);
        pessoaStorage.add(funcionario);
    }

    //Função para editar informações de funcionários
    private static void editarFuncionario() {
        int contadorFuncionarios = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                contadorFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (contadorFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[contadorFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contador] = funcionario.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.print("Indique o id do funcionário que quer editar: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 1) {
                    System.out.println("Não é possivel remover administrador geral.");
                }
                if (!Arrays.asList(idsFuncionario).contains(valorLido)) {
                    System.out.println("Funcionário com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while ((!Arrays.asList(idsFuncionario).contains(valorLido)) || (valorLido == 1));

            boolean editar = false;
            int index = 0;
            for (Pessoa funcionario : pessoaStorage) {
                index = pessoaStorage.indexOf(funcionario);
                if ((funcionario.getCodigo() == valorLido) && (index >= 0) && (index <= pessoaStorage.size())) {
                    editar = true;
                    break;
                }
            }

            if (editar) {
                System.out.print("Indique o novo nome do funcionário: ");
                String nome = input.nextLine();

                System.out.print("Indique o novo nif do funcionário: ");
                double nif = Double.parseDouble(input.nextLine());

                //Verificar se o username ainda nao existe no sistema
                String username = "";
                boolean userExiste = false;
                do {
                    System.out.print("Indique o username do funcionário: ");
                    username = input.nextLine();
                    //Verificaçao
                    for (int contadorUser = 0; contadorUser < pessoaStorage.size(); contadorUser++) {
                        if ((pessoaStorage.get(contadorUser).getUsername().contentEquals(username)) && (!pessoaStorage.get(contadorUser).getUsername().contentEquals(pessoaStorage.get(index).getUsername()))) {
                            userExiste = true;
                            break;
                        } else {
                            userExiste = false;
                        }
                    }
                    if (userExiste) {
                        System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
                    }
                } while (userExiste);

                System.out.print("Indique a nova password do funcionário: ");
                String password = input.nextLine();

                System.out.print("Indique o novo salário do funcionário: ");
                double salario = Double.parseDouble(input.nextLine());

                boolean isAdmin = false;
                System.out.print("O funcionário é administrador? [Sim/(default Não)]: ");
                String perguntaAdmin = input.nextLine();
                // Converte o texto introduzido para letra minuscula, para poder verificar
                // se contem uma parte do texto. Neste caso apenas procura pela letra "s"
                // que por sua vez se encontra na palavra "Sim" introduzida. Caso seja
                // introduzido outro valor, o programa por default assume como um "não".
                if (perguntaAdmin.toLowerCase().startsWith("s") || perguntaAdmin.toLowerCase().startsWith("y")) {
                    isAdmin = true;
                }

                Pessoa cliente = new Pessoa(valorLido, nome, nif, username, password, salario, isAdmin, TipoPessoa.Funcionário);
                pessoaStorage.set(index, cliente);
                System.out.println("Funcionário com id " + valorLido + " editado.");
            }
            listarFuncionarios();

        } else {
            System.out.println("Não existem funcionários.");
        }
    }

//Função para remover funcionarios do sistema
    private static void removerFuncionario() {
        int contadorFuncionarios = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                contadorFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (contadorFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[contadorFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contador] = funcionario.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.print("Indique o id do funcionário que quer remover: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 1) {
                    System.out.println("Não é possivel remover administrador geral.");
                }
                if (!Arrays.asList(idsFuncionario).contains(valorLido)) {
                    System.out.println("Funcionário com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while ((!Arrays.asList(idsFuncionario).contains(valorLido)) || (valorLido == 1));

            boolean remover = false;
            int index = 0;
            for (Pessoa funcionario : pessoaStorage) {
                index = pessoaStorage.indexOf(funcionario);
                if ((funcionario.getCodigo() == valorLido) && (index >= 0) && (index <= pessoaStorage.size())) {
                    remover = true;
                    break;
                }
            }

            if (remover) {
                pessoaStorage.remove(index);
                System.out.println("Funcionário com id " + valorLido + " removido.");
            }
            listarFuncionarios();

        } else {
            System.out.println("Não existem funcionários.");
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a produtos
    //Função para apresentar lista de menus
    private static void listarProdutos() {

    }

    //Função para criar novo menu
    private static void criarProduto() {

        System.out.print("Indique o nome do produto: ");
        String nome = input.nextLine();

        System.out.print("Indique o tipo de produto [Refeição/Bebida/Sobremesa]: ");
        String tipoLido = input.nextLine();

        TipoProduto tipoProduto = TipoProduto.Bebida;
        // Converte o texto introduzido todo para letra minuscula, para poder verificar
        // se contem uma parte do texto, de forma a facilitar a leitura do que é inserido
        if (tipoLido.toLowerCase().startsWith("ref")) {
            tipoProduto = TipoProduto.Refeiçao;
        } else if (tipoLido.toLowerCase().startsWith("beb")) {
            tipoProduto = TipoProduto.Bebida;
        } else if (tipoLido.toLowerCase().startsWith("sob")) {
            tipoProduto = TipoProduto.Sobremesa;
        }

        System.out.print("Indique a decrição do produto: ");
        String descriçao = input.nextLine();

        System.out.print("Indique o preço do produto: ");
        double preço = Double.parseDouble(input.nextLine());

        System.out.print("Indique o stock do produto: ");
        int stock = input.nextInt();

        Produto novoProduto = new Produto(nome, tipoProduto, descriçao, preço, stock);
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
