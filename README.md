# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 📄 Documento de Requisitos do Projeto

https://github.com/aplaraujo/delivery-api-assets/blob/main/delivery-api-documentacao-requisitos.pdf

## 💻 Tecnologias Usadas

- **Spring Boot:** *framework* Java que facilita a criação de **APIs e aplicativos web**
- **Spring Data JPA:** simplifica a implementação de camadas de acesso a dados baseadas na **Java Persistence API (JPA)**
- **Swagger/OpenAPI:** conjunto de ferramentas usado para documentar APIs de forma padronizada
- **SpringDoc:** biblioteca usada para integrar o OpenAPI 3 com Spring Boot
- **Spring Security:** módulo oficial do Spring que cuida da segurança em aplicações Java
- **JWT (JSON Web Token):** padrão aberto (RFC 7519) para troca segura de informações entre partes
- **JUnit 5:** *framework* principal de testes para Java, oferecendo anotações, verificações e execução de testes
- **Mockito:** biblioteca para criar objetos simulados (*mocks*) de dependências em testes unitários
- **Spring Boot Test:** suporte completo para testes com Spring, incluindo autoconfigurações
- **MockMVC:** permite testar controladores REST sem iniciar um servidor HTTP completo
- **Spring Boot Actuator:** módulo que fornece *endpoints* prontos para monitoramento de aplicativos Spring Boot
- **Micrometer:** biblioteca de instrumentação para coletar e expor métricas em projetos Spring Boot
- **Maven:** ferramenta de automação para projetos Java

## 👩🏾‍💼 Regras de Negócio

- Clientes precisam ser cadastrados e consultados
- Restaurantes devem ser gerenciados no sistema
- Produtos precisam ser organizados por restaurante
- Pedidos devem ser criados e rastreados

## 🔐 Autenticação e Autorização

A API utiliza autenticação baseada em JWT.

#### Fluxo resumido:

- Usuário realiza _login_
- A API retorna um _token_ JWT
- O _token_ deve ser enviado no cabeçalho de autorização (**_Authorization_**) das requisições protegidas

```
Authorization: Bearer <token>
```

## ✅ Endpoints

### Autenticação

- `POST /api/auth/login` - _login_ do usuário
- `POST /api/auth/register` - cadastro de usuário

### Pedidos

- `POST /api/pedidos` - cria um novo pedido

### Produtos

- `PUT /api/produtos/{id}` - atualiza um produto
- `POST /api/produtos` - cadastra um novo produto
- `PATCH /api/produtos/{id}/disponiblidade` - altera a disponibilidade de um produto
- `GET /api/produtos/restaurante/{restauranteId}` - busca produtos por restaurante

### Restaurante

- `GET /api/restaurantes/{id}` - busca um restaurante por identificador
- `PUT /api/restaurantes/{id}` - atualiza um restaurante
- `GET api/restaurantes` - busca todos os restaurantes
- `POST /api/restaurantes` - cadastra um novo restaurante
- `GET /api/restaurantes/categoria/{categoria}` - busca restaurantes por categoria

### Cliente

- `GET /api/clientes/{id}` - busca um cliente por identificador
- `PUT /api/clientes/{id}` - atualiza um cliente
- `GET /api/clientes` - busca todos os clientes ativos
- `POST /api/clientes` - cadastra um novo cliente
- `PATCH /api/clientes/{id}/status` - ativa ou desativa um cliente

## 🏃🏾‍♀️‍➡️ Como Executar

#### Pré-requisitos:

- JDK 21 Instalado
- Docker instalado 
- Docker Desktop instalado

#### Clone do repositório:

```
https://github.com/aplaraujo/delivery-api
```

#### Execuçao do projeto:

```terminaloutput
mvn clean install
mvn spring-boot:run
```

## 🧪 Testes

- Testes de integração de controladores

## 📁 Estruturas do Projeto

```
src/
├── config
│     ├── OpenApiConfig.java
│     ├── SecurityConfiguration.java
├── controllers
│     ├── AuthController.java
│     ├── ClienteController.java
│     ├── HealthController.java
│     ├── PedidoController.java
│     ├── ProdutoController.java
│     ├── RestauranteController.java
├── dto
│     ├── request
│     │     ├── ClienteRequest.java
│     │     ├── ItemPedidoRequest.java
│     │     ├── LoginRequest.java
│     │     ├── PedidoRequest.java
│     │     ├── ProdutoRequest.java
│     │     ├── RegisterRequest.java
│     │     ├── RestauranteRequest.java
│     ├── response
│     │     ├── ClienteResponse
│     │     ├── ItemPedidoResponse
│     │     ├── PedidoResponse
│     │     ├── ProdutoResponse
│     │     ├── RestauranteResponse
├── entities
│     ├── Cliente.java
│     ├── Endereco.java
│     ├── ItemPedido.java
│     ├── Pedido.java
│     ├── Produto.java
│     ├── Restaurante.java
│     ├── Role.java
│     ├── StatusPedido.java
│     ├── Usuario.java
├── exception
│     ├── BusinessException.java
│     ├── ConflictException.java
│     ├── EntityNotFoundException.java
│     ├── ErrorResponse.java
│     ├── GlobalExceptionHandler.java
├── repositories
│     ├── ClienteRepository.java
│     ├── PedidoRespository.java
│     ├── ProdutoRepository.java
│     ├── RestauranteRepository.java
│     ├── UsuarioRepository.java
├── security
│     ├── JwtAutenticationFilter.java
│     ├── JwtUtil.java
│     ├── UsuarioDetailsServiceImpl.java
├── services
│     ├── ClienteService.java
│     ├── PedidoService.java
│     ├── ProdutoService.java
│     ├── RestauranteService.java
│     ├── response
│     │     ├── ClienteServiceImpl.java
│     │     ├── PedidoServiceImpl.java
│     │     ├── ProdutoServiceImpl.java
│     │     ├── RestauranteServiceImpl.java
│     │ 
```

## 👩🏾‍💻 Desenvolvedora

Ana Paula Lopes Araujo - Arquitetura de Sistemas T5
<br>
Desenvolvido com JDK 21 e Spring Boot 3.5.x
