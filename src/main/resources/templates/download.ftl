<#import "/parts/base.ftl" as b>

<@b.page>
    <@b.search/>

    <form method="get" action="downloadAll">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit" >Скачать все</button>
    </form>

    <#if notes??>
        <form method="get" action="downloadSearch">
            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <button type="submit" name="download">Скачать найденные</button>
        </form>

    <#list notes as note>
    <div>
        ${note.check()}

        <b>${note.title}</b>
        <i>${note.status}</i>
        <span>${note.description}</span>
        <i>${note.time}</i>

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