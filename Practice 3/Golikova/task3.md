### Задача 1

Отношение существует только между двумя конкретными уникальными сущностями. Отношение без привязки к сущностям не существует (извините за тавтологию) и не имеет смысла. А чтобы конкретно указывать на нужную уникальную сущность, используется уникальный ключ, относящийся к этой сущности. 

### Задача 2

#### 1.
**Книга**: {[<ins>ISBN</ins>, год, название, автор, количество страниц, id_издательства]}\
**Экземпляр книги**: {[<ins>id</ins>, <ins>ISBN</ins>, положение на полке]}\
**Категория**: {[<ins>имя</ins>, имя категории-родителя]}\
**Издательство**: {[<ins>id</ins>, имя издателя, адрес]}\
**Читатель**: {[<ins>id</ins>, фамилия, имя, адрес, дата рождения]}\
**Запись о взятии копии**: {[<ins>id</ins>, дата возврата]}\

_**имеет категорию**_: {[<ins>ISBN</ins>, <ins>имя категории</ins>]}\
_**взял экземпляр**_: {[<ins>id_читателя</ins>, <ins>id_копии</ins>, <ins>ISBN</ins>, дата возврата]}

#### 2.1.
**Квартира**: {[<ins>id</ins>, id_дома]}\
**Дом**: {[<ins>id</ins>, id_улицы]}\
**Улица**: {[<ins>id</ins>, id_города]}\
**Город**: {[<ins>id</ins>, id_страны]}\
**Страна**: {[<ins>id</ins>]}

#### 2.2.
**Команда**: {[<ins>id</ins>]}\
**Арбитр**: {[<ins>id</ins>]}

_**игра**_: {[<ins>id_команды1</ins>, <ins>id_команды2</ins>, <ins>id_арбитра</ins>]}

#### 2.3.
**Мужчина**: {[<ins>id</ins>, id_отца, id_матери]}\
**Женщина**: {[<ins>id</ins>, id_отца, id_матери]}

#### 3.
**Сущность**: {[<ins>имя</ins>]}\
**Атрибут сущности**: {[<ins>имя</ins>, является ли ключом, имя_сущности]}\
**Отношение**: {[<ins>имя</ins>]}\
**Атрибут отношения**: {[<ins>имя</ins>, имя_отношения]}

_**участие**_: {[<ins>id_сущности</ins>, <ins>id_отношения</ins>, функциональность, роль, минимум, максимум]}

### Задача 3

#### 1.
**Station**: {[<ins>Name</ins>, #Tracks, Region_City, Name_City]}\
**Train**: {[<ins>TrainNr</ins>, Length, Name_EndStation, Name_StartStation]}\
**City**: {[<ins>Region</ins>, <ins>Name</ins>]}\

_**connection**_: {[<ins>Name_EndStation</ins>, <ins>Name_StartStation</ins>, <ins>TrainNr</ins>, Departure, Arrival]}

#### 2.
**StationPersonnel**: {[<ins>PersNr</ins>, #Name, StatNr]}\
**Station**: {[<ins>StatNr</ins>, Name]}\
**Room**: {[<ins>RoomNr</ins>, #Beds, StatNr]}\
**Patient**: {[<ins>PatientNr</ins>, Name, Disease, RoomNr, AdmissionFrom, AdmissionTo, DoctorNr]}\
**Doctor**: {[<ins>DoctorNr</ins>, Area, Rank]}\
**Caregiver**: {[<ins>CaregiverNr</ins>, Qualification]}\