package br.com.alura.LiterAlura.model;

import java.time.LocalDate;

public class Livro {
    // Atributos
    private String tituloLivro;
    private Idioma idiomaLivro;
    private Integer numeroDownloads;

    // Construtores
    public Livro() {

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
        return "------ LIVRO ------\n" +
                "Livro: '" + tituloLivro + "\n" +
//                "Autor: '" + nomeAutor + "\n" +
                "Idioma: " + idiomaLivro + "\n" +
                "Downloads: " + numeroDownloads + "\n" +
                "--------------------" ;
    }
}
