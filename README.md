# 🍕 Pits A

Recentemente, diversas empresas do ramo alimentício têm se desvinculado dos grandes aplicativos de delivery. As causas
dessa tendência são diversas e vão desde a transformação no modo de operação de cada estabelecimento, até as taxas
abusivas das grandes plataformas.

Porém, em 2023, simplesmente não é viável voltar ao modo de trabalho “pré-Ifood”... Foi por isso que a pizzaria Pits A
decidiu desenvolver seu próprio aplicativo de delivery. E adivinha só… vocês foram escolhidos para ajudar!

### Endereços Úteis

- [Swagger](http://localhost:8080/swagger-ui/index.html)
- [H2 Console](http://localhost:8080/h2-console)

## 1ª Etapa: USs 01-07

- *Descrição das participações, critérios utilizados e modelagem do sistema: [Link para o documento da etapa 1](https://docs.google.com/document/d/1i-knQ6T3lSrhleyF5Rj7yopdhAIhdrUBjccU0psZPcQ/edit)*

- [x] **US1:** Eu, enquanto administrador do sistema, quero utilizar o sistema para criar, editar e remover um
  estabelecimento;
- [x] **US2:** Eu, enquanto cliente, quero utilizar o sistema para me cadastrar como cliente do sistema. Mais
  detalhadamente, deve ser possível criar, ler, editar e remover clientes;
- [x] **US3:** Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me cadastrar como entregador(
  a) do sistema. Mais detalhadamente, deve ser possível criar, ler, editar e remover entregadores;
- [x] **US4:** Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me associar como entregador(a)
  de um estabelecimento;
- [x] **US5:** Eu, enquanto estabelecimento, quero utilizar o sistema para aprovar ou rejeitar entregadores do
  estabelecimento;
- [x] **US6:** Eu, enquanto estabelecimento, quero utilizar o sistema para o CRUD dos sabores de pizza vendidos pelo
  estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover sabores;
- [x] **US7:** Eu, enquanto cliente, quero visualizar o cardápio de um estabelecimento;

## 2ª Etapa: USs 08-11

- *Descrição das participações, critérios utilizados e modelagem do sistema: [Link para o documento da etapa 1](https://docs.google.com/document/d/1i-knQ6T3lSrhleyF5Rj7yopdhAIhdrUBjccU0psZPcQ/edit)*

- [x] **US8:** Eu, enquanto cliente, quero utilizar o sistema para fazer pedidos de pizza a um estabelecimento. Mais
  detalhadamente, deve ser possível criar, ler, editar e remover pedidos;
- [x] **US9:** Eu, enquanto estabelecimento, quero modificar a disponibilidade dos sabores do cardápio. Mais
  detalhadamente, deve ser possível visualizar e editar a disponibilidade dos sabores de pizza — dado que, nem sempre,
  todos os produtos estão disponíveis;
- [x] **US10:** Eu, enquanto cliente, quero demonstrar interesse em sabores de pizza que não estão disponíveis no
  momento;
- [x] **US11:** Eu, enquanto estabelecimento, quero disponibilizar diferentes meios de pagamento para os pedidos, tal
  que cada meio de pagamento também gere descontos distintos;

## 3ª Etapa: USs 12-17

- *Descrição das participações, critérios utilizados e modelagem do sistema: [Link para o documento da etapa 1](https://docs.google.com/document/d/1i-knQ6T3lSrhleyF5Rj7yopdhAIhdrUBjccU0psZPcQ/edit)*

- [x] **US12:** Eu, enquanto estabelecimento, quero que o sistema garanta a corretude nas mudanças de status dos
  pedidos;
- [x] **US13:** Eu, enquanto cliente, quero ser notificado(a) quando meus pedidos estiverem em rota e, por medidas de
  segurança, quero ser informado(a) com o nome do(a) entregador(a) responsável pela entrega e os detalhes sobre seu
  veículo. A notificação deve ser representada como uma mensagem no terminal da aplicação (print), indicando o motivo e
  quem está recebendo a notificação;
- [x] **US14:** Eu, enquanto cliente, quero ser responsável por confirmar a entrega dos meus pedidos;
- [x] **US15:** Eu, enquanto estabelecimento, quero ser notificado(a) sempre que o status de um pedido for modificado
  para “Pedido entregue”. A notificação deve ser representada como uma mensagem no terminal da aplicação (print),
  indicando o motivo e quem está recebendo a notificação;
- [x] **US16:** Eu, enquanto cliente, quero ter a possibilidade de cancelar um pedido que fiz no estabelecimento;
- [x] **US17:** Eu, enquanto cliente, quero poder verificar os pedidos que já realizei no estabelecimento;


## 4ª Etapa: USs 18-20

- *Descrição das participações, critérios utilizados e modelagem do sistema: [Link para o documento da etapa 1](https://docs.google.com/document/d/1i-knQ6T3lSrhleyF5Rj7yopdhAIhdrUBjccU0psZPcQ/edit)*

- [x] **US18:** Eu, enquanto funcionário(a) terceirizado(a), desejo definir se estou disponível (ou não) para realizar
  as entregas do estabelecimento;
- [x] **US19:** Eu, enquanto funcionário(a), gostaria que o sistema atribuísse automaticamente as entregas dos pedidos
  com status “Pedido Pronto” a um(a) entregador(a) que esteja disponível para realizar entregas;
- [x] **US20:** Eu, enquanto cliente, quero ser notificado(a) quando meu pedido não puder ser atribuído para entrega
  devido à indisponibilidade de entregadores. A notificação deve ser representada como uma mensagem no terminal da
  aplicação (print), indicando o motivo e quem está recebendo a notificação;

## Bônus

- [x] Envio de notificação por E-mail.
  
## Diagrama UML

<div align="center">
  
<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/modelagem.png" width="750px;"/>

</div>

## Avaliação

- [x] Quantidade, complexidade e corretude das user stories implementadas.
- [x] Decisões de design tomadas e implementadas durante o desenvolvimento. Por exemplo, o uso adequado de arquiteturas e padrões de projeto.
- [x] Qualidade do código desenvolvido. Por exemplo, o uso de princípios de projeto, a atribuição de responsabilidades e a presença de bad smells no código.
- [x] Cobertura e qualidade dos testes automáticos desenvolvidos, abordagem funcional, com a correta utilização do MockMvc aplicados aos endpoints no formato REST para todos os membros da API disponibilizada.
- [x] O sistema web deverá utilizar o framework Spring Boot.
- [x] A cobertura dos testes para as classes de serviço e controladores deve ser de 100% (cem por cento), a fim de que fique evidenciada a prática de TDD.

- *Ao atingirmos 100% de êxito em todas as etapas, a nota final do projeto foi 10,00.*

## Equipe

<div align="center">

| [<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/samuel-cabral.png" width="120px;" /><br /><sub><b>Samuel Cabral</b></sub>](https://github.com/samuelcluna)<br /> | [<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/marcos-antonio.png" width="120px;"/><br /><sub><b>Marcos Antônio</b></sub>](https://github.com/W00kyz)<br /> | [<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/paola-moura.png" width="120px;"/><br /><sub><b>Paola Moura</b></sub>](https://github.com/paolamoura)<br /> | [<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/victor-freire.png" width="120px;"/><br /><sub><b>Victor Freire</b></sub>](https://github.com/VictorFreir)<br> | [<img src="https://github.com/samuelcluna/Pitsa/blob/master/images/gabriel-guimaraes.png" width="120px;"/><br /><sub><b>Gabriel Guimarães</b></sub>](https://github.com/Gaabrielg1)<br /> |
| :---: | :---: | :---: | :---: | :---: |

</div>
