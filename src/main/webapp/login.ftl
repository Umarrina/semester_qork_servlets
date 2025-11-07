<html lang="en">
<#include "base.ftl">

<#macro title>Вход</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Вход в систему</h1>
        <form method="post" action="/login">
            <div>
                <strong>Логин:</strong><br>
                <input type="text" name="login" placeholder="Введите ваш логин">
            </div>

            <div>
                <strong>Пароль:</strong><br>
                <input type="password" name="password" placeholder="Введите ваш пароль">
            </div>

            <#if error??>
                <div class="error">${error}</div>
            </#if>

            <input type="submit" value="Войти" style="margin-top: 15px;">
        </form>

        <div style="margin-top: 20px;">
            <a href="/sign_up">Зарегистрироваться</a>
        </div>
    </center>
</#macro>
</html>