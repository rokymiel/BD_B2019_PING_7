# Задание №3. Борисов Костя БПИ197
## Задача 1
Отношения используют ключи, чтобы уникально ссылаться на сущности. Уникальные ссылки можно сделать только через ключи, потомучто если это не ключ, то не будет гарантии что ссылка уникальная. Если у отношения нет ключей, то оно ничего связать не может, и полностью безполезно.

## Задача 2
### Пункт 1

**Категория**: {[<ins>id</ins>, имя, idПодкатегории]}\
**Экземпляр**: {[<ins>id</ins>, <ins>isnb</ins>, положениеНаПолке]}\
**Книга**: {[<ins>isnb</ins>, год, название, автор, колвоCтраниц, idИздательства]}\
**Издательство**: {[<ins>id</ins>, имя, адрес]}\
**Читатель**: {[<ins>id</ins>, фамилия, имя, адрес, деньРождения]}\
взял: {[<ins>idЧитателя</ins>, <ins>idЭкземпляря</ins>, датаВозврата]}\
категоризирована: {[<ins>idКниги</ins>, <ins>idКатегории</ins>]}

### Пункт 2.1

**Квартира**: {[<ins>id</ins>, idДома]}\
**Дом**: {[<ins>id</ins>, idУлицы]}\
**Улица**: {[<ins>id</ins>, idГорода]}\
**Город**: {[<ins>id</ins>, idСтраны]}\
**Страна**: {[<ins>id</ins>]}

### Пункт 2.2
**Арбитр**: {[<ins>id</ins>]}\
**Команда**: {[<ins>id</ins>]}\
играют: {[<ins>idПервойКоманды</ins>, <ins>idВторойКоманды</ins>, <ins>idАрбитра</ins>]}

### Пункт 2.3
**Мужчина**: {[<ins>id</ins>, idОтца, idМатери]}\
**Женщина**: {[<ins>id</ins>, idОтца, idМатери]}

### Пункт 3
**Сущность**: {[<ins>id</ins>, название, слабость]}\
**Связь**: {[<ins>id</ins>, описание]}\
**Атрибут**: {[<ins>id</ins>, название, являетсяКлючом, idСущности]}\
связана: {[<ins>idСущности</ins>, <ins>idСвязи</ins>, idМатери, функцианальность, роль, минимум, максимум]}

## Задача 3
### Пункт 1
**Station**: {[<ins>name</ins>, traks, cityRegion, cityName]}\
**City**: {[<ins>region</ins>, <ins>name</ins>]}\
**Train**: {[<ins>trainNr</ins>, length, startStationName, endStationName]}\
connected: {[<ins>trainNr</ins>, <ins>stationName</ins>, arrival, departure, nextStationName]}

### Пункт 2
**StationPersonell**: {[<ins>persNr</ins>, name, stationNr]}\
**Station**: {[<ins>statNr</ins>, name]}\
**Room**: {[<ins>stationNr</ins>, <ins>roomNr</ins>, numberOfBeds]}\
**Patient**: {[<ins>patientNr</ins>, name, disease, doctorNr, roomNr, admissionFrom, amissionTo]}\
**Doctor**: {[<ins>persNr</ins>, area, rank]}\
**Caregiver**: {[<ins>persNr</ins>, qualification]}
