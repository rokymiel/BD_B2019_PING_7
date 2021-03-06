# Задание 1

Мы хотим иметь наборы однотипных сущностей и выражать связи между разными сущностями разных наборов -- в реляционной схеме для этого используется запись главного ключа некоторой сущности в поле другой сущности.

Каждое отношение содержит хотя бы один главный ключ по дизайну реляционной схемы -- иначе, мы бы не могли выражать связи именно так.

# Задание 2
(я сделал по правилам из лекции, но главные ключи выделаются как ___ключ___, потому что в маркдауне нет подчёркивания)

### Библиотека
Правило 1 (сущности с аттрибутами):

* Книга :: {[ ___ISBN___: string, Заглавие: string, ГодИздания: date, Автор: string, Кол-воСтр: integer ]}
* Категория :: {[ ___ИдКатегория___: integer, Имя: string ]} 
* Издательство :: {[ ___ИдИздательсво___: integer, Имя: string, Адрес: string ]}
* Копия :: {[ ___НомерКопии___: integer, Положение: string (я не уверен в типе) ]}
* Читатель :: {[ ___НомерЧитателя___: integer, Адрес: string, Имя: string, Фамилия: string, ДатаРождения: date ]}

Правило 2 (добавляем отношения):

* Принадлежит (Книга-Категория) :: {[ ___ISBN___: string, ___ИдКатегория___: integer ]}
* Принадлежит (Категория-Категория) :: {[ ___ИдКатегория___: integer, ИдКатегорияРодитель: integer ]}
* Издаёт :: {[ ___ISBN___: string, ИдИздательство: integer ]}
* Копирует :: {[ ___НомерКопии___: integer, ISBN: string ]}
* БерётЧитать :: {[ ___НомерЧитателя___: integer, ___НомерКопии___: integer, ДатаВозврата: string ]}

Правило 3 (совмещаем отношения с единственным ключом с соответсвующей сущностью, и получаем):

* Книга :: {[ ___ISBN___: string, Заглавие: string, ГодИздания: date, Автор: string, Кол-воСтр: integer, ИдИздательства: integer ]} (+ отношение Издаёт)
* Категория :: {[ ___ИдКатегория___: integer, Имя: string, ИдКатегорияРодитель: integer ]} (+ отношение Принадлежит (Категория-Категория)) 
* Издательство :: {[ ___ИдИздательсво___: integer, Имя: string, Адрес: string ]}
* Копия :: {[ ___НомерКопии___: integer, Положение: string (я не уверен в типе), ISBN: string ]} (+ отношение Копирует)
* Читатель :: {[ ___НомерЧитателя___: integer, Адрес: string, Имя: string, Фамилия: string, ДатаРождения: date ]}
* Принадлежит (Книга-Категория) :: {[ ___ISBN___: string, ___ИдКатегория___: integer ]}
* БерётЧитать :: {[ ___НомерЧитателя___: integer, ___НомерКопии___: integer, ДатаВозврата: string ]}

Для этой диаграммы это последний шаг.

Для следующих диаграмм я буду писать результат сразу после применения этих трёх правил.

### Судья и команды

Судья :: {[ ___ИдСудья___: integer ]}
Команда :: {[ ___ИдКоманда___: integer ]}
Играют :: {[ ___ИдКоманда1___: integer, ___ИдКоманда2___: integer, ИдСудья: integer ]}

### Страна, улица, дом, квартира.

Тут нужен дополнительный шаг с переводом слабых сущностей в реляционную схему. Его суть заключается в том, что к слабой сущности присоединяется ключ соответствующей ей сильной сущности.

Страна :: {[ ___ИдСтрана___: integer ]}
Улица :: {[ ___ИдУлица___: integer, ___ИдСтрана___: integer ]}
Дом :: {[ ___ИдУлица___: integer, ___ИдСтрана___: integer, ___ИдДом___: integer ]}
Квартира :: {[ ___ИдУлица___: integer, ___ИдСтрана___: integer, ___ИдДом___: integer, ___ИдКвартира___: integer ]}

### Мужчина и женщина

Мужчина :: {[ ___ИдМужчина___: integer ]}
Женщина :: {[ ___ИдЖенжина___: integer ]}
Сын :: {[ ___ИдМужчинаСын___: integer, ИдЖенщинаМать: integer, ИдМужчинаОтец: integer ]}
Дочь :: {[ ___ИдЖенщинаДочь___: integer, ИдЖенщинаМать: integer, ИдМужчинаОтец: integer ]}

### ER as ER

Для слабых версий сущностей и отношений я также применил здесь правило 4, которое диктует как описывать сущности со стрелкой типа is-a.

Сущность :: {[ ___ИдСущность___: integer, Имя: string ]}
Отношение :: {[ ___ИдОтношение___: integer, Имя: string ]}
Связь :: {[ ___ИдОтношение___: integer, ИдСущность: integer, Функциональность: integer, Роль: string, Минимум: integer, Максимум: integer ]}
АттрибутCущности :: {[ ___ИдАттрибут___: integer, ___ИдСущность___: integer, Имя: string, ЭтоГлавныйКлюч: boolean]}
АттрибутОтношения :: {[ ___ИдАттрибут___: integer, ___ИдОтношение___: integer, Имя: string ]}
СлабаяСущность :: {[ ___ИдСущность___: integer ]}
CлабоеОтношение :: {[ ___ИдОтношение___: integer, ИдСущность: integer ]}

# Задание 3

### Схема первая

Станция :: {[ ___Имя___: string, Кол-воПутей: integer, ИмяГорода: string, РегионГорода: string ]}
Город :: {[ ___Имя___: string, ___Регион___: string ]}
Поезд :: {[ ___НоПоезд__: integer, Длина: integer, ИзИмяСтанции: string, ВИмяСтанции: string ]}
Путь :: {[ ___НоПоезд___: integer, ИмяСтанции: string, Отправление: time, Прибытие: time ]}

### Схема вторая (я решил опустить типы)

StationPersonell :: {[ ___PersNr___, #Name, WorksFor ]}
Caregiver :: {[ ___PersNr___, Qualification ]}
Doctor :: {[ ___PersNr___, Area, Rank ]}
Patient :: {[ ___PatientNr___, TreatedBy, Name, Disease, ]}
Admissions :: {[ ___PatientNr___, From, To, RoomId ]}
Station :: {[ ___StatNr___, Name ]}
Room :: {[ ___RoomNr___, ___StatNr___, #Beds ]}