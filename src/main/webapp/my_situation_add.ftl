<html lang="en">
<#include "base.ftl">

<#macro title>Добавление запроса</#macro>
<#macro script></#macro>

<#macro content>
    <form method="post" action="/my_situation_add">
        <center>
            <h2>Создание нового запроса</h2>

            <div>
                <strong>Название темы:</strong><br>
                <input type="text" name="title" placeholder="Введите название темы">
            </div>

            <div>
                <strong>Описание:</strong><br>
                <input type="text" name="description" placeholder="Введите описание">
            </div>

            <input type="submit" value="Создать запрос" style="margin-top: 15px;">
        </center>
    </form>
</#macro>
</html>