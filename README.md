# SGCVP

Sistema de Gestão de Compra e Venda de Produtos (SGCVP) desenvolvido em Java com Spring Boot, com persistência em PostgreSQL e exposição de serviços REST.

Este projeto foi iniciado a partir do estudo de caso "Sistema de Gestão de Compra e Venda de Produtos (SGCVP)", usado na disciplina de Programação Orientada a Serviços. O documento define um sistema para controle de produtos, categorias, promotores de venda, clientes, pedidos, compras, comissões e consultas operacionais.

## Objetivo do estudo de caso

De acordo com o documento de referência, o sistema deve atender a cenários como:

* cadastro de produtos e categorias;
* organização de produtos por categoria;
* controle de estoque;
* gestão de promotores de venda e regiões de atendimento;
* cadastro de clientes;
* processamento de pedidos de clientes;
* processamento de pedidos a fornecedores;
* geração e quitação de comissões;
* consultas operacionais e gerenciais.

## Escopo implementado hoje

A aplicação ainda representa uma parte inicial do SGCVP. No estado atual do repositório, estão implementados:

* CRUD de `CategoriaProduto`;
* CRUD de `Produto`;
* associação de `Produto` com `CategoriaProduto`;
* persistência em PostgreSQL;
* documentação OpenAPI/Swagger;
* testes automatizados usando o profile `h2`.

## O que ainda não foi implementado

O estudo de caso prevê um escopo maior do que o código atual. Ainda faltam, entre outros pontos:

* entidades como `Promotor de Venda`, `Cliente`, `Pedido do Cliente`, `Pedido ao Fornecedor` e `Comissão`;
* serviços de mudança de estado de pedidos, compras e comissões;
* consultas específicas listadas no documento;
* autenticação de usuários;
* HATEOAS;
* modelo físico SQL versionado no repositório;
* logs e padrões de erro mais estruturados.

## Tecnologias utilizadas

* Java 21
* Spring Boot 4.0.6
* Spring Web MVC
* Spring Data JPA
* PostgreSQL
* SpringDoc OpenAPI
* Maven Wrapper
* H2 apenas para testes

## Estrutura atual do projeto

```text
src/main/java/com/ifmt/sisvendas
|- controller
|  |- CategoriaProdutoController.java
|  |- ProdutoController.java
|- dto
|  |- ProdutoRequest.java
|- model
|  |- CategoriaProduto.java
|  |- Produto.java
|  |- Regiao.java
|- repository
   |- CategoriaProdutoRepository.java
   |- ProdutoRepository.java
```

## Modelo de dados implementado

### CategoriaProduto

Representa a categoria de um produto.

Campos atuais:

* `idCategoriaProduto`
* `nome`
* `percentualComissao`
* `percentualDesconto`

### Produto

Representa um produto comercializado.

Campos atuais:

* `idProduto`
* `nome`
* `vlCusto`
* `qtdEstoque`
* `qntdReservadaProduto`
* `qntdMinEstoque`
* `qntdMaxEstoque`
* `percentualComissao`
* `percentualPromocao`
* `margemLucro`
* `categoriaProduto`

## Configuração do banco de dados

O ambiente padrão da aplicação usa PostgreSQL.

Arquivo: `src/main/resources/application.properties`

Configuração padrão:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:sgcvp}
spring.datasource.username=${DB_USER:???}
spring.datasource.password=${DB_PASSWORD:???}
```

Variáveis suportadas:

* `DB_HOST`
* `DB_PORT`
* `DB_NAME`
* `DB_USER`
* `DB_PASSWORD`

Observações:

* o Hibernate está configurado com `spring.jpa.hibernate.ddl-auto=update`;
* as tabelas são criadas ou ajustadas automaticamente no banco configurado;
* o profile `h2` existe apenas para testes automatizados.

## Como executar

### Pré-requisitos

* JDK 21
* PostgreSQL em execução
* banco `sgcvp` criado
* usuário e senha configurados para acesso

### Executando com Maven Wrapper

No Linux/WSL:

```bash
./mvnw spring-boot:run
```

No Windows:

```bat
mvnw.cmd spring-boot:run
```

Garanta que o `JAVA_HOME` esteja apontando para um JDK 21 antes de executar o comando.

Se quiser sobrescrever a conexão com o banco:

```bash
DB_HOST=localhost DB_PORT=5432 DB_NAME=sgcvp DB_USER=USUARIO DB_PASSWORD=SUASENHA ./mvnw spring-boot:run
```

### Observação para ambiente WSL

Se a aplicação estiver rodando dentro do WSL e o PostgreSQL estiver instalado no Windows host, pode ser necessário ajustar rede, firewall e `pg_hba.conf` para permitir conexões externas ao `localhost` do Windows.

## Testes

Os testes usam H2 em memória com o profile `h2`, para não depender do PostgreSQL local.

```bash
./mvnw test
```

## Endpoints atualmente disponíveis

Base URL:

```text
http://localhost:8080
```

### Categorias

#### Listar categorias

```http
GET /categorias
```

#### Buscar categoria por ID

```http
GET /categorias/{id}
```

#### Cadastrar categoria

```http
POST /categorias
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Calçados",
  "percentualComissao": 10.50,
  "percentualDesconto": 5.00
}
```

#### Atualizar categoria

```http
PUT /categorias/{id}
Content-Type: application/json
```

#### Excluir categoria

```http
DELETE /categorias/{id}
```

### Produtos

#### Listar produtos

```http
GET /produtos
```

#### Buscar produto por ID

```http
GET /produtos/{id}
```

#### Cadastrar produto

```http
POST /produtos
Content-Type: application/json
```

O endpoint aceita a categoria de três formas:

* `idCategoriaProduto`
* `categoriaProduto` como número
* `categoriaProduto` como objeto com `id` ou `idCategoriaProduto`

Exemplo recomendado:

```json
{
  "nome": "Tênis Esportivo",
  "vlCusto": 120.00,
  "qtdEstoque": 15,
  "qntdReservadaProduto": 2,
  "qntdMinEstoque": 3,
  "qntdMaxEstoque": 30,
  "percentualComissao": 8.5,
  "percentualPromocao": 2.0,
  "margemLucro": 25.0,
  "idCategoriaProduto": 1
}
```

Também funciona:

```json
{
  "nome": "Tênis Esportivo",
  "vlCusto": 120.00,
  "qtdEstoque": 15,
  "qntdReservadaProduto": 2,
  "qntdMinEstoque": 3,
  "qntdMaxEstoque": 30,
  "percentualComissao": 8.5,
  "percentualPromocao": 2.0,
  "margemLucro": 25.0,
  "categoriaProduto": 1
}
```

#### Atualizar produto

```http
PUT /produtos/{id}
Content-Type: application/json
```

#### Excluir produto

```http
DELETE /produtos/{id}
```

## Regras atuais do endpoint de produto

No controller atual:

* um produto só pode ser salvo se a categoria existir;
* quando a categoria informada não existe, a API retorna `400 Bad Request`;
* a relação `Produto -> CategoriaProduto` é obrigatória.

## Swagger

Com a aplicação em execução, a documentação pode ser acessada em:

* `http://localhost:8080/swagger-ui.html`
* `http://localhost:8080/v3/api-docs`

Dependendo da versão do frontend do SpringDoc, `swagger-ui/index.html` também pode estar disponível.

## Relação com o estudo de caso

O documento exige uma API REST com:

* CRUD de entidades;
* operações de processamento;
* consultas especializadas;
* modelo físico em PostgreSQL;
* critérios de qualidade como autenticação, logs e HATEOAS.

Este repositório atualmente atende apenas a uma primeira fatia desse estudo de caso, concentrada em categorias e produtos. Portanto, este projeto deve ser entendido como base inicial para a evolução do SGCVP completo.

## Próximos passos sugeridos

* implementar as entidades faltantes do estudo de caso;
* criar os estados de pedido, compra e comissão;
* adicionar serviços de processamento;
* implementar consultas especializadas;
* introduzir camada de serviço para regras de negócio;
* adicionar validações de entrada com Bean Validation;
* padronizar respostas de erro;
* adicionar autenticação;
* gerar script SQL versionado do modelo físico.
