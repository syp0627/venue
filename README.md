# Design of TicketService

I am trying to design a ticket service that can support multiple servers.
Each server has a venue cache to save the current seats status, and trigger updating by jms(only for simulating, in real server, I prefer to use rabbitmq) after seats holded or reserved.
>
Two assumptions (configured in application.yml, default seats row is 9, column is 32, ticket hold 15 seconds):
>Please Update if you want to test with your own settings.
>
>veune.seats.row : 9, column:32
>
>#Second: 13, minutes: 12, hours: 10
>
>ticket.hold.expired.unit: 13, duration 15


## Api for testing

> numSeatsAvailable:   http://localhost:8080/available , response example : 288
>
> hold(email consided as id, not necessary an email), response as SeatHold : http://localhost:8080/{email}/{numSeats} ,
response example : {"seatHodeId":0,"customerEmail":"aaaaa","expired":1537887285965,"seatIndexs":[144,143,145,142]}
>
> reserve:  http://localhost:8080/reserve/{email}/{seatHoldId} , response example : "IqiIUp"

## Excpetion Handling

>TicketsHoldExpiredException :  reserve api call return, due to seatHoldId expired
>
>TicketsHoldFailedException : hold api call return, due to hold failed, should not happened since all in memory
>
>TicketsHoldInfoNotValidedException : reserve api call return, due to seatHoldId not exists or email not match
>
>TicketsNotAvailableException: hold api call return, due to no avalibale seats
>
>TicketsReserveFailedException: reserve api call return, due to reserve failed, should not happened since all in memory

## How to run

> mvn clean install
>
> mvn spring-boot:run

## How to Manually Test
> Visit : http://localhost:8080, using provided api for test.

## Auto Test Provided
> Visit : http://localhost:8080/auto
>
> Rule : every 2 seconds, trigger a hold api request with random (1 - 10) seats, trigger reserve api after get hold response in range (1-25) seconds, so that certain holds will reserved, certain holds will released.

## Strategy for selecting seats
> make center as weight 1 and adding weight 1 up, 2 left, 2 right, 3 bottom, take row 9 and column 32 as example:
>
>13,13,12,12,11,11,10,10,09,09,08,08,07,07,06,06,05,06,06,07,07,08,08,09,09,10,10,11,11,12,12,13
>
>12,12,11,11,10,10,09,09,08,08,07,07,06,06,05,05,04,05,05,06,06,07,07,08,08,09,09,10,10,11,11,12
>
>11,11,10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,04,04,05,05,06,06,07,07,08,08,09,09,10,10,11
>
>10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,03,02,03,03,04,04,05,05,06,06,07,07,08,08,09,09,10
>
>09,09,08,08,07,07,06,06,05,05,04,04,03,03,02,02,01,02,02,03,03,04,04,05,05,06,06,07,07,08,08,09
>
>10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,03,02,03,03,04,04,05,05,06,06,07,07,08,08,09,09,10
>
>10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,03,02,03,03,04,04,05,05,06,06,07,07,08,08,09,09,10
>
>10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,03,02,03,03,04,04,05,05,06,06,07,07,08,08,09,09,10
>
>11,11,10,10,09,09,08,08,07,07,06,06,05,05,04,04,03,04,04,05,05,06,06,07,07,08,08,09,09,10,10,11
>
> try to select one line with lowest average weight first, if cannot, select one group or multiple group with lowest average weight.
