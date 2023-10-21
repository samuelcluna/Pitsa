package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import com.ufcg.psoft.commerce.repository.*;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
public class PedidoControllerTests {
    final String URI_PEDIDOS = "/pedidos";

    @Autowired
    MockMvc driver;

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    AssociacaoRepository associacaoRepository;
    @Autowired
    ModelMapper modelMapper;

    ObjectMapper objectMapper = new ObjectMapper();
    Cliente cliente;
    Entregador entregador;
    Sabor sabor1;
    Sabor sabor2;
    Pizza pizzaM;
    Pizza pizzaG;
    Estabelecimento estabelecimento;
    Pedido pedido;
    Pedido pedido1;
    PedidoPostPutRequestDTO pedidoPostPutRequestDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("654321")
                .email("victorfreire@gmail.com")
                .build());
        sabor1 = saborRepository.save(Sabor.builder()
                .nome("Sabor Um")
                .tipo("salgado")
                .precoM(10.0)
                .precoG(20.0)
                .disponivel(true)
                .build());
        sabor2 = saborRepository.save(Sabor.builder()
                .nome("Sabor Dois")
                .tipo("doce")
                .precoM(15.0)
                .precoG(30.0)
                .disponivel(true)
                .build());
        cliente = clienteRepository.save(Cliente.builder()
                .nome("Anton Ego")
                .endereco("Paris")
                .email("victorvfreire@gmail.com")
                .codigoAcesso("123456")
                .build());
        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Joãozinho")
                .placaVeiculo("ABC-1234")
                .corVeiculo("Azul")
                .tipoVeiculo("Moto")
                .codigoAcesso("101010")
                .build());
        pizzaM = Pizza.builder()
                .sabor1(sabor1)
                .tamanho("media")
                .build();
        pizzaG = Pizza.builder()
                .sabor1(sabor1)
                .sabor2(sabor2)
                .tamanho("grande")
                .build();
        List<Pizza> pizzas = List.of(pizzaM);
        List<Pizza> pizzas1 = List.of(pizzaM, pizzaG);
        pedido = Pedido.builder()
                .preco(10.0)
                .enderecoEntrega("Casa 237")
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimento.getId())
                .entregadorId(entregador.getId())
                .pizzas(pizzas)
                .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO)
                .data(LocalDateTime.now())
                .build();
        pedido1 = Pedido.builder()
                .preco(10.0)
                .enderecoEntrega("Casa 237")
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimento.getId())
                .entregadorId(entregador.getId())
                .pizzas(pizzas1)
                .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO)
                .data(LocalDateTime.now())
                .build();
        pedidoPostPutRequestDTO = PedidoPostPutRequestDTO.builder()
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .pizzas(pedido.getPizzas())
                .build();
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        pedidoRepository.deleteAll();
        saborRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
    class PedidoVerificacaoFluxosBasicosApiRest {

        @Test
        @DisplayName("Quando criamos um novo pedido com dados válidos")
        void quandoCriamosUmNovoPedidoComDadosValidos() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(post(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteId", cliente.getId().toString())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", estabelecimento.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())// Codigo 201
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.PedidoResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(), resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo pedido com código de acesso inválido")
        void quandoCriamosUmNovoPedidoComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // Act
            String responseJsonString = driver.perform(post(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteId", cliente.getId().toString())
                            .param("clienteCodigoAcesso", "999999")
                            .param("estabelecimentoId", estabelecimento.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())// Codigo 201
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);
            // Assert
            assertEquals("Código de acesso inválido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando criamos um novo pedido sem endereço de entrega")
        void quandoCriamosUmNovoPedidoSemEnderecoEntrega() throws Exception {
            // Arrange
            PedidoPostPutRequestDTO pedidoRequest = PedidoPostPutRequestDTO.builder()
                    .enderecoEntrega(null)
                    .pizzas(pedido.getPizzas())
                    .build();
            // Act
            String responseJsonString = driver.perform(post(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteId", cliente.getId().toString())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", estabelecimento.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoRequest)))
                    .andExpect(status().isCreated())
                    .andDo(print())// Codigo 201
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.PedidoResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertNotEquals(pedidoRequest.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoRequest.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(), resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando alteramos um novo pedido com dados válidos")
        void quandoAlteramosPedidoValido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            Long pedidoId = pedido.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", pedido.getId().toString())
                            .param("codigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.PedidoBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(pedidoId, resultado.getId().longValue()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(), resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando alteramos um pedido inexistente")
        void quandoAlteramosPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", "999999")
                            .param("codigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um pedido passando codigo de acesso invalido")
        void quandoAlteramosPedidoPassandoCodigoAcessoInvalido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", pedido.getId().toString())
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca por todos seus pedidos salvos")
        void quandoClienteBuscaTodosPedidos() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);
            pedido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);
            pedido1.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);
            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/clientes/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido seu salvo pelo id primeiro")
        void quandoClienteBuscaPedidoPorId() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/" + pedido.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(), resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido seu salvo por id inexistente")
        void quandoClienteBuscaPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/999999")
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido feito por outro cliente")
        void quandoClienteBuscaPedidoDeOutroCliente() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            Cliente cliente1 = clienteRepository.save(Cliente.builder()
                    .nome("Catarina")
                    .endereco("Casinha")
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/clientes/" + cliente1.getId() + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente1.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Código de acesso inválido!", resultado.getMessage());
        }


        @Test
        @DisplayName("Quando um estabelecimento busca todos os pedidos feitos nele")
        void quandoEstabelecimentoBuscaTodosPedidos() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId())
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id primeiro")
        void quandoEstabelecimentoBuscaPedidoPorId() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId() + "/" + pedido.getId())
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(), resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id inexistente")
        void quandoEstabelecimentoBuscaPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId() + "/" + "999999")
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoBuscaPedidoDeOutroEstabelecimento() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            Estabelecimento estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/" + pedido.getId())
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido para esse estabelecimento não foi encontrado!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente exclui um pedido feito por ele salvo")
        void quandoClienteExcluiPedidoSalvo() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente exclui um pedido inexistente")
        void quandoClienteExcluiPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente exclui todos seus pedidos feitos por ele salvos")
        void quandoClienteExcluiTodosPedidosSalvos() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedidoRepository.save(Pedido.builder()
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .build());

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/clientes/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um estabelecimento exclui um pedido feito nele salvo")
        void quandoEstabelecimentoExcluiPedidoSalvo() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId() + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um estabelecimento exclui um pedido inexistente")
        void quandoEstabelecimentoExcluiPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId() + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelecimento exclui um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoExcluiPedidoDeOutroEstabelecimento() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            Estabelecimento estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Código de acesso inválido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí todos os pedidos feitos nele salvos")
        void quandoEstabelecimentoExcluiTodosPedidosSalvos() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedidoRepository.save(Pedido.builder()
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .build());
            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                    )
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido")
        void quandoClienteCancelaPedido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/cancelar-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido com código de acesso inválido")
        void quandoClienteCancelaPedidoCodigoAcessoInvalido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/cancelar-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", "121212"))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente tenta cancelar um pedido que já está pronto")
        void testCancelamentoPedidoPronto() throws Exception {
            // Arrange
            pedido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_PRONTO);
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/cancelar-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Pedidos que ja estao prontos nao podem ser cancelados!", resultado.getMessage());
        }

//


    }

    @Nested
    @DisplayName("Cliente listar com filtragem")
    public class ClienteListarPedidosFiltrados{

        Pedido pedido_recebido;
        Pedido pedido_em_preparo;
        Pedido pedido_pronto;
        Pedido pedido_em_rota;
        Pedido pedido_entregue;
        Estabelecimento estabelecimento1;

        @BeforeEach
        void setUp(){
            estabelecimento1 = Estabelecimento.builder()
                    .codigoAcesso("131313")
                    .sabores(List.of(sabor1, sabor2))
                    .email("victorvfreire@gmail.com")
                    .build();
            estabelecimentoRepository.save(estabelecimento1);
            pedido_recebido = Pedido.builder()
                    .clienteId(cliente.getId())
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_RECEBIDO)
                    .data(LocalDateTime.now())
                    .build();
            pedidoRepository.save(pedido_recebido);
            pedido_em_preparo = Pedido.builder()
                    .clienteId(cliente.getId())
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO)
                    .data(LocalDateTime.now())
                    .build();
            pedidoRepository.save(pedido_em_preparo);
            pedido_pronto = Pedido.builder()
                    .clienteId(cliente.getId())
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento1.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_PRONTO)
                    .data(LocalDateTime.now())
                    .build();
            pedidoRepository.save(pedido_pronto);
            pedido_em_rota = Pedido.builder()
                    .clienteId(cliente.getId())
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento1.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA)
                    .data(LocalDateTime.now())
                    .build();
            pedidoRepository.save(pedido_em_rota);
            pedido_entregue = Pedido.builder()
                    .clienteId(cliente.getId())
                    .preco(10.0)
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento1.getId())
                    .pizzas(List.of(pizzaM, pizzaG))
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_ENTREGUE)
                    .data(LocalDateTime.now())
                    .build();
            pedidoRepository.save(pedido_entregue);
        }
        @AfterEach
        void tearDown() {
            clienteRepository.deleteAll();
            estabelecimentoRepository.deleteAll();
            pedidoRepository.deleteAll();
            saborRepository.deleteAll();
        }

        @Test
        @DisplayName("Cliente listar por estabelecimento")
        void clienteListaSeusPedidosEmEstabelecimento() throws Exception {
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", estabelecimento1.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

            assertEquals(3, resultado.size());

            assertEquals(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA, resultado.get(0).getStatusEntrega());
            assertEquals(PedidoStatusEntregaEnum.PEDIDO_PRONTO, resultado.get(1).getStatusEntrega());
            assertEquals(PedidoStatusEntregaEnum.PEDIDO_ENTREGUE, resultado.get(2).getStatusEntrega());

        }
        @Test
        @DisplayName("Cliente listar por status")
        void clienteListaSeusPedidosPorStatus() throws Exception {
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("statusEntrega", PedidoStatusEntregaEnum.PEDIDO_RECEBIDO.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

            assertEquals(1, resultado.size());
            assertEquals(PedidoStatusEntregaEnum.PEDIDO_RECEBIDO ,resultado.get(0).getStatusEntrega());
        }
        @Test
        @DisplayName("Cliente listar por estabelecimento e status")
        void clienteListaSeusPedidosPorEstabelecimentoStatus() throws Exception {
            pedido_em_preparo.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_RECEBIDO);
            pedidoRepository.flush();
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", estabelecimento.getId().toString())
                            .param("statusEntrega", PedidoStatusEntregaEnum.PEDIDO_RECEBIDO.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Cliente listar por estabelecimento")
        void clienteListarComCodigoAcessoInvalido() throws Exception {
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", "999999")
                            .param("estabelecimentoId", estabelecimento1.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertEquals("Codigo de acesso inválido.", resultado.getMessage());
        }

        @Test
        @DisplayName("Cliente listar por estabelecimento")
        void clienteListarClienteInexistente() throws Exception {
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + "99999")
                            .param("clienteCodigoAcesso", "999999")
                            .param("estabelecimentoId", estabelecimento1.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertEquals("Cliente não encontrado.", resultado.getMessage());
        }
        @Test
        @DisplayName("Cliente listar por estabelecimento")
        void clienteListarComEstabelecimentoInexistente() throws Exception {
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", "99999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertEquals("Estabelecimento não encontrado.", resultado.getMessage());
        }
        @Test
        @DisplayName("Cliente listar por status com status iguais, ordenando por mais recente")
        void clienteListarStatusIguais() throws Exception {

            pedido_recebido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);
            pedido_recebido.setData(LocalDateTime.now());
            pedidoRepository.flush();

            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/cliente-estabelecimento/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("statusEntrega", PedidoStatusEntregaEnum.PEDIDO_EM_ROTA.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

            assertEquals(2, resultado.size());
            assertEquals(estabelecimento.getId() ,resultado.get(0).getEstabelecimentoId());
            assertEquals(estabelecimento1.getId(), resultado.get(1).getEstabelecimentoId());
        }

    }
    @Nested
    @DisplayName("Alteração de estado de pedido")
    public class AlteracaoEstadoPedidoTest {
        Pedido pedido1;

        @BeforeEach
        void setUp() {
            pedido1 = pedidoRepository.save(Pedido.builder()
                    .estabelecimentoId(estabelecimento.getId())
                    .clienteId(cliente.getId())
                    .enderecoEntrega("Rua 1")
                    .pizzas(List.of(pizzaG))
                    .preco(10.0)
                    .build()
            );
        }

//        @Test
//        @DisplayName("Quando o estabelecimento associa um pedido a um entregador")
//        void quandoEstabelecimentoAssociaPedidoEntregador() throws Exception {
//            // Arrange
//            pedidoRepository.save(pedido);
//            pedido.setStatusEntrega("Pedido pronto");
//            List<Entregador> entregadores = new LinkedList<>();
//            entregadores.add(entregador);
//            estabelecimento.setEntregadoresDisponiveis(entregadores);
//            entregador.setDisponibilidade(true);
//
//
//            // Act
//            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + pedido.getId() + "/" + "/associar-pedido-entregador")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("estabelecimentoId", estabelecimento.getId().toString())
//                            .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
//                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
//                    .andExpect(status().isOk())
//                    .andDo(print())
//                    .andReturn().getResponse().getContentAsString();
//
//            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);
//
//            // Assert
//            assertEquals(resultado.getStatusEntrega(), "Pedido em rota");
//            assertEquals(entregador.getId(), resultado.getEntregadorId());
//        }

        @Test
        @DisplayName("Quando o cliente confirma a entrega de um pedido")
        void quandoClienteConfirmaEntregaPedido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/" + pedido.getId() + "/confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);

            // Assert
            assertEquals(resultado.getStatusEntrega(), PedidoStatusEntregaEnum.PEDIDO_ENTREGUE);
        }

        @Test
        @DisplayName("Quando o cliente confirma a entrega com código inválido")
        void quandoClienteConfirmaEntregaCodigoInvalido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/" + pedido.getId() + "/confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", "999999"))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando o cliente confirma a entrega o pedido com status inválido")
        void quandoClienteConfirmaEntregaPedidoStatusInvalido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedido.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/" + pedido.getId() + "/confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isConflict())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Pedido com status de entrega inválido.", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de teste da confirmação de pagamento de um pedido")
    public class PedidoConfirmarPagamentoTests {

        Pedido pedido1;

        @BeforeEach
        void setUp() {
            cliente = clienteRepository.save(Cliente.builder()
                    .nome("Anton Ego")
                    .endereco("Paris")
                    .codigoAcesso("123456")
                    .build());

            pedido1 = pedidoRepository.save(Pedido.builder()
                    .estabelecimentoId(estabelecimento.getId())
                    .clienteId(cliente.getId())
                    .enderecoEntrega("Rua 1")
                    .pizzas(List.of(pizzaG))
                    .preco(10.0)
                    .statusPagamento(false)
                    .build()
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito")
        void confirmaPagamentoCartaoCredito() throws Exception {
            // Arrange
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("metodoPagamento", "Cartão de crédito")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(10, resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de débito")
        void confirmaPagamentoCartaoDebito() throws Exception {
            // Arrange
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("metodoPagamento", "Cartão de débito")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(9.75, resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por Pix")
        void confirmaPagamentoPIX() throws Exception {
            // Arrange
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("metodoPagamento", "PIX")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(9.5, resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento com código de acesso inválido")
        void confirmaPagamentoCodigoInvalido() throws Exception {
            // Arrange
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", "999999")
                            .param("pedidoId", pedido1.getId().toString())
                            .param("metodoPagamento", "PIX")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            // Assert
            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de pedido já pago")
        void confirmaPagamentoPedidoPago() throws Exception {
            // Arrange
            pedido1.setStatusPagamento(true);
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/clientes/" + cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("metodoPagamento", "PIX")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isConflict())
                    .andReturn().getResponse().getContentAsString();
            // Assert
            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertEquals("O pedido já foi pago!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de teste de alteração de status de pedido.")
    public class PedidoAlterarStatus {

        Pedido pedido1;
        Estabelecimento estabelecimento1;

        @BeforeEach
        void setUp() {
            entregador = entregadorRepository.save(Entregador.builder()
                    .nome("Joãozinho")
                    .placaVeiculo("ABC-1234")
                    .corVeiculo("Azul")
                    .tipoVeiculo("Moto")
                    .codigoAcesso("101010")
                    .build());

            estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("654321")
                    .build());

            pedido1 = pedidoRepository.save(Pedido.builder()
                    .estabelecimentoId(estabelecimento1.getId())
                    .clienteId(cliente.getId())
                    .enderecoEntrega("Rua 1")
                    .pizzas(List.of(pizzaG))
                    .preco(10.0)
                    .statusPagamento(true)
                    .statusEntrega(PedidoStatusEntregaEnum.PEDIDO_RECEBIDO)
                    .build()
            );
        }

        @Test
        @DisplayName("Alterando pedido recebido para pedido em preparo")
        void preparandoPedido() throws Exception{
            //Arrange
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/"+ pedido1.getId() + "/preparando-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);

            // Assert
            assertAll(
                    () -> assertEquals(resultado.getStatusEntrega(), PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO)
            );
        }

        @Test
        @DisplayName("Alterando pedido recebido para pedido em preparo, pedido não confirmado")
        void preparandoPedidoNaoConfirmado() throws Exception{
            //Arrange
            pedido1.setStatusPagamento(false);
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/"+ pedido1.getId() + "/preparando-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isConflict()) // Codigo 409
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertAll(
                    () -> assertEquals(PedidoStatusEntregaEnum.PEDIDO_RECEBIDO, pedido1.getStatusEntrega())
            );
        }

        @Test
        @DisplayName("Alterando pedido recebido para pedido em preparo")
        void pedidoPronto() throws Exception{
            //Arrange
            pedido1.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO);
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/" + pedido1.getId() + "/pedido-pronto")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);

            // Assert
            assertAll(
                    () -> assertEquals(PedidoStatusEntregaEnum.PEDIDO_PRONTO, resultado.getStatusEntrega())
            );
        }

        @Test
        @DisplayName("Definindo entregador")
        void definindoEntregador() throws Exception{
            //Arrange
            clienteRepository.save(cliente);
            estabelecimentoRepository.save(estabelecimento1);
            entregadorRepository.save(entregador);
            pedido1.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_PRONTO);
            Associacao associacao = associacaoRepository.save(
                    Associacao.builder()
                            .entregador(entregador)
                            .estabelecimento(estabelecimento1)
                            .status(true)
                            .build()
            );
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/" + pedido1.getId() + "/associar-pedido-entregador")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("associacaoId", associacao.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);

            // Assert
            assertAll(
                    () -> assertEquals(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA, resultado.getStatusEntrega()),
                    () -> assertEquals(entregador.getId(),resultado.getEntregadorId())
            );
        }

        @Test
        @DisplayName("Definindo entregador com associacao False")
        void definindoEntregadorFalse() throws Exception{
            //Arrange
            pedido1.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_PRONTO);
            Associacao associacao = associacaoRepository.save(
                    Associacao.builder()
                            .entregador(entregador)
                            .estabelecimento(estabelecimento1)
                            .status(false)
                            .build()
            );
            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/estabelecimentos/" + estabelecimento1.getId() + "/" + pedido1.getId() + "/associar-pedido-entregador")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso())
                            .param("pedidoId", pedido1.getId().toString())
                            .param("associacaoId", associacao.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isConflict()) // Codigo 409
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertAll(
                    () -> assertNull(pedido1.getEntregadorId())
            );
        }
    }
}