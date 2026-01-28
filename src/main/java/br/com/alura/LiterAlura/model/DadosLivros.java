package br.com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(@JsonAlias("title") String tituloLivro,
                          @JsonAlias("languages") String idiomaLivro,
                          @JsonAlias("download_count") Integer numeroDownloads) {
}
