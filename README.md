# Aplicação do processo seletivo Facilit Tecnologia

## Considerações

- Utilizei SpringBoot para mapeamento de rotas e serviço HTTP;

- Inicialmente iria fazer a persistência completamente em memória, no entanto, optei por SQLite. Deixei o caminho para a base fixo no código mesmo, não criei arquivo de configuração para mudar o local de armazenamento do banco de dados;

- Optei por deixar o carrinho de compras em memória (sem persistência na base) uma vez que observei que em grandes varejistas o carrinho de compras não é compartilhado entre dispotivos, é vinculado ao seu browser/cookies;

- Como é possível observar nas migrations, criei apenas duas tabelas (cupom e produto). A ideia inicial era que houvessem mais duas tabelas, carrinho e carrinho_produto, com suas respectivas chaves estrangeiras. No entanto eu também teria de vincular um carrinho para um determinado usuário, iria acrescentar ainda mais tabelas na base, fugindo do escopo/proposta inicial. Tudo isso somando ao que apontei no tópico anterior, preferi deixar dessa forma mais simplista;

- Não utilizei nenhum framework (JDBC/Hibernate) para gerenciamento da base de dados uma vez que as consultas são poucas e bastante simples;

- Tentei demonstrar também meu domínio em orientação a objetos durante a construção deste projeto.

## Grato

Luan Augusto Xavier de Oliveira