<html lang="en">
<#include "base.ftl">

<#macro title>${situation.title!}</#macro>
<#macro script></#macro>

<#macro content>
    <#if situation??>
        <div class="situation-card approved" style="max-width: 800px; margin: 0 auto;">
            <center>
                <h2>${situation.title!}</h2>
                <p><strong>Автор:</strong> ${username!}</p>
                <p><strong>Дата публикации:</strong> ${situation.date}</p>
                <p><strong>Описание:</strong> ${situation.description}</p>


                <div style="margin: 20px 0;">
                    <a href="/situation_add_track?situationId=${situation.id}"
                       style="
                            background: linear-gradient(135deg, #8b5cf6, #7c3aed);
                            color: white;
                            padding: 10px 20px;
                            border-radius: 20px;
                            text-decoration: none;
                            font-weight: bold;
                            display: inline-block;
                       ">
                        Добавить песню
                    </a>
                </div>

                <#if situation.tracks?? && situation.tracks?size gt 0>
                    <h3 style="color: #7c3aed; margin: 30px 0 15px 0;">Музыкальные треки</h3>
                    <#list situation.tracks as track>
                        <div class="situation-card" style="text-align: left; margin: 15px 0;">
                            <p><strong>🎵 ${track.title}</strong></p>
                            <#if track.filePath??>
                                <audio controls style="width: 100%; margin-top: 10px;">
                                    <source src="${track.filePath}" type="audio/mpeg">
                                    Ваш браузер не поддерживает аудио элемент.
                                </audio>
                            </#if>
                        </div>
                    </#list>
                <#else>
                    <div class="situation-card">
                        <p>Пока нет добавленных треков</p>
                    </div>
                </#if>

                <div style="margin-top: 30px;">
                    <a href="/main" style="color: #8b5cf6; text-decoration: none;">
                        Вернуться к запросам
                    </a>
                </div>
            </center>
        </div>
    </#if>
</#macro>
</html>