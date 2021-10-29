## Задача 1

Ключи нужны для уникального идентифицирования сущностей в базе. Отношения между ними невозможны без ключей, так как нуждыются в указаниях на связываемые сущности, причем указание должно быть уникально.

## 
Задача 2

### Библиотека

**Book**: {[**isbn**, year, name, author, num_pages, publisher_id]}

**Copy**: {[**copy_id**, **isbn**]}

**Publisher**: {[**publisher_id**, name, address]}

**Category**: {[**category_name**, super_category_name]}

**Reader**: {[**reader_id**, name, surname, date_of_birth, address]}

Borrowed A Copy: {[**reader_id**, **copy_id**, **isbn**, date_of_return]}

Has Category: {[**isbn**, **category_name**]}

### Дома и улицы

**Country**: {[**country_name**]}

**City**: {[**city_id**, city_name, country_id]}

**Street**: {[**street_id**, street_name, city_id]}

**House**: {[**house_id**, number, street_id]}

**Flat**: {[**flat_id**, number, house_id]}

### Футбол

**Team**: {[**team_id**, name]}

**Referee**: {[**ref_id**, name]}

Playing: {[**team1_id**, **team2_id**, **ref_id**]}

### Отцы и дети

**Man**: {[**man_id**, father_id, mother_id]}

**Woman**: {[**woman_id**, father_id, mother_id]}

### ER диаграмма

**Entity**: {[**entity_id**, name]}

**Attribute**: {[**attribute_id**, name, entity_id, is_key]}

**Relationship**: {[**relationship_id**, name]}

InARelationship: {[**entity_id**, **relationship_id**, functionality, role, minimum, maximum]}


## Задача 3

### (Я ненавижу) Вокзалы и поезда

**Station**: {[**station_id**, previous, next, city_id, tracks]}

**City**: {[**city_id**, **region_id**, name]}

**Train**: {[**train_id**, length, departure_station_id, arrival_station_id]}

Connected: {[**station1_id**, **station2_id**, **train_id**, departure, arrival]}

### Больничка

**StationPersonnel**: {[**pers_id**, name, station_id]}

**Station**: {[**station_id**, name]}

**Doctor**: {[**doctor_id**, area, rank]}

**Caregiver**: {[**caregiver_id**, qualification]}

**Room**: {[**room_id**, beds, station_id]}

**Patient**: {[**patient_id**, name, disease, station_id, room_id, admission_from, admission_to, doctor_id]}
