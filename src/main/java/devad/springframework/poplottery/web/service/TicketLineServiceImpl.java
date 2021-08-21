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

        for(int  c = 1; c <= noOfValues; c++){
            values.add(random.nextInt(maxValue - minValue) + minValue);
        }

        return values;
    }

    @Override
    public void saveNewLines(List<TicketLineDto> ticketLines) {
            ticketLineDao.saveAll(ticketLines);
    }
}
