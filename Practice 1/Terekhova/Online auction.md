# Онлайн аукцион [Терехова София]
## Функциональные требования
### Аккаунт
1. Создание аккаунта
	- Имя
	- Номер телефона
	- Почта (опционально)
	- Пароль и повторный пароль
	- Аватар
2. Редактирование аккаунта
	- Возможность редактирования всех полей, кроме номера телефона и имени
	- Возможность установить новое фото для аватара
3. Просмотр текущих аукционов и ставок.
4. Поиск аукционов или товаров по названию.
5. Сортировка аукционов по городу.
### Продавцы
1. Возможность создавать новый аукцион с указанием информации:
	- Время окончания аукциона
	- Время начала аукциона
	- Начальная ставка 
	- Описание аукциона
	- Перечисление товаров
2. Возможность показать список аукционов от этого продавца, включая:
	- Текущие аукционы
	- Законченные аукционы
	- Отмененные аукционы
3. Возможность редактировать аукцион, если аукцион не был начат. 
4. Возможность отмены аукциона в любой момент.
### Покупатели
1. Возможность делать ставку.
	- Автоматически проверять на минимальную сумму на данный момент
	- Проверять на минимальный шаг от последней ставки
2. Возможность оплатить лот.
3. Возможность указать адрес доставки.
### Аукционы
1. Название 
2. Содержание 
3. Время начала 
4. Время конца 
5. Перечисление товаров
6. Владелец аукциона(продавец)
7. Ставки
	- Начальная ставка
	- Шаг ставок
	- Все значения ставок должны быть уникальными
	- Кто ставит
8. При окончании аукциона выводить финального победителя и его сумму
9. Местоположение аукциона
### Ставка
1. Сумма
2. Валюта
3. Кем была сделана ставка

### Заказ
- Оплата (для покупателя)
	1. Возможность провести оплату с помощью платежной карты
		- Указывать PAN карты
		- Дата окончания карты
		- ФИО держателя карты
		- CVC
	2. Возможность указать адрес для доставки
	3. Возможность узнать контактные данные продавца
- Доставка (для продавца)
	1. Возможность посмотреть процесс оплаты
	2. Возможность узнать адрес покупателя
	3. Возможность узнать контактные данные покупателя
### Товары
1. Фото товара
2. Описание товара
3. Название товара
## Роли пользователя и их действия
Пользователь может быть одновременно и как продавец, так и как покупатель. 

 - Продавцы имеют право создавать аукцион, редактировать, добавлять свои товары в аукционе, товаров может быть несколько за один аукцион. Делать доставку товаров к покупателю.
 - Покупатели имеют право делать ставки, задавать сумму, оплачивать картой аукционы, в которых они победили, указывать адрес. 

## Объекты
 - Аккаунты
 - Аукционы
 - Товары
 - Ставки
 - Заказ
## Связи между объектами
У аккаунтов будет связь с аукционами и ставками. У аукционов будет связь с товарами, ставками и заказом.
## Схема объектной модели
![Schema](Schema.png)
