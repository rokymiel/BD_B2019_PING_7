# Агроскин Александр, БПИ197, Задание 10

# №1:

Отношение (A, B, C, D, E, G) имеет следующие функциональные зависимости:

1. AB → C
2. C → A 
3. BC → D 
4. ACD → B 
5. D → EG 
6. BE → C 
7. CG → BD 
8. CE → AG 

Необходимо построить замыкание BD. Построю цепочку, указывая зависимости, которые использую:

BD --[5]-> BDEG --[6]-> BCDEG --[2]-> ABCDEG.

Получили, что замыканием является все множество ABCDEG

# №2:

Имеется отношение **Order: (ProductNo, ProductName, CustomerNo, CustomerName, OrderDate, UnitPrice, Quantity, SubTotal, Tax, Total)**

А) Из базового представления о структуре магазина можно получить такие нетривиальные зависимости: 
  * ProductNo → ProductName
  * CustomerNo → CustomerName 
  * ProductNo → Tax
  * ProductNo → UnitPrice
  * SubTotal, Tax → Total
  * UnitPrice, Quantity → SubTotal
  * ProductNo, CustomerNo, OrderDate → UnitPrice, Quantity, SubTotal, Tax, Total

Б) Ключами-кандидатами являются: ProductNo, CustomerNo, OrderDate

# №3:

А) Ключи-кандидаты: AB или AC
Б) Алогритм синтеза по шагам:
  1. Найдем minimal cover: тут он совпадает с F
  2. Построим отношение с каждой зависимостью из F:
    * (A, D)
    * (A, B, C)
    * (A, C, B)
  3. Видим, что в них попали все элементы, и что для них выполняются все условия 3NF. Тогда получаем, что 3NF для R это R_1: (A, D) и R_2: (A, B, C)
