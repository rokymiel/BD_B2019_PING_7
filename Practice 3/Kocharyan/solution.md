# Задание №3. Кочарян Тигран БПИ197
## Задача 1
Как нам известно, отношения используют ключи, чтобы уникально ссылаться на сущности. 
Для того, чтобы гарантировать уникальность ссылки, необходимо использовать ключи. 
Таким образом, в реляционных схемах без ключей невозможно однозначно обозначить, какие объекты эти отношения связывают.
Что приводит к непригодности проектируемой модели.


## Задача 2
### Пункт 1 (Библиотека)

<b>Сущности:</b>:

* Читатель: {[<ins>Номер_Читателя</ins>: int, Имя: string, Фамилия: string, Адрес: string, Дата_Рождения: date]}

* Объект_книги: {[<ins>Номер_копии</ins>: int, <ins>ISBN</ins>: int, Местонахождение_на_полке: int]}

* Книга: {[<ins>ISBN</ins>: int, Год: int, Название: string, Автор: string, Количество_страниц: int, ID_Издателя: int]}

* Издатель: {[<ins>ID_Издателя</ins>: int, Название_издателя: string, Адрес: string]}

* Библиотека: {[<ins>ID_Библиотеки</ins>: int, Объект_книги: Объект_книги]}

* Запись_о_взятии_копии_книги: {[<ins>ID_записи</ins>: int, Дата_возврата: date]}

* Category: {[<ins>ID_Категории</ins>: int, Название_категории: string, ID_Родительской_категории: int]}

<b>Отношения</b>:

* Издаёт_Книгу (Объект_Книги - Издатель): {[<ins>ISBN</ins>: int, <ins>ID_Издателя</ins>: int]}

* Соответствует категории (Book - Category): {[<ins>ISBN</ins>: int, <ins>CategoryID</ins>: int]}

* Владеет_копией_книни (Читатель - Запись_о_взятии_копии_книги): {[<ins>Номер_Читателя</ins>: int, <ins>ID_записи</ins>: int]}

* Соответствует_объекту_книги (Запись_о_взятии_копии_книги - Объект_Книги): {[<ins>Номер_копии</ins>: int, <ins>ISBN</ins>: int, <ins>ID_записи</ins>: int]}

* Хранит_объект_книги (Объект_Книги - Библиотека): {[<ins>Номер_копии</ins>: int, <ins>ISBN</ins>: int, <ins>ID_записи</ins>: int, Местонахождение_на_полке: int, <ins>ID_Библиотеки</ins>: int]}

* Связь "Является_подкатегорией" выполнена по правилу №3 из лекции.

### Пункт 2.1 (Страна-Город-...-Квартира)

<b>Сущности</b>:

* Квартира: {[<ins>***ApartmentID***</ins>: int, HouseID: int]}

* Дом: {[<ins>***HouseID***</ins>: int, StreetID: int]}

* Улица: {[<ins>***StreetID***</ins>: int, CityID: int]}

* Город: {[<ins>***CityID***</ins>: int, CountryID: int]}

* Страна: {<ins>***CountryID***</ins>: int}

### Пункт 2.2 (Футбол)
Сущности:
* Команда: {[***TeamId***]}
* Арбитр: {[***ArbiterId***]}
* 
Relationships:
* Играют матч (Команда - Команда - Арбитр): {[***Team1Id***, ***Team2Id***, ***ArbiterId***]}

### Пункт 2.3 (Родители)
<b>Сущности:</b>:
* Мужчина: {[***Id***, MotherId, FatherId]}
* Женщина {[***Id***, MotherId, FatherId]}

### Пункт 3 (ER-модель):
<b>Сущности:</b>:
* Сущность: {[<ins>Имя_сущности</ins>: string, EntityName: string]}
* Аттрибут: {[<ins>Имя_атрибута</ins>: string, Являеся_ли_частью_ключа: bool]}
* Отношение: {[<ins>Имя_связи</ins>]}

<b>Отношения</b>:

* Имеет атрибуты (Сущность - Атрибут): {[<ins>Имя_сущности</ins>: string, <ins>Имя_атрибута</ins>: string]}

* Участвует_в_отношении (Сущность - Отношение): {[<ins>Имя</ins>: string, <ins>Имя_связи</ins>: string, Функциональность: string, Роль: string, Минимум: int, Максимум: int]}


## Задача 3
### Метро:
<b>Сущности:</b>:
* City: {[<ins>Name</ins>: string, <ins>Region</ins>: string]}
* Station: {[<ins>Name</ins>: string, #Tracks: int, CityName: string, PreviousStationName: string, NextStationName: string]}
* Train: {[<ins>TrainNr</ins>: int, Length: int, StartStationName: string, CurrentConnectionID: int, EndStationName: string]}

<b>Связи:</b>:
* Connected (Station - Station - Train): {[<ins>ConnectionID</ins>: int, <ins>StartStationName</ins>: string, <ins>EndStationName</ins>: string, Departure: date, Arrival: date]}

### Госпиталь:
<b>Сущности:</b>:
* Station: {[<ins>StatNr</ins>: int, Name: string]}
* StationPersonell: {[<ins>PersNr</ins>: int, #Name: string, StationNr: int]}
* Room: {[<ins>RoomNr</ins>: int, <ins>StationNr: int</ins>, #Beds: int]}
* Caregiver: {[<ins>PersNr</ins>: int, Qualification: string]}
* Doctor: {[<ins>PersNr</ins>: int, Area: string, Rank: int]}
* Patient: {[<ins>PatientNr</ins>: int, Name: string, Disease: string, PersNr: int, AdmissionRoomNr: int, AdmissionFrom: string, AdmissionTo: string]}
