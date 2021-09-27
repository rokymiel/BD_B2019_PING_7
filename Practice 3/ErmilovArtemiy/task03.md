# Задание 3. Ермилов Артемий Васильевич, БПИ197

## Subtask 1:
Без ключей отношений в реляционных схемах невозможно <ins>однозначно установить</ins> , какие объекты эти отношения связывают, делая его непригодным для проектирования модели.

## Subtask 2:
### <b>Library</b>:

<b>Entities</b>:

* Reader: {[<ins>ReaderNumber</ins>: int, FirstName: string, LastName: string, Address: string, BirthDate: date]}

* BookCopy: {[<ins>BookCopyNumber</ins>: int, <ins>ISBN</ins>: int, Location: int]}

* Book: {[<ins>ISBN</ins>: int, Year: int, Name: string, Author: string, PageCount: int, PublisherID: int]}

* Publisher: {[<ins>PubID</ins>: int, Name: string, Address: string]}

* Category: {[<ins>CategoryID</ins>: int, Name: string, ParentCategoryID: int]}

<b>Relationships</b> (Publishes, IsCopy, IsSubCategory relationships are merged according to lecture rule #3):

* HasCategory (Book - Category): {[<ins>ISBN</ins>: int, <ins>CategoryID</ins>: int]}

* ChecksOut (Reader - BookCopy): {[<ins>BookCopyNumber</ins>: int, <ins>ISBN</ins>: int, <ins>ReaderNumber</ins>: int, ReturnDate: date]}

### <b>Countries and Cities and so on</b>:

<b>Entities</b>:

* Apartment: {[<ins>ApartmentID</ins>: int, HouseID: int]}

* House: {[<ins>HouseID</ins>: int, StreetID: int]}

* Street: {[<ins>StreetID</ins>: int, CityID: int]}

* City: {[<ins>CityID</ins>: int, CountryID: int]}

* Country: {<ins>CountryID</ins>: int}

<b>Relationships</b> are all merged according to rule #3.

### <b>Soccer Teams</b>:

<b>Entities</b>:

* Team: {[<ins>TeamID</ins>: int]}
* Referree: {[<ins>ReferreeID</ins>: int]}

<b>Relationships</b>:

* SoccerMatch: (Team - Team - Referree): {[<ins>Team1ID</ins>: int, <ins>Team2ID</ins>: int, ReferreeID: int]}

### <b>Sweet Home Alabama</b>:

<b>Entities</b>:

* Man: {[<ins>ManID</ins>, MotherID: int, FatherID: int]}
* Woman: {[<ins>WomanID</ins>: int, MotherID: int, FatherID: int]}

<b>Relationships</b> are all merged according to rule #3.


### <b>ER Model</b>:

<b>Entities</b>:

* Entity: {[<ins>EntityName</ins>: string]}
* Attribute: {[<ins>AttributeName</ins>: string, IsPartOfKey: bool, EntityName: string]}
* RelationShip: {[<ins>RelationshipName</ins>]}

<b>Relationships</b> (ContainsAttributes merged according to rule #3):

* PartakesInRelationship (Entity - RelationShip): {[<ins>EntityName</ins>: string, <ins>RelationshipName</ins>: string, Functionality: string, Role: string, Min: int, Max: int]}

## Subtask 3:

### Metro:

<b>Entities</b>:

* Station: {[<ins>Name</ins>: string, #Tracks: int, CityName: string, NextStationName: string, PreviousStationName: string]}

* City: {[<ins>Name</ins>: string, <ins>Region</ins>: string]}

* Train: {[<ins>TrainNr</ins>: int, Length: int, StartStationName: string, EndStationName: string, CurrentConnectionID: int]}

* Connected (Station - Station - Train): {[<ins>ConnectionID</ins>: int, <ins>StartStationName</ins>: string, <ins>EndStationName</ins>: string, Departure: date, Arrival: date]}

### Hospital:

<b>Entities</b>:

StationPersonell: {[<ins>PersNr</ins>: int, #Name: string, StationNr: int, EmployeeType: string]}

Caregiver: {[<ins>PersNr</ins>: int, Qualification: string]}

Doctor: {[<ins>PersNr</ins>: int, Area: string, Rank: int]}

Room: {[<ins>RoomNr</ins>: int, #Beds: int, StationNr: int]}

Station: {[<ins>StatNr</ins>: int, Name: string]}

Patient: {[<ins>PatientNr</ins>: int, Name: string, Disease: string, TreatingDoctorNr: int, AdmissionFrom: string, AdmissionTo: string, AdmissionRoomNr: int]}
