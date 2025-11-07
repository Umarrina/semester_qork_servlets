<html lang="en">
<#include "base.ftl">

<#macro title>Мои треки</#macro>
<#macro script></#macro>

<#macro content>
    <center>
        <h1>Мои треки</h1>

        <div style="margin: 20px 0;">
            <a href="/track_upload" class="btn-primary">+ Загрузить новый трек</a>
        </div>

        <#if tracks?? && tracks?size gt 0>
            <#list tracks as track>
                <div class="situation-card <#if track.approved>approved<#else>not-approved</#if>">
                    <h3>${track.title}</h3>
                    <p><strong>Статус:</strong>
                        <#if track.approved>
                            ✅ Одобрен
                        <#else>
                            ⏳ Ожидает одобрения
                        </#if>
                    </p>

                    <#if track.filePath??>
                        <audio controls style="width: 100%; margin: 10px 0;">
                            <source src="${track.filePath}" type="audio/mpeg">
                            Ваш браузер не поддерживает аудио элемент.
                        </audio>
                    </#if>

                    <form action="/user_track_delete" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="trackId" value="${track.id}">
                        <button type="submit" onclick="return confirm('Удалить трек?')"
                                style="background: #ff6b6b; color: white; padding: 5px 10px; border: none; border-radius: 10px; cursor: pointer;">
                            Удалить трек
                        </button>
                    </form>
                </div>
            </#list>
        <#else>
            <div class="situation-card">
                <p>У вас пока нет загруженных треков</p>
            </div>
        </#if>
    </center>
</#macro>
</html>