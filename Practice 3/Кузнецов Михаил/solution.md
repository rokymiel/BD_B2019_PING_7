# Задание 3
## Задача 1

Докажем от противного. 

Допустим существует отношение, в котором нет ни одного ключа. 
Тело отношения состоит только из уникальных данных (по определению). То есть в таблице каждая запись является уникальным объектом со своими значениями атрибутов. Чтобы идентифицировать объект необходимо обратиться к какому-то уникальному набору значений, которого у других объектов нет. Значит у объекта есть атрибут(ы) с уникальным(и) значением/ями, что и есть ключ(и)=> противоречие. 

Если же определять уникальность не по значению каких-то ключей, а по всем данным объекта, то по сути мы прийдем к тому, что все атрибуты это ключи 

## Задача 2
### Задача 2.1
CopyBook({[<ins>number: Int</ins>, position: int, book_isbn: int]})

taken({[<ins>reader_number: int, book_number: int, return_date: date</ins>]})

Reader({[<ins>number: int</ins>, surname: string, name: string, address: string, date_of_birth: date]})
  
Book({[<ins>isbn: int</ins>, year: int, name: string, author: string, pages_number: int, publisher_id: int]})
  
Publisher({[<ins>id:  int</ins>, name: string, address: string]})
  
belongs({[<ins>book_isbn: int, category_name: string</ins>]})
  
Category({[<ins>name: string</ins>, parent_category_name: string]})
### Задача 2.2
1. Пункт

Country({[<ins>id: int</ins>]})

City({[<ins>id: int, country_id: int</ins>]})

Street({[<ins>id: int, city_id: int, country_id: int</ins>]})

Building({[<ins>id: int, street_id: int, city_id: int, country_id: int</ins>]})

Flat({[<ins>id: int, building_id: int, street_id: int, city_id: int, country_id: int</ins>]})

2. Пункт

#### Если один (арбитр) ко многим

Arbitrator({[<ins>id: int</ins>]})

Team({[<ins>id: int</ins>, enemy_team_id: int, arbitrator_id: int]})

#### Если многий ко многим

Arbitrator({[<ins>id: int</ins>]})

Team({[<ins>id: int</ins>]})

Game({<ins>arbitrator_id: int, host_id: int, guest: id</ins>})

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
### Задача 3.2
StationPersonell({[<ins>persNr</ins>, name, works_for_staNr]})

Caregiver({[<ins>persNr</ins>, qualification]})

Doctor({[<ins>persNr</ins>, area, rank]})

Station({[<ins>staNr</ins>, name]})

Room({[<ins>roomNr, staNr</ins>, beds, staNr]})

Patient({[<ins>patientNr</ins>, name, disease, persNr]})

Addmision({[<ins>patientNr</ins>, roomNr, from, admission_from, amission_to]})

