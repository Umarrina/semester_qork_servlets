<html lang="en">
<#include "base.ftl">

<#macro title>Изменение аккаунта</#macro>
<#macro script>
    <script>

    </script>
</#macro>

<#macro content>
    <form method="post" action="/situation_edit?situationId=${situation.id}" >
        <center>
            <h2>Редактирование запроса</h2>

            <input type="hidden" name="situationId" value="${situation.id}">

            <div>
                <strong>Название темы:</strong><br>
                <input type="text" name="title" placeholder="Введите название темы" value="${situation.title!}">
            </div>

            <div>
                <strong>Описание:</strong><br>
                <input type="text" name="description" placeholder="Введите описание" value="${situation.description!}">
            </div>

            <input type="submit" value="Сохранить изменения" style="margin-top: 15px;">

            <button id="delete" onclick="deleteSituation">Удалить ситуацию</button>
        </center>
    </form>
</#macro>
</html>