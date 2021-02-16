<#import "/parts/base.ftl" as b>
<#import "/parts/logPass.ftl" as lp>

<@b.page>
    <h3>Логин или <a href="/register">Регистрация</a></h3>
    <@lp.logPass path="login"/>
</@b.page>