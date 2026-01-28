package br.com.alura.LiterAlura.model;

public class Autor {
    // Atributos
    private String nomeAutor;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    // Construtores
    public Autor() {

    }

    public Autor(DadosAutor dadosAutor) {
        this.nomeAutor = dadosAutor.nomeAutor();
        this.anoNascimento = dadosAutor.anoNascimento();
        this. anoFalecimento = dadosAutor.anoFalecimento();
    }

    // Getters e Setters
    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    @Override
    public String toString() {
        return  "Autor: '" + nomeAutor + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n";
    }
}
