package br.com.alura.LiterAlura.model;

import java.time.LocalDate;

public class Autor {
    // Atributos
    private String nomeAutor;
    private LocalDate anoNascimento;
    private LocalDate anoFalecimento;

    // Construtores
    public Autor() {

    }

    // Getters e Setters
    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public LocalDate getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(LocalDate anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public LocalDate getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(LocalDate anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    @Override
    public String toString() {
        return  "Autor: '" + nomeAutor + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n";
    }
}
