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
1. Сперва нужно вычислить inner join. 
2. Потом добавить строки из 1й таблицы, которые не вошли в результат inner join, отсутствующие значения из 2й таблицы заполняем null.
3. Потом аналогично для 2й таблицы.
Более точно:
1. Пусть InJoin = декартово произведение(cartesian) по кортежам таблиц, далее фильтурем, чтобы выполнялось условие (совпадали ключи, которые нам нужны в таблицах)
2. Пусть A - кортеж 1й таблицы. Пусть Adop = A minus InJoin декартово умножить(cartesian) на кортеж из null(кортеж 2й таблицы, где все заменено на null)
3. Аналогично Bdop:
Пусть B - кортеж 2й таблицы. Пусть Bdop = B minus InJoin декартово умножить на кортеж из null(кортеж 1й таблицы, где все заменено на null)
4. Объеденяем(union) InJoin,Adop,Bdop - это и есть ответ(full outer join) 