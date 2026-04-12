# ️ League of Java - MOBA API

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4+-brightgreen.svg)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue.svg)

Uma API RESTful desenvolvida em Java com Spring Boot, inspirada no gerenciamento de entidades de um jogo estilo MOBA (Multiplayer Online Battle Arena). O sistema permite o gerenciamento de Campeões, Skins, Estatísticas de Jogo e Autenticação de Invocadores (Usuários).

##  Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e boas práticas:

* **Java 17+**
* **Spring Boot 3.4+** (Web, Data JPA, Security, Validation)
* **Spring Security & JWT (JSON Web Token)** para autenticação e autorização.
* **Banco de Dados Relacional** (JPA / Hibernate)
* **Lombok** para redução de código boilerplate.
* **ModelMapper** para conversão inteligente entre Entidades e DTOs.
* **Swagger (OpenAPI 3)** para documentação interativa da API.
* **JUnit 5 & Mockito** para testes unitários isolados (Controllers, Services e Repositories).

##  Arquitetura

O projeto segue a arquitetura em camadas padrão do mercado (N-Tier):
* **Controller:** Camada de apresentação da API, gerenciamento de rotas e validações de entrada.
* **Service:** Regras de negócio e orquestração.
* **Repository:** Acesso aos dados utilizando Spring Data JPA.
* **Domain (Entidades):** Representação das tabelas do banco de dados.
* **DTO (Data Transfer Object):** Objeto de transferência para evitar exposição de dados sensíveis e isolar o domínio.

##  Funcionalidades e Endpoints

A API está dividida nos seguintes domínios principais:

###  Autenticação (`/auth`)
* `POST /auth/register`: Cadastra um novo invocador (Admin/User).
* `POST /auth/login`: Autentica o invocador e retorna o Token JWT.

### ️ Campeões (`/champions`) *Requer Token*
* `POST /champions`: Cadastra um novo campeão.
* `GET /champions`: Lista todos os campeões de forma paginada.
* `GET /champions/{id}`: Busca um campeão pelo ID.
* `GET /champions/name/{name}`: Busca um campeão pelo nome.
* `PUT /champions/inactivate/{id}`: Inativa um campeão.
* `PUT /champions/activate/{id}`: Ativa um campeão.
* `DELETE /champions/{id}`: Remove um campeão do sistema.

###  Skins (`/skins`) *Requer Token*
* `POST /skins`: Cadastra uma nova skin vinculada a um campeão.
* `GET /skins`: Lista todas as skins de forma paginada.
* `GET /skins/{id}`: Busca uma skin pelo ID.
* `PUT /skins/inactivate/{id}`: Inativa uma skin.
* `PUT /skins/activate/{id}`: Ativa uma skin.

###  Estatísticas (`/championStats`) *Requer Token*
* `POST /championStats`: Registra o Win Rate, Pick Rate e Ban Rate de um campeão.
* `GET /championStats`: Lista as estatísticas de todos os campeões.

##  Testes Unitários

O projeto possui uma alta cobertura de testes unitários garantindo a confiabilidade das regras de negócio e rotas.
* **Controllers:** Testados utilizando `MockMvc` simulando o contexto Web e requisições HTTP.
* **Services:** Testados utilizando `Mockito` para isolar a regra de negócio do banco de dados.
* **Repositories:** Testados utilizando `@DataJpaTest` com banco de dados em memória.

Para rodar os testes:
```bash
mvn test
```
##  Documentação (Swagger)

A documentação interativa da API foi gerada pelo Swagger. Após iniciar a aplicação, ela pode ser acessada através do link:

* **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

> **Atenção:** Para testar as rotas protegidas diretamente pelo Swagger, você deve primeiro realizar uma requisição na rota `POST /auth/login` utilizando credenciais válidas. Copie o token JWT retornado, clique no botão **"Authorize"** localizado no topo da página do Swagger e cole o token (adicione o prefixo "Bearer " se a sua configuração exigir).

---

##  Como Executar o Projeto

### Passo a Passo

1. **Clone o repositório:**
   Abra o seu terminal e execute o comando abaixo (lembre-se de substituir o link pelo do seu repositório real):
   ```bash
   git clone [https://github.com/SEU-USUARIO/league-of-java.git](https://github.com/SEU-USUARIO/league-of-java.git)

2. **Acesse o diretório do projeto:**
```bash
cd league-of-java
```
3. **Atualize as dependências e compile o projeto:**
```bash
mvn clean install
```
4. **Inicie a aplicação:**
```bash
mvn spring-boot:run
```
5. **Acesse a API:**
```bash
http://localhost:8080