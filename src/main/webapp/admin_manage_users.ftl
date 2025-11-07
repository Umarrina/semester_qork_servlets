<html lang="en">
<#include "admin_base.ftl">

<#macro title>Управление пользователями</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Пользователи</h1>
        <#if users?? && users?size gt 0>
            <#list users as user>
                <#if user.role == "USER">
                    <div class="situation-card">
                        <h3>${user.login}</h3>
                        <p><strong>Email:</strong> ${user.email!}</p>
                        <p><strong>Роль:</strong> ${user.role!}</p>
                        <p><strong>Имя пользователя:</strong> ${user.username!}</p>

                        <div style="text-align: center; margin-top: 15px;">
                            <form action="/admin_user_delete" method="post" style="display: inline;">
                                <input type="hidden" name="userId" value="${user.id}">
                                <button type="submit" onclick="return confirm('Удалить пользователя?')"
                                        style="background: #ff6b6b; color: white; padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer;">
                                    Удалить
                                </button>
                            </form>
                        </div>
                    </div>
                </#if>
            </#list>
        <#else>
            <div class="situation-card">
                <p>Пользователей не найдено</p>
            </div>
        </#if>

    </center>
</#macro>