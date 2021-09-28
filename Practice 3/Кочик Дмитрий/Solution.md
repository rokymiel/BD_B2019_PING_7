### Часть 1.
Потому что соотношение описывает соответсвие одного объекта другому или другим. 
Если мы не имеем хотя бы одного ключа, т.е. уникального идентификатора одного
объекта, то такое соотношение не будет нести никакой полезной информации
о хранимых данных, потому что мы не сможем вычислить объекты,
участвующие в соотношении. 
### Часть 2.
#### №1
Reader: {[**User_id**, Name, Surname, Address. Birth_date]}  
Rented: {[**User_id**, **Copy_id**, **Return_date**]}  
Book_copy: {[**Copy_id**, Position, ISBN]}  
Is_copy_of: {[**Copy_id**, ISBN]}  
Book: {[**ISBN**, Author, Pages, Year]}  
Has_category: {[**Category_name**, **ISBN**]}  
Category: {[**Category_name**]}  
Is_child_of: {[**Parent_category_name**, **Parent_category_name**]}  
Has_publisher: {[Publisher_name, **ISBN**]}  
Publisher: {[**Name**, Address]}  
#### №2
Country: {[**Name**]}  
Is_located_in_country: {[Country_name, **City_name**]}  
City: {[**Name**]}  
Is_located_in_city: {[City_name, **Street_name**]}  
Street: {[**Name**]}  
Is_located_in_street: {[Street_name, **House_number**]}  
House: {[**Number**]}  
Is_located_in_house: {[House_number, **Flat_number**]}  
Flat: {[**Number**]}  

Referee: {[**Referee_id**]}  
Play: {[**Referee_id**, **Team1_name**, **Team2_name**]}  
Team: {[**Team_name**]}  

Man: {[**Man_id**, father_id, mother_id]}  
Woman: {[**Woman_id**, father_id, mother_id]}  
#### №3
Attribute: {[**Text**, Is_key]}   
Has_attribute: {[**Attribute_name**, **Entity_name**]}  
Entity: {[**Text**]}  
Participate: {[**Entity_name**, **Relationship_name**, functionality, role, min, max]}  
Relationship: {[**Text**]}  
Has_relationship_attribute: {[**Attribute_name**, **Relationship_name**]}  
Relationship_attribute: {[**Text**]}  
### Часть 3.
#### №1
Station: {[**Name**, Tracks]}  
Connected: {[**Station_name1**, **Station_name1**, **TrainNr**, Departure, Arrival]}  
Start: {[**Station_name**, **TrainNr**]}  
End: {[**Station_name**, **TrainNr**]}  
Train: {[**Station_name**, Length]}  
Lie_in: {[City_name, **Station_name**]}  
City: {[**Name**, Region]}  
#### №2
StationPersonell: {[**PersNr**, #Name]}  
Caregiver: {[**PersNr**, Qualification]}  
Doctor: {[**PersNr**, Area, Rank]}  
Treats: {[**PersNr**, **PatientNr**]}  
Patient: {[**PatientNr**, Name, Disease]}  
Addmission: {[**RoomNr**, **PatientNr**, **From**, **To**]}  
Room: {[**RoomNr**, #Beds]}  
Has: {[**StatNr**, **RoomNr**]}  
Station: {[**StatNr**, Name]}  
Work_for: {[**StatNr**, PersNr]}  
