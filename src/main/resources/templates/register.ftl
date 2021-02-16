<#import "parts/base.ftl" as b>
<#import "parts/logPass.ftl" as lp>

<@b.page>
    <h3>Регистрация или <a href="/login">Логин</a></h3>
    ${message?ifExists}
    <@lp.logPass path="register"/>
</@b.page>