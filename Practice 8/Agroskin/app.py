from argparse import ArgumentParser

from flask import Flask, make_response, jsonify, request
from flask_sqlalchemy import SQLAlchemy

arg_parser = ArgumentParser()
arg_parser.add_argument("host", help="Host of the database")
arg_parser.add_argument("port", help="Port of the database")
arg_parser.add_argument("db", help="Name of the database")
arg_parser.add_argument("user", help="Database user login")
arg_parser.add_argument("pass", help="Database user password")

args = arg_parser.parse_args()

app = Flask(__name__)
app.config[
    'SQLALCHEMY_DATABASE_URI'] = f"postgresql+psycopg2://{args.user}:{args.password}@{args.host}:{args.port}/{args.db}"
db = SQLAlchemy(app)

import generators
from schemas import *


@app.route('/generate/<obj_type>', methods=["POST"])
def generate_date(obj_type):
    try:
        obj = generators.object_generators[obj_type]
        obj.create()
        return make_response(jsonify({'created': obj}), 200)
    except KeyError:
        return make_response(jsonify({'result': "no such table in database"}), 400)


@app.route('/book/<isbn>', method=["GET"])
def get_book(isbn):
    book = Book.query.get(isbn)
    book_schema = BookSchema()
    book = book_schema.dump(book)
    return make_response(jsonify({"result": book}), 200)


@app.route('/book/', method=["POST"])
def post_book():
    data = request.get_json()
    schema = BookSchema()
    book = schema.load(data)
    result = schema.dump(book.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/book/<isbn>', method=["PUT"])
def put_book(isbn):
    data = request.get_json()
    book = Book.query.get(isbn)
    if book:
        db.session.delete(book)
        db.session.commit()
    schema = BookSchema()
    book = schema.load(data)
    result = schema.dump(book.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/book/<isbn>', method=["DELETE"])
def delete_book(isbn):
    book = Book.query.get(isbn)
    db.session.delete(book)
    db.session.commit()
    return make_response("", 204)


@app.route('/copy/<copy_nr>', method=["GET"])
def get_copy(copy_nr):
    copy = Copy.query.get(copy_nr)
    copy_schema = CopySchema()
    copy = copy_schema.dump(copy)
    return make_response(jsonify({"result": copy}), 200)


@app.route('/copy/', method=["POST"])
def post_copy():
    data = request.get_json()
    schema = CopySchema()
    copy = schema.load(data)
    result = schema.dump(copy.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/copy/<copy_nr>', method=["PUT"])
def put_copy(copy_nr):
    data = request.get_json()
    copy = Copy.query.get(copy_nr)
    if copy:
        db.session.delete(copy)
        db.session.commit()
    schema = CopySchema()
    copy = schema.load(data)
    result = schema.dump(copy.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/copy/<copy_nr>', method=["DELETE"])
def delete_copy(copy_nr):
    copy = Copy.query.get(copy_nr)
    db.session.delete(copy)
    db.session.commit()
    return make_response("", 204)


@app.route('/borrow/<borrow_nr>', method=["GET"])
def get_borrow(borrow_nr):
    borrow = Borrows.query.get(borrow_nr)
    borrow_schema = BorrowsSchema(only=["reader_nr", "isbn", "copy_nr"])
    borrow = borrow_schema.dump(borrow)
    return make_response(jsonify({"result": borrow}), 200)


@app.route('/borrow/', method=["POST"])
def post_borrow():
    data = request.get_json()
    schema = BorrowsSchema()
    borrow = schema.load(data)
    result = schema.dump(borrow.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/borrow/<borrow_nr>', method=["PUT"])
def put_borrow(borrow_nr):
    data = request.get_json()
    borrow = Borrows.query.get(borrow_nr)
    if borrow:
        db.session.delete(borrow)
        db.session.commit()
    schema = BorrowsSchema()
    borrow = schema.load(data)
    result = schema.dump(borrow.create())
    return make_response(jsonify({"result": result}), 200)


@app.route('/borrow/<borrow_nr>', method=["DELETE"])
def delete_borrow(borrow_nr):
    borrow = Borrows.query.get(borrow_nr)
    db.session.delete(borrow)
    db.session.commit()
    return make_response("", 204)
