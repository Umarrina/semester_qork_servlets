<html lang="en">
<#include "admin_base.ftl">

<#macro title>Управление темами</#macro>
<#macro script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function updateTrackStatus(situationId, approved) {

            fetch('/change_status_situation?situationId=' + ${situation.id} + '&approved=' + approved , {
                method: 'GET',
            })
                .then(response => response.text())
                .then(statusText => {
                    const statusElement = document.getElementById('situation-status-' + situationId);
                    statusElement.textContent = statusText;
                })
                .catch(error => console.error('Error:', error));
        }
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

        <p><strong>Статус:</strong>
            <span id="situation-status-${situation.id}">
                        ${situation.approved?then('Одобрен', 'Не одобрен')}
                    </span>
        </p>
        <button onclick="updateTrackStatus(${situation.id}, 'true')">Одобрить</button>
        <button onclick="updateTrackStatus(${situation.id}, 'false')">Отклонить</button>

        <button id="delete" onclick="deleteSituation">Удалить ситуацию</button>
    </center>
</form>
</#macro>