[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FelipeNathan_Guiabolso&metric=alert_status)](https://sonarcloud.io/dashboard?id=FelipeNathan_Guiabolso)
[![CI](https://github.com/FelipeNathan/Guiabolso/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/FelipeNathan/Guiabolso/actions/workflows/ci.yml)

## Requisitos
- java 11+
 - springboot
 - junit5
 - h2 database

## Objetivo
- Sistema usado para gerenciar os funcionários de uma empresa pequena.

## Uso

Para rodar o projeto execute:
` `` 
./mvnw spring-boot:run
` ``


- O servidor irá iniciar em localhost na porta 8080 por padrão
- Chama o  edpoint 

- POST - Cadastrar um usuário
![](./backend/doc/Post.png)

- GET - Buscar um usuário específico pelo id dele
- GET - Buscar todos os usuários cadastrados
- GET - Buscar usuários por CEP
- PUT - Atualizar a entidade funcionário
- DELETE - Excluir funcionário

```json
[GET] http://localhost:8080/employee/2
