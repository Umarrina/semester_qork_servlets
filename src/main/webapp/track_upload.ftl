<html lang="en">
<#include "base.ftl">

<#macro title>Загрузка трека</#macro>
<#macro script>
    <script>
        function validateForm() {
            const title = document.querySelector('input[name="title"]').value;
            const author = document.querySelector('input[name="author"]').value;
            const file = document.querySelector('input[name="audioFile"]').files[0];

            if (!title || !author || !file) {
                alert('Все поля должны быть заполнены');
                return false;
            }

            const allowedTypes = ['audio/mpeg', 'audio/mp3', 'audio/wav', 'audio/ogg'];
            if (!allowedTypes.includes(file.type)) {
                alert('Разрешены только аудиофайлы (MP3, WAV, OGG)');
                return false;
            }

            return true;
        }
    </script>
</#macro>

<#macro content>
    <center>
        <h1>Загрузка трека на сайт</h1>
        <form method="post" action="/track_upload" enctype="multipart/form-data" onsubmit="return validateForm()">

            <div>
                <strong>Название трека:</strong><br>
                <input type="text" name="title" placeholder="Введите название трека" required>
            </div>

            <div>
                <strong>Аудиофайл:</strong><br>
                <input type="file" name="audioFile" accept="audio/*" required>
                <br>
                <small style="color: #666;">Поддерживаемые форматы: MP3, WAV, OGG (макс. 10MB)</small>
            </div>

            <#if error??>
                <div class="error">${error}</div>
            </#if>

            <input type="submit" value="Загрузить трек" style="margin-top: 15px;">
        </form>
    </center>
</#macro>
</html>