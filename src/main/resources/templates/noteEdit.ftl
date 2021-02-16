<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Edit note</title>
</head>
<body>
    <form method="post">
        <input type="text" name="title" placeholder="Название" value="${noteEdit.title}">
        <input type="text" name="description" placeholder="Описание" value="${noteEdit.description}">

        <input type="date" name="dateTo" placeholder="Дата" value="${noteEdit.dateTo}">

        <input type="hidden" name="noteId" value="${noteEdit.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit">Изменить</button>
    </form>
</body>
</html>