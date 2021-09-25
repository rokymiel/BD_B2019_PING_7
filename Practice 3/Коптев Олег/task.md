# Задание 3
## Задача 1
### Условия
Почему любое отношение в реляционной схеме имеет по крайней мере один ключ?
### Решение
Главной особенностью реляционной схемы является отношение между различными сущностями. Для реализации возможности ссылаться на конкретную запись в таблице, необходимо иметь возможность уникально идентифицировать эти записи, следовательно для этого и нужен хотя бы один ключ.

## Задача 2
### Условия
Переведите все диаграммы ER из предыдущей домашней работы в реляционные схемы.
### Решение
#### Задача 1
Book: {[<u>ISBN</u>, Title, Year, Author, NumberOfPages, PublisherId]}  
Category: {[<u>CategoryId</u>, CategoryName, parentCategory]}  
Publisher: {[<u>PublisherId</u>, Name, Address]}  
Copy: {[<u>ISBN, CopyNumber</u>, Position]}  
Reader: {[<u>ReaderNumber</u>, LastName, FirstName, Address, Birthday]}  
categoryBelonging: {[<u>ISBN, CategoryId</u>]}  
borrowing: {[<u>ISBN, CopyNumber, ReaderNumber</u>, ReturnDate]}  

#### Задача 2
##### Задача 2.1
Country: {[<u>CountryId</u>]}  
City: {[<u>CityId, CountryId</u>]}  
Street: {[<u>StreetId, CityId, CountryId</u>]}  
House: {[<u>HouseId, StreetId, CityId, CountryId</u>]}  
Apartment: {[<u>ApartmentId, HouseId, StreetId, CityId, CountryId</u>]}  
##### Задача 2.2
Player: {[<u>PlayerId</u>]}  
Referee: {[<u>RefereeId</u>]}  
Relation: {[<u>Host, Guest</u>, RefereeId]}  
##### Задача 2.3
Man: {[<u>ManId</u>]}  
Woman: {[<u>WomanId</u>]}  

isSon: {[<u>isSonId</u>, Son, Father, Mother]}  
isDaughter: {[<u>isDaughterId</u>, Daughter, Father, Mother]}  
#### Задача 3
Entity: {[<u>EntityId</u>, Name]}  
Relationship: {[<u>Name</u>]}  
AttributeOfEntity: {[<u>AttributeOfEntityId, EntityId</u>, Name, isPartOfKey]}  
AttributeOfRelationship: {[<u>AttributeOfRelationship, RelationshipId</u>, Name]}  
WeakEntity: {[<u>WeakEntityId, EntityId</u>]}  
RelationshipOfWeakEntity: {[<u>RelationshipId, WeakEntityId</u>, Functionality, Role, Min, Max]}  

ParticipateEntityAndRelationship: {[<u>EntityId, RelationshipId</u>, Functionality, Role, Min, Max]}  

## Задача 3
### Условия
Переведите приведенные диаграммы ER в реляционные схемы. [3.1.](https://imgur.com/w2iDI1o) [3.2.](https://imgur.com/oFBM5pp)
### Решение
#### Задача 3.1
City: {[<u>Name, Region</u>]}  
Train: {[<u>TrainNr</u>, Length]}  
Station: {[<u>Name</u>, TracksAmount]}  

LieIn: {[CityName, CityRegion, <u>StationName</u>]}  
Connected: {[Station1, Station2, <u>TrainNr</u>, Departure, Arrival]}  
Start: {[Name, <u>TrainNr</u>]}  
End: {[Name, <u>TrainNr</u>]}  

#### Задача 3.2
Station: {[<u>StatNr</u>, Name]}  
Room: {[<u>RoomNr, StatNr</u>, BedsAmount]}  
StationPersonell: {[<u>PersNr</u>, Name]}  
Caregiver: {[<u>PersNr</u>, Qualification]}  
Doctor: {[<u>PerNr</u>, Area, Rank]}  
Patient: {[<u>PatientNr</u>, Name, Disease]}  

WorksFor: {[StatNr, <u>PersNr</u>]}  
Admission: {[StatNr, RoomNr, <u>PatientNr</u>, from, to]}  
Treats: {[PersNr, <u>PatientNr</u>]}  
