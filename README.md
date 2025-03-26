**Calculadora de impostos**

**Descrição**

A calculadora de impostos é uma aplicação web desenvolvida em Java utilizando o framework Spring Boot. O objetivo do projeto é fornecer uma API REST para realizar cálculos de impostos e gerenciar usuários.

**Estrutura do Projeto**

O projeto está organizado em pacotes, cada um com sua responsabilidade específica:

* `br.com.zup.cataliza`: pacote raiz do projeto
* `br.com.zup.cataliza.controllers`: pacote responsável por controlar as requisições e respostas da aplicação
* `br.com.zup.cataliza.dtos`: pacote responsável por definir os objetos de transferência de dados
* `br.com.zup.cataliza.exceptions`: pacote responsável por definir as exceções personalizadas da aplicação
* `br.com.zup.cataliza.models`: pacote responsável por definir os modelos de dados da aplicação
* `br.com.zup.cataliza.repositories`: pacote responsável por interagir com a base de dados
* `br.com.zup.cataliza.services`: pacote responsável por implementar a lógica de negócios da aplicação

**Tecnologias Utilizadas**

* Java 21
* Spring Boot 3.4.3
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JWT (JSON Web Tokens)

**Como Executar**

1. Clone o repositório Git
2. Execute o comando `mvn spring-boot:run` para iniciar a aplicação
3. Acesse a aplicação via URL `http://localhost:8080`

**Endpoints**

* `POST /user/login`: realiza o login de um usuário e retorna um token JWT
* `POST /user/register`: cadastra um novo usuário
* `GET /user/{id}`: consulta um usuário por ID
* `POST /calculation`: realiza um cálculo de impostos

**Segurança**

* A aplicação utiliza Spring Security para autenticação e autorização
* Os dados são armazenados em uma base de dados PostgreSQL
* A aplicação utiliza JWT para autenticar e autorizar as requisições
**Exemplo de JSON para login**
## Exemplos de JSON para requisições
### post/user/register
```JSON
{
	"username": "usuario",
	"password": "senha",
	"role": "ROLE_USER"
}
```
Resposta:
201 Created
```
{
	"id": 1,
	"username": "usuario",
	"role": "ROLE_USER"
}
```
### post/login
```JSON
{
	"username": "usuario",
	"password": "senha"
}
```
Resposta:
200 OK
```JSON
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaGFuIjoiMjMwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
### post/tipos
```JSON
{
	"nome": "ICMS",
	"descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
	"aliquota": 19.2
}
```
Resposta:
201 Created
```JSON
{
	"id": 1,
	"nome": "ICMS",
	"descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
	"aliquota": 19.2
}
```
### get/tipos/{id}
Resposta:
200 OK
```JSON
{
	"id": 1,
	"nome": "ICMS",
	"descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
	"aliquota": 19.2
}
```
### get/tipos
Resposta:
200 OK
```JSON
[
	{
		"id": 1,
		"nome": "ICMS",
		"descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
		"aliquota": 19.2
	},
	{
		"id": 2,
		"nome": "COFINS",
		"descricao": "Contribuição para o Financiamento da Seguridade Social",
		"aliquota": 12.0
	}
]
```
### delete/tipos/{id}
Resposta:
204 No Content
### post/calculation
```JSON
{
	"tipoImpostoId": 1,
	"valorBase": 10000.0
}
```
Resposta:
200 OK
```JSON
{
	"tipoImposto": "Imposto sobre Circulação de Mercadorias e Serviços",
	"valorBase": 10000.0,
	"aliquota": 19.2",
	valorImposto": 1920.0
}
```
**Os valores das alíquotas foram modificados para simplificar os cálculos.**
**Foram implementados testes unitários para validar algumas funcionalidades da aplicação.** 