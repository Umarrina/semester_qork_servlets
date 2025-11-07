<html lang="en">
<#include "base.ftl">

<#macro title>Добавление песни</#macro>
<#macro script>
    <script src = "https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(function() {
            let situationId = '${situationId!}';
            if (!situationId) {
                const urlParams = new URLSearchParams(window.location.search);
                situationId = urlParams.get('situationId');
            }
            // start search on input
            $('#searchInput').on('input', function () {
                const query = $(this).val().trim();
                if (query.length === 0) {
                    $('results').empty;
                    return;
                }
                $.ajax({
                    url: '/search',
                    method: 'GET',
                    data: {search: query},
                    success: function (data) {
                        $('#results').empty();
                        data.forEach(function (track) {
                            $('#results').append(
                                $('<div>')
                                    .addClass('result-item')
                                    .attr('data-id', track.id)
                                    .text(track.title)
                            )
                        });
                    }
                });
            });

            // click to track and full line
            $('#results').on('click', '.result-item', function () {
                const text = $(this).text();
                $('#selectedTrack').val(text);
                $('#selectedTrack').data('trackId', $(this).attr('data-id'));
                $('#results').empty();
                $('#searchInput').val(text);
            });

            // click to save send track to servlet
            $('#saveBtn').on('click', function () {
                let trackId = $('#selectedTrack').data('trackId');
                if (!trackId) {
                    alert('Выберите песню из списка');
                    return;
                }
                if (!situationId) {
                    alert('Ошибка: ситуация не выбрана');
                    return;
                }

                $.ajax({
                    url: '/situation_add_track',
                    method: 'POST',
                    data: {trackId : trackId, situationId : situationId},
                    success: function (response) {
                        alert("success");
                        window.location.href = '/situation?situationId=' + situationId
                    },
                    error: function () {
                        alert('error');
                    }
                });
            });
        });
    </script>

    <style>
        #results { border: 1px solid #ccc; max-height: 200px; overflow-y: auto; margin-top: 8px; }
        .result-item { padding: 6px 8px; cursor: pointer; }
        .result-item:hover { background: #f0f4ff; }
    </style>
</#macro>

<#macro content>
    <form method="post" action="/situation_add_track">
        <center>
            <h2>Добавление песни к запросу</h2>

            <h3>Поиск песни</h3>
            <input type="text" id="searchInput" placeholder="Введите название или автора">
            <div id="results"></div>

            <h3>Выбранная песня</h3>
            <input type="text" id="selectedTrack" readonly>

            <button id="saveBtn">Сохранить</button>

        </center>
    </form>
</#macro>
</html>