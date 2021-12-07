from dataclasses import dataclass

from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
db = SQLAlchemy(app)


class Reader(db.Model):
    __tablename__ = "Readers"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    last_name = db.Column(db.String)
    first_name = db.Column(db.String)
    address = db.Column(db.String)
    birth_date = db.Column(db.Date)

    def __init__(self, last_name, first_name, address, birth_date):
        self.last_name = last_name
        self.first_name = first_name
        self.address = address
        self.birth_date = birth_date

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class Book(db.Model):
    __tablename__ = "Books"
    isbn = db.Column(db.String, primary_key=True)
    title = db.Column(db.String)
    author = db.Column(db.String)
    pages_num = db.Column(db.Integer)
    pub_year = db.Column(db.Integer)
    pub_name = db.Column(db.String)

    def __init__(self, isbn, title, author, pages_num, pub_year, pub_name):
        self.isbn = isbn
        self.title = title
        self.author = author
        self.pages_num = pages_num
        self.pub_year = pub_year
        self.pub_name = pub_name

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class Publisher(db.Model):
    __tablename__ = "Publishers"
    pub_name = db.Column(db.String, primary_key=True)
    pub_address = db.Column(db.String)

    def __init__(self, pub_name, pub_address):
        self.pub_name = pub_name
        self.pub_address = pub_address

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class Category(db.Model):
    __tablename__ = "Categories"
    category_name = db.Column(db.String, primary_key=True)
    parent_category = db.Column(db.String)

    def __init__(self, category_name, parent_category):
        self.category_name = category_name
        self.parent_category = parent_category

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class Copy(db.Model):
    __tablename__ = "Copies"
    isbn = db.Column(db.String, primary_key=True)
    copy_number = db.Column(db.Integer, primary_key=True)
    shelf_position = db.Column(db.String)

    def __init__(self, isbn, copy_number, shelf_position):
        self.isbn = isbn
        self.copy_number = copy_number
        self.shelf_position = shelf_position

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class Borrows(db.Model):
    __tablename__ = "Borrowing"
    borrows_id = db.Column(db.Integer, primary_key=True)
    reader_nr = db.Column(db.Integer)
    isbn = db.Column(db.String)
    copy_number = db.Column(db.Integer)
    return_date = db.Column(db.Date)

    def __init__(self, reader_nr, isbn, copy_number, return_date):
        self.reader_nr = reader_nr
        self.isbn = isbn
        self.copy_number = copy_number
        self.return_date = return_date

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


@dataclass
class BookCategory(db.Model):
    __tablename__ = "BookCategories"
    isbn = db.Column(db.String, primary_key=True)
    category_name = db.Column(db.String, primary_key=True)

    def __init__(self, isbn, category_name):
        self.isbn = isbn
        self.category_name = category_name

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self


db.create_all()
