<#import "/parts/base.ftl" as b>
<#import "/parts/logPass.ftl" as l>

<@b.page>
    <@l.logout/>

    <h3>${email?ifExists}</h3>

    <form method="post">
        <input type="text" name="email" placeholder="email">

        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit">Отправить</button>
    </form>

    <a href="/home">Домашния страница</a>

</@b.page>