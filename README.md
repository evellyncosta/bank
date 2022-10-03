# bank

API bancária para o teste técnico da PISMO. Este projeto foi construído usando:

- Java 17
- SpringBoot
- PostgreSQL
- pgAdmin
- Docker
- Junit

Após baixar o projeto, rodar o seguinte comando:

```make up```

É importante que todas as portas estejam disponíveis:

- 8080 (Spring)
- 5432 (Postgres)

Para verificar se os containers estão rodando corretamente, executar :

```make ps```

Para verificar os logs:

```make logs```

Para abrir a linha de comando do PostgreSql:

```make db-shell```

Ao logar no PGAdmin pela primeira vez, será necessário criar a conexão, lembre-se de que o host é a rede interna do docker, portanto, a url de conexão do banco deve ser:

```host.docker.internal```

Na raiz do projeto existe uma collection que pode ser importada para o Postman
