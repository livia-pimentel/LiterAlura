package br.com.alura.LiterAlura.repository;


import br.com.alura.LiterAlura.model.Idioma;
import br.com.alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloLivroIgnoreCase(String tituloLivro);

    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.autor")
    List<Livro> findAllComAutores();

    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.autor WHERE l.id = :id")
    Optional<Livro> findByIdComAutores(Long id);

    List<Livro> findByIdiomaLivro(Idioma idioma);
}
