package devad.springframework.poplottery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devad.springframework.poplottery.web.controller.form.LineNumberForm;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import devad.springframework.poplottery.web.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @MockBean
    TicketService ticketScv;
    @Autowired
    MockMvc mockMvc;

    TicketDto validTicket;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        List<Integer> tenPointer = new ArrayList<>(
                List.of( 0, 2, 0));
        List<Integer> fivePointer = new ArrayList<>(
                List.of( 1, 1, 1));
        List<Integer> onePointer = new ArrayList<>(
                List.of( 1, 2, 3));
        List<Integer> zeroPointer = new ArrayList<>(
                List.of( 1, 2, 1));

        TicketLineDto ticketLineOne =  TicketLineDto.builder()
                .id(1)
                .lineValues(
                        tenPointer
                ).build();

        TicketLineDto ticketLineTwo =  TicketLineDto.builder()
                .id(2)
                .lineValues(
                        fivePointer
                ).build();

        TicketLineDto ticketLineThree =  TicketLineDto.builder()
                .id(3)
                .lineValues(
                        onePointer
                ).build();

        TicketLineDto ticketLineFour =  TicketLineDto.builder()
                .id(4)
                .lineValues(
                        zeroPointer
                ).build();

        List<TicketLineDto> ticketLines = new ArrayList<>(
                List.of( ticketLineOne, ticketLineTwo, ticketLineThree, ticketLineFour));

        validTicket = TicketDto.builder()
                .id(1)
                .created(new Timestamp(2019, 10, 10, 10, 10, 10, 10))
                .lastModified(new Timestamp (2020, 10, 10, 10, 10, 10, 10))
                .ticketLines(ticketLines)
                .checked(false).build();
    }

    @Test
    void createTicket() throws Exception {
        int noOfLines = 4;
        LineNumberForm lineNumberForm = new LineNumberForm();
        lineNumberForm.setNoOfLines(noOfLines);

        given(ticketScv.createNewTicket(lineNumberForm.getNoOfLines())).willReturn(validTicket);

        mockMvc.perform(post("/api/v1/ticket/newticket").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("noOfLines", String.valueOf(noOfLines)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTicket() throws Exception {
        given(ticketScv.getTicketById(1)).willReturn(validTicket);
        mockMvc.perform(get("/api/v1/ticket/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTicketsWithEmptyList() throws Exception {
        given(ticketScv.listAllTickets()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/ticket/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There are no tickets! :("));
    }

    @Test
    void testGetAllTickets() throws Exception {
        given(ticketScv.listAllTickets()).willReturn(new ArrayList<TicketDto>(Arrays.asList(validTicket)));

        mockMvc.perform(get("/api/v1/ticket/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addNewLinesChecked() throws Exception {

        int noOfLines = 10;
        LineNumberForm lineNumberForm = new LineNumberForm();
        lineNumberForm.setNoOfLines(noOfLines);
        validTicket.setChecked(true);
        given(ticketScv.amend(1, lineNumberForm.getNoOfLines())).willReturn(validTicket);

        mockMvc.perform(put("/api/v1/ticket/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noOfLines", String.valueOf(noOfLines)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ticket is checked already! Cannot amend!"));;
    }

    @Test
    void addNewLinesNotExist() throws Exception {

        int noOfLines = 10;
        LineNumberForm lineNumberForm = new LineNumberForm();
        lineNumberForm.setNoOfLines(noOfLines);
        given(ticketScv.amend(1, lineNumberForm.getNoOfLines())).willReturn(null);

        mockMvc.perform(put("/api/v1/ticket/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noOfLines", String.valueOf(noOfLines)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The ticket id and the number of lines must be valid!"));;
    }

    @Test
    void addNewLines() throws Exception {

        int noOfLines = 10;
        LineNumberForm lineNumberForm = new LineNumberForm();
        lineNumberForm.setNoOfLines(noOfLines);
        given(ticketScv.amend(1, lineNumberForm.getNoOfLines())).willReturn(validTicket);

        mockMvc.perform(put("/api/v1/ticket/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noOfLines", String.valueOf(noOfLines)))
                .andExpect(status().isOk());
    }

    @Test
    void checkTicket() throws Exception {
        given(ticketScv.checkTicket(1)).willReturn(validTicket);

        mockMvc.perform(put("/api/v1/ticket/check/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void checkTicketNotAvailable() throws Exception {
        given(ticketScv.checkTicket(1)).willReturn(null);

        mockMvc.perform(put("/api/v1/ticket/check/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The ticket doesn't exist or already checked!"));
    }
}