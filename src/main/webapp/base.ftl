<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title></@title></title>
    <style>
        body {
            background: #f8f9fa;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .header {
            background: #6c5ce7;
            padding: 20px 0;
            margin-bottom: 30px;
            box-shadow: 0 4px 15px rgba(108, 92, 231, 0.3);
        }

        .header-center {
            text-align: center;
        }

        .header h1 {
            color: white;
            margin: 0 0 15px 0;
            font-size: 28px;
            font-weight: bold;
        }

        .header a {
            color: white;
            text-decoration: none;
            margin: 0 12px;
            padding: 8px 16px;
            border-radius: 20px;
            display: inline-block;
            transition: all 0.3s ease;
        }

        .header a:hover {
            background: rgba(255,255,255,0.2);
            transform: translateY(-2px);
        }

        .content {
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
        }

        h1 {
            color: #6c5ce7;
            text-align: center;
            margin-bottom: 30px;
        }

        .situation-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            margin: 15px auto;
            max-width: 800px;
            border: 1px solid #ddd;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .situation-card h3 {
            color: #6c5ce7;
            margin: 0 0 10px 0;
        }

        .situation-card a {
            color: #6c5ce7;
            text-decoration: none;
        }

        .situation-card a:hover {
            text-decoration: underline;
        }

        .situation-card p {
            margin: 5px 0;
            color: #666;
        }

        form {
            background: white;
            padding: 20px;
            border-radius: 10px;
            max-width: 500px;
            margin: 0 auto;
            border: 1px solid #ddd;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="file"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button, input[type="submit"] {
            background: #6c5ce7;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        button:hover, input[type="submit"]:hover {
            background: #5b4cd6;
        }

        .btn-delete {
            background: #ff6b6b;
        }

        .btn-delete:hover {
            background: #ff5252;
        }

        .approved {
            border-left: 4px solid #00b894;
        }

        .not-approved {
            border-left: 4px solid #fdcb6e;
        }

        audio {
            width: 100%;
            margin: 10px 0;
        }

        .situation-actions {
            text-align: center;
            margin-top: 15px;
        }
    </style>
    <@script></@script>
</head>

<body>
<div class="header">
    <div class="header-center">
        <h1>Музыкальная система</h1>
        <a href="/main">Главная</a>
        <a href="/my_account">Мой аккаунт</a>
        <a href="/my_situation">Мои запросы</a>
        <a href="/track_upload">Загрузить трек</a>
        <#if user?? && user.role == "ADMIN">
            <a href="/admin_manage_users">Админка</a>
        </#if>
        <a href="/logout">Выйти</a>
    </div>
</div>

<div class="content">
    <@content></@content>
</div>
</body>
</html>