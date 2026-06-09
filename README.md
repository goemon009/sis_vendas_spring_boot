# SGCVP

Sistema de Gestao de Compra e Venda de Produtos (SGCVP) desenvolvido em Java com Spring Boot, com persistencia em PostgreSQL e exposicao de servicos REST.

Este projeto foi iniciado a partir do estudo de caso "Sistema de Gestao de Compra e Venda de Produtos (SGCVP)", usado na disciplina de Programacao Orientada a Servicos. O documento define um sistema para controle de produtos, categorias, promotores de venda, clientes, pedidos, compras, comissoes e consultas operacionais.

## Objetivo do estudo de caso

De acordo com o documento de referencia, o sistema deve atender cenarios como:

- cadastro de produtos e categorias;
- organizacao de produtos por categoria;
- controle de estoque;
- gestao de promotores de venda e regioes de atendimento;
- cadastro de clientes;
- processamento de pedidos de clientes;
- processamento de pedidos a fornecedores;
- geracao e quitacao de comissoes;
- consultas operacionais e gerenciais.

## Escopo implementado hoje

A aplicacao ainda representa uma parte inicial do SGCVP. No estado atual do repositorio, estao implementados:

- CRUD de `CategoriaProduto`;
- CRUD de `Produto`;
- associacao de `Produto` com `CategoriaProduto`;
- persistencia em PostgreSQL;
- documentacao OpenAPI/Swagger;
- testes automatizados usando profile `h2`.

## O que ainda nao foi implementado

O estudo de caso preve um escopo maior do que o codigo atual. Ainda faltam, entre outros pontos:

- entidades como `Promotor de Venda`, `Cliente`, `Pedido do Cliente`, `Pedido ao Fornecedor` e `Comissao`;
- servicos de mudanca de estado de pedidos, compras e comissoes;
- consultas especificas listadas no documento;
- autenticacao de usuarios;
- HATEOAS;
- modelo fisico SQL versionado no repositorio;
- logs e padroes de erro mais estruturados.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- SpringDoc OpenAPI
- Maven Wrapper
- H2 apenas para testes

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

- `idCategoriaProduto`
- `nome`
- `percentualComissao`
- `percentualDesconto`

### Produto

Representa um produto comercializado.

Campos atuais:

- `idProduto`
- `nome`
- `vlCusto`
- `qtdEstoque`
- `qntdReservadaProduto`
- `qntdMinEstoque`
- `qntdMaxEstoque`
- `percentualComissao`
- `percentualPromocao`
- `margemLucro`
- `categoriaProduto`

## Configuracao do banco de dados

O ambiente padrao da aplicacao usa PostgreSQL.

Arquivo: [src/main/resources/application.properties](/mnt/c/Users/leuca/OneDrive/Documentos/Projetos/sisvendas/src/main/resources/application.properties:1)

Configuracao padrao:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:sgcvp}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD:123456}
```

Variaveis suportadas:

- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`

Observacoes:

- o Hibernate esta configurado com `spring.jpa.hibernate.ddl-auto=update`;
- as tabelas sao criadas ou ajustadas automaticamente no banco configurado;
- o profile `h2` existe apenas para testes automatizados.

## Como executar

### Pre-requisitos

- JDK 21
- PostgreSQL em execucao
- banco `sgcvp` criado
- usuario e senha configurados para acesso

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

Se quiser sobrescrever a conexao com o banco:

```bash
DB_HOST=localhost DB_PORT=5432 DB_NAME=sgcvp DB_USER=postgres DB_PASSWORD=123456 ./mvnw spring-boot:run
```

### Observacao para ambiente WSL

Se a aplicacao estiver rodando dentro do WSL e o PostgreSQL estiver instalado no Windows host, pode ser necessario ajustar rede, firewall e `pg_hba.conf` para permitir conexoes externas ao `localhost` do Windows.

## Testes

Os testes usam H2 em memoria com o profile `h2`, para nao depender do PostgreSQL local.

```bash
./mvnw test
```

## Endpoints atualmente disponiveis

Base URL:

```text
http://localhost:8080
```

### Categorias

#### Listar categorias

```http
GET /categorias
```

#### Buscar categoria por id

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
  "nome": "Calcados",
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

#### Buscar produto por id

```http
GET /produtos/{id}
```

#### Cadastrar produto

```http
POST /produtos
Content-Type: application/json
```

O endpoint aceita a categoria de tres formas:

- `idCategoriaProduto`
- `categoriaProduto` como numero
- `categoriaProduto` como objeto com `id` ou `idCategoriaProduto`

Exemplo recomendado:

```json
{
  "nome": "Tenis Esportivo",
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

Tambem funciona:

```json
{
  "nome": "Tenis Esportivo",
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

- um produto so pode ser salvo se a categoria existir;
- quando a categoria informada nao existe, a API retorna `400 Bad Request`;
- a relacao `Produto -> CategoriaProduto` eh obrigatoria.

## Swagger

Com a aplicacao em execucao, a documentacao pode ser acessada em:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

Dependendo da versao do frontend do SpringDoc, `swagger-ui/index.html` tambem pode estar disponivel.

## Relacao com o estudo de caso

O documento exige uma API REST com:

- CRUD de entidades;
- operacoes de processamento;
- consultas especializadas;
- modelo fisico em PostgreSQL;
- criterios de qualidade como autenticacao, logs e HATEOAS.

Este repositorio atualmente atende apenas uma primeira fatia desse estudo de caso, concentrada em categorias e produtos. Portanto, este projeto deve ser entendido como base inicial para evolucao do SGCVP completo.

## Proximos passos sugeridos

- implementar as entidades faltantes do estudo de caso;
- criar os estados de pedido, compra e comissao;
- adicionar servicos de processamento;
- implementar consultas especializadas;
- introduzir camada de servico para regras de negocio;
- adicionar validacoes de entrada com Bean Validation;
- padronizar respostas de erro;
- adicionar autenticacao;
- gerar script SQL versionado do modelo fisico.
