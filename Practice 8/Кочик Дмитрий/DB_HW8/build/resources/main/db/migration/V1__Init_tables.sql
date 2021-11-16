CREATE TABLE IF NOT EXISTS publishers
(
    "pubName"
    TEXT
    NOT
    NULL,
    "pubAddress"
    TEXT
    NOT
    NULL
)

ALTER TABLE publishers
    ADD CONSTRAINT publishers_pubname_unique UNIQUE ("pubName")

CREATE TABLE IF NOT EXISTS books
(
    isbn
    TEXT
    NOT
    NULL,
    title
    TEXT
    NOT
    NULL,
    author
    TEXT
    NOT
    NULL,
    "pagesNum"
    INT
    NOT
    NULL,
    "pubYear"
    INT
    NOT
    NULL,
    "pubName"
    TEXT
    NOT
    NULL,
    CONSTRAINT
    fk_books_pubname_pubname
    FOREIGN
    KEY
(
    "pubName"
) REFERENCES publishers
(
    "pubName"
) ON DELETE RESTRICT
  ON UPDATE RESTRICT)

ALTER TABLE books
    ADD CONSTRAINT books_isbn_unique UNIQUE (isbn)

CREATE TABLE IF NOT EXISTS categories
(
    "categoryName"
    TEXT
    NOT
    NULL,
    "parentCat"
    TEXT
    NULL
)

ALTER TABLE categories
    ADD CONSTRAINT categories_categoryname_unique UNIQUE ("categoryName")

CREATE TABLE IF NOT EXISTS bookcategories
(
    isbn
    TEXT
    NOT
    NULL,
    "categoryName"
    TEXT
    NOT
    NULL
)

CREATE TABLE IF NOT EXISTS readers
(
    id
    SERIAL
    NOT
    NULL,
    "lastName"
    TEXT
    NOT
    NULL,
    "firstName"
    TEXT
    NOT
    NULL,
    address
    TEXT
    NOT
    NULL,
    "date"
    DATE
    NOT
    NULL
)

ALTER TABLE readers
    ADD CONSTRAINT readers_id_unique UNIQUE (id)

ALTER TABLE readers
    ADD CONSTRAINT readers_lastname_unique UNIQUE ("lastName")

CREATE TABLE IF NOT EXISTS copies
(
    isbn
    TEXT
    NOT
    NULL,
    "copyNumber"
    TEXT
    NOT
    NULL,
    "shelfPosition"
    INT
    NOT
    NULL,
    CONSTRAINT
    fk_copies_isbn_isbn
    FOREIGN
    KEY
(
    isbn
) REFERENCES books
(
    isbn
) ON DELETE RESTRICT
  ON UPDATE RESTRICT)

ALTER TABLE copies
    ADD CONSTRAINT copies_copynumber_unique UNIQUE ("copyNumber")

CREATE TABLE IF NOT EXISTS borrowings
(
    "readerNr"
    INT
    NOT
    NULL,
    isbn
    TEXT
    NOT
    NULL,
    "copyNumber"
    TEXT
    NOT
    NULL,
    "returnDAte"
    DATE
    NOT
    NULL,
    CONSTRAINT
    fk_borrowings_readernr_id
    FOREIGN
    KEY
(
    "readerNr"
) REFERENCES readers
(
    id
) ON DELETE RESTRICT
  ON UPDATE RESTRICT, CONSTRAINT fk_borrowings_isbn_isbn FOREIGN KEY
(
    isbn
) REFERENCES books
(
    isbn
)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT, CONSTRAINT fk_borrowings_copynumber_copynumber FOREIGN KEY
(
    "copyNumber"
) REFERENCES copies
(
    "copyNumber"
)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT)

ALTER TABLE categories
    ADD CONSTRAINT fk_categories_parentcat_categoryname FOREIGN KEY ("parentCat") REFERENCES categories ("categoryName") ON DELETE RESTRICT ON UPDATE RESTRICT

ALTER TABLE bookcategories
    ADD CONSTRAINT fk_bookcategories_isbn_isbn FOREIGN KEY (isbn) REFERENCES books (isbn) ON DELETE RESTRICT ON UPDATE RESTRICT

ALTER TABLE bookcategories
    ADD CONSTRAINT fk_bookcategories_categoryname_categoryname FOREIGN KEY ("categoryName") REFERENCES categories ("categoryName") ON DELETE RESTRICT ON UPDATE RESTRICT
