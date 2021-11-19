# Задание 8: Агроскин Александр, БПИ197

Для запуска нужно иметь установленный `Python>=3.6` с библиотеками:
* `Faker`
* `psycopg2`. 
* `SQLAlchemy`
* `marshmallow`
* `marshmallow-sqlalchemy`
* `Flask`
* `flask-marshmallow`
* `Flask-SQLAlchemy`

Программа запускается следующим образом:

```shell
flask run -db DATABASE_NAME -host DATABASE_HOST -port DATABASE_PORT -user DATABASE_USERNAME -pass DATABASE_PASSWORD
```

Программа поддерживает следующие типы запросов:

* `POST` на `{host}/generate/<type>`, где `<type>` является одним из `['reader', 'book', 'publisher', 'category', 'copy', 'book_category']`: случайно генерирует объект соответствующего типа.
* `POST`, `GET`, `PUT`, `DELETE` на `{host}/book` (или на `{host}/book/<isbn>` при надобности): стандартные CRUD операции для книг.
* `POST`, `GET`, `PUT`, `DELETE` на `{host}/copy` (или на `{host}/copy/<copy_nr>` при надобности): стандартные CRUD операции для копий.
* `POST`, `GET`, `PUT`, `DELETE` на `{host}/borrows` (или на `{host}/borrow/<borrow_nr>` при надобности): стандартные CRUD операции для записей о бронировании книги.
