### Task 1
1. select LastName from Reader where Address = '%Moscow%'
2. select Title,Author from Book where ISBN in (select ISBN from Borrowing where ReaderNr in (select ID from Reader where LastName = 'Иванов' and FirstName = 'Иван' limit 1))
3. (select ISBN from BookCat where CategoryName = 'Горы') minus (select ISBN from BookCat where CategoryName = 'Путешествия')
4. select LastName,FirstName from Reader where ID in (select distinct ReaderNr from Borrowing where CopyNumber is not null and ReturnDate is null)
5. select LastName,FirstName from Reader where ID in (select distinct ReaderNr from Borrowing where ISBN in (select ISBN from Borrowing where ReaderNr in (select ID from Reader where LastName = 'Иванов' and FirstName = 'Иван' limit 1))) and LastName != 'Иванов' and FirstName != 'Иван'

### Task 2

1. select TrainNr from Train where TrainNr not in (select TrainNr from Train, Connection where Train.TrainNr=Connection.TrainNr and (Train.StartStationName!=Connection.FromStation or Train.EndStationName!=Connection.ToStation)) and StartStationName in (select Name from Station where CityName = 'Москва') and EndStationName in (select Name from Station where CityName = 'Тверь')
2. select TrainNr from Connection where TrainNr in (select TrainNr from Train, Connection where Train.TrainNr=Connection.TrainNr and (Train.StartStationName!=Connection.FromStation or Train.EndStationName!=Connection.ToStation)) and FromStation in (select Name from Station where CityName = 'Москва') and ToStation in (select Name from Station where CityName = 'Санкт-Петербург') and Day(Departure)=Day(Arrival)
3. Не нужно было бы искать разность двух множеств. В первом случае достаточно было бы найти а из Train и b из Connection с одинаковыми TrainNr и конечными станциями. Во втором бы мы нашли все однодневные трансферы (Connection) между Москвой и Питером и отсеяли бы те, что являются прямыми рейсами (по аналогии с тем, как делали бы первый пункт).

### Task 3
<img src="https://render.githubusercontent.com/render/math?math=\large Given: A = \{a_{1},...,a_{n},b_{1},...,b_{m}\}, C = \{b_{1},...b_{m},c_{1},...,c_{l}\}">  
<img src="https://render.githubusercontent.com/render/math?math=\large NaturalJoinRes=\Pi_{a_{1},...,a_{n},b_{1},...,b_{m},c_{1},...,c_{l}}(\sigma_{A.b_{1}=C.b_{1},...,A.b_{m}=C.b_{m}}(A, C))">  
Находим строки из A, для которых не нашлось соответсвий из C и вместо значений из С, дописываем l Значений null:
<br/><br/>
<img src="https://render.githubusercontent.com/render/math?math=\large RightNullable = (A - \Pi_{a_{1},...,a_{n},b_{1},...,b_{m}}(NaturalJoinRes))\ \times \ \{(null, ..., null)\}">  
Находим строки из C, для которых не нашлось соответсвий из A и вместо значений из A, дописываем n Значений null: 
<br/><br/>
<img src="https://render.githubusercontent.com/render/math?math=\large LeftNullable = \{(null, ..., null)\}\ \times \ (C - \Pi_{b_{1},...,b_{m},c_{1},...,c_{l}}(NaturalJoinRes))">  
<img src="https://render.githubusercontent.com/render/math?math=\large \boldsymbol{Result = NaturalJoinRes\ \union \ RightNullable\ \union \ LeftNullable}">  
