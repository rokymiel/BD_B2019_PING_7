## Задание 3

1. Почему любое отношение в реляционной схеме имеет по крайней мере один ключ?

   Во-первых, давайте представим отношение без ключа, там, если мы хотим добавить какое-то значение, тогда, чтобы избежать дублирования, мы должны сравнить все записи в отношении с записью, которую мы хотим добавить, и это будет работать очень медленно, и если мы хотим отредактировать или удалить какую-то запись, то это тоже будет нелегко, вам нужно просмотреть все записи и провести сравнение со всеми столбцами, а вероятность редактирования неправильной записи не низкая, но про поиск тоже очевидно, что поиск по одному столбцу намного быстрее, чем поиск по всем столбцам. Кроме того, как же соединить два отношения без ключа?</br>По-моему, никак!!!!</br>
   Каждый объект в мире имеет имя, и каждый объект имеет свойства, и между этими свойствами точно найдется, какое свойство является различным для каждого объекта этого типа. Это же помог нас понимать и различать все, что есть в жизни.

2. Переведите все диаграммы ER из предыдущей домашней работы в реляционные схемы.

   2.1:

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/LibraryScheme.png?raw=true)

   2.2.1:

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/CountriesScheme.png?raw=true)

   2.2.2:

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/FootballScheme.png?raw=true)

   2.2.3:

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/FamiliesScheme.png?raw=true)

   2.3:

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/E_RSheme.png?raw=true)

3. Переведите приведенные диаграммы ER в реляционные схемы.

   3.1. https://imgur.com/w2iDI1o

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/TrainStationScheme.png?raw=true)

   3.2. https://imgur.com/oFBM5pp

   ![alt text](https://github.com/yyahya-2000/BD_B2019_PING_7/blob/Task3/Practice%203/Task03_YanalYahya_198/Hospital.png?raw=true)
