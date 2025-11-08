<html lang="en">
<#include "base.ftl">

<#macro title>Изменение аккаунта</#macro>
<#macro script>
    <script>
        function checkPhoto(input) {
            var file = input.files[0];
            var errorSpan = document.getElementById('file-error');

            if (file) {
                var allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];

                if (!allowedTypes.includes(file.type)) {
                    errorSpan.innerHTML = 'Неверный тип файла. Разрешены: JPG, PNG, GIF, WebP';
                    errorSpan.style.color = 'red';
                    $("#submit-btn").prop("disabled", true);
                } else {
                    errorSpan.innerHTML = 'Файл принят';
                    errorSpan.style.color = 'green';
                    $("#submit-btn").prop("disabled", false);
                }

                if (file.size > 5 * 1024 * 1024) {
                    errorSpan.innerHTML = 'Файл слишком большой (макс. 5MB)';
                    errorSpan.style.color = 'red';
                    $("#submit-btn").prop("disabled", true);
                }
            } else {
                errorSpan.innerHTML = '';
                $("#submit-btn").prop("disabled", false);
            }
        }
    </script>
</#macro>

<#macro content>
    <form method="post" action="/my_account_change" enctype="multipart/form-data">
        <center>
            <h2>Редактирование профиля</h2>

            <div style="margin-bottom: 15px;">
                <strong>Фото профиля:</strong><br>
                <input type="file" name="profilePhoto" onchange="checkPhoto(this)" accept="image/*" style="margin-top: 5px;">
                <span id="file-error" style="margin-left: 10px; font-size: 12px;"></span>
                <br>
                <small style="color: #666;">Выберите фото профиля (JPEG, PNG, GIF, WebP)</small>
            </div>

            <div>
                <strong>Имя пользователя:</strong><br>
                <input type="text" name="username" placeholder="Введите имя пользователя" value="${username!}">
            </div>

            <div>
                <strong>Имя:</strong><br>
                <input type="text" name="name" placeholder="Введите ваше имя" value="${firstName!}">
            </div>

            <div>
                <strong>Фамилия:</strong><br>
                <input type="text" name="lastName" placeholder="Введите вашу фамилию" value="${lastName!}">
            </div>

            <div>
                <strong>Почта:</strong><br>
                <input type="text" name="email" placeholder="Введите адрес почты" value="${email!}">
            </div>

            <div>
                <strong>О себе:</strong><br>
                <input type="text" name="bio" placeholder="Расскажите о себе" value="${bio!}">
            </div>

            <input type="submit" value="Сохранить изменения" style="margin-top: 15px;">

            <div style="margin-top: 30px; border-top: 1px solid #ccc; padding-top: 20px;">
                <form action="/user_delete_account" method="post" onsubmit="return confirm('Вы уверены, что хотите удалить аккаунт? Это действие нельзя отменить.')">
                    <input type="hidden" name="userId" value="${user.id!}">
                    <button type="submit" style="background: #ff6b6b; color: white; padding: 10px 20px; border: none; border-radius: 20px; cursor: pointer;">
                        Удалить аккаунт
                    </button>
                </form>
            </div>
        </center>
    </form>
</#macro>
</html>