package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.dao.TicketLineDao;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TicketLineServiceImpl implements TicketLineService {

    @Autowired
    TicketLineDao ticketLineDao;

    /**
     * Generates new lines for a ticket
     * @param ticketDto the parent object
     * @param noOfLines number of lines to create
     * @return list of lines
     */
    @Override
    public List<TicketLineDto> createLines(TicketDto ticketDto, int noOfLines) {
        List<TicketLineDto> lines = new ArrayList<>();
        while(lines.size() < noOfLines){
            lines.add(TicketLineDto.builder()
                    .lineValues(this.generateValues())
                    .ticket(ticketDto)
                    .build());
        }
        return lines;
    }

    /**
     * Generates specific numbers from a specific range
     * @return a list of the generated numbers
     */
    private List<Integer> generateValues(){
        int noOfValues = 3;
        int minValue = 0;
        int maxValue = 3;
        Random random = new Random();
        List<Integer> values = new ArrayList<>();

        for(int  i = 1; i <= noOfValues; i++){
            values.add(random.nextInt(maxValue - minValue) + minValue);
        }

        return values;
    }

    /**
     * Saves all ticket lines
     * @param ticketLines List of ticket lines
     */
    @Override
    public void saveNewLines(List<TicketLineDto> ticketLines) {
            ticketLineDao.saveAll(ticketLines);
    }

    /**
     * Calculates the result of each line
     * @param ticketLines list of all lines belong to a ticket
     * @return returns all ticket lines updated with individual results
     */
    @Override
    public List<TicketLineDto> checkLines(List<TicketLineDto> ticketLines) {
        int sumNumber = 2;
        int winSum = 10;
        int winAllSame = 5;
        int winDifferentFromFirst = 1;

        for(TicketLineDto line: ticketLines)
        {
            List<Integer> values = line.getLineValues();
            if(sumCheck(values, sumNumber)){
                line.setLineResult(winSum);
            }
            else if(this.allSameCheck(values))
            {
                line.setLineResult(winAllSame);
            }
            else if(this.differentFromFirstCheck(values))
            {
                line.setLineResult(winDifferentFromFirst);
            }
        }

        return ticketLines;
    }

    /**
     * If the sum of all numbers in line equal to a specific value, adds the highest result
     * @param values all numbers from a line
     * @param sumNumber the specific number to match with
     * @return boolean based on win or lose this check
     */
    private boolean sumCheck(List<Integer> values, int sumNumber){
        int sumOfValues = values.stream().mapToInt(Integer::intValue).sum();
        return sumOfValues == sumNumber;
    }

    /**
     * If all the numbers after first are different from first, adds the middle result
     * @param values all numbers from a line
     * @return boolean based on win or lose this check
     */
    private boolean differentFromFirstCheck(List<Integer> values) {
        int firstValue = values.get(0);
        long matchWithFirst = values.stream().filter(v -> v != firstValue).count();
        return matchWithFirst == (values.size() - 1);
    }

    /**
     * If all numbers are the same in a line, adds the middle result
     * @param values all numbers from a line
     * @return boolean based on win or lose this check
     */
    private boolean allSameCheck(List<Integer> values){
        int firstValue = values.get(0);
        long matchWithFirst = values.stream().filter(v -> v == firstValue).count();
        return matchWithFirst == values.size();
    }
}
