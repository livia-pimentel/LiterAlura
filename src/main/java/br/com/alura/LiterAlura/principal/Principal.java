package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.DadosLivros;
import br.com.alura.LiterAlura.model.Idioma;
import br.com.alura.LiterAlura.model.Livro;
import br.com.alura.LiterAlura.repository.AutorRepository;
import br.com.alura.LiterAlura.repository.LivroRepository;
import br.com.alura.LiterAlura.service.ConsumoApi;
import br.com.alura.LiterAlura.service.ConverteDados;
import br.com.alura.LiterAlura.service.DadosResposta;
import br.com.alura.LiterAlura.service.LivroService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Principal {
    // Atributos
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private final LivroService livroService;


    @Autowired
    public Principal(LivroService livroService) {
        this.livroService = livroService;
    }

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
//                case 2:
//                    listarLivros();
//                    break;
//                case 3:
//                    listarAutores();
//                    break;
//                case 4:
//                    listarAutoresVivos();
//                    break;
//                case 5:
//                    listarIdiomaDoLivro();
//                    break;
                case 0:
                    System.out.println("Finalizando a aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        DadosLivros dados = getDadosLivros();

        if (dados != null) {
            Livro livroSalvo = livroService.salvarLivroComAutore(dados);

            System.out.println(livroSalvo);
        }
    }

    private DadosLivros getDadosLivros() {
        System.out.println("\nDigite o nome do livro para busca: ");
        var nomeLivro = scanner.nextLine();

        // Delega a verificação para o Service
        Optional<Livro> livroNoBanco = livroService.buscarLivroPeloTitulo(nomeLivro);

        if (livroNoBanco.isPresent()) {
            System.out.println("\nLivro encontrado no banco de dados: ");
            System.out.println(livroNoBanco.get());
            return null; // Indica para o buscarLivro que não precisa fazer a requisição
        }

        // Se não tiver no banco local, faz a requisição
        var nomeCodificado = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);
        var json = consumoApi.obterDados(ENDERECO + nomeCodificado);

        // Testa o json recebido
//        System.out.println("JSON recebido: " + json);
        if (json == null || json.isBlank() || json.contains("\"results\":[]")) {
            System.out.println("Nenhum livro encontrado para: " + nomeLivro);
            System.out.println("Digite o nome completo do livro.\n");
            return null; // Retorna null em vez de estourar erro no terminal
        }

        // Converte para a lista "results" - Desserialização
        DadosResposta dados = conversor.obterDados(json, DadosResposta.class);

        // Se houver resultados pega o primeiro da lista
        return dados.resultadosLivros().stream()
                .findFirst()
                .orElse(null);
    }

//    private void listarLivros() {
//        entityManager.clear();
//        List<Livro> livrosNoBanco = repositorio.findAllComAutores();
//
//        if (livrosNoBanco.isEmpty()) {
//            System.out.println("Nenhum livro registrado no banco de dados");
//        } else {
//            // Ordena e exibe usando o toString
//            livrosNoBanco.stream()
//                    .sorted(Comparator.comparing(Livro::getTituloLivro))
//                    .forEach(System.out::println);
//        }
//    }
//
//    private void listarAutores() {
//        List<Autor> autoresNoBanco = autorRepository.findAll();
//
//        autoresNoBanco.stream()
//                .sorted(Comparator.comparing(Autor::getNomeAutor))
//                .forEach(System.out::println);
//
//    }
//
//    private void listarAutoresVivos() {
//        System.out.println("Insira o ano que deseja pesquisar: ");
//        var ano = scanner.nextInt();
//        scanner.nextLine();
//
//        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosNoAno(ano);
//
//        if (autoresVivos.isEmpty()) {
//            System.out.println("\nNenhum autor vivo encontrado para o ano " + ano);
//        } else {
//            System.out.println("\nAutores vivos em " + ano + " : ");
//            autoresVivos.forEach(System.out::println);
//        }
//    }
//
//    private void listarIdiomaDoLivro() {
//        System.out.println("Insira o idioma para realizar a busca: ");
//        System.out.println( """
//                          es - espanhol
//                          en - inglês
//                          fr - francês
//                          pt - português
//                          """);
//        var opcaoIdioma = scanner.nextLine().trim().toLowerCase();
//
//        try {
//            Idioma idioma = Idioma.fromString(opcaoIdioma);
//            List<Livro> livrosPorIdioma = repositorio.findByIdiomaLivro(idioma);
//
//            if (livrosPorIdioma.isEmpty()) {
//                System.out.println("\nNão existem livros registrados no idioma: " + opcaoIdioma);
//            } else {
//                livrosPorIdioma.forEach(System.out::println);
//            }
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("\nOpção de idioma inválida! Escolha entre es, en, fr ou pt\n");
//        }
//    }
}
