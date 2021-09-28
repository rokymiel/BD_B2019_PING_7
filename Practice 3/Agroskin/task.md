# Задание 3: Агроскин Александр, БПИ197
# №1:
Ключи отношений в реляционных схемах указывают, что и с чем это отношение связывает. Отношение, не имеющее ключей, не может участвовать в связях с другими элементами схемы (потому что не будет иметь способ уникально себя идентифицировать), а значит будет бесполезным в рамках общей структуры данных.

# №2:

(Здесь и в №3 я использую ***bold cursive*** для выделения ключей, потому что в маркдауне нет подчеркиваний.)

### Библиотека:
Entities:
* BookCopy: {[***CopyNumber***, ***ISBN*** ]}
* Book: {[***ISBN***, Name, Author, Year, NumberOfPages, PublisherId]}
* Publisher: {[***PublisherId***, Name, Address]}
* Category: {[***CategoryName***, SuperCategoryName]}
* Reader: {[***ReaderId***, Name, Surname, DateOfBirth, Address]}

Relationships (здесь и далее те, которые не были объединены согласно правилу №3 из презентации)
* HasCategory (Book <-> Category): {[***ISBN***, ***CategoryName***]}
* BorrowACopy (BookCopy <-> Reader): {[***CopyNumber***,***ISBN***, ***ReaderId***, ReturnDate]}

### Дома и улицы:
Entities:
* Flat: {[***FlatId***, HouseId]}
* House: {[***HouseId***, StreetId]}
* Street: {[***StreetId***, CityId]}
* City: {[***CityId***, CountryId]}
* Country: {[***CountryId***]}

### Футбольные команды:
Entities:
* Team: {[***TeamId***]}
* Arbiter: {[***ArbiterId***]}
Relationships:
* PlayingAMatch (Team <-> Team <-> Arbiter): {[***Team1Id***, ***Team2Id***, ArbiterId]}

### Отцы и дети:
Entities:
* Man: {[***Id***, MotherId, FatherId]}
* Woman {[***Id***, MotherId, FatherId]}

### ER-модель:
Entities:
* Entity: {[***Name***]}
* Attribute: {[***AttributeName***, IsPartOfKey, EntityName]}
* Relationship: {[***Name***]}
Relationships:
* ParticipateInARelationship (Entity <-> Relationship): {[***EntityName***, ***RelationshipName***, Functionality, Role, Minimum, Maximum]}

# №3:

### Метро:
Entities:
* Station: {[***Name***, #Tracks, CityName, NextStationName, PreviousStationName]}
* Train: {[***TrainNr***, Length, StartStationName, EndStationName, CurrentStationConnectionId]}
* City: {[***Name***, ***Region***]}
Relationships:
* Connected (Station <-> Station <-> Train): {[***ConnectionId***, ***StartStationName***, ***EndStationName***, Departure, Arrival]}

### Госпиталь:
Entities:
* StationPersonell: {[***PersNr***, #Name, StationNr, JobType (for the is-a, Caregiver or Doctor)]}
* Caregiver: {[***PersNr***, Qualification]}
* Doctor: {[***PersNr***, Area, Rank]}
* Patient: {[***PatientNr***, Name, Disease, TreatingDoctorNr, AdmissionFrom, AdmissionTo, AdmissionRoomNr]}
* Room: {[***RoomNr***, #Beds, StationNr]}
* Station: {[***StatNr***, Name]}
