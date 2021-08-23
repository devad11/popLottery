package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.dao.TicketDao;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TicketServiceImplTest {

    @Autowired
    private TicketServiceImpl ticketSvc;

    @MockBean
    private TicketDao ticketDao;

    @MockBean
    private TicketLineService ticketLineSvc;

    TicketDto validTicket;

    List<TicketLineDto> ticketLines;

    @BeforeEach
    void setUp() {

        List<Integer> tenPointer = new ArrayList<>(
                List.of(0, 2, 0));
        List<Integer> fivePointer = new ArrayList<>(
                List.of(1, 1, 1));
        List<Integer> onePointer = new ArrayList<>(
                List.of(1, 2, 3));
        List<Integer> zeroPointer = new ArrayList<>(
                List.of(1, 2, 1));

        TicketLineDto ticketLineOne = TicketLineDto.builder()
                .id(1)
                .lineValues(
                        tenPointer
                ).build();

        TicketLineDto ticketLineTwo = TicketLineDto.builder()
                .id(2)
                .lineValues(
                        fivePointer
                ).build();

        TicketLineDto ticketLineThree = TicketLineDto.builder()
                .id(3)
                .lineValues(
                        onePointer
                ).build();

        TicketLineDto ticketLineFour = TicketLineDto.builder()
                .id(4)
                .lineValues(
                        zeroPointer
                ).build();

        ticketLines = new ArrayList<>(
                List.of(ticketLineOne, ticketLineTwo, ticketLineThree, ticketLineFour));

        validTicket = TicketDto.builder()
                .id(1)
                .created(new Timestamp(2019, 10, 10, 10, 10, 10, 10))
                .lastModified(new Timestamp(2020, 10, 10, 10, 10, 10, 10))
                .ticketLines(ticketLines)
                .checked(false).build();
    }

    /**
     *  Checks that the correct ticket was fetched
     */
    @Test
    void getTicketById() {
        // Arrange
        int ticketId = 1;
        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.ofNullable(validTicket));

        // Act
        TicketDto result = ticketSvc.getTicketById(ticketId);

        // Assert
        assertEquals(result.getId(), ticketId);
    }

    /**
     * Checks that it returns null when ticket doesnt exist
     */
    @Test
    void getTicketByIdNotExist() {
        // Arrange
        int ticketId = 1;
        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.empty());

        // Act
        TicketDto result = ticketSvc.getTicketById(ticketId);

        // Assert
        assertEquals(result, null);
    }

    /**
     * Checks not to return ticket when noOfLines to add is 0
     */
    @Test
    void amendZeroLines() {
        // Arrange
        int ticketId = 1;
        int noOfLines = 0;
        validTicket.setTicketLines(null);

        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.ofNullable(validTicket));

        // Act
        TicketDto result = ticketSvc.amend(ticketId, noOfLines);

        // Assert
        assertEquals(result, null);
    }

    /**
     * Checks to return ticket if its checked already
     */
    @Test
    void amendChecked() {
        // Arrange
        int ticketId = 1;
        int noOfLines = 4;
        TicketDto ticket = validTicket;
        ticket.setChecked(true);

        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.ofNullable(ticket));

        // Act
        TicketDto result = ticketSvc.amend(ticketId, noOfLines);

        // Assert
        assertTrue(result.isChecked());
    }

    /**
     * If ticket doesnt exist return null
     */
    @Test
    void checkTicketNotExist() {
        // Arrange
        int ticketId = 1;
        validTicket.setTicketLines(null);

        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.empty());

        // Act
        TicketDto result = ticketSvc.checkTicket(ticketId);

        // Assert
        assertEquals(result, null);
    }

    /**
     * Checks if null is returned when ticket is already checked
     */
    @Test
    void checkTicket() {
        // Arrange
        int ticketId = 1;
        TicketDto ticket = validTicket;
        ticket.setChecked(true);
        validTicket.setTicketLines(null);

        Mockito.when(ticketDao.findById(ticketId)).thenReturn(Optional.ofNullable(ticket));

        // Act
        TicketDto result = ticketSvc.checkTicket(ticketId);

        // Assert
        assertEquals(result, null);
    }
}