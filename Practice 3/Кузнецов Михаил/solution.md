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
## Задача 3
### Задача 3.1
### Задача 3.1
