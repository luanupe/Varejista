# Aplicação do processo seletivo Facilit Tecnologia

## Considerações

- Utilizei SpringBoot para mapeamento de rotas e serviço HTTP;

- Optei por deixar todos os métodos GET, nenhum POST, para testar mais facilmente pelo navegador;

- Inicialmente iria fazer a persistência completamente em memória, no entanto, optei por SQLite. Deixei o caminho para a base fixo no código mesmo, não criei arquivo de configuração para mudar o local de armazenamento do banco de dados;

- Optei por deixar o carrinho de compras em memória (sem persistência na base) uma vez que observei que em grandes varejistas o carrinho de compras não é compartilhado entre dispotivos, é vinculado ao seu browser/cookies;

- Como é possível observar nas migrations, criei apenas duas tabelas (cupom e produto). A ideia inicial era que houvessem mais duas tabelas, carrinho e carrinho_produto, com suas respectivas chaves estrangeiras. No entanto eu também teria de vincular um carrinho para um determinado usuário, iria acrescentar ainda mais tabelas na base, fugindo do escopo/proposta inicial. Tudo isso somando ao que apontei no tópico anterior, preferi deixar dessa forma mais simplista;

- Não utilizei nenhum framework (JDBC/Hibernate) para gerenciamento da base de dados uma vez que as consultas são poucas e bastante simples;

- Não realizei seed no banco de dados, quando a aplicação for executada, a base de dados estará vazia, no arquivo Database existe um método que descomentando a chamada do mesmo (linha 30) alguns dados padrões serão inseridos automaticamente;

- Não é interessante ter uma rota que irá listar todos os cupoms (e também os produtos sem filtros) no entanto, mantive a rota para testes;

- Mesmo não sendo indicado utilizar os tipos nativos float e double para aplicações monetárias devido ao nível de precisão, para este exercício utilizei float tanto pela praticidade (ao invés das classes específicas) e pelo suporte do SQLite;

- Uma vez que não há necessidade de validar o preço do carrinho e o preço do cupom (nem mesmo um valor mínimo para o cupom) é possível o carrinho ser zerado e a compra sair de graça; 

- Tentei demonstrar também meu domínio em orientação a objetos durante a construção deste projeto.

## Grato

Luan Augusto Xavier de Oliveira