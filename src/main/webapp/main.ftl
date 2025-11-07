<html lang="en">
<#include "base.ftl">

<#macro title>Главная страница</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Темы и запросы</h1>

        <#if situations??>
            <#list situations as situation>
                <div class="situation-card <#if situation.approved>approved<#else>not-approved</#if>">
                    <h3>
                        <a href="/situation?situationId=${situation.id}">${situation.title}</a>
                    </h3>
                    <p><strong>Описание:</strong> ${situation.description!}</p>
                    <p><strong>Дата:</strong> ${situation.date!}</p>
                    <#if situation.approved>
                        <p style="color: #10b981;">✅ Одобрено</p>
                    <#else>
                        <p style="color: #f59e0b;">⏳ Ожидает одобрения</p>
                    </#if>
                </div>
            </#list>
        <#else>
            <div class="situation-card">
                <p>Пока нет созданных тем</p>
            </div>
        </#if>
    </center>
</#macro>
</html>