<html lang="en">
<#include "admin_base.ftl">

<#macro title>Управление темами</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Темы и запросы</h1>

        <#if situations?? && situations?size gt 0>
            <h2 style="color: #ffa94d; margin-top: 30px;">Неодобренные запросы</h2>
            <#list situations as situation>
                <#if !situation.approved>
                    <div class="situation-card not-approved">
                        <h3>${situation.title}</h3>
                        <p><strong>Описание:</strong> ${situation.description!}</p>
                        <p><strong>Дата:</strong> ${situation.date!}</p>

                        <div class="admin-actions">
                            <form action="/admin_situation_approve" method="post">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" class="btn-primary">Одобрить</button>
                            </form>
                            <form action="/admin_situation_delete" method="post">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" class="btn-delete">Удалить</button>
                            </form>
                        </div>
                    </div>
                </#if>
            </#list>

            <h2 style="color: #51cf66; margin-top: 30px;">Одобренные запросы</h2>
            <#list situations as situation>
                <#if situation.approved>
                    <div class="situation-card approved">
                        <h3>${situation.title}</h3>
                        <p><strong>Описание:</strong> ${situation.description!}</p>
                        <p><strong>Дата:</strong> ${situation.date!}</p>

                        <div class="admin-actions">
                            <form action="/admin_situation_approve" method="post">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" class="btn-primary">Отозвать</button>
                            </form>
                            <form action="/admin_situation_delete" method="post">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <button type="submit" class="btn-delete">Удалить</button>
                            </form>
                        </div>
                    </div>
                </#if>
            </#list>
        <#else>
            <div class="situation-card">
                <p>Пока нет созданных запросов</p>
            </div>
        </#if>
    </center>
</#macro>
</html>