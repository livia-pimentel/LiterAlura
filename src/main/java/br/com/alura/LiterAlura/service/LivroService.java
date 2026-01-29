package br.com.alura.LiterAlura.service;

import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.DadosLivros;
import br.com.alura.LiterAlura.model.Livro;
import br.com.alura.LiterAlura.repository.AutorRepository;
import br.com.alura.LiterAlura.repository.LivroRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    EntityManager entityManager;

    @Transactional // Salva em uma única transação
    public Livro salvarLivroComAutore(DadosLivros dados) {
        Livro livro = new Livro(dados);
        List<Autor> autoresAux = new ArrayList<>(livro.getAutor());
        livro.getAutor().clear();

        // Salva livro vazio para evitar conflitos de cascata
        Livro livroSalvo = livroRepository.save(livro);

        autoresAux.forEach(a -> {
            Optional<Autor> autorNoBanco = autorRepository.findByNomeAutorIgnoreCase(a.getNomeAutor());

            if (autorNoBanco.isPresent()) {
                Autor autorExistente = autorNoBanco.get();
                autorExistente.adicionarLivro(livroSalvo);
                autorRepository.save(autorExistente);
                livroSalvo.getAutor().add(autorExistente);
            } else {
                a.adicionarLivro(livroSalvo);
                Autor autorNovo = autorRepository.save(a);
                livroSalvo.getAutor().add(autorNovo);
            }
        });

        livroRepository.saveAndFlush(livroSalvo);
        entityManager.clear(); // Limpa o cache

        return livroRepository.findByIdComAutores(livroSalvo.getId())
                .orElse(livroSalvo);
    }

    public Optional<Livro> buscarLivroPeloTitulo(String titulo) {
        return livroRepository.findByTituloLivroIgnoreCase(titulo);
    }

    public List<Livro> listarTodosOsLivros() {
        entityManager.clear(); // Limpa o cache
        return livroRepository.findAllComAutores();
    }

    public List<Autor> listarTodosOsAutores() {
        entityManager.clear(); // Limpa o cache
        return autorRepository.findAllComLivros();
    }

    public List<Autor> listarAutoresVivosNoAno(Integer ano) {
        return autorRepository.buscarAutoresVivosNoAno(ano);
    }
}
