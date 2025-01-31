## Entrega Média
```mermaid
classDiagram
class Produto {
  - String nome
  - double preco
  - int quantidade
  + Produto(String nome, double preco, int quantidade)
  + getNome(): String
  + getPreco(): double
  + getQuantidade(): int
  + setQuantidade(int quantidade): void
}

class Cliente {
  - String nome
  - String cpf
  - String email
  + Cliente(String nome, String cpf, String email)
  + getNome(): String
  + getCpf(): String
  + getEmail(): String
}

class Compra {
  - Cliente cliente
  - List~Produto~ produtos
  + Compra(Cliente cliente, List~Produto~ produtos)
  + getCliente(): Cliente
  + getProdutos(): List~Produto~
}

class SistemaECommerce {
  - List~Produto~ produtos
  - List~Cliente~ clientes
  - List~Compra~ compras
  + cadastrarProduto(Produto produto): void
  + cadastrarCliente(Cliente cliente): void
  + realizarCompra(String cpf, List~String~ nomesProdutos): void
  + getProdutos(): List~Produto~
  + getClientes(): List~Cliente~
  + getCompras(): List~Compra~
}

Produto "1" --> "*" Compra : contém
Cliente "1" --> "*" Compra : realiza
SistemaECommerce "1" --> "*" Produto : gerencia
SistemaECommerce "1" --> "*" Cliente : gerencia
SistemaECommerce "1" --> "*" Compra : gerencia
```

Adicionada a classe Compra para representar as compras realizadas.

O sistema agora gerencia compras e permite associar clientes e produtos às compras.



## Entrega Máxima
```mermaid
classDiagram
class Produto {
  - String nome
  - double preco
  - int quantidade
  + Produto(String nome, double preco, int quantidade)
  + getNome(): String
  + getPreco(): double
  + getQuantidade(): int
  + setQuantidade(int quantidade): void
}

class Cliente {
  - String nome
  - String cpf
  - String email
  + Cliente(String nome, String cpf, String email)
  + getNome(): String
  + getCpf(): String
  + getEmail(): String
}

class Compra {
  - Cliente cliente
  - List~Produto~ produtos
  + Compra(Cliente cliente, List~Produto~ produtos)
  + getCliente(): Cliente
  + getProdutos(): List~Produto~
}

class SistemaECommerce {
  - List~Produto~ produtos
  - List~Cliente~ clientes
  - List~Compra~ compras
  + cadastrarProduto(Produto produto): void
  + cadastrarCliente(Cliente cliente): void
  + realizarCompra(String cpf, List~String~ nomesProdutos): void
  + getProdutos(): List~Produto~
  + getClientes(): List~Cliente~
  + getCompras(): List~Compra~
  + getComprasPorCliente(String cpf): List~Compra~
}

Produto "1" --> "*" Compra : contém
Cliente "1" --> "*" Compra : realiza
SistemaECommerce "1" --> "*" Produto : gerencia
SistemaECommerce "1" --> "*" Cliente : gerencia
SistemaECommerce "1" --> "*" Compra : gerencia
```

Expansão da funcionalidade de compras.

Adicionado o método getComprasPorCliente para consultar compras específicas de um cliente.

Regras de negócio como validação de estoque e redução de quantidade são implementadas no método realizarCompra.
