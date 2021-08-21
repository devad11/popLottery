package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.dao.TicketDao;
import devad.springframework.poplottery.web.dao.TicketLineDao;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TicketLineServiceImpl implements TicketLineService {

    @Autowired
    TicketLineDao ticketLineDao;

    TicketService ticketSvc;

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

    @Override
    public void saveNewLines(List<TicketLineDto> ticketLines) {
            ticketLineDao.saveAll(ticketLines);
    }

    @Override
    public List<TicketLineDto> checkLines(List<TicketLineDto> ticketLines) {
        int sumNumber = 2;
        int winSum = 10;
        int winAllSame = 5;
        int winDifferentFromFirst = 1;
        List<List<Integer>> orderDetails = new ArrayList<>();
        List<Integer> highestPoint = new ArrayList<>();
        List<Integer> middlePoint = new ArrayList<>();
        List<Integer> lowestPoint = new ArrayList<>();
        List<Integer> noPoint = new ArrayList<>();

        for(TicketLineDto line: ticketLines)
        {
            List<Integer> values = line.getLineValues();
            if(sumCheck(values, sumNumber)){
                line.setLineResult(winSum);
                highestPoint.add(line.getId());
            }
            else if(this.allSameCheck(values))
            {
                line.setLineResult(winAllSame);
                middlePoint.add(line.getId());
            }
            else if(this.differentFromFirstCheck(values))
            {
                line.setLineResult(winDifferentFromFirst);
                lowestPoint.add(line.getId());
            }
            else
            {
                noPoint.add(line.getId());
            }
        }

        orderDetails.add(highestPoint);
        orderDetails.add(middlePoint);
        orderDetails.add(lowestPoint);
        orderDetails.add(noPoint);

        // order by result
        ticketLines = this.orderByResult(orderDetails, ticketLines);

        //ticketLines.stream().forEachOrdered(l -> l.getLineResult());
        //ticketLines.stream().sorted().toArray();

        return ticketLines;
    }

    private List<TicketLineDto> orderByResult(List<List<Integer>> orderDetails, List<TicketLineDto> ticketLines) {
        List<TicketLineDto> orderedLines = new ArrayList<>();
        for(int i = 0; i < orderDetails.size(); i++)
        {
            int priority = i;
            orderedLines.addAll(ticketLines.stream().filter(l -> orderDetails.get(priority).contains(l.getId())).collect(Collectors.toList()));
        }

        return orderedLines;
    }

    private boolean sumCheck(List<Integer> values, int sumNumber){
        int sumOfValues = values.stream().mapToInt(Integer::intValue).sum();
        return sumOfValues == sumNumber;
    }

    private boolean differentFromFirstCheck(List<Integer> values) {
        int firstValue = values.get(0);
        long matchWithFirst = values.stream().filter(v -> v != firstValue).count();
        return matchWithFirst == (values.size() - 1);
    }

    private boolean allSameCheck(List<Integer> values){
        int firstValue = values.get(0);
        long matchWithFirst = values.stream().filter(v -> v == firstValue).count();
        return matchWithFirst == values.size();
    }
}
