### 1.
Изначально в {BD}+ можем добавить B и D. Получаем {B, D}.  
D -> EG, можем добавить E, G. Получаем {B, D, E, G}.  
BE -> C => Получаем {B, D, E, G, C}.  
CE -> AG Получаем {A, B, D, E, G, C}.   
Ответ: (BD)+ = {A, B, C, D, E, G}.
### 2.
а) имеем:  
ProductNo -> Tax  ("Ставка налога зависит от Товара")  
ProductNo, CustomerNo, OrderDate -> UnitPrice, Quantity, SubTotal, Total ("В день допускается только один заказ на продукт и клиента (несколько заказов объединяются)")  
б) Логично было бы предположить, что от идентификатора товара зависит его имя, а от идентификатора пользователя, имя пользователя, но нам такое не сказано, поэтому:  
ключ-кандидат: (ProductNo, ProductName, CustomerNo, CustomerName, OrderDate)
### 3.
а) Ключи кандидаты:
1. A, C (A->D, AC->B)
2. A, B (A->D, AB->C)
б) 
1. Пусть минимальный базис - A, B
2. Для каждого отношения из ключа-кандидата строим отдельную таблицу, получаем R1(a, b, c), R2(a, d)
3. На этом можно остановиться, мы получили 3NF.
