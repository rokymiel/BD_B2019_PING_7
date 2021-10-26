### 1
1. select Title, Author from Book
2. select max(PagesNum) from Book
3. select Author from Book group by Author having count(*) > 5
4. select ISBN from Book where PagesNumb > 2 * (select avg(PagesNumb) from Book)
5. select distinct ParentCat from Category where ParentCat is not null 
6. select Author from Book group by Author order by count(*) desc limit 1
7. select (select ISBN from Book where Author = 'Марк Твен') 
8. select ISBN from Copy group by ISBN having count(*) > 1
9. select ISBN from Book order by PubYear limit 10
10. with Req(First, Second) as (select CategoryName, ParentCat from Category where ParentCat = 'Спорт' union all select c.CategoryName, r.First from Req r, Category c where c.ParentCat = r.First)
select First from Req

### 2
1. insert into Borrowing select ReaderNr, ISBN, CopyNumber from Reader, Copy where LastName = 'Петров' and FirstName = 'Василий' and ISBN = '123456'
2. delete Book where PubYear > 2000
3. update Borrowing set ReturnDate = ReturnDate + 30 where ISBN in (select ISBN from BookCat where CategoryName = 'Базы данных') and ReturnDate >= '2016-01-01'

### 3
1. Выбираем Name и MatrNr таких студентов, для которых не существует тестов, которые они написали на 4 и лучше. Т.е. выбираются студенты троечники.
2. Получаем информацию об идентификаторе профессора, его имени и сумме кредитов всех читаемых им лекций. Если он не читает ни одну лекцию, сумма кредитов равна 0.
3. Выбираем пары студентов и таких оценок за тест, что за все время обучения у студента не было более высоких оценок, а сама оценка равна 4 или 5.
