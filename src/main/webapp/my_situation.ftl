<html lang="en">
<#include "base.ftl">

<#macro title>Мои запросы</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>📝 Мои запросы</h1>

        <div style="margin: 20px 0;">
            <a href="/my_situation_add" class="btn-primary">+ Добавить запрос</a>
        </div>

        <#if user_situations??>
            <h2 style="color: #ffa94d; margin-top: 30px;">Неодобренные запросы</h2>
            <#list user_situations as situation>
                <#if !situation.approved>
                    <div class="situation-card not-approved">
                        <h3>
                            <a href="/situation_edit?situationId=${situation.id}">${situation.title}</a>
                        </h3>
                        <p><strong>Описание:</strong> ${situation.description!}</p>
                        <p><strong>Дата:</strong> ${situation.date!}</p>

                        <div style="text-align: center; margin-top: 15px;">
                            <form action="/user_situation_delete" method="post" style="display: inline-block;">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" onclick="return confirm('Удалить ситуацию?')"
                                        style="background: #ff6b6b; color: white; border: none; border-radius: 6px; padding: 8px 16px; cursor: pointer; font-size: 14px;">
                                    Удалить
                                </button>
                            </form>
                        </div>
                    </div>
                </#if>
            </#list>

            <h2 style="color: #51cf66; margin-top: 30px;">Одобренные запросы</h2>
            <#list user_situations as situation>
                <#if situation.approved>
                    <div class="situation-card approved">
                        <h3>
                            <a href="/situation_control?situationId=${situation.id}">${situation.title}</a>
                        </h3>
                        <p><strong>Описание:</strong> ${situation.description!}</p>
                        <p><strong>Дата:</strong> ${situation.date!}</p>

                        <div style="text-align: center; margin-top: 15px;">
                            <form action="/user_situation_delete" method="post" style="display: inline-block;">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" onclick="return confirm('Удалить ситуацию?')"
                                        style="background: #ff6b6b; color: white; border: none; border-radius: 6px; padding: 8px 16px; cursor: pointer; font-size: 14px;">
                                    Удалить
                                </button>
                            </form>
                        </div>
                    </div>
                </#if>
            </#list>
        <#else>
            <div class="situation-card">
                <p>У вас пока нет созданных запросов</p>
            </div>
        </#if>
    </center>
</#macro>
</html>