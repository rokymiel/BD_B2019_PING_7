# 1. SELECT
1. Show all book titles together with publisher names.
```sql
select title, publisher_name
from books;
```

2. Which book has the largest number of pages?
```sql
select title, author, page_count
from books
where page_count = (select max(page_count) from books);
```

3. Which authors have written more than 5 books?
```sql
select author, count(1)
from books
group by author
having count(1) > 5;
```

4. Which books have more than twice as many pages as the average number of pages for all books?
```sql
select title, author, page_count
from books
where page_count > 2 * (select avg(page_count) from books);
```

5. Which categories contain subcategories?
```sql
select distinct parent_name
from categories
where parent_name is not null;
```

6. Which author (assume authors' names to be unique) has the maximum number of books written?
```sql
select author, max(ac.count)
from (
    select author, count(*)
    from books
    group by author
) as ac
group by author;
```

7. What readers have borrowed all the books (not copies) authored by "Mark Twain"?
```sql
select title, author
from books
where author = 'Mark Twain';
```

8. Which books has more than one copy?
```sql
select title, author, count(*) copies
from books, copies
where books.isbn = copies.isbn
group by title, author, copies.isbn
having count(*) > 1;
```

9. What are the ten oldest books?  
--
10. Enumerate all categories under “Sports” category (on any distance from it).  
--


# 2. DML
1. Add a Borrowing record for reader ‘John Johnson’ and the book with ISBN 123456 and the copy number 4.
```sql
insert into borrowings
select reader_nr, isbn, copy_number, ''
from readers, copies
where readers.first_name = 'John' and
      readers.last_name = 'Johnson' and
      copies.isbn = '123456' and
      copies.copy_number = 4 ;
```

2. Delete all books with the Year of publish greater than 2000.
```sql
delete from books
where year > 2000;
```

3. Change the return date for all the books of the category "Databases" starting from 01.01.2016 so that they will be on borrow for 30 days longer (Assume that it’s possible to add numbers to dates in SQL).
```sql
update borrowings
set return_date = return_date + interval '30 day'
from book_categories cat
where cat.isbn = borrowings.isbn and
      cat.category_name = 'Databases' and
      return_date > '01.01.2016';
```

# 3. Reverse Engineering
1. Получить студентов, у которых нет оценок выше или равно 4.0 за любой тест
```sql
SELECT s.Name, s.StudID FROM Student s
WHERE NOT EXISTS (
SELECT * FROM Test c WHERE c.StudID = s.StudID AND c.Grade >= 4.0 ) ;
```

2. Список всех профессоров со списком кредитов по их лекциям (у кого нет лекций, стоит 0 кредитов)
```sql
( SELECT p.ProfNr, p.Name, sum(lec.Credit)
FROM Professor p, Lecture lec
WHERE p.ProfNr = lec.ProfNr
GROUP BY p.ProfNr, p.Name)
UNION
( SELECT p.ProfNr, p.Name, 0
FROM Professor p
WHERE NOT EXISTS (
SELECT * FROM Lecture lec WHERE lec.ProfNr = p.ProfNr ));
```

3. Студент с максимальной оценкой за любой тест
```sql
SELECT s.Name, p.Grade
FROM Student s, Lecture lec, Test c
WHERE s.StudID = c.StudID AND lec.LectNr = c.LectNr AND c.Grade >= 4
    AND c.Grade >= ALL (
      SELECT c1.Grade FROM Test c1 WHERE c1.StudID = c.StudID )
```