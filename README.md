# Movies Battle

## Setup inicial
> O projeto pode ser aplicado utilizando Maven + Java 11

[Maven](https://maven.apache.org/install.html)

[SdkMan - Java](https://sdkman.io/install)

## Introdução

### Frameworks
> O que são frameworks? Quais as vantagens de utilizar?

Os frameworks são _conjuntos de ferramentas_, trazem consigo uma infinidade de possibilidades para resolução dos problemas mais comuns do dia-a-dia, além de implementar os _padrões arquiteturais_ mais utilizados e difundidos no mercado.

> Conjunto de ferramentas

Imagine que você precise codificar uma solução para autenticação de usuários.
Não é necessário codificar tudo "do zero", é possivel utilizar dependências, libs e frameworks que já aplicam soluções de autenticação e muito mais.

> Frameworks, libs e dependências são a mesma coisa?

Não.

Vejamos o diagrama a seguir:

![image](https://user-images.githubusercontent.com/39572088/227717982-9b4e063c-8f09-4896-8036-47ccfa5f3700.png)

Os frameworks _contém_ libs (libraries ou bibliotecas) de código, enquanto que as libs podem ser injetadas individualmente em seu código.

> Sempre devo utilizar frameworks então? Afinal eles contém libs

Nem sempre os frameworks devem ser utilizados. Em certos casos, apenas injetar uma lib pode trazer a solução que você precisa.

Porém, para agilizar o desenvolvimento de aplicações, os frameworks são utilizados levando em consideração o _pacote_ de opções que ele oferece:

_preciso de:_ solução de login, autenticação, segurança, consumo de banco de dados, padrão arquitetural de desenvolvimento, aplicação com deploy em nuvem -> O melhor é um framework

_preciso de:_ consumir um banco de dados, mas meu framework atual não suporta -> injetar uma lib para consumo de BD.

### O que é Spring/Spring boot?

Spring e Spring Boot são frameworks Java.

A diferença entre os dois é que o "Boot" é uma extensão do "Spring", trazendo mais facilidades na configuração da aplicação e ainda libs que estão presentes no Spring.

> Então sempre devo utilizar o Spring boot, não o Spring?

Isso depende:

Sua aplicação vai ser um microsserviço? Sem necessidade de filtros de requisição HTTP e afins? Vai precisar de testes unitários e plugins para gerenciar dependências (maven, gradle)? Se sim, o recomendado é o Spring Boot.

Se sua aplicação não vai conter microsserviços, mas vai precisar filtrar requisições HTTP, aplicar proteções (CORS por exemplo), permitir ou negar domínios, O recomendado é o Spring. Comumente utilizado como "BFF" (Backend for Frontend).


### Injeção de dependências
> Como funciona a injeção de dependências no Spring boot?

Antes de falar sobre injeção de dependências, precisamos conhecer primeiro sobre "IoC" - Inversion of control (inversão de controle)

> O que é a inversão de controle (IoC)?

É um principio de desenvolvimento de software, onde transferimos a responsabilidade do controle de objetos ou partes de uma aplicação para um container ou um _framework_.
Na programação tradicional, esse controle é feito por nosso próprio código quando consumimos uma lib, por exemplo.

Passando este controle para um framework, temos acesso à um leque de possibilidades e vantagens que esta arquitetura permite:

1. separar a execução da implementação;
2. facilidade em trocar implementações;
3. maior modularidade;
4. facilidade em teste de aplicações, facilita o uso de mocks de componentes e dependências;

> Método tradicional

![image](https://user-images.githubusercontent.com/39572088/227721548-1e3a615b-9704-4ba8-b701-adcdb0f7cdcf.png)

> Usando IoC

![image](https://user-images.githubusercontent.com/39572088/227721566-587a53cc-30f6-40f9-a7c8-8906d2309664.png)

### Interfaces

Interfaces são tipos _abstratos_ que contém um conjunto de métodos. São utilizadas para se obter polimorfismo e herança.

Exemplo de interface:

``` java
public interface HttpClient {
    String getMovieDetail(String title, int year);
    String getMovieById(String movieId);
}
```

Na interface acima, chamada de HttpClient temos dois métodos:

1 - getMovieDetail = retorna detalhes de um filme baseado em seu titulo e ano;

2 - getMovieById = retorna detalhes de um filme, baseado em seu id do Imdb;


Temos uma interface acima para executar as requisições HTTP. 
Agora precisamos criar uma outra interface, para CONFIGURAR as nossas conexões:

``` java
public interface ClientConfigurator {
    String prepareServiceUrl(String title, int year);
    String prepareServiceUrl(String titleId);
    String prepareAuthentication(String uri);
}
```

Agora temos as chamadas por titulo e ano, outra chamada por id do filme. Também temos o método prepareAuthentication, onde definimos a chave de nossa API KEY para fazer o consumo seguro da API.
<br>

De acordo com o OpenImdbAPI, devemos consumir assim:

> https://www.omdbapi.com/?apikey=[yourkey]&[params]

Desta forma podemos nos conectar de forma segura na API e fazer as requisições.

Já temos as interfaces, agora precisamos implementá-las.

Para isso, vamos precisar de:
- Maven 
- Spring boot


para facilitar nosso trabalho podemos utilizar o [spring initializr](https://start.spring.io/), que configura um projeto pra nós.

Selecionar a dependência _web_ por hora é suficiente.

<br>

### Spring web
<br>
Com a dependência do spring web adicionada ao nosso _pom.xml_ estamos habilitados a executar e receber requisições HTTP.

Junto com esta dependência, conseguimos acesso à uma lib chamada _RestTemplate_ que nos auxilia na execução de chamadas HTTP, que vamos utilizar para nos conectar com a API do Imdb.
<br>
<br>

## Criando nova classe para implementar as interfaces

A partir deste ponto, podemos criar uma classe chamada de _RestClient_ que vai ser responsável por obter os dados do serviço externo que vamos consumir.

``` java
public class RestClient{

    // lib para executar as chamadas
    RestTemplate restTemplate = new RestTemplate();

    // dados de configuração
    private static final String API_KEY = "f81cfd34";
    private static final String URI_API_IMDB = "https://www.omdbapi.com/";
}
```

Depois, fazemos a implementação da interface _CLientConfigurator_:

``` java
public class RestClient implements ClientConfigurator{

    @Override
    public String prepareServiceUrl(String title, int year){
        String uri = URI_API_IMDB;

        uri = uri.concat("?t=" + title)
                 .concat("&y=" + year);

        uri = prepareAuthentication(uri);

        return uri;
    }

    @Override
    public String prepareAuthentication(String uri){
        return uri.concat("&apikey=" + API_KEY);
    }

    @Override
    public String prepareServiceUrl(String titleId){
        String uri = URI_API_IMDB;

        uri = uri.concat("?i=" + titleId);
        uri = prepareAuthentication(uri);

        return uri;
    }
}
```

A _annotation_ _Override_ garante que a implementação está sendo feita da classe externa;

Em seguida, implementamos os métodos da _HttpClient_ :

``` java
public class RestClient implements ClientConfigurator{

    @Override
    public String getMovieDetail(String title, int year) {
        String uri = prepareServiceUrl(title, year);

        ResponseEntity<String> response = executeCall(uri);

        return  response.getBody();
    }

    @Override
    public String getMovieById(String movieId) {
        String uri = prepareServiceUrl(movieId);

        ResponseEntity<String> response = executeCall(uri);

        return response.getBody();
    }
}
```

E criamos um método privado chamado de _executeCall_ para poder fazer a chamada em si ao serviço:

``` java
    private ResponseEntity<String> executeCall(String uri){
        return restTemplate.exchange(uri,
                HttpMethod.GET,
                HttpEntity.EMPTY
                ,String.class);
    }
```
#
<details>
  <summary>Implementação completa</summary>
  
  ``` java

import interfaces.ClientConfigurator;
import interfaces.HttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient implements HttpClient, ClientConfigurator {

    RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "f81cfd34";
    private static final String URI_API_IMDB = "https://www.omdbapi.com/";

    @Override
    public String getMovieDetail(String title, int year) {
        String uri = prepareServiceUrl(title, year);

        ResponseEntity<String> response = executeCall(uri);

        return  response.getBody();
    }

    @Override
    public String getMovieById(String movieId) {
        String uri = prepareServiceUrl(movieId);

        ResponseEntity<String> response = executeCall(uri);

        return response.getBody();
    }


    public String prepareServiceUrl(String title, int year){
        String uri = URI_API_IMDB;

        uri = uri.concat("?t=" + title)
                 .concat("&y=" + year);

        uri = prepareAuthentication(uri);

        return uri;
    }


    public String prepareAuthentication(String uri){
        return uri.concat("&apikey=" + API_KEY);
    }

    public String prepareServiceUrl(String titleId){
        String uri = URI_API_IMDB;

        uri = uri.concat("?i=" + titleId);
        uri = prepareAuthentication(uri);

        return uri;
    }

    private ResponseEntity<String> executeCall(String uri){
        return restTemplate.exchange(uri,
                HttpMethod.GET,
                HttpEntity.EMPTY
                ,String.class);
    }

}

  ```
</details>

#

### Response da API
``` json

HTTP GET https://www.omdbapi.com/?t=batman&y=1997&apikey=f81cfd34
Accept=[text/plain, application/json, application/*+json, */*]
Response 200 OK
Reading to [java.lang.String] as "application/json;charset=utf-8"

```

<details>
 <summary>response.json</summary>

 ```
{
  "Title": "Batman & Robin",
  "Year": "1997",
  "Rated": "PG-13",
  "Released": "20 Jun 1997",
  "Runtime": "125 min",
  "Genre": "Action, Sci-Fi",
  "Director": "Joel Schumacher",
  "Writer": "Bob Kane, Akiva Goldsman",
  "Actors": "Arnold Schwarzenegger, George Clooney, Chris O'Donnell",
  "Plot": "Batman and Robin try to keep their relationship together even as they must stop Mr. Freeze and Poison Ivy from freezing Gotham City.",
  "Language": "English",
  "Country": "United States, United Kingdom",
  "Awards": "10 wins & 22 nominations",
  "Poster": "https://m.media-amazon.com/images/M/MV5BMGQ5YTM1NmMtYmIxYy00N2VmLWJhZTYtN2EwYTY3MWFhOTczXkEyXkFqcGdeQXVyNTA2NTI0MTY@._V1_SX300.jpg",
  "Ratings": [
    {
      "Source": "Internet Movie Database",
      "Value": "3.7/10"
    },
    {
      "Source": "Rotten Tomatoes",
      "Value": "12%"
    },
    {
      "Source": "Metacritic",
      "Value": "28/100"
    }
  ],
  "Metascore": "28",
  "imdbRating": "3.7",
  "imdbVotes": "257,380",
  "imdbID": "tt0118688",
  "Type": "movie",
  "DVD": "21 Oct 1997",
  "BoxOffice": "$107,353,792",
  "Production": "N/A",
  "Website": "N/A",
  "Response": "True"
}
```
</details>

O response de nossa aplicação, até então, está no formato de String, como pode ser visto no retorno dos métodos.

Precisamos mapear nossa resposta para um POJO (classe pura Java), então nosso primeiro passo é criar uma classe de
uma classe de _Domain_ para que possamos jogar as informações e depois uma _Utils_ onde vamos concentrar os métodos mais comuns utilizados por nossa aplicação:

``` java
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {

    private String movieName;
    private String releaseYear;
    private String movieId;
    private Double imdbRating;
}

```
> Para utilizar as anotações @Getter, @Setter e afins, precisamos ativar o _Lombok_ em nosso projeto (pom.xml), facilitando a escrita
> de código
``` xml
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>RELEASE</version>
        <scope>compile</scope>
    </dependency>
```
Criação da classe Utils, que vai fazer a conversão e mapeamento de nossos campos:
``` java
public class Utils {

    public static Movie convertResponse(String apiResponse) throws JsonProcessingException {
        // cria um objeto que possa ser mapeado em JSON, a partir de String
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(apiResponse);

        return mapResponseFields(node);
    }

    private static Movie mapResponseFields(JsonNode node){
        // mapeia somente os campos necessarios para nossa batalha
        Movie movie = new Movie();
        movie.setMovieName(node.get("Title").asText());
        movie.setReleaseYear(node.get("Year").asText());
        movie.setMovieId(node.get("imdbID").asText());
        movie.setImdbRating(node.get("imdbRating").asDouble());

        return movie;
    }
}

```
### Expondo as informações

Tendo em vista que já possuimos:
- Chamada válida a uma fonte de dados;
- Conhecemos a informação que precisamos;
- Mapeamos a informação e convertemos em JSON;
- Temos os parâmetros necessários para obter as informações;

Já podemos fazer a exposição de um _controller_ em nossa aplicação, ficando responsável por manusear as requisições HTTP
baseadas em nossas regras.

Nosso controller de filmes vai ser composto de 2 endpoints:
- getMovieById: id do filme no imdb
- getMovieByParams: nome do filme e ano ou somente pelo nome

````java

@RestController
@RequestMapping("/v1/movies")
@AllArgsConstructor
public class MovieController {
    
    private final RestClient restClient;
    
    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) throws JsonProcessingException {
        String response = restClient.getMovieById(id);

        if (response.contains("Error")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return  new ResponseEntity<>(Utils.convertResponse(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Movie> getMovieByParams(@RequestParam  String title,
                                                  @RequestParam (required = false) Integer year) throws JsonProcessingException {

        String response = restClient.getMovieDetail(title, year);

        if (response.contains("Error")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return  new ResponseEntity<>(Utils.convertResponse(response), HttpStatus.OK);
    }
}

````

> @RestController: expõe nossa interface para receber requisições
>
> @RequestMapping: define o caminho de nossa interface sendo: url da aplicação + requestMapping
> 
> @AllArgsConstructor: cria um construtor que adiciona todos os parâmetros necessários
> 
> @GetMapping: informa que aquela requisição é do tipo GET
> 
> ResponseEntity<Movie>: tipo de resposta de nosso serviço + status code

Como pode ser visto, fizemos injeção de nosso RestClient dentro do _controller_, porém, se tentarmos subir a aplicação assim, 
vai apresentar um erro pra nós, onde o Spring vai comentar que não possui o objeto RestClient em memória.
Graças  ao IoC, é muito simples ajustar esse erro:

````java
@Configuration
public class ApplicationConfig {

    @Bean
    public RestClient prepareHttpClient(){
        return new RestClient();
    }
}
````

Anotando com _@Configuration_ nós dizemos ao Spring que inicialize as configurações desta classe antes de subir
a aplicação, criando assim, o objeto que precisamos para injetar em nosso controller.

Agora que possuimos endpoints expostos, nossa aplicação já está rodando e funcional, podemos focar na modelagem de nossos dados:

![image](https://user-images.githubusercontent.com/39572088/227784481-e77f3606-be14-48f6-8d95-d68dc9f566c0.png)

> Jogador: pode possuir várias partidas e várias sequências;
> 
> Partidas: pode possuir vários jogadores e sequências;
> 
> Sequências: pode possuir vários jogadores e vários filmes, além de aparecer em diversas partidas;
> 
> Filmes: pode estar em várias sequências;

### Regras
- Os jogadores precisam criar cadastro e estarem logados;
- A sequência tem que ter um par de filmes;
- Este par não pode se repetir;
- Deve ser calculado o score do par, baseado no imdbRating de cada filme;
- Deve possuir um ranking;

Com base nas regras acima e todas as classes já criadas, vamos elaborar nosso fluxo completo da aplicação.
    
> Fluxo completo

 ![image](https://user-images.githubusercontent.com/39572088/227791540-009caf15-75be-496e-9542-e645f6886d46.png)

### Conclusão
### Extras
