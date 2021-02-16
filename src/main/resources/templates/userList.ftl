<#import "/parts/base.ftl" as b>

<@b.page>
    <h3>Список пользывателей</h3>

    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Роль</th>
            <th>Изменить</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">edit</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</@b.page>