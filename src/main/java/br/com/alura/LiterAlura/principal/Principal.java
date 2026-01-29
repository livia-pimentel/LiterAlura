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

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {
    // Atributos
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private List<DadosLivros> dadosLivros = new ArrayList<>();
    private LivroRepository repositorio;
    private AutorRepository autorRepository;

    // Construtor
    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
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
        DadosLivros dados = getDadosLivros();
        if (dados != null) {
            Livro livro = new Livro(dados);

            // Garante que a tabela intermediária seja preenchida
            List<Autor> autoresAux = new ArrayList<>(livro.getAutor());

            // Lima lista original para evitar duplicação
            livro.getAutor().clear();

            // Salva livro vazio para gerar o id para evitar conflitos de cascata com autores existentes
            Livro livroSalvo = repositorio.save(livro);

            // Adiciona no banco
            autoresAux.forEach(a -> {

                        // Veririfca se o autor existe no banco
                        Optional<Autor> autorNoBanco = autorRepository.findByNomeAutorIgnoreCase(a.getNomeAutor());

                        if (autorNoBanco.isPresent()) {
                            // Se o autor já existe, vinculamos ao livro salvo
                            Autor autorExistente = autorNoBanco.get();
                            autorExistente.adicionarLivro(livroSalvo);
                            autorRepository.save(autorExistente); // Atualiza a tabela de ligação
                        } else {
                            // Se for novo salva o autor e vincula
                            a.adicionarLivro(livroSalvo);
                            autorRepository.save(a);
                        }
                    });
            System.out.println("\nLivro salvo com sucesso\n");
            System.out.println(livroSalvo);
        }
    }

    private DadosLivros getDadosLivros() {
        System.out.println("\nDigite o nome do livro para busca: ");
        var nomeLivro = scanner.nextLine();

        // Verifica se tem o livro no banco de dados
        Optional<Livro> livroNoBanco = repositorio.findByTituloLivroIgnoreCase(nomeLivro);

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

    private void listarLivros() {
        List<Livro> livrosNoBanco = repositorio.findAll();

        livrosNoBanco.stream()
                .sorted(Comparator.comparing(Livro::getTituloLivro))
                .forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autoresNoBanco = autorRepository.findAll();

        autoresNoBanco.stream()
                .sorted(Comparator.comparing(Autor::getNomeAutor))
                .forEach(System.out::println);

    }

    private void listarAutoresVivos() {
        // DEVE RETORNAR OS AUTORES VIVOS NO ANO DESEJADO
        System.out.println("Insira o ano que deseja pesquisar: ");
        var ano = scanner.nextInt();
    }

    private void listarIdiomaDoLivro() {
        System.out.println("Insira o idioma para realizar a busca: ");
        System.out.println( """
                          es - espanhol
                          en - inglês
                          fr - francês
                          pt - português
                          """);
        var opcaoIdioma = scanner.nextLine().trim();
        Idioma idioma = Idioma.fromDescricaoIdioma(opcaoIdioma);


        // DEPOIS DE ESCOLHER O IDIOMA DEVE RETORNAR A LISTA DE LIVROS NO IDIOMA DESEJADO
        // SE NÃO HOUVER LIVROS NO IDIOMA DESEJADO, RETORNAR UMA MENSAGEM QUE NÃO CONSTAM LIVROS DO IDIOMA NO BANCO
    }

}
