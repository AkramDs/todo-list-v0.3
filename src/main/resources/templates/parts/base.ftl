<#macro page title="Title">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
<#nested>
</body>
</html>
</#macro>

<#macro download>
<button type="submit"><a href="/download">Скачать заметки</a></button>
</#macro>

<#macro search>
<form method="post" action="filter">
    <label>Название: <input type="text" name="title"></label>

    <input type="hidden" name="_csrf" value="${_csrf.token}">

    <button type="submit" name="search">Поиск</button>
</form>
</#macro>

