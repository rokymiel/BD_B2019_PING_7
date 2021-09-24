# Задание 3
## Задача 1

## Задача 2
### Задача 2.1
CopyBook({[<ins>number: Int</ins>, position: int, book_isbn: int]})

taken({[<ins>reader_number: int, book_number: int</ins>, return_date: date]})

Reader({[<ins>number: int</ins>, surname: string, name: string, address: string, date_of_birth: date]})
  
Book({[<ins>isbn: int</ins>, year: int, name: string, author: string, pages_number: int, publisher_id: int]})
  
Publisher({[<ins>id:  int</ins>, name: string, address: string]})
  
belongs({[<ins>book_isbn: int, category_name: string</ins>]})
  
Category({[<ins>name: string</ins>, parent_category_name: string]})
### Задача 2.2
1. Пункт

Country({[<ins>id: int</ins>]})

City({[<ins>id: int</ins>, country_id: int]})

Street({[<ins>id: int</ins>, city_id: int]})

Building({[<ins>id: int</ins>, street_id: int]})

Flat({[<ins>id: int</ins>, building_id: int]})

2. Пункт

Arbitrator({[<ins>id: int</ins>]})

Team({[<ins>id: int</ins>, enemy_team_id: int, arbitrator_id: int]})

3. Пункт

Male({[<ins>id: in</ins>t, mother_id: int, father_id: int]})

Female({[<ins>id: int</ins>, mother_id: int, father_id: int]})
### Задача 2.3
Attribute({[<ins>id: int</ins>, name: string, isKey: bool, entity_name: string]})

Entity({[<ins>name: string</ins>]})

Relationship({[<ins>name: string</ins>]})

participate({[<ins>entity_name: string, relationship_name: string</ins>, functionality: string, role: string, min: int, max: int]})
## Задача 3
### Задача 3.1
Station({[<ins>name</ins>, traks, city]})

City({[<ins>region, name</ins>]})

Train({[<ins>trainNr</ins>, length, start_station_name, end_station_name]})

Connected({[<ins>trainNr, station_name</ins>, arrival_time, departure_time, next_station_name]})
### Задача 3.1
StationPersonell({[<ins>persNr</ins>, name, works_for_staNr]})

Caregiver({[<ins>persNr</ins>, qualification]})

Doctor({[<ins>persNr</ins>, area, rank]})

Station({[<ins>staNr</ins>, name]})

Room({[<ins>roomNr</ins>, beds, staNr]})

Patient({[<ins>patientNr</ins>, name, disease, doctorNr]})

Addmision({[<ins>patientNr</ins>, roomNr, from, admission_from, amission_to]})

