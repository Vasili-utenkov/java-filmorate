# java-filmorate
Template repository for Filmorate project.

Диаграмма БД
![Диаграмма БД](src/images/BDDiagram.png)


id во всех таблицах автоинкременты  
в таблице Friend поле isConfirm имеет значение по умолчанию false

Примеры запросов:

список всех фильмов категории @id:
```
select * from Film where genre_id = @id
```

список общих друзей @user1 и @user2:
```
select u.*
from User u
join Friend f1 on f1.sideTwo = u.id and f1.sideOne = @user1 and f1.isConfirm = 1
join Friend f2 on f2.sideTwo = u.id and f2.sideOne = @user2 and f2.isConfirm = 1
```

Удаление друга @user1 из списка друзей @user2 (2 запроса):
```
Delete Friend where sideTwo = @user2 and sideOne = @user1
Delete Friend where sideTwo = @user1 and sideOne = @user2
```


