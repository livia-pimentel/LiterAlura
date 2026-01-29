# LiterAlura - Catálogo de Livros 📚

O **LiterAlura** é um desafio proposto pela Alura referente ao ONE - Oracle Next Education, em que consiste em desenvolver um catálogo de livros que consome dados da API **Gutendex**. O projeto permite realizar buscas, persistir dados em um banco relacional e realizar filtragens.



## 🛠️ Funcionalidades

A aplicação oferece um menu interativo via console com as seguintes capacidades:

1.  **Buscar livro pelo título**: Consulta a API externa, realiza o tratamento de dados e salva o livro e seus autores no banco de dados.
2.  **Listar livros registrados**: Exibe todos os livros armazenados, com informações de idioma e total de downloads.
3.  **Listar autores registrados**: Lista os autores salvos e as obras associadas a cada um.
4.  **Listar autores vivos em um determinado ano**: Consulta o banco de dados para encontrar autores que estavam vivos em um ano específico.
5.  **Listar livros em um determinado idioma**: Filtra a biblioteca local por siglas de idioma (ex: `pt`, `en`, `es`, `fr`).

---

## 🏗️ Arquitetura e Boas Práticas

O projeto foi construído seguindo padrões de arquitetura em camadas para garantir o **desacoplamento** e a facilidade de manutenção:

* **Camada de Serviço (`LivroService`)**: Centraliza toda a lógica de negócio, persistência transacional (`@Transactional`) e gerenciamento de cache do Hibernate para garantir a integridade dos dados.
* **Camada de Repositório**: Utiliza Spring Data JPA com **Derived Queries** e consultas **JPQL** customizadas para otimizar o acesso ao banco de dados.
* **Camada de Interface (`Principal`)**: Atua exclusivamente como controladora de fluxo e interface com o usuário, delegando o processamento de dados para o Service.
* **Tratamento de Enums**: Implementação customizada no Enum `Idioma` para converter siglas da API em constantes do sistema de forma robusta.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 4.0.2**
* **Spring Data JPA**
* **PostgreSQL**
* **Jackson** (Manipulação de JSON)
* **API Gutendex** (Fonte de dados)

---

## 🔧 Configuração

Para rodar o projeto localmente, configure o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update