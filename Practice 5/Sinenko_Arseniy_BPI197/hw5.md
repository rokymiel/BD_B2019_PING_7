# Задача 1

а) Показать все названия книг вместе с именами издателей.

    select title, publisher_name
	from books

б) В какой книге наибольшее количество страниц?

    select title
	from books
	where page_count in
	(select max(page_count)
	from books)
	
в) Какие авторы написали более 5 книг?

    select author
	from books
	group by author
	having count(*) > 5
	
г) В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?

    select title
	from books
	where books.page_count > 2 * (select avg(page_count) from books)
	
д) Какие категории содержат подкатегории?

    select distinct parent_name
	from categories
	
е) У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг? (не работает)

    select author
	from books
	group by author
	having count(*) = (select max(y.c)
	from (select author, count(*) as c
	from books
	group by author) y)


ж) Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?

    select reader_number
	from bookings
	inner join books on books.ISBN = bookings.ISBN
	where author = 'Марк Твен'
	group by reader_number
	having count(*) = (select count(ISBN) from books where author = 'Марк Твен')

з) Какие книги имеют более одной копии?

    select isbn
	from bookings
	group by isbn
	having count(*) > 1
	
и) ТОП 10 самых старых книг.

    select isbn
	from books
	order by year
	fetch first 10 rows only
	
к) Перечислите все категории в категории “Спорт” (с любым уровнем вложености).

	with recursive r as (
		select name
		from categories
		where name = 'Sport' or parent_name = 'Sport'
		union
		select c1.name
		from categories c1
		inner join r c2 on c2.name = c1.parent_name
	) select * from r

# Задача 2

а) В условии нет ничего про return_date, но база данных требует, чтобы она была не null, поэтому там CURRENT_DATE.

	insert into bookings(reader_number, isbn, copy_number, return_date)
	values((select number
	from readers
	where last_name = 'Петров'
		and first_name = 'Василий'
	fetch first 1 rows only),
	123456, 4, CURRENT_DATE)

б)

	delete from books
	where year > 2000

в) 

	update bookings set return_date = return_date + 30
	from book_categories where bookings.isbn = book_categories.isbn
    and book_categories.category_name = 'Базы данных'
    and bookings.return_date >= DATE('2016-01-01')

# Задача 3

1. Берем имена и номера студентов, у которых Note в их Check меньше 4 или вовсе нет записей.<br>
2. Берем номера, имена всех профессоров и их сумму кредитов по лекции (или курсу),<br>
	если за ними закреплена хотя бы одна лекция <br>
	и тех профессоров (номер, имя и 0 кредитов) за которыми нет закрепленной лекции.<br>
3. Берем имена студентов и максимальное количество Note, которое студент сделал на лекциях, но не меньше 4.<br>
<br>
Синенко Арсений БПИ197