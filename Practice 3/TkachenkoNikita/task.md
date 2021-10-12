# Задание 3: Ткаченко Никита, БПИ197
## Пункт №1:
Любое отношение в реляционной схеме имеет по крайней мере один ключ для того, чтобы единственным образом установить какие объекты связывают эти отношения. Значит, не имея ключей, оно не сможет участвовать в связях с другими элементами схемы, так как такое отношение невозможно будет определить и, как следствие, оно будет бесполезно для схемы.


## Пункт №2:
### Библиотека:
Entities:
* Publisher: {[<ins>PublisherId</ins>, Name, Address]}
* Book: {[<ins>ISBN</ins>,YearOfPublication, Name, Author, NumberOfPages, PublisherId]}
* BookCopy: {[<ins>CopyNumber</ins>, <ins>ISBN</ins>, Position]}
* Category: {[<ins>CategoryName</ins>, SubCategoryName]}
* Reader: {[<ins>ReaderId</ins>, Name, Surname, Address, DateOfBirth]}
*Relationships (according to rule #3):*
* HasCategory (Book <-> Category): {[<ins>ISBN</ins>, <ins>CategoryName</ins>]}
* TakeACopy (Reader <-> BookCopy): {[<ins>ISBN</ins>, <ins>CopyNumber</ins>, <ins>ReaderId</ins>, ReturnDate]}

### Дома и улицы:
Entities:
* Flat: {[<ins>FlatId</ins>, <ins>HouseId</ins>, <ins>StreetId</ins>,
<ins>CityId</ins>, <ins>CountryId</ins>]}
* House: {[<ins>HouseId</ins>, <ins>StreetId</ins>, <ins>CityId</ins>, <ins>CountryId</ins>]}
* Street: {[<ins>StreetId</ins>, <ins>CityId</ins>, <ins>CountryId</ins>]}
* City: {[<ins>CityId</ins>, <ins>CountryId</ins>]}
* Country: {[<ins>CountryId</ins>]}

### Футбольные команды:
Entities:
* Team: {[<ins>TeamId</ins>]}
* Arbiter: {[<ins>ArbiterId</ins>]}
*Relationships (according to rule #3):*
* PlayAMatch (Team <-> Team <-> Arbiter): {[<ins>Team1Id</ins>, <ins>Team2Id</ins>, ArbiterId]}

### Отцы и дети:
Entities:
* Man: {[<ins>ManId</ins>, MotherId, FatherId]}
* Woman {[<ins>WomanId</ins>, MotherId, FatherId]}

### ER-модель:
Entities:
* Entity: {[<ins>Name</ins>]}
* Attribute: {[<ins>Name</ins>, IsPartOfKey, EntityName]}
* Relationship: {[<ins>Name</ins>]}
*Relationships (according to rule #3):*
* BeInARelationship (Entity <-> Relationship): {[<ins>EntityName</ins>, <ins>RelationshipName</ins>, Role, Min, Max, Functionality]}


## Пункт №3:
### Метро:
Entities:
* Station: {[<ins>Name</ins>, #Tracks, CityName, NextStationName, PreviousStationName]}
* City: {[<ins>Name</ins>, <ins>Region</ins>]}
* Train: {[<ins>TrainNr</ins>, Length, StartStationName, EndStationName, ConnectionId]}
*Relationships (according to rule #3):*
* Connected (Station <-> Station <-> Train): {[<ins>ConnectionId</ins>, <ins>StartStationName</ins>, <ins>EndStationName</ins>, Arrival, Departure]}

### Госпиталь:
Entities:
* StationPersonell: {[<ins>PersNr</ins>, #Name, StationNr]}
* Caregiver: {[<ins>PersNr</ins>, Qualification]}
* Doctor: {[<ins>PersNr</ins>, Area, Rank]}
* Patient: {[<ins>PatientNr</ins>, Name, Disease, PersNr, AdmissionFrom, AdmissionTo, AdmissionRoomNr]}
* Station: {[<ins>StatNr</ins>, Name]}
* Room: {[<ins>RoomNr</ins>, #Beds, <ins>StationNr</ins>]}
