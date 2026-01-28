package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.model.Livro;
import br.com.alura.LiterAlura.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    // Atributos
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "https://gutendex.com//books?search=";

    // Metodos
    public void exibeMenu() {
                var opcao = -1;
        while (opcao != 0) {

            System.out.println("Escolha o número de sua opção: ");

            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarIdiomaDoLivro();
                    break;
                case 0:
                    System.out.println("Finalizando a aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        DadosLivro dados = getDadosLivro();
        Livro livro = new Livro(dados);
        dadosLivros.add(dados);
//        System.out.println("------ LIVRO ------");
//        // TITULO DO LIVRO
//        // AUTOR
//        // IDIOMA
//        // NUMERO DE DOWNLOADS
//        System.out.println("-------------------");
        System.out.println(dados);
    }

    private void listarLivros() {
        // DEVE RETORNAR UMA LISTA DE TODOS OS LIVROS QUE FORAM ADICIONADOS AO BANCO

    }

    private void listarAutores() {
        // DEVE RETORNAR OS DADOS DE TODOS OS AUTORES DO BANCO
        // NOME DO AUTOR
        // ANO DE NASCIMENTO
        // ANOS DE FALECIMENTO
        // LIVROS DO AUTOR (UMA ARRAY)
    }

    private void listarAutoresVivos() {
        // DEVE RETORNAR OS AUTORES VIVOS NO ANO DESEJADO
        System.out.println("Insira o ano que deseja pesquisar: ");
        var ano = scanner.nextInt();
    }

    private void listarIdiomaDoLivro() {
        System.out.println("Insira o idioma para realizar a busca: ");

        var opcaoIdioma = """
                          es - espanhol
                          en - inglês
                          fr - francês
                          pt - português
                          """;

        System.out.println(opcaoIdioma);

        // DEPOIS DE ESCOLHER O IDIOMA DEVE RETORNAR A LISTA DE LIVROS NO IDIOMA DESEJADO
        // SE NÃO HOUVER LIVROS NO IDIOMA DESEJADO, RETORNAR UMA MENSAGEM QUE NÃO CONSTAM LIVROS DO IDIOMA NO BANCO
    }

}
