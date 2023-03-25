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

Sua aplicação vai ser um microsserviço? Sem necessidade de filtros de requisição http e afins? Vai precisar de testes unitários e plugins para gerenciar dependências (maven, gradle)? Se sim, o recomendado é o Spring Boot.

Se sua aplicação não vai conter microsserviços, mas vai precisar filtrar requisições http, aplicar proteções (CORS por exemplo), permitir ou negar domínios, O recomendado é o Spring. Comumente utilizado como "BFF" (Backend for Frontend).


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
### Spring web
## Desenvolvimento
## Conclusão
## Extras
