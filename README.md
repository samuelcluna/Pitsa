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
  
<img src="https://lh3.googleusercontent.com/pw/ADCreHd347b7erqfR80IiQefd4lWHO_BVglLxzdyLbA_h0fZE0YYcaeZis2EPqFm54bJsRtsHy6Agxe0E1zGcPbc31XIXSlygjQ0joX92doiEX2L6pW0Wr7fMPGS9RydKxGR98UmKXruSSVyeAaJ_3pbPdtbyAQQZFUclyrPhF9RcEkT_iZsYRhPZUvtjlrsEgomN5sWYxmJGej_WDoZthx3olWc9h83fsT7NOtGECdUj_IDl-Fu51QncijtOWN75M4k-dHKmTgdIvn0lZw9jUOAGMgzaPn6gpqN1FajafyfbkrP5ogxE35KVKdcJEOFzO0YVlQeLy0GbFwilOEdDSqHUtO4LxftVo0b9zCqmVgFWMqd42gZKL0XWWeNDqhVjoCe5OO7cYS6GwshXYUV0uRV_m7Am6fiUJeSD4szvdiLakOm9mhyrNm4XC7OjaXOm2134b4EpYeW3DIiCGj3QStA1ZI2FotKGyxbiajve3MzbYkt6lT9zAAYWz4zgUnrYw7GvlQKkeSOBif0a_IqtB_NzlDH4Ni28ltW6R-7x01HOF5V5HC2AHy1LaX5wEGpFwjM8_HWOaj1SOVp1Obvkog0GDbzsyyrddUfkvl4ZltdQt1H6JwPhcXyaarUrVdCmo9fZS5uwED4JV_zZcXj7MlmqusiM9IaI-b-F3VZVgwYYbLNFRg5FGri8lgOWveel9vTYemsF2oFZdUAtYBdHMsD4UIXbKyXK3rXlbOnGNLaMypNvATviq6r1sGCnQ621VIegxj745S9fF_EFw574dwNod_pYWBWDzptfFXlvJmOqSXYiaQXJstBTcBpuhwGFup64rHKhOwrun80oewjIHMYO3UkvhhhXRpJBGdv0zh__SXn3blV88P2gYXPB2XPoiAKFGASPCYxi2NxCQfYzOkX0DG4rMZp7dldVLX7qhJYGw4a5zPS8AvckgOb6dsqBXXe3MhFVpUNlP8AVAAXs5DLu60HFxcxvkv02Q=w936-h655-s-no?authuser=1"/>

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

| [<img src="https://lh3.googleusercontent.com/pw/ADCreHdTiKf0t3_Ag98vXUzdPnaJzLj92cd4fLMCBxzWyt0e1c5Z5JRgbi_2gX_ZpWoKsj0X_9Z6V2Ke6dz3kWzzPZUm33mt95zUByKN5qWMAdULjF-7o7RNHYnhBz_u4jJKv64YyjLqh7PNAvbDu2lnCEpxzBw4AsaCds0Qd6mY3SFJ62Ac5wzCRNCRrkATENAuLdByGaYSx7Qko7FarCM42f_EVzEWpOWka-M291VmsCSlUmM6Dgr-Yd4UMFnKIe-_KzhRK3MG0_EWrG0XK1CMTG8YejOxZzdRtoBkVryXTnR3ilL7FPzY9y1BaUsmWYcYybX2YTB7zQhlli4brvrNkY0T5tPBgqKas4OHG22Y5YmYQw7K7QH3phUOsJAjU6_ZV3ME1sIVl9pEHuW2UUyjhO-i7I2Z1mNGOVOWXFuflVQUZfrLGwL9uckW4ekJnIp_55QriaZaarlqUmFibzCt4IpXo8SWudnWHfQ_yExmJ_NwiQyMaXNCRhUaoRfFz_rX19yqol0_SVngbCJmIBQaNG4bzTsLrg2vLUf8_F8B7enFwMK8ax6zgYI_foS0Pj-hNEW1xgOruEOPZL8DHDn8C_g6f0hQF4b3krNvYYUoCLXnzUcSyLOzAHH_r9ezlDQXhMmk6i8DjiNHq83KDYd04ZBWiHLb6jq-llxf7_7T2fPfKkxemNkfxGy5ACAQBEfzBGEt2YbGwpx1F7g6CKt08RSb_Juk-5o09MP8q9hXFCV3UdKzWB6Ky2nNi2NDraY0yhrL4E96egOS2D3KmJVWytysz3wKwFqW1UtpiULAPGNffD3ncd-4ko_Tv3Q55kmDlErSsXyG9i22VSMfyFwfbrgIkDiplFmhQYNFBZ9wfGBs2pWH3vo79YqzndYfrTFWuXlL-moEqqT_5rHW6K7D0FfeLMYQyNc-JGThbsuYfADzboeJj4IcbkeZw178oz00qVlyKrWnhNdL4uLL1nnZiTdnAxHZBVDANg=w608-h608-s-no?authuser=1" width="120px;" /><br /><sub><b>Samuel Cabral</b></sub>](https://github.com/samuelcluna)<br /> | [<img src="https://lh3.googleusercontent.com/pw/ADCreHcHAtbmVr44OJxIhwfFncPTsTXeIB7Av3GoC3U1dv_MWuiOkP0hRLzQJOEtsJfdNl2IqVvWbYd0ZVQK1cFUz1eGmWuUQaMBmsXPFvdydgJDVg5E-SpLRBlJdqT0g-hIPt-1S8FM83LJ1DlATFb9cqezn9UerILCxoXLbUK8XqvViUXuKLosrJs6JX1fjc1RnMd3BbBlDafNNFwgrRWRxTA4Sc5hoBR4vh-yJ7J8s8C5EbDT_Y0Sgzx9f5L4cG-yTxvnl6jC-jyEGl_ijyM71xzKNpMzgWgYGCTRWUuHWEglhZwB_zzwJDEDC7Tc0xldpWRVs69TRfr-OwzXA2Z92YYmtkYrEQRAaoozWqS-0TUkURlA-UjciUGJ9snHuHAIO__wgzGfZunZcGjaLHGVPRt5IMhq9j5x8jJ6znOMG6tM5GszFZjf7-tap0LgVMpqK5jEGlgio3EMzhFxybAWQNiAhno7IeaAxfnIZp_gXTz9gFk0jiMbIcZ63z1gxBsvoAwyPwcbTeka0O7Y4Y34bEu6Kb7fril4h0LC6dLBPDcNTZJ3lQhI8gu_Czt5VvEuoDj_ZQc3PP4x6Cb1iFoDGfA3teV36OuixNju6hou-XLIu2ZvtjcX5WS7SBw2zHIwr4QGuq3dPtslyS9jqiOHO8dzQPYKnNrKQeF03V7hpjpo59RCkzGY6qQ-1sg-9f8k7lKBFkxkmvCI5N07iXH2ANZ1AvrGCF0sx9rpIB0mVH-YurUbr6RjbHAZj5oW1GlonP-ry4uFUKoebeRMdjCX9jFl7hzmCWY3kl4nAomqwtewJz8ikRVB1hyfMCfayLA-2-kX8ZDnk6Qz_pYIWsW-mf4v0L6k3rO7h2D26ha2jjE_E-OIupGg-LKIeqalbMLfMjRZCmtBZ_MYsa3UJ0mlc28Lh6KZsbmk0bphFDrIn5l1MSDvCmezyU9i5_zdXPrAGBU_aIMECexI6tktOOrdXjssuXXw_7laeQ=w349-h349-s-no?authuser=1" width="120px;"/><br /><sub><b>Marcos Antônio</b></sub>](https://github.com/W00kyz)<br /> | [<img src="https://lh3.googleusercontent.com/pw/ADCreHe6lFXDrD4kyMHFdeIuea14wraTXp96OvTN45I1jqhuahkOWywG-jKwCI990axFDUfFdujI8jWlbuhqtAwccvrFFNQHH8JmYa-YB5UO8By2Pen97wttrqnvbfk8D0ifjRMXu0D2q4qu2MgR5g4JMypaQV5JUGay3dsBLV7oy3k0DonPLM6zA0st1kJENNUGzRjxh9U71EFtT9yh1QhekYiMUeSvxpweW9Blu9lFhCC4KNY3sEiHrR6qYmNd443AtSROcr8rouWqeB-2fL8GYuCDgHFN88K6ZZhs3sdGj1DqtsbtgjLDWopm1CG6eQWngjBhoeplvLnseNhGIBFulgC4OlxbF3e-2sIAZJrhPl2LNjwnNWU91mz9rG2FCef1G_bKOuVYXUX6uJW6Qr_kjk-glbe5DEuQWqgzdUw801_c9K7aJxdrlQ2bpKysyoGdAbegKKFNXvYJvx3SmqZ1T8CWQuuQjZlB_8MMlFlnWsEFNrCzsgPknVfAO4FkI-Q9x_ANsjAJOy7gM68IP7Hztc2tKGy8Od1sWOlKsaebER7fhAjNaaiCe66CD2-g_ztPYTUGGGf68EJRYZQMhPzHjhS4WXvVpQba38CCKt_RihtMnQNOkQ4wIZVnqUOnpkxjKi2hzcaJKVHqT9eKk33ponu_o94eBUP2PL151C_7dQ4e6kSCnQyIs9F7Cic6LS2-FmXp416wrSjGb3Shvhvukyqr8RAiA9V_DUOtwqQrSg267e_YeEjqLvYJVkvICF31CWlSh6GRtIeUmLpkf-f6hs8SXgRx5Y_lgomW5wgv0Br9dp8xQdCgwHYcHhBeEj_8wUpW2aKAjjXd9wotcdLfBAgnnMVgfnmHXoKswd1Cy8wMbNt_D43aQgOMRk3oESXZ1FvBcUCoaaEldQOGB9_DQC2KJVXB-MyAX2Zgh_02yc62KX-CYkbwmurwWv1asZK7zC1drWriQ4Myg51kazKN7iDTB4qGk3X8KA=w656-h656-s-no?authuser=1" width="120px;"/><br /><sub><b>Paola Moura</b></sub>](https://github.com/paolamoura)<br /> | [<img src="https://lh3.googleusercontent.com/pw/ADCreHfMJq4Dr_ILMqPJzb-EwiRxwhC0CwAV4gIiYQ5VGl4FPAjgRMohZPDHumTjjmqTg_blEf8bbiQV5ewadhIDL8FvQMyY7JDt3nsvwTLUBmkZgQPznM5OCjvvXUOz85i8FWk7lyULvkVoClNoWvQINiVN-xOAiB8Qe0B5aybjMaz88F4f6Fsef-dhD8iCGAzos2EJ_drfXnModdG6nHVymH0gMyhe7YB4jiA0uLtK3156Nhww0ybq3hEPf6xwn5AjJGvECXiLpSFhIfDgxZKS1Elqfk0OX5R1p7_HrwIWgAR_JFNHK37VOzqIraFkBeFrfFmXdI99Bmb9K6g3LtLdkAe8g2GkcmoB_mRX8ZZmVYJKfc8wNo2Q-tq-npdoRmrkoiEBZ1ZaDJqSITu3EFhCybMBnyE1xHjoCI6EsiJFy8IpF2Aaf5EW2byL6rM58jC0RQGNbg-P0Cfngal3SKYNgeLSr-mPR2kSBty6TrrxwMNXcMsdfB62kh1IwjGSkqmJiJGIDfB8JNQJNrm6EYZfBB5yGP2ePB2FiECIRamTES-capS6iCPiEx9iQ4mmPyM9ON6VN710YyPVi7KWHylEl8ofAEooEXjsRkVG_x6wFWh31IHLCUpVzJGpoLnkh_P_i6QtKlNKPJH2B6z1oRu9Tt878XEQkfWkIVwAK8RKHBqF2hxeh4h7IWaiQakKJ-yB1P9piTULTIomUuI7-J1vxIMaJ1J5pP4nlxGjWc9JlzfznRs3hx9QXSS4UpdIla90uHphFZjMDSBadMVCiWviNWXy9mOz3OLJv2NXKu7rFxUzKs-bWOqjVJdi5FGqYvud3EScwyfpnlDmV-OG0NWbBXcjdqf9AuGSWVo9plXswGIehPKXEx3T7-E2xekCI0sETgQ_ijYbsEjF1_SHpKZHrgEMlMOCAv2UFdENoBpV_Iuwq3VyWpRsX2qsr_8Q4xYGVyfM12XOH3LWYMyx2QaE-fGAm--uH3zqoA=w437-h437-s-no?authuser=1" width="120px;"/><br /><sub><b>Victor Freire</b></sub>](https://github.com/VictorFreir)<br> | [<img src="https://lh3.googleusercontent.com/pw/ADCreHfhEfUpYY4h8DFwS1XitDZK97Mr684QiBRkYCAiGSiBsFFdB4BGekNPYdgZEdlWMt3M6vP5keaPktjbDcc-DauyOjbEftBBw7mCDK3QrUHXEu70D2ytMO84gzFTtm0XY81KBVloAgP9z3WWv5SGArAx0duuphugQEiaQ-TVPpLqT1MewXHTr_CKJGmarOLJgnALxqAFhibsgiyDZNMhSU0bPxnTzRYb1z-0WM-NDdky6GMZdDdEF3LPA4NkWoE2rXzQ5uevq4ZgD8rCk4MkiHXvT_7zKMcDccSrjU33PDL6y9OnDvHx59bE_oz8PD7lPy1jDIWK-ykR7wVkiRt8vLBf9liDKXvEBs7I2BpcuIwFw5cv5dZqEiDYo2WuDudVeD6JcAFBT585jvlOE3u9sZztYz7ZXLYyXUpivvtNsuC-5JJxWvFAqkUJxxy5U7ztuW19JVn2OeWwiqmjBbPBzWIQfJNKXcQGHjMUr2MQGNQowrCyGQAZQzEUnRUGqx518wv5sVBdh3y5ya_kbSQLXBhF-aHEzkMAZPt7px0SThcjsDQtJwrU82eNO9PPYRCJv5KSh3z5SBgqbLgKgGOpjGPxKzMQslrEpMCAagZ7Hjf7-TW2X5tZuxxLMQX6ZuQAZZPPXa7GvEYhzC68LXKWUhzhPHypBnZ-VaWpFZ9sIswZ5lUrFDzliPFyuJHWHptB-MAQLj4XX91iNLypzE36E3aLxSmCL__fOi4PXqeLcNTguXR4hUG75eV0VlTQAIljrxBunjsafQKKu-0hrWcLOS8AUN7Dr8YeY7VWgspF7gGZG2q9Y91lyppJuQO11wQJgLaeM8KmKoXpzkOELWI-OVbbKe8cD--i-erffK24H5ZKz166ojRvLxX-OxfnUKhzFtWfg_geooOKO6gzj6NUNGhkumsfHXimuQvTzceU-IP1L2FjBZa25madwtq9Qf1sinuC5VaPVY2N4QM-jlBm7S9U64TSg3rREg=w608-h608-s-no?authuser=1" width="120px;"/><br /><sub><b>Gabriel Guimarães</b></sub>](https://github.com/Gaabrielg1)<br /> |
| :---: | :---: | :---: | :---: | :---: |

</div>
