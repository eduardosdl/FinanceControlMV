# Finance Control - MV

Este projeto é uma API backend para o gerenciamento de contas, transações e clientes. Ele utiliza Java com o framework Spring, Hibernate e JPA para lidar com as operações de banco de dados, incluindo procedimentos e triggers em Oracle Database para geração de relatórios financeiros e automação de atualizações.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Oracle Database
- Swagger

## Instalação

- Clone o repositório:

    ```bash
    git clone https://github.com/eduardosdl/FinanceControlMV.git
    cd FinanceControlMV
    ```

- Configure o banco de dados no arquivo application.properties:

    ```bash
    spring.datasource.url=jdbc:oracle:thin:@//<host>:<port>/<service>
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    ```

- **Execute o projeto**

## Principais Endpoints

> [!TIP]
O `financeControlMvEndpoints.json` que se encontra na raiz do projeto, contém todas as URLs exportadas do insomnia, para você importar para o client http que você desejar

### Documentação
- Acesse `/doc` em seu navegador para ter acesso a documentação dos endpoits gerados pelo insomnia

### Cliente

- GET `/clients`: Listar todos os clientes.
- GET `/clients/{id}`: Obter detalhes de um cliente por ID.
- POST `/clients`: Criação de um novo cliente.
- PUT `/clients/{id}`: Atualizar informações de um cliente.
- DELETE `/clients/{id}`: Excluir um cliente.

### Conta

- GET `/accounts`: Listar todas as contas.
- GET `/accounts/client/{id}`: Lista todas as contas de um cliente.
- POST `/accounts/client/{id}`: Criação de uma nova conta para um cliente.
- PUT `/accounts/{id}`: Atualizar informações de uma conta.
- DELETE `/accounts/{id}`: Excluir uma conta.

### Transações (movimentações de crédito ou débito)

- GET `/transactions`: Lista todas as transações realizadas.
- GET `/transactions/{id}`: Busca uma transação especifica.
- GET `/transactions/account/{id}`: Busca todas as transações de uma conta.
- POST `/transactions/account/{id}`: Criar uma nova transação relacionada a uma conta.

### Relatórios

- GET `/reports/client/{id}`: Retorna o relatório de saldo do cliente.
- GET `/reports/client/{id}/period?startDate=yyyy-mm-dd&endDate=yyyy-mm-dd`: Retorna o relatório de saldo do cliente em um periodo específico.
- GET `/reports/clients`: Retorna o relatório de saldo de todos os clientes.
- GET `/reports/company/period?startDate=2023-09-01&endDate=2024-09-30`: Retorna o relatório de receita da empresa em um determinado periodo.

## Procedures e Trigger

### Triggers

- update_account_balance: Realiza o calcula do saldo atual da conta de acordo com as inserções na tabela `transactions`

### Procedures

- REPORT_CLIENT_BALANCE: retorna os dados do relatório de saldo do cliente

- REPORT_CLIENT_BALANCE_BY_PERIOD: retorna os dados do relatório de saldo do cliente com base no periodo informado

- REPORT_BALANCE_ALL_CLIENTS: retorna os dados do relatório de saldo de todos os clientes cadastrados

- REPORT_COMPANY_REVENUE: retorna os dados do relatório de receita da empresa com base nas transações de um determinado periodo informado.

## Boas Práticas Utilizadas

- Utilização do padrão REST
- Tratamento de Exceções
- Validação de Dados

## Explicações

### Por que uma API ?

escolhi fazer uma API por ser uma padrão muito usado atualmente no mercado e pelo conhecimento prévio que tenho nesse tipo de código.

### Separação de componentes

Separei os componente em Model, Repository, Service e Controller e utilizei DTOs para fazer a transferencia e validação dos dados principalmente entre as camandas Service e Controller. Optei por essa divisão pensando na separação das reponsabilidades, onde:

- Controller: responsável por defiinir as rotas e os parametros de cada rota
- Service: centraliza a logica de négocio e chamda dos repositories
- Repository: realiza a interação com os Models, possibilitando troca da camada de armazenamento, caso necessário, de forma simples
- Model: onde se localiza a definição das classes e das entidades para criação e manipulação dos dados

### Uso de trigger e procedures

Optei por usar triggers e procedures no lugar de excutar a logica dentro do código Java com objetivo de melhorar a manutanção e otimizar o funcionamento da aplicação.
