# Курс "Базы данных"

## Задание 3
 
### задача 1

Если бы у отношений не было бы ни одного ключа, то установить соответствие между объектами, которые связаны отношениями, было бы нельзя.
 
 
### задача 2

#### Библиотека

<b>Сущности</b>:

* Book: {[<ins>ISBN</ins>, PublishYear, Name, Author, PageCount, PublisherId]}
* CopyBook: {[<ins>ISBN</ins>, <ins>CopyNumber</ins>, Position]}
* Category: {[<ins>CategoryId</ins>, Name, SubCategoryId]}
* Publisher: {[<ins>PublisherId</ins>, Name, Address]}
* Reader: {[<ins>ReaderId</ins>, FirstName, SecondName, Address, BithDate]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 

* HasCategory (Book <> Category): {[<ins>ISBN</ins>, <ins>CategoryId</ins>]}
* BookingCopyBook (Reader <> CopyBook): {[<ins>CopyNumber</ins>, <ins>ISBN</ins>, <ins>ReaderId</ins>, ReturnDate]}

#### Квартира-Дом-Улица..

<b>Сущности</b>:

* Flat: {[<ins>FlatId</ins>, BuildingId, StreetId, TownId, CountryId]}
* Building: {[<ins>BuildingId</ins>, StreetId, TownId, CountryId]}
* Street: {[<ins>StreetId</ins>, TownId, CountryId]}
* Town: {[<ins>TownId</ins>], CountryId}
* Country: {[<ins>CountryId</ins>]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 

#### Футбол

<b>Сущности</b>:

* Referree: {[<ins>ReferreeId</ins>]}
* Team: {[<ins>TeamId</ins>]}

<b>Связи</b>:

* Play: (Team <> Team <> Referree): {[<ins>HostTeamId</ins>, <ins>GuestTeamId</ins>, ReferreeId]}

#### Мужчина\Женщина

<b>Сущности</b>:

* Male: {[<ins>MaleId</ins>], MomId, DadId}
* Female: {[<ins>FemaleId</ins>, MomId, DadId]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 

#### ER-Модель

<b>Сущности</b>:

* Entity {[<ins>EntityName</ins>]}
* Attribute: {[<ins>AttributeName</ins>, EntityName, IsPartOfKey]}
* RelationShip: {[<ins>RelationshipName</ins>]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 

* ParticipationInRelationship (Entity <> RelationShip): {[<ins>EntityName</ins>, <ins>RelationshipName</ins>, Role, Min, Max, Functionality]}


### задача 3

#### Электричка

<b>Сущности</b>:

* City {[<ins>Name</ins>, <ins>Region</ins>]}
* Station {[<ins>Name</ins>, #Tracks, CityName, RegionName, NextStationName, PreviousStationName]}
* Train {[<ins>TrainNr</ins>, Length, StartStationName, EndStationName]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 

* Connected (Station <> Station <> Train): {[<ins>TrainNr</ins>, <ins>StartStationName</ins>, <ins>EndStationName</ins>, Departure, Arrival]}

#### Больница

<b>Сущности</b>:

* Station {[<ins>StatNr</ins>, Name]}
* Room {[<ins>RoomNr</ins>, #Beds, StatNr]}
* Patient {[<ins>PatientNr</ins>, Name, Disease, AdmissionFrom, AdmissionTo, RoomNr, PersNr]}
* Doctor {[<ins>PersNr</ins>, Rank, Area]}
* Caregiver {[<ins>PersNr</ins>, Qualification]}
* StationPersonell {[<ins>PersNr</ins>, #Name, StatNr, StaffType]}

<b>Связи</b>:

По 3-лекционному правилу простые связи были соединены 