package br.com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(@JsonAlias("title") String tituloLivro,
                          @JsonAlias("languages") List<String> idiomaLivro,
                          @JsonAlias("download_count") Integer numeroDownloads,
                          @JsonAlias("authors") List<DadosAutor> autor) {
}
