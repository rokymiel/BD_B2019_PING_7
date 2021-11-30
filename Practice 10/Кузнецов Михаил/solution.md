# Задание 1

> Отношение (A, B, C, D, E, G) имеет следующие функциональные зависимости:
> 
> * AB → C
> * C → A 
> * BC → D 
> * ACD → B 
> * D → EG 
> * BE → C 
> * CG → BD 
> * CE → AG 
> 
> Постройте закрытие атрибута (Attribute Closure )(BD)+

1. Изначально (B, D), так как (BD)+
2. D → EG => Добавляем E, G => (B, D, E, G)
3. BE → C => Добавляем C => (B, C, D, E, G)
4. C → A => Добавляем A => (A, B, C, D, E, G)

Получаем, что замыкание это все (A, B, C, D, E, G)

# Задание 2

> Посмотрите на отношения: Order (ProductNo, ProductName, CustomerNo, CustomerName,OrderDate,UnitPrice, Quantity, SubTotal, Tax, Total)
> 
> Ставка налога зависит от Товара (например, 20 % для книг или 30 % для предметов роскоши).
> В день допускается только один заказ на продукт и клиента (несколько заказов объединяются).
> 
> * А) Определить нетривиальные функциональные зависимости в отношении
> * Б) Каковы ключи-кандидаты?

A) Получаем:
  * ProductNo → ProductName
  * ProductNo → Tax
  * ProductNo → UnitPrice
  * CustomerNo → CustomerName 
  * SubTotal, Tax → Total
  * UnitPrice, Quantity → SubTotal
  * ProductNo, CustomerNo, OrderDate → UnitPrice, Quantity, SubTotal, Tax, Total

# Задание 3

> Рассмотрим соотношение R(A, B, C, D) со следующими функциональными зависимостями:
> F = {A→D, AB→ C, AC→ B}
> * А) *Каковы все ключи-кандидаты?
> * Б) Преобразуйте R в 3NF, используя алгоритм синтеза.
