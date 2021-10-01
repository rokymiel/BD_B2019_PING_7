### Task 1
а). select distinct LastName from Reader where Address like "%Moscow%"
б). select Author, Title from Book, Borrowing where FirstName="Иван" and LastName="Иванов" and ReaderNr=ID and Book.ISBN=Borrowing.ISBN 
в). (select ISBN from Book, BookCat where Book.ISBN=BookCat.ISBN and CategoryName="Горы") minus (select ISBN from Book, BookCat where Book.ISBN=BookCat.ISBN and CategoryName="Путешествия")
г). select LastName, FirstName from Reader, Borrowing where ID=ReaderNr and ReturnDate is not null   // Я предполагаю, что ReturnDate - фактическое время возвращения книги(изначально null), а не запланированное при выдаче книги
д). select LastName, FirstName from Reader, Borrowing where (FirstName!="Иван" or LastName!="Иванов") and ID=ReaderNr and ISBN in 
	( select ISBN from Reader, Borrowing where FirstName="Иван" and LastName="Иванов" and ID=ReaderNr )
### Task 2
а). select TrainNr from Train where StartStationName="Москва" and EndStationName="Тверь"  // Честно говоря, я предположил, что не нужно проверять, есть ли остановки в других городах
б). select TrainNr from Train, Connection where Train.TrainNr=Connection.TrainNr and FromStation="Москва" and ToStation="Санкт-Петербург" and DAY(Departure)=DAY(Arrival) 
в). 
В задаче а) тогда:
	select TrainNr from Train, Connection where Train.TrainNr=Connection.TrainNr and FromStation="Москва" and ToStation="Тверь" and StartStationName="Москва" and EndStationName="Тверь"
Так как есть прямой переезд(Connection) между этими городами, а также старт=конец
В задаче б) тогда:
	select TrainNr from Train where exists (select Departure from Connection where Train.TrainNr=Connection.TrainNr and FromStation="Москва") and exists (select Arrival from Connection where Train.TrainNr=Connection.TrainNr and ToStation="Тверь" ) and DAY(Departure)=DAY(Arrival) 
Т.е. нужно проверить, что есть остановка, исходящая их Москвы, и остановка, приезжающая в Санкт-Петербург

### Task 3