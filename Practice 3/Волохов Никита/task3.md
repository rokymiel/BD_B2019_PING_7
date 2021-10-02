# Курс "Базы данных"

## Домашняя работа 3. Волохов Никита Алексеевич, БПИ197

### **_Задача 1._**
Для связи сущностей друг с другом отношения используют уникальные ключи, чтобы ссылаться на связываемые сущности. Если ссылаться по чему-либо другому, например, по набору атрибутов сущностей, то нет гарантий, что такая сущность единственная, а ключ такую гарантию дает, поэтому отношения и используют ключи - чтобы уникально ссылаться на требуемые сущности.

### **_Задача 2._**
#### Задание 1:
Сущности:
- Издатель: {[<ins>publisherId</ins>, название, адрес]};
- Книга: {[<ins>ISBN</ins>, год, название, автор, количествоСтраниц, publisherId]};
- Экземпляр книги: {[<ins>bookCopyId (номер копии)</ins>, положениеНаПолке, ISBN]};
- Читатель: {[<ins>readerId</ins>, фамилия, имя, адрес, деньРождения]};
- Категория книги: {[<ins>categoryId</ins>, название, parentCategoryId]}.

Связи:
- Забронировал: {[<ins>readerId</ins>, <ins>rentId</ins>, датаВозврата]};
- Присваивается: {[<ins>categoryId</ins>, <ins>ISBN</ins>]}

#### Задание 2.1:
Сущности:
- Страна: {[<ins>countryId</ins>]};
- Город: {[<ins>cityId</ins>, countryId]};
- Улица: {[<ins>streetId</ins>, cityId]};
- Дом: {[<ins>houseId</ins>, streetId]};
- Квартира: {[<ins>flatId</ins>, houseId]}.

#### Задание 2.2:
Сущности:
- Арбитр: {[<ins>refereeId</ins>]};
- Команда: {[<ins>teamId</ins>]}.

Связи:
- Проводят матч: {[<ins>refereeId</ins>, <ins>guestTeamId</ins>, <ins>homeTeamId</ins>]}.

#### Задание 2.3:
Сущности: 
- Мужчина: {[<ins>manId</ins>, fatherId, fatherId]};
- Женщина: {[<ins>womanId</ins>, fatherId, fatherId]}.

#### Задание 3:
Сущности:
- Связь: {[<ins>relationId</ins>, название]};
- Сущность: {[<ins>entityId</ins>, название]};
- Атрибут: {[<ins>atributeId</ins>, название, isKey]}.

Связи: 
- Участие: {[<ins>relationId</ins>, <ins>entityId</ins>, функциональность, роль, минимум, максимум]}.

### **_Задача 3._**
#### Задание 3.1:
Сущности: 
- Station: {[<ins>name</ins>, #tracks, cityName, cityRegion]};
- Train: {[<ins>trainNr</ins>, length, startStationName, endStationName]};
- City: {[<ins>name</ins>, <ins>region</ins>]}.

Связи: 
- Connected: {[<ins>trainNr</ins>, <ins>startStationName</ins>, <ins>endStationName</ins>, departure, arrival]}.

#### Задание 3.2:
Сущности: 
- StationPersonell: {[<ins>persNr</ins>, #name, statNr]};
- Caregiver: {[<ins>persNr</ins>, qualification]};
- Doctor: {[<ins>persNr</ins>, area, rank]};
- Station: {[<ins>statNr</ins>, name]};
- Room: {[<ins>roomNr</ins>, #beds, <ins>statNr</ins>]};
- Patient: {[<ins>patientNr</ins>, name, disease, doctorNr, admissionFrom, admissionTo, roomNr]}.