<html lang="en">
<#include "admin_base.ftl">

<#macro title>Управление музыкой</#macro>
<#macro script></#macro>

<#macro content>
<center>
    <h1>Музыка</h1>

    <#if tracks?? && tracks?size gt 0>
        <h2 style="color: #ffa94d; margin-top: 30px;">Неодобренная музыка</h2>
        <#list tracks as track>
            <#if !track.approved>
                <div class="situation-card not-approved">
                    <div class="track-info">
                        <h3>${track.title}</h3>
                        <p><strong>Автор:</strong> ${track.author}</p>

                        <#if track.filePath??>
                            <audio controls class="audio-player">
                                <source src="${track.filePath}" type="audio/mpeg">
                                Ваш браузер не поддерживает аудио элемент.
                            </audio>
                        </#if>
                    </div>

                    <div class="admin-actions">
                        <form action="/admin_track_approve" method="post" style="display: inline;">
                            <input type="hidden" name="trackId" value="${track.id}">
                            <button type="submit" class="btn-primary">
                                Одобрить
                            </button>
                        </form>
                        <form action="/admin_track_delete" method="post" style="display: inline;">
                            <input type="hidden" name="trackId" value="${track.id}">
                            <button type="submit" onclick="return confirm('Удалить трек?')"
                                    style="background: #ff6b6b; color: white; padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer;">
                                Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </#if>
        </#list>

        <h2 style="color: #51cf66; margin-top: 30px;">Одобренная музыка</h2>
        <#list tracks as track>
            <#if track.approved>
                <div class="situation-card approved">
                    <div class="track-info">
                        <h3>${track.title}</h3>
                        <p><strong>Автор:</strong> ${track.author}</p>

                        <#if track.filePath??>
                            <audio controls class="audio-player">
                                <source src="${track.filePath}" type="audio/mpeg">
                                Ваш браузер не поддерживает аудио элемент.
                            </audio>
                        </#if>
                    </div>

                    <div class="admin-actions">
                        <form action="/admin_track_approve" method="post" style="display: inline;">
                            <input type="hidden" name="trackId" value="${track.id}">
                            <button type="submit" class="btn-primary">
                                Отозвать одобрение
                            </button>
                        </form>
                        <form action="/admin_track_delete" method="post" style="display: inline;">
                            <input type="hidden" name="trackId" value="${track.id}">
                            <button type="submit" onclick="return confirm('Удалить трек?')"
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
            <p>Пока нет добавленной музыки</p>
        </div>
    </#if>

</center>
</#macro>