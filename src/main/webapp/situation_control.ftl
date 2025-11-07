<html lang="en">
<#include "base.ftl">

<#macro title>Управление треками</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Управление треками в ситуации</h1>
        <h2>${situation.title}</h2>

        <#if situation.tracks?? && situation.tracks?size gt 0>
            <#list situation.tracks as track>
                    <div class="situation-card">
                        <h3>${track.title}</h3>
                        <p><strong>Автор:</strong> ${track.author}</p>

                        <#if track.filePath??>
                            <audio controls style="width: 100%; margin: 10px 0;">
                                <source src="${track.filePath}" type="audio/mpeg">
                            </audio>
                        </#if>

                        <p><strong>Статус:</strong>
                            <#if track.approved>
                                Одобрен
                            <#else>
                                Не одобрен
                            </#if>
                        </p>

                        <div style="text-align: center; margin-top: 15px;">
                            <form action="/change_status_track" method="post" style="display: inline;">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <input type="hidden" name="trackId" value="${track.id}">
                                <input type="hidden" name="approved" value="true">
                                <button type="submit" style="background: #51cf66; color: white; padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer;">
                                    Одобрить
                                </button>
                            </form>

                            <form action="/change_status_track" method="post" style="display: inline;">
                                <input type="hidden" name="situationId" value="${situation.id}">
                                <input type="hidden" name="trackId" value="${track.id}">
                                <input type="hidden" name="approved" value="false">
                                <button type="submit" style="background: #ff6b6b; color: white; padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer; margin-left: 10px;">
                                    Отклонить
                                </button>
                            </form>
                        </div>
                    </div>

            </#list>
        <#else>
            <div class="situation-card">
                <p>Нет треков для управления</p>
            </div>
        </#if>

        <div style="margin-top: 20px;">
            <a href="/my_situation" style="background: #6c5ce7; color: white; padding: 10px 20px; border-radius: 6px; text-decoration: none;">
                Назад к ситуациям
            </a>
        </div>
    </center>
</#macro>
</html>