
# Задание №3


## Ионко Максим Олегович БПИ197


## Задача №1
Так как необходимо иметь уникальные ссылки на каждую сущность. Если же использовать не ключ, то нет никакой гарантии, что выбранный атрибут является уникальным.


## Задача №2
### 1)
### Entities:

* Book: {[*ISBN, title, author, number of pages, category, publisher*]}

* Book Copy: {[*id, shelf position*]}

* Reader: {[*first name, second name, address, birthday*]}

* Category: {[*name*]}

* Publisher: {[*address, name*]}

### Relationships:

* relaton: {[*Book id, Book copy id*]}

* published by: {[*publisher id, book id*]}

* belongs to: {[*category id, book id*]}

* borrows: {[*reader id, book copy id, return date*]}

### 2)
### 1. 
* Flat: {[*id, building id*]}
* Building: {[*id, street id*]}
* Street: {[*id, city id*]}
* City: {[*id, country id*]}
* Country: {[*id*]}

### 2. 
### Entities: 
* Team: {[*id, isGuest*]}
* Referee: {[*id*]}

### Relationships:
* playes: {[*first team id, second team id, referee id*]}

### 3.
### Entities: 
* Man: {[*id, father id, mother id*]}
* Woman: {[*id, father id, mother id*]}

### Relationships:
* isSon: {[*man/woman id, man id*]}
* is Daughter: {[*man/woman id, woman id*]}

### 3)
### Entities: 
* Enitity: {[*id, name, isWeakEntity*]}
* Attributes: {[*id, name, isPartOfKey]}
* Relationship: {[*id, name]}

### Relationships:
* connects: {[*entity id, relation id, role, functionality min, max*]}


## Задача №3
## 1
### Entities: 
* Station: {[*id, name, tracks, city id*]}
* Train: {[*trainNr, length, start station id, end station id*]}
* City: {[id, region, name]}

### Relationships:
* connected: {[train id, station id, arriaval, departure]}

## 2
### Entities: 
* Station: {[id, StatNr, ]}




