# Задание 1
## Условия

Вам поручено разработать онлайн-аукцион. Он позволяет продавцам продавать свои товары с помощью аукциона. Покупатели делают ставки. Выигрывает последняя самая высокая ставка. После закрытия аукциона победитель оплачивает товар с помощью кредитной карты. Продавец отвечает за доставку товара покупателю.

* Предложите список функциональных требований для проекта.
* Определите роли пользователей и действия для каждой роли.
* Определите объекты, о которых будут храниться данные.
* Определите связи между объектами для хранения данных.
* Нарисуйте схему объектной модели (используя любые обозначения, которые вам удобны).

## Решение

Разрабатываемая система - *Онлайн-аукцион*

### Функциональные требования
1. Регистрация (авторизация)
2. Добавление товара на аукцион
3. Открытие торгов для товара
4. Ставка на товар в определенном размере у.е.
5. Определение финального покупателя
6. Оплата товара покупателем через систему онлайн-платежей
7. Закрытие торгов для товара

### Роли и действия пользователей
- Продавец
    1. Регистрация (авторизация)
    2. Добавление товара на аукцион
    3. Снятие товара с аукциона
    4. Отправки товара покупателю
- Покупатель
    1. Регистрация (авторизация)
    2. Добавление метода оплаты
    3. Нахождение интересующего товара на аукционе
    4. Ставка на товар в определенном размере у.е.
    5. Уведомление о выигрыше торгов
    6. Оплата товара
    7. Указание адреса доставки
- Модератор
    1. Проверка продавцов на наличие товара, его легитимность, верность описания

### Объекты, хранящиеся в базе данных
1. User
    - id: Int
    - name: String
    - role: String
    - cellphoneNumber: String
    - payment: String?
2. Product
    - id: Int
    - name: String
    - seller: Int
    - description: String
    - startingPrice: Int
    - imageUrl: String
    - acceptedForSelling: Bool
    - bargainingStatus: Int
4. BargainingStatus
    - id: Int
    - name: String

### Связи между объеками в базе данных
1. User.id -> Product.seller (one to many)
1. BargainingStatus.id -> Product.bargainingStatus (one to many)

### Схема объектной модели

![схема](https://i.ibb.co/VqddNfF/draw-SQL-export-2021-09-11-17-54.png)