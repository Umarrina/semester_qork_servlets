<html lang="en">
<#include "base.ftl">

<#macro title>Мой аккаунт</#macro>
<#macro script></#macro>

<#macro content>
    <div class="profile-info">
        <#if profilePhoto??>
            <div>
                <strong>Фото профиля:</strong><br>
                <img src="${profilePhoto}" alt="Profile Photo" style="max-width: 300px; margin-top: 10px;">
            </div>
        </#if>

        <div><strong>Имя пользователя:</strong> ${username!}</div>
        <div><strong>Имя:</strong> ${firstName!}</div>
        <div><strong>Фамилия:</strong> ${lastName!}</div>
        <div><strong>О себе:</strong> ${bio!}</div>
    </div>

    <center style="margin-top: 20px;">
        <a href="/my_account_change" class="btn-primary">Изменить аккаунт</a>
    </center>
</#macro>
</html>