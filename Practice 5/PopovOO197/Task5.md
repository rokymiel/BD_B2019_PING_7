### Task 1
1. SELECT b.Title, p.PubName FROM Book b, Publisher p WHERE b.PubName=p.PubName
2. SELECT * FROM Book b WHERE b.PagesNum >= ALL (SELECT b1.PagesNum FROM Book b1)
3. SELECT b.Author FROM Book b GROUP BY b.Author HAVING COUNT(*)>5
4. SELECT * FROM Book b where b.PagesNum > 2 * (SELECT AVG(b1.PagesNum) FROM Book)
5. SELECT c1.CategoryName FROM Category c1 WHERE EXIST (SELECT c2.CategoryName FROM Category c2 WHERE c1.CategoryName = c2.ParentCat)
6. SELECT b.Author FROM Book b GROUP BY b.Author ORDER BY COUNT(*) DESC LIMIT 1
Пояснение: таких авторов может быть несколько (с макс. кол-вом книг), но будет выбран 1 "самый везучий"
7. SELECT br.ReaderNr FROM Borrowing br, Book b WHERE b.ISBN=br.ISBN and b.Author="Марк Твен" GROUP BY br.ReaderNr HAVING COUNT(*)=COUNT(SELECT 1 FROM Book b1 WHERE b.Author="Марк Твен")
8. SELECT ISBN FROM Copy GROUP BY ISBN HAVING COUNT(*)>1
9. SELECT * FROM Book ORDER BY PubYear LIMIT 10
10. WITH getSubCatsOfSport(cat) AS (
	(SELECT c1.CategoryName FROM Category c1 WHERE ParentCat = "Спорт")
    UNION
	(SELECT c2.CategoryName FROM Category c2 WHERE c2.ParentCat = ANY (get CategoryName FROM getSubCatsOfSport))
) SELECT * FROM getSubCatsOfSport

### Task 2
1. INSERT INTO Borrowing VALUES((SELECT ID FROM Reader WHERE FirstName="Василий" AND LastName="Петров"), 123456, 4, NOW() + interval '7' day)
2. DELETE FROM Book WHERE PubYear>2000
3. UPDATE Borrowing SET ReturnDate = ReturnDate + interval '30' day WHERE EXIST(SELECT * FROM BookCat WHERE CategoryName="Базы данных" AND Book.ISBN=BookCat.ISBN)

### Task 3
1. Возвращает Name и MatrNr студентов для которых нет записей в Check с таким же MatrNr и Note больше или равным 4м.
2. Возвращает ProfNr, Name и сумму кредитов(Credit) лекций преподавателя, которые он проводил. Преподаватели, не проводящие лекции, также будут возвращены(их Name и ProfNr), в их случае сумма кредитов лекции будет = 0.
3. Возвращает Name студента и его за оценку за тесты по лекциям, за которые он получил как минимум 4 и при этом эта оценка была наибольшей(не было оценки выше) среди всех оценок за все тесты этого студента.