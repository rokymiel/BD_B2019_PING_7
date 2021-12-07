
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
    "pagesNumber"
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
  ON UPDATE RESTRICT);
