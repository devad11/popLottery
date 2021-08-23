# popLottery
 lottery game project

API endpoints:

Rules(GET): http://localhost:8080/api/v1/ticket/

Create(POST): http://localhost:8080/api/v1/ticket/newticket
        requires: noOfLines

Find all tickets(GET): http://localhost:8080/api/v1/ticket/all

Find a ticket(GET): http://localhost:8080/api/v1/ticket/{ticketId}

Add more lines(PUT): http://localhost:8080/api/v1/ticket/{ticketId}
                     requires: noOfLines

Check ticket(PUT): http://localhost:8080/api/v1/ticket/check/{ticketId}