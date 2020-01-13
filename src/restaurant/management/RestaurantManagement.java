/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
    public static boolean isAdminGlobal = false;
    public static String nomeLogged = "";
    private static final File pessoaFile = new File("C:\\Users\\Dinip\\Desktop\\pessoas.txt");
    private static final File produtoFile = new File("C:\\Users\\Dinip\\Desktop\\produtos.txt");
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        //precisa verificação quando ler para ver se ficheiro existe
        //caso nao exista deve criar
        readPessoa();
        if (produtoFile.length() < 1) {
            System.out.println(produtoFile + " Ficheiro vazio");
        } else {
            readProduto();
        }
        menuPreLogin();

        //pessoaStorage.add(new Pessoa(1, "Administrador Geral", "123456789", "admin", "admin", 1000, true, TipoPessoa.Funcionário));

    }

    //----------------------------------------------------------------------------------------------//

    //Writes the updates to files and loads again to arraylists
    private static void reloadFiles() {
        writePessoa(gson.toJson(pessoaStorage));
        readPessoa();
        writeProduto(gson.toJson(produtoStorage));
        readProduto();
    }

    //Menu inicial
    private static void menuPreLogin() {
        int opçao = 0;
        do {
            System.out.println("1.Login");
            System.out.println("0.Exit");
            System.out.print("Escolha a opção: ");
            opçao = Integer.parseInt(input.nextLine());
        } while ((opçao > 1) || (opçao < 0));

        switch (opçao) {
            case 1:
                menuAuth();
            case 0:
                System.exit(0);
        }
    }

    //Menu de autenticaçao
    private static void menuAuth() {
        boolean valido = false;
        do {
            System.out.print("Introduza o username: ");
            String user = input.nextLine();
            System.out.print("Introduza a password: ");
            String pwd = input.nextLine();

            for (int contador = 0; contador < pessoaStorage.size(); contador++) {
                if (pessoaStorage.get(contador).getUsername().contentEquals(user) && pessoaStorage.get(contador).getPassword().contentEquals(pwd)) {
                    nomeLogged = pessoaStorage.get(contador).getUsername();
                    valido = true;
                    if (pessoaStorage.get(contador).isIsAdmin()) {
                        isAdminGlobal = true;
                        menusLoggedAdmin();
                        break;
                    } else {
                        isAdminGlobal = false;
                        menusLoggedFuncionario();
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
    private static void menusLoggedFuncionario() {
        System.out.println("--------------------------------------------------");
        System.out.println("Menu de funcionarios. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Logout");
        System.out.println("1. Gerir clientes");
        System.out.println("2. Gerir produtos");
        System.out.println("3. Gerir menus");
        System.out.println("4. Gerir compras");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 4);

        switch (escolha) {
            case 0:
                menuPreLogin();
                break;
            case 1:
                subMenuClientesAmbos();
                voltarMenuLogged();
                break;
            case 2:
                subMenuProdutosEmFuncionario();
                voltarMenuLogged();
                break;
            case 3:
                subMenuMenusEmFuncionario();
                voltarMenuLogged();
                break;
            case 4:
                subMenuComprasAmbos();
                voltarMenuLogged();
                break;
        }
    }

    //Menu exclusivo para administradores
    private static void menusLoggedAdmin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Menus de admins. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Logout");
        System.out.println("1. Gerir clientes");
        System.out.println("2. Gerir funcionarios");
        System.out.println("3. Gerir produtos");
        System.out.println("4. Gerir menus");
        System.out.println("5. Gerir compras");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 5);

        switch (escolha) {
            case 0:
                menuPreLogin();
                break;
            case 1:
                subMenuClientesAmbos();
                voltarMenuLogged();
                break;
            case 2:
                subMenuFuncionariosEmAdmin();
                voltarMenuLogged();
                break;
            case 3:
                subMenuProdutosEmAdmin();
                voltarMenuLogged();
                break;
            case 4:
                subMenuMenusEmAdmin();
                voltarMenuLogged();
                break;
            case 5:
                subMenuComprasAmbos();
                voltarMenuLogged();
                break;
        }
    }

    //SubMenu para gerir clientes (funciona tanto em admin como em funcionario)
    private static void subMenuClientesAmbos() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de clientes. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        System.out.println("2. Criar");
        System.out.println("3. Editar");
        System.out.println("4. Remover");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 4);

        switch (escolha) {
            case 0:
                voltarMenuLogged();
                break;
            case 1:
                listarClientes();
                break;
            case 2:
                criarCliente();
                break;
            case 3:
                editarCliente();
                break;
            case 4:
                removerCliente();
                break;
        }
    }

    //SubMenu para gerir funcionarios quando logado como admin
    private static void subMenuFuncionariosEmAdmin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de funcionários. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        System.out.println("2. Criar");
        System.out.println("3. Editar");
        System.out.println("4. Remover");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 4);

        switch (escolha) {
            case 0:
                menusLoggedAdmin();
                break;
            case 1:
                listarFuncionarios();
                break;
            case 2:
                criarFuncionario();
                break;
            case 3:
                editarFuncionario();
                break;
            case 4:
                removerFuncionario();
                break;
        }
    }

    //SubMenu para gerir produtos quando logado como admin
    private static void subMenuProdutosEmAdmin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de produtos. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        System.out.println("2. Criar");
        System.out.println("3. Editar");
        System.out.println("4. Remover");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 4);

        switch (escolha) {
            case 0:
                voltarMenuLogged();
                break;
            case 1:
                listarProdutos();
                break;
            case 2:
                criarProduto();
                break;
            case 3:
                editarProduto();
                break;
            case 4:
                removerProduto();
                break;
        }
    }

    //SubMenu para gerir produtos quando logado como funcionario
    private static void subMenuProdutosEmFuncionario() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de produtos. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 1);

        switch (escolha) {
            case 0:
                voltarMenuLogged();
                break;
            case 1:
                listarProdutos();
                break;
        }
    }

    //SubMenu para gerir produtos quando logado como admin
    private static void subMenuMenusEmAdmin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de menus. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        if (isAdminGlobal) {
            System.out.println("2. Criar");
            System.out.println("3. Editar");
            System.out.println("4. Remover");
        }
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while ((isAdminGlobal && (escolha < 0 || escolha > 4)) || escolha < 0 || escolha > 1);

        switch (escolha) {
            case 0:
                voltarMenuLogged();
                break;
            case 1:
                listarMenus();
                break;
            case 2:
                criarMenu();
                break;
            case 3:
                editarMenu();
                break;
            case 4:
                removerMenu();
                break;
        }
    }

    //SubMenu para gerir produtos quando logado como admin
    private static void subMenuMenusEmFuncionario() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de menus. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("0. Back");
        System.out.println("1. Listar");
        if (isAdminGlobal) {
            System.out.println("2. Criar");
            System.out.println("3. Editar");
            System.out.println("4. Remover");
        }
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while ((isAdminGlobal && (escolha < 0 || escolha > 4)) || escolha < 0 || escolha > 1);

        switch (escolha) {
            case 0:
                voltarMenuLogged();
                break;
            case 1:
                listarMenus();
                break;
            case 2:
                criarMenu();
                break;
            case 3:
                editarMenu();
                break;
            case 4:
                removerMenu();
                break;
        }
    }

    private static void subMenuComprasAmbos() {
        System.out.println("--------------------------------------------------");
        System.out.println("Gestão de compras. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");

    }

    private static void voltarMenuLogged() {
        if (isAdminGlobal) {
            menusLoggedAdmin();
        } else {
            menusLoggedFuncionario();
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a clientes
    //Apresentar lista de clientes
    private static void listarClientes() {
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                System.out.println(cliente.getAllInfo());
            }
        }
        System.out.println("-------------------------------");
    }

    //Criar novos clientes
    private static void criarCliente() {
        //Precisa de incrementar baseado no ultimo numero existente
        //Codigo da ultima pessoa + 1
        int codigo = pessoaStorage.get(pessoaStorage.size() - 1).getCodigo() + 1;
        System.out.print("Indique o nome do cliente: ");
        String nome = input.nextLine();
        System.out.print("Indique o número de contribuinte do cliente: ");
        String nif = input.nextLine();
        pessoaStorage.add(new Pessoa(codigo, nome, nif, TipoPessoa.Cliente));
        reloadFiles();
    }

    //Editar informações de clientes
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
                String nif = input.nextLine();

                pessoaStorage.set(index, new Pessoa(valorLido, nome, nif, TipoPessoa.Cliente));
                System.out.println("Cliente com id " + valorLido + " editado.");
                reloadFiles();
            }

        } else {
            System.out.println("Não existem clientes.");
        }
    }

    //Remover clientes do sistema
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
                reloadFiles();
            }
        } else {
            System.out.println("Não existem clientes para remover.");
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a funcionários
    //Apresentar lista de clientes
    private static void listarFuncionarios() {
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionário)) {
                System.out.println(funcionario.getAllInfo());
            }
        }
        System.out.println("-------------------------------");
    }

    //Criar novos funcionários
    private static void criarFuncionario() {
        //Precisa de incrementar baseado no ultimo numero existente
        //Codigo da ultima pessoa + 1
        int codigo = pessoaStorage.get(pessoaStorage.size() - 1).getCodigo() + 1;

        System.out.print("Indique o nome do funcionário: ");
        String nome = input.nextLine();

        System.out.print("Indique o número de contribuinte do funcionário: ");
        String nif = input.nextLine();

        //Verificar se o username ainda nao existe no sistema
        String username = "";
        boolean userExiste = false;
        do {
            System.out.print("Indique o username do funcionário: ");
            username = input.nextLine();
            //Verificaçao
            for (int contador = 0; contador < pessoaStorage.size(); contador++) {
                if (pessoaStorage.get(contador).getUsername().equalsIgnoreCase(username)) {
                    System.out.println(pessoaStorage.get(contador).getUsername().contentEquals(username));
                    userExiste = true;
                    break;
                } else {
                    userExiste = false;
                    System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
                }
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
        pessoaStorage.add(new Pessoa(codigo, nome, nif, username, password, salarioBruto, isAdmin, TipoPessoa.Funcionário));
        reloadFiles();
    }

    //Editar informações de funcionários
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
                String nif = input.nextLine();

                //Verificar se o username ainda nao existe no sistema
                String username = "";
                boolean userExiste = false;
                do {
                    System.out.print("Indique o username do funcionário: ");
                    username = input.nextLine();
                    //Verificaçao
                    for (int contadorUser = 0; contadorUser < pessoaStorage.size(); contadorUser++) {
                        if ((pessoaStorage.get(contadorUser).getUsername().equalsIgnoreCase(username)) && (!pessoaStorage.get(contadorUser).getUsername().equalsIgnoreCase(pessoaStorage.get(index).getUsername()))) {
                            userExiste = true;
                            break;
                        } else {
                            userExiste = false;
                            System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
                        }
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

                pessoaStorage.set(index, new Pessoa(valorLido, nome, nif, username, password, salario, isAdmin, TipoPessoa.Funcionário));
                System.out.println("Funcionário com id " + valorLido + " editado.");
            }
            reloadFiles();
        } else {
            System.out.println("Não existem funcionários.");
        }
    }

    //Remover funcionarios do sistema
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
            reloadFiles();
        } else {
            System.out.println("Não existem funcionários.");
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a produtos
    //Apresentar lista de produtos
    private static void listarProdutos() {
        if (produtoStorage.size() > 1) {
            for (Produto produto : produtoStorage) {
                System.out.println(produto.getAllInfo());
            }
            System.out.println("-------------------------------");
        } else {
            System.out.println("Não existem produtos!");
        }
    }

    //Criar novo produto
    private static void criarProduto() {
        int codigo = 1;

        if (produtoStorage.size() > 0) {
            codigo = produtoStorage.get(produtoStorage.size() - 1).getCodigo();
        }

        System.out.print("Indique o nome do produto: ");
        String nome = input.nextLine();

        TipoProduto tipo = null;
        int escolha = 0;

        do {
            System.out.println("1. Refeição");
            System.out.println("2. Bebida");
            System.out.println("3. Sobremesa");
            System.out.print("Indique o tipo de produto: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 1 || escolha > 3);

        switch (escolha) {
            case 1:
                tipo = TipoProduto.Refeiçao;
                break;
            case 2:
                tipo = TipoProduto.Bebida;
                break;
            case 3:
                tipo = TipoProduto.Sobremesa;
                break;
        }

        System.out.print("Indique a decrição do produto: ");
        String descriçao = input.nextLine();

        System.out.print("Indique o preço do produto: ");
        double preço = Double.parseDouble(input.nextLine());

        System.out.print("Indique o stock do produto: ");
        int stock = Integer.parseInt(input.nextLine());

        produtoStorage.add(new Produto(codigo, nome, tipo, descriçao, preço, stock));
        reloadFiles();
    }

    //Editar informações de produto
    private static void editarProduto() {

    }

    //Remover produtos do sistema
    private static void removerProduto() {

    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a menus
    //Apresentar lista de menus
    private static void listarMenus() {

    }

    //Criar novo menu
    private static void criarMenu() {

    }

    //Editar informações de menus
    private static void editarMenu() {

    }

    //Remover menus do sistema
    private static void removerMenu() {

    }

    // Save to file (Pessoa)
    private static void writePessoa(String myData) {
        if (!pessoaFile.exists()) {
            try {
                File directory = new File(pessoaFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                pessoaFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Excepton Occured: " + e.toString());
            }
        }

        try {
            // Convenience class for writing character files
            FileWriter crunchifyWriter;
            //False refere-se a "append". Neste caso quero dar overwrite ao que existe
            crunchifyWriter = new FileWriter(pessoaFile.getAbsoluteFile(), false);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
            bufferWriter.write(myData);
            bufferWriter.close();

            System.out.println("---------------------------------------------------------------------");
            System.out.println("Pessoas data saved at file location: " + pessoaFile + " Data: " + myData);
        } catch (IOException e) {
            System.out.println("Hmm.. Got an error while saving Company data to file " + e.toString());
        }
    }

    // Read from file (Pessoa)
    private static void readPessoa() {
        if (!pessoaFile.exists()) {
            System.out.println("File doesn't exist");
        }
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(pessoaFile), "UTF-8");
            JsonReader myReader = new JsonReader(isReader);

            Type arrayListPessoaType = new TypeToken<ArrayList<Pessoa>>() {
            }.getType();
            pessoaStorage = gson.fromJson(myReader, arrayListPessoaType);

        } catch (Exception e) {
            System.out.println("error load cache from file " + e.toString());
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Pessoas data loaded successfully from file " + pessoaFile);

    }

    // Save to file (Produto)
    private static void writeProduto(String myData) {
        if (!produtoFile.exists()) {
            try {
                File directory = new File(produtoFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                produtoFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Excepton Occured: " + e.toString());
            }
        }

        try {
            // Convenience class for writing character files
            FileWriter crunchifyWriter;
            //False refere-se a "append". Neste caso quero dar overwrite ao que existe
            crunchifyWriter = new FileWriter(produtoFile.getAbsoluteFile(), false);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
            bufferWriter.write(myData);
            bufferWriter.close();

            System.out.println("---------------------------------------------------------------------");
            System.out.println("Produtos data saved at file location: " + produtoFile + " Data: " + myData);
        } catch (IOException e) {
            System.out.println("Hmm.. Got an error while saving Company data to file " + e.toString());
        }
    }

    // Read from file (Produto)
    private static void readProduto() {
        if (!produtoFile.exists()) {
            System.out.println("File doesn't exist");
        }
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(produtoFile), "UTF-8");
            JsonReader myReader = new JsonReader(isReader);

            Type arrayListProdutoType = new TypeToken<ArrayList<Produto>>() {
            }.getType();
            produtoStorage = gson.fromJson(myReader, arrayListProdutoType);

        } catch (Exception e) {
            System.out.println("error load cache from file " + e.toString());
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Produtos data loaded successfully from file " + produtoFile);

    }
}
