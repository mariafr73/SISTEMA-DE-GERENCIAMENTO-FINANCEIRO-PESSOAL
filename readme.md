# Sistema de Gerenciamento Financeiro Pessoal

[![Tecnologia Principal](https://img.shields.io/badge/Tecnologia-Java-red)](https://www.java.com/)
[![Banco de Dados](https://img.shields.io/badge/Banco-SQLite-blue)](https://www.sqlite.org/)

## Sobre o Projeto

O **Sistema de Gerenciamento Financeiro Pessoal** foi desenvolvido com o objetivo de auxiliar usuários no controle e organização de suas finanças pessoais de forma simples, segura e eficiente.

O sistema permite o gerenciamento completo de rendas, despesas e categorias, além de fornecer recursos de autenticação de usuários e relatórios financeiros para acompanhamento da situação financeira ao longo do tempo.

---

## Funcionalidades

### Gestão de Usuários

* Cadastro de usuários.
* Login seguro utilizando criptografia de senhas com BCrypt.
* Edição de perfil.
* Exclusão de conta.
* Controle de sessão do usuário autenticado.

### Gestão de Rendas

* Cadastro de rendas.
* Edição de rendas.
* Exclusão de rendas.
* Visualização e listagem de rendas.
* Classificação entre renda fixa e renda extra.
* Consulta de rendas por período.
* Cálculo de total mensal de rendas.

### Gestão de Despesas

* Cadastro de despesas.
* Edição de despesas.
* Exclusão de despesas.
* Visualização e listagem de despesas.
* Consulta por período.
* Consulta por categoria.
* Cálculo de total mensal de despesas.

### Gestão de Categorias

* Cadastro de categorias.
* Edição de categorias.
* Desativação de categorias.
* Busca de categorias.
* Listagem de categorias do usuário.

### Relatórios Financeiros

* Relatório consolidado de rendas e despesas.
* Consulta por período personalizado.
* Cálculo de saldo financeiro.
* Visualização de totais de receitas e gastos.

### Segurança

* Senhas armazenadas com BCrypt.
* Controle de acesso por sessão.
* Validações de dados na camada de serviço.

---

## Tecnologias Utilizadas

| Categoria                |      Tecnologia      |
| ------------------------ | -------------------- |
| Linguagem de Programação | Java                 |
| Banco de Dados           | SQLite               |
| Arquitetura              | MVC + Service + DAO  |
| Persistência             | JDBC                 |
| Segurança                | BCrypt               |
| Controle de Versão       | Git e GitHub         |

---

## Arquitetura do Projeto

O sistema segue uma arquitetura baseada em MVC (Model-View-Controller), complementada pelas camadas Service e DAO para separação das regras de negócio e persistência dos dados.

```text
View
 ↓
Controller
 ↓
Service
 ↓
DAO
 ↓
Model
```

### Camadas

**View**

* Responsável pela interação com o usuário.

**Controller**

* Responsável por receber as ações da interface e encaminhá-las para as regras de negócio.

**Service**

* Responsável pelas validações e regras de negócio do sistema.

**DAO**

* Responsável pelo acesso ao banco de dados.

**Model**

* Representa as entidades do sistema.

**SQLite**

* Responsável pelo armazenamento persistente das informações financeiras do sistema.

---

## Estrutura do Banco de Dados

O sistema utiliza as seguintes entidades principais:

* Usuario
* Categoria
* Renda
* Despesa

Banco de dados utilizado: SQLite.

Relacionamentos:

* Um usuário pode possuir várias categorias.
* Um usuário pode possuir várias rendas.
* Um usuário pode possuir várias despesas.
* Cada despesa pertence a uma categoria.

---

## Como Utilizar

### 1. Cadastro

Crie uma conta informando:

* Nome
* Email
* Senha
* Data de nascimento

### 2. Login

Acesse o sistema utilizando seu email e senha.

### 3. Controle Financeiro

Após o login, o usuário poderá:

* Gerenciar rendas.
* Gerenciar despesas.
* Gerenciar categorias.
* Consultar relatórios financeiros.
* Atualizar seus dados cadastrais.

---

## Diagrama de Classes

O diagrama de classes representa a estrutura das entidades, seus atributos e relacionamentos.

![Diagrama de Classe](Docs/WhatsApp%20Image%202026-06-05%20at%2002.17.22.jpeg)

---

## Equipe de Desenvolvimento

| Integrante | Função |
|------------|---------|
| Maria Francisca Pessoa de Queiroz | Desenvolvimento e Modelagem |
| Levi Araujo Maia Neto | Desenvolvimento e Modelagem |

Universidade Federal Rural do Semi-Árido (UFERSA)

Curso: Bacharelado Interdisciplinar em Tecnologia da Informação (BTI)

Disciplina: Programação Orientada a Objetos