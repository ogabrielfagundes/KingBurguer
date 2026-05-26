# 🍔 KingBurguer App

> Aplicativo Android nativo desenvolvido para consolidação de arquitetura moderna, clean code e desenvolvimento de interfaces declarativas.

O **KingBurguer** é um aplicativo de simulação de fast-food (hamburgueria) que permite aos usuários navegar por categorias de produtos, visualizar detalhes, gerenciar cupons de desconto e acessar perfis de usuário. O projeto tem como foco principal a aplicação de boas práticas de Engenharia de Software, escalabilidade e separação clara de responsabilidades.

---

## 🛠 Tecnologias e Arquitetura

Este projeto foi construído seguindo as diretrizes oficiais mais recentes do Android, utilizando **Kotlin** e focando em uma arquitetura robusta e testável.

* **Jetpack Compose:** Construção de UI 100% declarativa, garantindo reatividade e menos código.
* **Arquitetura MVVM (Model-View-ViewModel):** Separação estrita de responsabilidades. A UI apenas reage às mudanças de estado emitidas pelas ViewModels via `StateFlow`.
* **Retrofit & OkHttp:** Implementação de clientes HTTP estruturados para consumo da API RESTful, garantindo chamadas de rede eficientes e interceptação de requisições.
* **GSON:** Serialização e desserialização de objetos JSON para as Data Classes do Kotlin.
* **Coil:** Biblioteca moderna e leve suportada por Kotlin Coroutines para download, cache e exibição assíncrona de imagens via URL.
* **Jetpack Navigation Compose:** Gerenciamento do fluxo de navegação entre telas, passando parâmetros de forma segura e controlando a pilha de navegação.

---

## 🏗 Componentização

Para garantir a consistência visual e a facilidade de manutenção, o projeto conta com um pacote exclusivo de componentes base.

Em vez de repetir a estilização nativa, foram criados componentes customizados que encapsulam regras de negócio e design:
* `KingButton`: Botão padrão com suporte a estado de carregamento e desativação condicional.
* `KingAlert`: Caixa de diálogo padronizada para exibição de erros, avisos e confirmações de ações.
* `KingTextField`: Campos de entrada reativos com suporte a validação visual de erros, mascaramento de senhas e transições de teclado.

---

## 📱 Funcionalidades

1. **Autenticação e Cadastro:**
   * Formulários reativos com validação de campos.
   * Gerenciamento de estado complexo garantindo que o botão de envio só seja habilitado com dados válidos.
2. **Catálogo de Produtos:**
   * Listagem horizontal de categorias aninhada em uma rolagem vertical principal, otimizando o consumo de memória.
   * Destaque de produtos promocionais no topo.
3. **Detalhes do Produto e Cupons:**
   * Exibição aprofundada das informações do produto selecionado.
   * Geração dinâmica de cupons de desconto.
   * Tela dedicada para listagem e filtragem de cupons.
4. **Perfil do Usuário:**
   * Recuperação de dados mockados do servidor.
   * Formatação e mascaramento de dados sensíveis na camada de apresentação.

---

## 🚀 Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/ogabrielfagundes/KingBurguer.git
