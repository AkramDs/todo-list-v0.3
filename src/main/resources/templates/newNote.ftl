<#import "/parts/base.ftl" as b>
<#import "/parts/logPass.ftl" as l>

<@b.page>
    <@l.logout/>

    <form method="post">
        <input type="text" name="title" placeholder="Название">
        <input type="text" name="description" placeholder="Описание">

        <p>Срок выполнения</p>
        <p>От</p><input type="date" name="dateFrom">

        <p>До</p><input type="date" name="dateTo">

        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit">Добавить</button>
    </form>
</@b.page>