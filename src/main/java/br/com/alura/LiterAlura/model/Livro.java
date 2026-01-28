package br.com.alura.LiterAlura.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Livro {
    // Atributos
    private String tituloLivro;
    private Idioma idiomaLivro;
    private Integer numeroDownloads;
    private List<Autor> autor;

    // Construtores
    public Livro() {

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
