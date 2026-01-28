package br.com.alura.LiterAlura.service;

public interface ICoverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
