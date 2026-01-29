package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="autores")
public class Autor {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nomeAutor;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @ManyToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private Set<Livro> livros = new HashSet<>();

    // Construtores
    public Autor() {

    }

    public Autor(DadosAutor dadosAutor) {
        // Nome vindo da API
        String nomeAutorApi = dadosAutor.nomeAutor();

        // Verifica se tem vírgula
        if (nomeAutorApi != null && nomeAutorApi.contains(",")) {
            String[] partes = nomeAutorApi.split(",");
            this.nomeAutor = partes[1].trim() + " " + partes[0].trim();
        } else {
            this.nomeAutor = nomeAutorApi;
        }
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

    public Set<Livro> getLivros() {
        return livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    // Metodo
    public String getNomeFormatado() {
        if (this.nomeAutor != null && this.nomeAutor.contains(",")) {
            String[] partes = this.nomeAutor.split(",");
            return partes[1].trim() + " " + partes[0].trim();
        }
        return this.nomeAutor;
    }

    public void adicionarLivro(Livro livro) {
        // Adiciona livro na lista de livro do autor
        this.livros.add(livro);

        // Pega o livro adicionado e adiciona ao autor
        livro.getAutor().add(this);
    }

    @Override
    public String toString() {
        String titulos = livros.stream()
                .map(Livro::getTituloLivro)
                .collect(Collectors.joining(", "));

        return  "----- INFORMAÇÕES DO AUTOR -----\n" +
                "Autor: " + nomeAutor + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n" +
                "Livros: [" + titulos + "]\n" +
                "---------------------------------\n";

    }
}
