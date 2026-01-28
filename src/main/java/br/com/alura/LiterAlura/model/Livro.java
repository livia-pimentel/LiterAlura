package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="livros")
public class Livro {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tituloLivro;
    @Enumerated(EnumType.STRING)
    private Idioma idiomaLivro;
    private Integer numeroDownloads;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Autor> autor;

    // Construtores
    public Livro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }

    public Livro(DadosLivros dadosLivros) {
        this.tituloLivro = dadosLivros.tituloLivro();
        this.idiomaLivro = Idioma.fromString(dadosLivros.idiomaLivro().get(0));
        this.numeroDownloads = dadosLivros.numeroDownloads();

        // Transformando a lista de DadosAutor em uma lista de Autor
        this.autor = dadosLivros.autor().stream()
                .map(dados -> new Autor(dados))
                .collect(Collectors.toList());

    }

    // Getters e Setters
    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Idioma getIdiomaLivro() {
        return idiomaLivro;
    }

    public void setIdiomaLivro(Idioma idiomaLivro) {
        this.idiomaLivro = idiomaLivro;
    }


    @Override
    public String toString() {
        // 1 - Busca autor na lista
        String nomeDosAutor = autor.stream()
                .map(Autor::getNomeFormatado)
                .collect(Collectors.joining(", "));

        return "------ LIVRO ------\n" +
                "Livro: " + tituloLivro + "\n" +
                "Autor: " + (nomeDosAutor.isEmpty() ? "Desconhecido" : nomeDosAutor)+ "\n" +
                "Idioma: " + idiomaLivro.getCodigoDoIdiomaApi() + "\n" +
                "Downloads: " + numeroDownloads + "\n" +
                "--------------------" ;
    }
}
