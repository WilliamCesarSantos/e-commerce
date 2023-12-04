package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @MockBean
    private ICustomerUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void nao_deve_ser_possivel_cadastrar_cliente_sem_nome() throws Exception {
        // O teste garante que ao receber um cliente sem a informação de nome a
        // aplicação irá retornar o status code 400
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .content("""
                                {
                                    "document": "0000",
                                    "email" : ["one@teste.com"],
                                    "telephone": ["999999"],
                                    "birthDate": "2020-01-01"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

}
