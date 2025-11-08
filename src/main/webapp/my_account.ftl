<html lang="en">
<#include "base.ftl">

<#macro title>Мой аккаунт</#macro>
<#macro script></#macro>

<#macro content>
    <div class="situation-card" style="max-width: 600px;">
        <#if profilePhoto??>
            <div style="text-align: center; margin-bottom: 20px;">
                <strong>Фото профиля:</strong><br>
                <img src="${profilePhoto}" alt="Profile Photo" style="max-width: 300px; margin-top: 10px;">
            </div>
        </#if>

        <div style="margin: 10px 0;"><strong>Имя пользователя:</strong> ${username!}</div>
        <div style="margin: 10px 0;"><strong>Имя:</strong> ${firstName!}</div>
        <div style="margin: 10px 0;"><strong>Фамилия:</strong> ${lastName!}</div>
        <div style="margin: 10px 0;"><strong>О себе:</strong> ${bio!}</div>
    </div>

    <center style="margin-top: 20px;">
        <a href="/my_account_change" class="btn-primary">Изменить аккаунт</a>
    </center>
</#macro>
</html>