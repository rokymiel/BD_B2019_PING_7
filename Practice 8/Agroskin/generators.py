from faker import Faker

from models import *

faker = Faker()

isbns = [faker.unique.isbn13() for _ in range(10000)]
publishers = [faker.unique.pystr() for _ in range(1000)]
used_isbns = []
used_publishers = []


def random_isbn():
    isbn = isbns[-1]
    isbns.pop()
    used_isbns.append(isbn)
    if len(isbns) == 0:
        isbns.extend([faker.unique.isbn() for _ in range(10000)])
    return isbn


def random_pub():
    pub = publishers[-1]
    publishers.pop()
    used_publishers.append(pub)
    if len(publishers) == 0:
        publishers.extend([faker.unique.isbn() for _ in range(10000)])
    return pub


object_generators = {
    'reader': lambda: Reader(faker.last_name(),
                             faker.first_name(),
                             faker.address(),
                             faker.date()),
    'book': lambda: Book(random_isbn(),
                         faker.pystr(),
                         faker.name(),
                         faker.random.randint(100, 900),
                         faker.year(),
                         faker.random.choice(used_publishers)),
    'publisher': lambda: Publisher(random_pub(),
                                   faker.address()),
    'category': lambda: Category(faker.pystr(),
                                 faker.pystr()),
    'copy': lambda: Copy(faker.random.choice(used_isbns),
                         faker.random.randint(0, 99999),
                         faker.random.randint(0, 500)),

    'book_category': lambda: BookCategory(faker.random.choice(used_isbns),
                                          faker.pystr())
}
