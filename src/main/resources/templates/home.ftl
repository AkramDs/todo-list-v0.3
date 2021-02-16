<#import "/parts/base.ftl" as b>
<#import "/parts/logPass.ftl" as l>

<@b.page>
    <h1>Вы зарегистрованный пользыватель</h1>

    <span>
        <a href="/">Назад</a> <br/>
        <a href="newNote/">Новая заметка</a>
    </span>
<!--    <span><a href="/user">Список пользывателей</a></span>-->

    <@l.logout/>
    <@b.download/>
    <@b.search/>

    <#if notes??>
    <#list notes as note>
    <div>
        <b>${note.title}</b>
        <i>${note.status}</i>
        <span>${note.description}</span>
        <i>${note.dateCreate}</i>
        <i><p>До</p> ${note.dateTo}</i>

        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="hidden" name="noteId" value="${note.id}">

            <button type="submit"><a href="/noteEdit/${note.id}">Редактировать</a></button>
        </form>

        <form method="post">
            <input type="hidden" name="noteId" value="${note.id}">

            <button type="submit" name="performed"><a href="/performed/${note.id}">Выполнено</a></button>
        </form>

        <form method="post">
            <input type="hidden" name="noteId" value="${note.id}">

            <button type="submit" name="delete"><a href="/delete/${note.id}">Удалить</a></button>
        </form>
    </div>

    </#list>
    <#else>
    <h2>Нечего не найдено</h2>

    </#if>

</@b.page>

