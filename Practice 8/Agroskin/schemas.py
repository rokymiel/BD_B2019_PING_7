from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from marshmallow import fields
from marshmallow_sqlalchemy import SQLAlchemyAutoSchema

app = Flask(__name__)
db = SQLAlchemy(app)
from models import *


class ReaderSchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Reader
        sqla_session = db.session

    id = fields.Number(required=True)
    last_name = fields.String(required=True)
    first_name = fields.String(required=True)
    address = fields.String(required=True)
    birth_date = fields.Date(required=True)


class BookSchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Book
        sqla_session = db.session

    isbn = fields.String(required=True)
    title = fields.String(required=True)
    author = fields.String(required=True)
    pages_num = fields.Integer(required=True)
    pub_year = fields.Integer(required=True)
    pub_name = fields.String(required=True)


class PublisherSchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Publisher
        sqla_session = db.session

    pub_name = fields.String(required=True)
    pub_address = fields.String(required=True)


class CategorySchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Category
        sqla_session = db.session

    category_name = fields.String(required=True)
    parent_category = fields.String(required=True)


class CopySchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Copy
        sqla_session = db.session

    isbn = fields.String(required=True)
    copy_number = fields.Integer(required=True)
    pub_address = fields.String(required=True)


class BorrowsSchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = Borrows
        sqla_session = db.session

    reader_nr = fields.Integer(required=True)
    isbn = fields.String(required=True)
    copy_number = fields.Integer(required=True)
    return_date = fields.Date(required=True)


class BookCategorySchema(SQLAlchemyAutoSchema):
    class Meta(SQLAlchemyAutoSchema.Meta):
        model = BookCategory
        sqla_session = db.session

    isbn = fields.String(required=True)
    category_name = fields.String(required=True)
