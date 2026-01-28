package br.com.alura.LiterAlura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    // Metodo
    public String obterDados(String endereco) {
//        HttpClient client = HttpClient.newHttpClient();
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();

//        System.out.println("Buscando URL: " + endereco);
//        System.out.println("Status Code: " + response.statusCode());
//        System.out.println("Corpo da Resposta: " + response.body());

        return json;
    }
}
