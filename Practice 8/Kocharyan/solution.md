# Задание №8. Кочарян Тигран БПИ197

## Задача 1
Возьмите схему библиотечной системы из задания 2, и создайте на ее основе таблицы, лучше на основе миграций.

```sql
CREATE TABLE if not exists Reader(
  ID INTEGER primary key,
  LastName TEXT,
  FirstName TEXT,
  Address TEXT,
  BirthDate DATE
);

CREATE TABLE if not exists Book(
  ISBN VARCHAR(17) primary key,
  Title TEXT,
  Author TEXT,
  Pages INTEGER,
  PubYear INTEGER,
  PubName TEXT references Publisher(PubName)
);

CREATE TABLE if not exists Publisher(
  PubName TEXT primary key,
  PubAdress TEXT
);

CREATE TABLE if not exists Category(
  CategoryName TEXT primary key,
  ParentCat TEXT references Category(CategoryName)
);

CREATE TABLE if not exists Copy(
  ISBN VARCHAR(17) references Book(ISBN),
  CopyNumber INTEGER,
  ShelfPosition INTEGER,
  primary key(ISBN, CopyNumber)
);

CREATE TABLE if not exists Borrowing(
  ReaderNr INTEGER references Reader(ID),
  ISBN VARCHAR(17) references Copy(ISBN),
  CopyNumber INTEGER references Copy(CopyNumber),
  ReturnDate DATE,
  primary key(ReaderNr, ISBN, CopyNumber)
);

CREATE TABLE if not exists BookCat(
  ISBN VARCHAR(17) references Book(ISBN),
  CategoryName TEXT references Category(CategoryName),
  primary key(ISBN, CategoryName)
);
```