# Projeto: MongoDB Change Stream Reativo com Spring Boot

Este projeto demonstra o uso do **Spring Data MongoDB Reactive** para escutar eventos de alterações (`insert` e `update`) em uma coleção MongoDB chamada `origem`, utilizando o recurso **Change Stream** de forma reativa.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data MongoDB Reactive
- MongoDB (preferencialmente com Replica Set)
- Projeto reativo (Reactor)

## Objetivo

O serviço `ListenService` inicia automaticamente ao subir a aplicação e escuta eventos na coleção `origem`. Ele imprime no console o valor do campo `nome` do documento sempre que ocorre um `insert` ou `update` e o campo `data` estiver presente.

## Estrutura do Projeto

### Dependências principais (`pom.xml`)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### Exemplo de operações com o banco de dados

#### Inserção com campo `data` (será capturado)
```javascript

// Inserção com campo 'data' (será capturado)
db.origem.insertOne({
    nome: "João da Silva",
    telefone: "222222",
    data: new Date()
})

// Inserção sem campo 'data' (será ignorado)
db.origem.insertOne({
    nome: "Marcio Alencar",
    telefone: "3333",
})

// Atualização com 'set' (capturado se já havia 'data')
db.origem.update(
    { nome: "João da Silva" },
    { $set: { telefone: "4444" } }
)
```
