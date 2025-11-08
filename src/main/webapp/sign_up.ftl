<html lang="en">
<#include "base.ftl">

<#macro title>Регистрация</#macro>
<#macro script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $('input[name="login"]').on('input', function() {
                var login = $(this).val();
                $.get("/checkLogin?login=" + login, function(response) {
                    if (response === "exist") {
                        $("#login-status").text("Логин занят").css("color", "#ff6b6b");
                        $("#submit-btn").prop("disabled", true);
                    } else {
                        $("#login-status").text("Логин свободен").css("color", "#51cf66");
                        $("#submit-btn").prop("disabled", false);
                    }
                });
            });

            $('input[name="profilePhoto"]').on('change', function() {
                checkPhoto(this);
            });
        });

        function checkForm() {
            const login = $('input[name="login"]').val();
            const password = $('input[name="password"]').val();
            const email = $('input[name="email"]').val();
            const username = $('input[name="username"]').val();
            const firstName = $('input[name="name"]').val();
            const lastName = $('input[name="lastName"]').val();
            const bio = $('input[name="bio"]').val();
            const file = $('input[name="profilePhoto"]')[0].files[0];

            const isFormValid = login && password && email && username &&
                firstName && lastName && bio && file;

            $('#submit-btn').prop('disabled', !isFormValid);
        }

        $('input').on('input', checkForm);
        $('input[name="profilePhoto"]').on('change', function() {
            checkPhoto(this);
            checkForm();
        });



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
    <center>
        <h1>Регистрация</h1>
        <form method="post" action="/sign_up" enctype="multipart/form-data">

            <div style="margin-bottom: 15px;">
                <strong>Фото профиля:</strong><br>
                <input type="file" name="profilePhoto" onchange="checkPhoto(this)" accept="image/*" style="margin-top: 5px;">
                <span id="file-error" style="margin-left: 10px; font-size: 12px;"></span>
                <br>
                <small style="color: #666;">Выберите фото профиля (JPEG, PNG, GIF, WebP)</small>
            </div>

            <div>
                <strong>Логин:</strong><br>
                <input type="text" name="login" placeholder="Введите логин">
                <span id="login-status" style="margin-left: 10px;"></span>
            </div>

            <div>
                <strong>Пароль:</strong><br>
                <input type="password" name="password" minlength="6" placeholder="Введите пароль">
            </div>

            <div>
                <strong>Почта:</strong><br>
                <input type="email" name="email" placeholder="Введите вашу почту" required>
            </div>

            <div>
                <strong>Имя пользователя:</strong><br>
                <input type="text" name="username" placeholder="Введите имя пользователя" required>
            </div>

            <div>
                <strong>Имя:</strong><br>
                <input type="text" name="name" placeholder="Введите ваше имя" required>
            </div>

            <div>
                <strong>Фамилия:</strong><br>
                <input type="text" name="lastName" placeholder="Введите вашу фамилию" required>
            </div>

            <div>
                <strong>О себе:</strong><br>
                <input type="text" name="bio" placeholder="Расскажите о себе" required>
            </div>

            <#if error??>
                <div class="error">${error}</div>
            </#if>

            <input type="submit" value="Зарегистрироваться" id="submit-btn" style="margin-top: 15px;">
        </form>
    </center>
</#macro>
</html>