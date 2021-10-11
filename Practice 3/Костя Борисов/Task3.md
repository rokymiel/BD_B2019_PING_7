# Задание №3. Борисов Костя БПИ197
## Задача 1
Отношения используют ключи, чтобы уникально ссылаться на сущности. Уникальные ссылки можно сделать только через ключи, потомучто если это не ключ, то не будет гарантии что ссылка уникальная. Если у отношения нет ключей, то оно ничего связать не может, и полностью безполезно.

## Задача 2
### Пункт 1

**Категория**: {[<ins>имя</ins>, *имяПодкатегории*]}\
**Экземпляр**: {[<ins>уникальныйНомер</ins>, <ins>*isnb*</ins>, положениеНаПолке]}\
**Книга**: {[<ins>isnb</ins>, год, название, автор, колвоCтраниц, *idИздательства*]}\
**Издательство**: {[<ins>id</ins>, имя, адрес]}\
**Читатель**: {[<ins>уникальныйНомер</ins>, фамилия, имя, адрес, деньРождения]}\
взял: {[<ins>*номерЧитателя*</ins>, <ins>*номерЭкземпляря*</ins>, <ins>*isnb*</ins>, датаВозврата]}\
категоризирована: {[<ins>*idКниги*</ins>, <ins>*имяКатегории*</ins>]}

### Пункт 2.1

**Квартира**: {[<ins>имя</ins>, <ins>*имяДома*</ins>, <ins>*имяУлицы*</ins>, <ins>*имяГорода*</ins>, <ins>*имяСтраны*</ins>]}\
**Дом**: {[<ins>имя</ins>, <ins>*имяУлицы*</ins>, <ins>*имяГорода*</ins>, <ins>*имяСтраны*</ins>]}\
**Улица**: {[<ins>имя</ins>, <ins>*имяГорода*</ins>, <ins>*имяСтраны*</ins>]}\
**Город**: {[<ins>имя</ins>, <ins>*имяСтраны*</ins>]}\
**Страна**: {[<ins>имя</ins>]}

### Пункт 2.2
**Арбитр**: {[<ins>id</ins>, имя]}\
**Команда**: {[<ins>id</ins>, имя]}\
играют: {[<ins>idПервойКоманды</ins>, <ins>idВторойКоманды</ins>, <ins>idАрбитра</ins>]}

### Пункт 2.3
**Мужчина**: {[<ins>id</ins>, *idОтца*, *idМатери*]}\
**Женщина**: {[<ins>id</ins>, *idОтца*, *idМатери*]}

### Пункт 3
**Сущность**: {[<ins>id</ins>, название, слабость]}\
**Связь**: {[<ins>id</ins>, описание]}\
**Атрибут**: {[<ins>id</ins>, название, являетсяКлючом, *idСущности*]}\
связана: {[<ins>*idСущности*</ins>, <ins>*idСвязи*</ins>, функцианальность, роль, минимум, максимум]}

## Задача 3
### Пункт 1
**Station**: {[<ins>name</ins>, #tracks, *cityRegion*, *cityName*]}\
**City**: {[<ins>region</ins>, <ins>name</ins>]}\
**Train**: {[<ins>trainNr</ins>, length, *startStationName*, *endStationName*]}\
connected: {[<ins>*trainNr*</ins>, <ins>*fromStationName*</ins>, <ins>*toStationName*</ins>, arrival, departure]}

### Пункт 2
**StationPersonell**: {[<ins>persNr</ins>, #name, *stationNr*]}\
**Station**: {[<ins>statNr</ins>, name]}\
**Room**: {[<ins>*stationNr*</ins>, <ins>roomNr</ins>, #beds]}\
**Patient**: {[<ins>patientNr</ins>, name, disease, *doctorPersNr*, *stationNr*, *roomNr*, admissionFrom, amissionTo]}\
**Doctor**: {[<ins>*persNr*</ins>, area, rank]}\
**Caregiver**: {[<ins>*persNr*</ins>, qualification]}
