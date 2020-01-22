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
import java.time.LocalDateTime;
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
    private static final File pessoaFile = new File("ficheiros\\pessoa.txt");
    private static final File produtoFile = new File("ficheiros\\produtos.txt");
    private static final File comprasFile = new File("ficheiros\\compras.txt");
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        //Vefica se o diretorio e ficheiros existem
        verifyFiles();
        //Caso o ficheiro da pessoa esteja vazio, cria uma pessoa como administrador
        //geral, para poder ser feito o login inicial
        if (pessoaFile.length() < 1) {
            pessoaStorage.add(new Pessoa(1, "Administrador Geral", "123456789", "admin", "admin", 1000, true, TipoPessoa.Funcionario));
            writePessoa(gson.toJson(pessoaStorage));
            readPessoa();
            for (Pessoa test : pessoaStorage) {
                System.out.println(test.getTipoPessoa());
            }
        } else {
            readPessoa();
        }
        //Caso o ficheiro de produtos esteja vazio, nao o vai ler
        if (produtoFile.length() < 1) {
            System.out.println(produtoFile + " Ficheiro vazio");
        } else {
            readProduto();
        }
        //Caso o ficheiro de compras esteja vazio, nao o vai ler
        if (comprasFile.length() < 1) {
            System.out.println(comprasFile + " Ficheiro vazio");
        } else {
            readCompras();
        }
        //Apresenta o 1º menu do sistema, onde é pedido o login
        menuPreLogin();
    }

    //----------------------------------------------------------------------------------------------//
    //Guarda os 3 arraylists no ficheiro e lê importa o conteudo
    //dos ficheiros novamente para os arraylists
    private static void reloadFiles() {
        writePessoa(gson.toJson(pessoaStorage));
        readPessoa();
        writeProduto(gson.toJson(produtoStorage));
        readProduto();
        writeCompras(gson.toJson(compraStorage));
        readCompras();
    }

    //Menu inicial (pre-autenticação)
    private static void menuPreLogin() {
        int opçao = 0;
        do {
            System.out.println("                                         .--,      .--,");
            System.out.println("                                        (  (`.---.´  ) )");
            System.out.println("                                        `.__/o   o\\__.´");
            System.out.println("======================================     {=  ^  =}");
            System.out.println("||                                  ||      >  -  <");
            System.out.println("||    *Bem-vindo ao Restaurante*    ||     /       \\");
            System.out.println("||                                  ||    //       \\\\");
            System.out.println("||            1.Login               ||   //|   .   |\\\\");
            System.out.println("||            0.Exit                ||    `\\       /´|_.-~^`´-.");
            System.out.println("||                                  ||      \\  _  /--'         `");
            System.out.println("======================================    ___)( )(___");
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Menu de funcionario. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Logout                                            |");
        System.out.println("|   1. Gerir clientes                                    |");
        System.out.println("|   2. Gerir produtos                                    |");
        System.out.println("|   3. Gerir menus                                       |");
        System.out.println("|   4. Gerir compras                                     |");
        System.out.println("----------------------------------------------------------");
        int escolha = -1;
        do {
            System.out.print("Escolha uma opção: ");
            escolha = Integer.parseInt(input.nextLine());
        } while (escolha < 0 || escolha > 3);

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
                subMenuComprasAmbos();
                voltarMenuLogged();
                break;
        }
    }

    //Menu exclusivo para quando logado administradores
    private static void menusLoggedAdmin() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Menu de administrador Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Logout                                            |");
        System.out.println("|   1. Gerir clientes                                    |");
        System.out.println("|   2. Gerir funcionarios                                |");
        System.out.println("|   3. Gerir produtos                                    |");
        System.out.println("|   4. Gerir compras                                     |");
        System.out.println("----------------------------------------------------------");
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
                subMenuFuncionariosEmAdmin();
                voltarMenuLogged();
                break;
            case 3:
                subMenuProdutosEmAdmin();
                voltarMenuLogged();
                break;
            case 4:
                subMenuComprasAmbos();
                voltarMenuLogged();
                break;
        }
    }

    //SubMenu para gerir clientes (funciona tanto em admin como em funcionario)
    private static void subMenuClientesAmbos() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Gestão de clientes. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Back                                              |");
        System.out.println("|   1. Listar                                            |");
        System.out.println("|   2. Criar                                             |");
        System.out.println("|   3. Editar                                            |");
        System.out.println("|   4. Remover                                           |");
        System.out.println("----------------------------------------------------------");
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Gestão de funcionários. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Back                                              |");
        System.out.println("|   1. Listar                                            |");
        System.out.println("|   2. Criar                                             |");
        System.out.println("|   3. Editar                                            |");
        System.out.println("|   4. Remover                                           |");
        System.out.println("----------------------------------------------------------");
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Gestão de produtos. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Back                                              |");
        System.out.println("|   1. Listar                                            |");
        System.out.println("|   2. Criar                                             |");
        System.out.println("|   3. Editar                                            |");
        System.out.println("|   4. Remover                                           |");
        System.out.println("----------------------------------------------------------");
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("     Gestão de produtos. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("----------------------------------------------------------");
        System.out.println("|   0. Back                                              |");
        System.out.println("|   1. Listar                                            |");
        System.out.println("----------------------------------------------------------");
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

    //SubMenu para gerir compras (funciona tanto em admin como em funcionario)
    private static void subMenuComprasAmbos() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("--------------------------------------------------");
        System.out.println("     Gestão de compras. Logado como: " + nomeLogged.toUpperCase());
        System.out.println("--------------------------------------------------");
        System.out.println("|   0. Back                                      |");
        System.out.println("|   1. Apresentar compras clientes               |");
        System.out.println("|   2. Apresentar vendas funcionario             |");
        System.out.println("|   3. Apresentar vendas data                    |");
        System.out.println("|   4. Criar                                     |");
        System.out.println("--------------------------------------------------");
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
                apresentarComprasCliente();
                break;
            case 2:
                apresentarVendasFuncionario();
                break;
            case 3:
                apresentarVendasData();
                break;
            case 4:
                criarVenda();
                break;
        }

    }

    //Verifica se o utilizador com login é admin ou funcionario
    //e redireciona-o para o menu adquado
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
        boolean existe = false;
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente) && (!cliente.getTipoPessoa().equals(TipoPessoa.Funcionario))) {
                System.out.println(cliente.getAllInfo());
                existe = true;
            }
        }
        if (!existe) {
            System.out.println("Não existem clientes!");
        }
        System.out.println("-------------------------------");
    }

    //Criar novo cliente
    private static void criarCliente() {
        //Codigo da ultima pessoa + 1
        int codigo = pessoaStorage.get(pessoaStorage.size() - 1).getCodigo() + 1;
        System.out.print("Indique o nome do cliente: ");
        String nome = input.nextLine();
        System.out.print("Indique o número de contribuinte do cliente: ");
        String nif = input.nextLine();
        pessoaStorage.add(new Pessoa(codigo, nome, nif, TipoPessoa.Cliente));
        reloadFiles();
    }

    //Editar informações de cliente
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
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do cliente que quer editar: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            boolean editar = false;
            int index = 0;
            for (Pessoa cliente : pessoaStorage) {
                index = pessoaStorage.indexOf(cliente);
                if ((cliente.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
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

    //Remover cliente do sistema
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
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do cliente que quer remover: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            boolean remover = false;
            int index = 0;
            for (Pessoa cliente : pessoaStorage) {
                index = pessoaStorage.indexOf(cliente);
                if ((cliente.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
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
        boolean existe = false;
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario) && (!funcionario.getTipoPessoa().equals(TipoPessoa.Cliente))) {
                System.out.println(funcionario.getAllInfo());
                existe = true;
            }
        }
        if (!existe) {
            System.out.println("Não existem funcionários!");
        }
        System.out.println("-------------------------------");
    }

    //Criar novo funcionário
    private static void criarFuncionario() {
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
                    System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
                    userExiste = true;
                    break;
                } else {
                    userExiste = false;
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
        pessoaStorage.add(new Pessoa(codigo, nome, nif, username, password, salarioBruto, isAdmin, TipoPessoa.Funcionario));
        reloadFiles();
    }

    //Editar informações do funcionário
    private static void editarFuncionario() {
        int contadorFuncionarios = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                contadorFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (contadorFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[contadorFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contador] = funcionario.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do funcionário que quer editar: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
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
                if ((funcionario.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
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
                    //Verifica se o nome de utilizador ja existe no sistema. Caso já exista, nao aceita.
                    //Apenas aceita se o "novo" user for igual ao "antigo" do funcionario a ser editado.
                    for (int contadorUser = 0; contadorUser < pessoaStorage.size(); contadorUser++) {
                        if ((pessoaStorage.get(contadorUser).getUsername().equalsIgnoreCase(username)) && (!pessoaStorage.get(contadorUser).getUsername().equalsIgnoreCase(pessoaStorage.get(index).getUsername()))) {
                            System.out.println("Este utilizador já existe no sistema. Por favor escolhe outro.");
                            userExiste = true;
                            break;
                        } else {
                            userExiste = false;
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

                pessoaStorage.set(index, new Pessoa(valorLido, nome, nif, username, password, salario, isAdmin, TipoPessoa.Funcionario));
                System.out.println("Funcionário com id " + valorLido + " editado.");
                reloadFiles();
            }
        } else {
            System.out.println("Não existem funcionários.");
        }
    }

    //Remover funcionario do sistema
    private static void removerFuncionario() {
        int contadorFuncionarios = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                contadorFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (contadorFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[contadorFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contador] = funcionario.getCodigo();
                    contador++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do funcionário que quer remover: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
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
                if ((funcionario.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
                    remover = true;
                    break;
                }
            }

            if (remover) {
                pessoaStorage.remove(index);
                System.out.println("Funcionário com id " + valorLido + " removido.");
                reloadFiles();
            }
        } else {
            System.out.println("Não existem funcionários.");
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a produtos
    //Apresentar lista de produtos
    private static void listarProdutos() {
        if (produtoStorage.size() > 0) {
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

        //Verifica se existem produtos no sistema, para poder ser associado
        //um codigo ao novo artigo. Este codigo é unico e sequencial
        if (produtoStorage.size() > 0) {
            codigo = produtoStorage.get(produtoStorage.size() - 1).getCodigo() + 1;
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

        System.out.print("Indique o preço do produto: ");
        double preço = Double.parseDouble(input.nextLine());

        System.out.print("Indique o stock do produto: ");
        int stock = Integer.parseInt(input.nextLine());

        produtoStorage.add(new Produto(codigo, nome, tipo, preço, stock));
        reloadFiles();
    }

    //Editar informações de produto
    private static void editarProduto() {
        int contadorProdutos = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos produtos existem no sistema
        for (Produto produto : produtoStorage) {
            contadorProdutos++;
        }

        //Corre se existirem produtos no sistema
        if (contadorProdutos > 0) {
            Integer[] idsProdutos = new Integer[contadorProdutos];
            for (Produto produto : produtoStorage) {
                System.out.println(produto.getAllInfo());
                idsProdutos[contador] = produto.getCodigo();
                contador++;
            }
            System.out.println("-------------------------------");

            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do produto que quer editar: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsProdutos).contains(valorLido)) {
                    System.out.println("Produto com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsProdutos).contains(valorLido));

            boolean editar = false;
            int index = 0;
            for (Produto produto : produtoStorage) {
                index = produtoStorage.indexOf(produto);
                if ((produto.getCodigo() == valorLido) && (index >= 0) && (index < produtoStorage.size())) {
                    editar = true;
                    break;
                }
            }

            if (editar) {
                System.out.print("Indique o novo nome do produto: ");
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

                System.out.print("Indique o novo preço do produto: ");
                double preço = Double.parseDouble(input.nextLine());

                System.out.print("Indique o novo stock do produto: ");
                int stock = Integer.parseInt(input.nextLine());

                produtoStorage.set(index, new Produto(valorLido, nome, tipo, preço, stock));
                System.out.println("Produto com id " + valorLido + " editado.");
                reloadFiles();
            }
        } else {
            System.out.println("Não existem produtos.");
        }
    }

    //Remover produto do sistema
    private static void removerProduto() {
        int contadorProdutos = 0;
        int valorLido = 0;
        int contador = 0;

        //"Conta" quantos produtos existem no sistema
        for (Produto produto : produtoStorage) {
            contadorProdutos++;
        }

        //Corre se existirem produtos no sistema
        if (contadorProdutos > 0) {
            Integer[] idsProdutos = new Integer[contadorProdutos];
            for (Produto produto : produtoStorage) {
                System.out.println(produto.getAllInfo());
                idsProdutos[contador] = produto.getCodigo();
                contador++;
            }
            System.out.println("-------------------------------");

            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do produto que quer remover: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsProdutos).contains(valorLido)) {
                    System.out.println("Produto com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsProdutos).contains(valorLido));

            boolean remover = false;
            int index = 0;
            for (Produto produto : produtoStorage) {
                index = produtoStorage.indexOf(produto);
                if ((produto.getCodigo() == valorLido) && (index >= 0) && (index < produtoStorage.size())) {
                    remover = true;
                    break;
                }
            }

            if (remover) {
                produtoStorage.remove(index);
                System.out.println("Produto com id " + valorLido + " editado.");
                reloadFiles();
            }
        } else {
            System.out.println("Não existem produtos.");
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Codigo referente a compras
    //Apresentar compras de um cliente
    private static void apresentarComprasCliente() {
        int quantidadeClientes = 0;
        int valorLido = 0;
        int contadorClientes = 0;

        //"Conta" quantos clientes existem no sistema
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                quantidadeClientes++;
            }
        }

        //Corre se existirem clientes no sistema
        if (quantidadeClientes > 0) {
            Integer[] idsClientes = new Integer[quantidadeClientes];
            for (Pessoa cliente : pessoaStorage) {
                if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                    System.out.println(cliente.getAllInfo());
                    idsClientes[contadorClientes] = cliente.getCodigo();
                    contadorClientes++;
                }
            }
            System.out.println("-------------------------------");
            do {
                System.out.println("Caso queira voltar, prima 0");
                System.out.print("Indique o id do cliente que consumiu: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                } else if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            for (Compra vendas : compraStorage) {
                if (vendas.getCliente().getCodigo() == valorLido) {
                    System.out.println("-------------------------------");
                    System.out.println("Compras efetuadas por " + vendas.getCliente().getNome());
                    System.out.println(vendas.getAllInfo());
                    for (Produto produto : vendas.getProdutos()) {
                        System.out.println(produto.getInfoVenda());
                    }
                }
            }
        } else {
            System.out.println("Não existem clientes!");
        }

    }
    //Apresentar vendas de um funcionario

    private static void apresentarVendasFuncionario() {
        int quantidadeFuncionarios = 0;
        int contadorFuncionarios = 0;
        int valorLido = 0;
        //------------------------------------------------------------//
        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                quantidadeFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (quantidadeFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[quantidadeFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contadorFuncionarios] = funcionario.getCodigo();
                    contadorFuncionarios++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.print("Indique o id do funcionário que vendeu: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                } else if (!Arrays.asList(idsFuncionario).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsFuncionario).contains(valorLido));

            for (Compra vendas : compraStorage) {
                if (vendas.getFuncionario().getCodigo() == valorLido) {
                    System.out.println("-------------------------------");
                    System.out.println("Vendas efetuadas por " + vendas.getFuncionario().getNome());
                    System.out.println(vendas.getAllInfo());
                    for (Produto produto : vendas.getProdutos()) {
                        System.out.println(produto.getInfoVenda());
                    }
                }
            }
        } else {
            System.out.println("Não existem funcionários!");
        }
    }

    //Apresentar vendes de uma data
    private static void apresentarVendasData() {

        System.out.print("Indique o ano da compra: ");
        int ano = Integer.parseInt(input.nextLine());

        System.out.print("Indique o mes da compra: ");
        int mes = Integer.parseInt(input.nextLine());

        System.out.print("Indique o dia da compra: ");
        int dia = Integer.parseInt(input.nextLine());

        System.out.println("-------------------------------");
        System.out.println("Vendas efetuadas a " + dia + "/" + mes + "/" + ano);

        for (Compra vendas : compraStorage) {
            if ((vendas.getData().getYear() == ano) && (vendas.getData().getMonthValue() == mes) && (vendas.getData().getDayOfMonth()) == dia) {
                System.out.println("-------------------------------");
                System.out.println(vendas.getAllInfo());
                for (Produto produto : vendas.getProdutos()) {
                    System.out.println(produto.getInfoVenda());
                }
            } else {
                System.out.println("Não houve vendas na data introduzida: " + dia + "/" + mes + "/" + ano);
            }
        }
    }

    //Criar compras
    private static void criarVenda() {
        Pessoa clienteComprou = null;
        int quantidadeClientes = 0;
        int valorLido = 0;
        int contadorClientes = 0;
        //------------------------------------------------------------//
        //"Conta" quantos clientes existem no sistema
        for (Pessoa cliente : pessoaStorage) {
            if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                quantidadeClientes++;
            }
        }

        //Corre se existirem clientes no sistema
        if (quantidadeClientes > 0) {
            Integer[] idsClientes = new Integer[quantidadeClientes];
            for (Pessoa cliente : pessoaStorage) {
                if (cliente.getTipoPessoa().equals(TipoPessoa.Cliente)) {
                    System.out.println(cliente.getAllInfo());
                    idsClientes[contadorClientes] = cliente.getCodigo();
                    contadorClientes++;
                }
            }
            System.out.println("-------------------------------");
            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do cliente que consumiu: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsClientes).contains(valorLido)) {
                    System.out.println("Cliente com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsClientes).contains(valorLido));

            int index = 0;
            for (Pessoa cliente : pessoaStorage) {
                index = pessoaStorage.indexOf(cliente);
                if ((cliente.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
                    clienteComprou = cliente;
                    break;
                }
            }
        } else {
            System.out.println("Não existem clientes. Crie primeiro cliente para poder realizar vendas.");
            voltarMenuLogged();
        }

        Pessoa funcionarioVendeu = null;
        int quantidadeFuncionarios = 0;
        int contadorFuncionarios = 0;
        valorLido = 0;
        //------------------------------------------------------------//
        //"Conta" quantos funcionarios existem no sistema
        for (Pessoa funcionario : pessoaStorage) {
            if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                quantidadeFuncionarios++;
            }
        }

        //Corre se existirem funcionarios no sistema
        if (quantidadeFuncionarios > 0) {
            Integer[] idsFuncionario = new Integer[quantidadeFuncionarios];
            for (Pessoa funcionario : pessoaStorage) {
                if (funcionario.getTipoPessoa().equals(TipoPessoa.Funcionario)) {
                    System.out.println(funcionario.getAllInfo());
                    idsFuncionario[contadorFuncionarios] = funcionario.getCodigo();
                    contadorFuncionarios++;
                }
            }
            System.out.println("-------------------------------");

            do {
                System.out.println("Prima 0 se quiser voltar atrás");
                System.out.print("Indique o id do funcionário que vendeu: ");
                valorLido = Integer.parseInt(input.nextLine());
                if (valorLido == 0) {
                    voltarMenuLogged();
                    break;
                }
                if (!Arrays.asList(idsFuncionario).contains(valorLido)) {
                    System.out.println("Funcionário com o id " + valorLido + " não existe. Tente novamente.");
                }
            } while (!Arrays.asList(idsFuncionario).contains(valorLido));

            int index = 0;
            for (Pessoa funcionario : pessoaStorage) {
                index = pessoaStorage.indexOf(funcionario);
                if ((funcionario.getCodigo() == valorLido) && (index >= 0) && (index < pessoaStorage.size())) {
                    funcionarioVendeu = funcionario;
                    break;
                }
            }
        } else {
            System.out.println("Não existem funcionários. Crie primeiro funcionário para poder realizar vendas.");
            voltarMenuLogged();
        }

        int contadorProdutos = 0;
        double preçoTotal = 0;
        ArrayList<Produto> produtosCompra = new ArrayList<Produto>();

        //------------------------------------------------------------//
        if (!produtoStorage.isEmpty()) {
            System.out.println(produtoStorage.size());
            Integer[] idsProduto = new Integer[produtoStorage.size()];
            for (Produto produto : produtoStorage) {
                System.out.println(produto.getAllInfo());
                idsProduto[contadorProdutos] = produto.getCodigo();
                contadorProdutos++;
            }
            System.out.println("-------------------------------");
            do {
                do {
                    System.out.println("Prima 0 se quiser voltar atrás");
                    System.out.print("Indique o id do produto que foi consumido: ");
                    valorLido = Integer.parseInt(input.nextLine());
                    if (valorLido == 0) {
                        break;
                    } else if (!Arrays.asList(idsProduto).contains(valorLido)) {
                        System.out.println("Produto com o id " + valorLido + " não existe. Tente novamente.");
                    }
                } while (!Arrays.asList(idsProduto).contains(valorLido));

                if (valorLido != 0) {
                    int quantidadeComprada = 0;
                    do {
                        System.out.print("Indique a quantidade que foi consumida: ");
                        quantidadeComprada = Integer.parseInt(input.nextLine());
                    } while (quantidadeComprada < 1);

                    for (int contadorQuantidade = 0; contadorQuantidade < quantidadeComprada; contadorQuantidade++) {
                        for (Produto produto : produtoStorage) {
                            if ((produto.getCodigo() == valorLido)) {
                                preçoTotal += produto.getPreço();
                                produtosCompra.add(produto);
                                break;
                            }
                        }
                    }
                }
            } while (valorLido != 0);
            compraStorage.add(new Compra(LocalDateTime.now(), funcionarioVendeu, clienteComprou, preçoTotal, produtosCompra));
            System.out.println("Compra adicionada com sucesso!");
            reloadFiles();
        } else {
            System.out.println("Não existem produtos para serem vendidos. Crie produto primeiro.");
            voltarMenuLogged();
        }
    }

    // Save to file (Pessoa)
    private static void writePessoa(String myData) {
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
            System.out.println("Pessoas guardadas com sucesso: " + pessoaFile + " Data: " + myData);
        } catch (IOException e) {
            System.out.println("Hmm.. Erro ao guardar ficheiro da pessoas: " + e.toString());
        }
    }

    // Read from file (Pessoa)
    private static void readPessoa() {
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(pessoaFile), "UTF-8");
            JsonReader myReader = new JsonReader(isReader);

            Type arrayListPessoaType = new TypeToken<ArrayList<Pessoa>>() {
            }.getType();
            pessoaStorage = gson.fromJson(myReader, arrayListPessoaType);

        } catch (Exception e) {
            System.out.println("Erro a ler: " + e.toString());
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Pessoas carregadas com sucesso: " + pessoaFile);

    }

    // Save to file (Produto)
    private static void writeProduto(String myData) {
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
            System.out.println("Produtos guardados com sucesso: " + produtoFile + " Data: " + myData);
        } catch (IOException e) {
            System.out.println("Hmm.. Erro ao guardar ficheiro dos produtos: " + e.toString());
        }
    }

    // Read from file (Produto)
    private static void readProduto() {
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(produtoFile), "UTF-8");
            JsonReader myReader = new JsonReader(isReader);

            Type arrayListProdutoType = new TypeToken<ArrayList<Produto>>() {
            }.getType();
            produtoStorage = gson.fromJson(myReader, arrayListProdutoType);

        } catch (Exception e) {
            System.out.println("Erro a ler: " + e.toString());
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Produtos carregados com sucesso: " + produtoFile);

    }

    // Save to file (Compras)
    private static void writeCompras(String myData) {
        try {
            // Convenience class for writing character files
            FileWriter crunchifyWriter;
            //False refere-se a "append". Neste caso quero dar overwrite ao que existe
            crunchifyWriter = new FileWriter(comprasFile.getAbsoluteFile(), false);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
            bufferWriter.write(myData);
            bufferWriter.close();

            System.out.println("---------------------------------------------------------------------");
            System.out.println("Compras guardadas com sucesso: " + comprasFile + " Data: " + myData);
        } catch (IOException e) {
            System.out.println("Hmm.. Erro ao guardar ficheiro de compras: " + e.toString());
        }
    }

    // Read from file (Compras)
    private static void readCompras() {
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(comprasFile), "UTF-8");
            JsonReader myReader = new JsonReader(isReader);

            Type arrayListComprasType = new TypeToken<ArrayList<Compra>>() {
            }.getType();
            compraStorage = gson.fromJson(myReader, arrayListComprasType);

        } catch (Exception e) {
            System.out.println("Erro a ler: " + e.toString());
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Compras carregadas com sucesso: " + comprasFile);

    }

    private static void verifyFiles() {
        //Verifica se pessoa.txt existe. Se nao existir, cria o ficheiro.
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
        //Verifica se produtos.txt existe. Se nao existir, cria o ficheiro.
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
        //Verifica se compras.txt existe. Se nao existir, cria o ficheiro.
        if (!comprasFile.exists()) {
            try {
                File directory = new File(comprasFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                comprasFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Excepton Occured: " + e.toString());
            }
        }
    }
}
