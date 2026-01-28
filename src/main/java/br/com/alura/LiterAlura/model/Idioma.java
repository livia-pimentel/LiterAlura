package br.com.alura.LiterAlura.model;

public enum Idioma {
    ESPANHOL("es", "Espanhol"),
    FRANCES("fr", "Francês"),
    INGLES("en", "Inglês"),
    PORTUGUES("pt", "Português");

    private String codigoDoIdiomaApi;
    private String descricaoIdioma;

    // Construtor
    Idioma(String codigoDoIdiomaApi, String descricaoIdioma) {
        this.codigoDoIdiomaApi = codigoDoIdiomaApi;
        this.descricaoIdioma = descricaoIdioma;
    }

    // Metodos
    public static Idioma fromString(String text) {
        for(Idioma idioma : Idioma.values()) {
            if (idioma.codigoDoIdiomaApi.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma não encontrado: " + text);
    }

    public static Idioma fromDescricaoIdioma(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.descricaoIdioma.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma não encontrado: " + text);
    }

    public String getCodigoDoIdiomaApi() {
        return codigoDoIdiomaApi;
    }
}
