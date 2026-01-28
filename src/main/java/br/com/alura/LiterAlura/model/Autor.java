package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();
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

    public List<Livro> getLivros() {
        return livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLivros(List<Livro> livros) {
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
        return  "Autor: '" + nomeAutor + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n";
    }
}
