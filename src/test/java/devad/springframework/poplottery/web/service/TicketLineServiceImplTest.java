package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TicketLineServiceImplTest {

    TicketDto validTicket;

    @Autowired
    private TicketLineServiceImpl ticketLineSvc;

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
    void createLines() {
        // Arrange
        validTicket.setTicketLines(null);
        int noOfLines = 4;

        // Act
        List<TicketLineDto> result = ticketLineSvc.createLines(validTicket, noOfLines);

        // Assert
        assertEquals(result.size(), noOfLines);
        assertNotNull(result.get(0).getTicket());
        assertNotNull(result.get(0).getLineValues());
    }

    @Test
    void generateValues() {
        // Arrange
        int noOfValues = 3;
        int minValue = 0;
        int maxValue = 3;

        // Act
        List<Integer> result = ticketLineSvc.generateValues();

        // Assert
        assertEquals(result.size(), noOfValues);
        assertFalse(Collections.max(result) >= maxValue);
        assertFalse(Collections.min(result) < minValue);
    }

    @Test
    void checkLines() {
        // Arrange
        List<TicketLineDto> ticketLines = validTicket.getTicketLines();

        // Act
        List<TicketLineDto> result = ticketLineSvc.checkLines(ticketLines);

        // Assert
        assertEquals(ticketLines.size(), result.size());
    }

    @Test
    void sumCheck() {
        // Arrange
        List<Integer> valuesOne = new ArrayList<>(Arrays.asList(0, 1, 1));
        List<Integer> valuesTwo = new ArrayList<>(Arrays.asList(2, 1, 1));
        int sumNumber = 2;

        // Act
        boolean resultOne = ticketLineSvc.sumCheck(valuesOne, sumNumber);
        boolean resultTwo = ticketLineSvc.sumCheck(valuesTwo, sumNumber);

        // Assert
        assertTrue(resultOne);
        assertFalse(resultTwo);
    }

    @Test
    void differentFromFirstCheck() {
        // Arrange
        List<Integer> valuesOne = new ArrayList<>(Arrays.asList(0, 1, 1));
        List<Integer> valuesTwo = new ArrayList<>(Arrays.asList(0, 1, 2));
        List<Integer> valuesThree = new ArrayList<>(Arrays.asList(1, 1, 1));
        List<Integer> valuesFour = new ArrayList<>(Arrays.asList(2, 0, 2));
        List<Integer> valuesFive = new ArrayList<>(Arrays.asList(2, 2, 0));

        // Act
        boolean resultOne = ticketLineSvc.differentFromFirstCheck(valuesOne);
        boolean resultTwo = ticketLineSvc.differentFromFirstCheck(valuesTwo);
        boolean resultThree = ticketLineSvc.differentFromFirstCheck(valuesThree);
        boolean resultFour = ticketLineSvc.differentFromFirstCheck(valuesFour);
        boolean resultFive = ticketLineSvc.differentFromFirstCheck(valuesFive);

        // Assert
        assertTrue(resultOne);
        assertTrue(resultTwo);
        assertFalse(resultThree);
        assertFalse(resultFour);
        assertFalse(resultFive);
    }

    @Test
    void allSameCheck() {
        // Arrange
        List<Integer> valuesOne = new ArrayList<>(Arrays.asList(0, 0, 0));
        List<Integer> valuesTwo = new ArrayList<>(Arrays.asList(1, 0, 0));
        List<Integer> valuesThree = new ArrayList<>(Arrays.asList(0, 1, 0));
        List<Integer> valuesFour = new ArrayList<>(Arrays.asList(0, 0, 1));
        List<Integer> valuesFive = new ArrayList<>(Arrays.asList(1, 1, 0));
        List<Integer> valuesSix = new ArrayList<>(Arrays.asList(0, 1, 1));
        List<Integer> valuesSeven = new ArrayList<>(Arrays.asList(1, 0, 1));

        // Act
        boolean resultOne = ticketLineSvc.allSameCheck(valuesOne);
        boolean resultTwo = ticketLineSvc.allSameCheck(valuesTwo);
        boolean resultThree = ticketLineSvc.allSameCheck(valuesThree);
        boolean resultFour = ticketLineSvc.allSameCheck(valuesFour);
        boolean resultFive = ticketLineSvc.allSameCheck(valuesFive);
        boolean resultSix = ticketLineSvc.allSameCheck(valuesFive);
        boolean resultSeven = ticketLineSvc.allSameCheck(valuesFive);

        // Assert
        assertTrue(resultOne);
        assertFalse(resultTwo);
        assertFalse(resultThree);
        assertFalse(resultFour);
        assertFalse(resultFive);
        assertFalse(resultSix);
        assertFalse(resultSeven);
    }
}