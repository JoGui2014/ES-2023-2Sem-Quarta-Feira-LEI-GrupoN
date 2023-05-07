<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<select id="dropdownCursos" class="custom-select"></select>
<div id="calendar"></div>
<input type="file">
<div id="preview"></div>
<script>
    function handleCSVFile() {
        const fileInput = document.querySelector('[type="file"]');
        fileInput.addEventListener('change', () => {
            const fr = new FileReader();
            fr.readAsText(fileInput.files[0]);
            fr.addEventListener('load', () => {
                const csvData = fr.result;
                const horario = new Horario(csvData); // Create an instance of Horario with CSV data
                var blocos = horario.getAulas(); // Retrieve the list of Aulas
                // var lines = csvData.split('\n');
                // for (var i = 0; i < lines.length; i++) {
                //     var cells = lines[i].split(';');
                //     // Extract values from CSV cells
                //     var curso = cells[0];
                //     var title = cells[1];
                //     var turno = cells[2];
                //     var prof = cells[3];
                //     var nInscricoes = cells[4];
                //     var diaSemana = cells[5];
                //     var horaInicio = cells[6];
                //     var horaFim = cells[7];
                //     var data = cells[8];
                //     var sala = cells[9];
                //     var lotacao = cells[10];
                //     // Call criarBloco function with extracted values
                //     blocos.push(criarBloco(curso, title, turno, prof, nInscricoes, diaSemana, horaInicio, horaFim, data, sala, lotacao));
                // }
                var cursos = {};
                for (var i = 1; i < blocos.length; i++) {
                    alert(blocos[i])
                    var curso = blocos[i].curso;
                    cursos[curso] = true;
                }
                var cursosUnique = Object.keys(cursos);
                var dropdownCursos = document.getElementById("dropdownCursos");
                for (var i = -1; i < cursosUnique.length; i++) {
                    var option = document.createElement("option");
                    option.text = cursosUnique[i];
                    dropdownCursos.add(option);
                }
                var cursoSelecionado = ""; // Definir variável para armazenar a opção selecionada
                dropdownCursos.addEventListener("change", function () {
                    cursoSelecionado = dropdownCursos.value;
                    var blocoSelecionado = blocos.filter(function (bloco) {
                        return bloco.curso === cursoSelecionado;
                    });
                    // Inicializar o FullCalendar
                    $('#calendar').fullCalendar({
                        header: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'month,agendaWeek,agendaDay'
                        },
                        height: 'auto', // Ajustar automaticamente a altura ao número de eventos
                        contentHeight: 'auto', // Ajustar a altura do conteúdo do calendário para que caiba todos os eventos
                        defaultView: 'agendaWeek',
                        minTime: '08:00:00', // Hora de início do horário
                        maxTime: '20:30:00', // Hora de término do horário
                        slotDuration: '00:30:00', // Duração de cada slot de tempo (30 minutos)
                        slotLabelFormat: 'h:mm A', // Formato do rótulo de tempo
                        allDaySlot: false, // Não mostrar o slot de tempo para o dia inteiro
                        events: blocoSelecionado, // Passar a lista de "blocos" como eventos do FullCalendar

                        eventRender: function (event, element) {
                            // Montar o título do evento com o nome da disciplina e a sala
                            var title = event.title + ' - Sala ' + event.sala;
                            // Adicionar um botão para ver mais detalhes
                            var detailsButton = $('<button>').addClass('btn btn-sm btn-info').text('Ver detalhes');
                            element.find('.fc-title').append(detailsButton);
                            // Definir o título no elemento do evento
                            element.find('.fc-title').text(title);
                        },
                        eventClick: function (event) {

                            if ($('#event-details-modal').length > 0) {
                                $('#event-details-modal').remove();
                            }

                            // Criar o modal com as informações do evento
                            var modal = $('<div>').addClass('modal fade').attr('id', 'event-details-modal');
                            var modalDialog = $('<div>').addClass('modal-dialog');
                            var modalContent = $('<div>').addClass('modal-content');
                            var modalHeader = $('<div>').addClass('modal-header').html('<h5 class="modal-title">' + event.title);
                            var modalBody = $('<div>').addClass('modal-body').html('Professor: ' + event.prof + '<br>Número de inscrições: ' + event.nInscricoes + '<br>Tamanho da sala: ' + event.lotacao + '<br>Curso: ' + event.curso);
                            modalContent.append(modalHeader, modalBody);
                            modalDialog.append(modalContent);
                            modal.append(modalDialog);

                            // Adicionar o modal ao corpo da página e exibi-lo
                            $('body').append(modal);
                            modal.modal('show');
                        }
                    });

                });
            });
        });
    }

    function criarBloco(curso, title, turno, prof, nInscricoes, diaSemana, horaInicio, horaFim, data, sala, lotacao) {
        var start = data + 'T' + horaInicio + ':00';
        var end = data + 'T' + horaFim + ':00';

        return {
            curso: curso,
            title: title,
            turno: turno,
            prof: prof,
            nInscricoes: nInscricoes,
            diaSemana: diaSemana,
            start: start,
            end: end,
            sala: sala,
            lotacao: lotacao
        };
    }

    handleCSVFile();
</script>
</body>
</html>





<!--<!DOCTYPE html>-->
<!--<html>-->
<!--<head>-->
<!--    <meta charset="utf-8">-->
<!--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" />-->
<!--    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>-->
<!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>-->
<!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>-->
<!--    <link rel="stylesheet" type="text/css" href="styles.css">-->
<!--</head>-->

<!--<select id="dropdownCursos" class="custom-select"></select>-->

<!--<body>-->
<!--<div id="calendar"></div>-->

<!--<input type="file">-->
<!--<div id="preview"></div>-->

<!--<script>-->
<!--    function handleCSVFile() {-->

<!--        const fileInput = document.querySelector('[type="file"]');-->

<!--        fileInput.addEventListener('change', () => {-->
<!--            const fr = new FileReader();-->

<!--            fr.readAsText(fileInput.files[0])-->
<!--            fr.addEventListener('load', () => {-->

<!--                const csvData = fr.result;-->

<!--                var lines = csvData.split('\n');-->
<!--                var blocos = [];-->
<!--                for (var i = 0; i < lines.length; i++) {-->
<!--                    var cells = lines[i].split(';');-->

<!--                    // Extract values from CSV cells-->
<!--                    var curso = cells[0];-->
<!--                    var title = cells[1];-->
<!--                    var turno = cells[2];-->
<!--                    var prof = cells[3];-->
<!--                    var nInscricoes = cells[4];-->
<!--                    var diaSemana = cells[5];-->
<!--                    var horaInicio = cells[6];-->
<!--                    var horaFim = cells[7];-->
<!--                    var data = cells[8];-->
<!--                    var sala = cells[9];-->
<!--                    var lotacao = cells[10];-->
<!--                    // Call criarBloco function with extracted values-->
<!--                    var bloco = criarBloco(curso, title, turno, prof, nInscricoes, diaSemana, horaInicio, horaFim, data, sala, lotacao);-->
<!--                    blocos.push(bloco);-->
<!--                }-->
<!--                var cursos = {};-->

<!--                for (var i = 1; i < blocos.length; i++) {-->
<!--                    var curso = blocos[i].curso;-->
<!--                    cursos[curso] = true;-->
<!--                }-->

<!--                var cursosUnique = Object.keys(cursos);-->

<!--                var dropdownCursos = document.getElementById("dropdownCursos");-->

<!--                for (var i = -1; i < cursosUnique.length; i++) {-->
<!--                    var option = document.createElement("option");-->
<!--                    option.text = cursosUnique[i];-->
<!--                    dropdownCursos.add(option);-->
<!--                }-->

<!--                var cursoSelecionado = ""; // Definir variável para armazenar a opção selecionada-->

<!--                dropdownCursos.addEventListener("change", function () {-->
<!--                    cursoSelecionado = dropdownCursos.value;-->

<!--                    var blocoSelecionado = blocos.filter(function (bloco) {-->
<!--                        return bloco.curso === cursoSelecionado;-->
<!--                    });-->


<!--                    // Inicializar o FullCalendar-->
<!--                    $('#calendar').fullCalendar({-->
<!--                        header: {-->
<!--                            left: 'prev,next today',-->
<!--                            center: 'title',-->
<!--                            right: 'month,agendaWeek,agendaDay'-->
<!--                        },-->
<!--                        height: 'auto', // Ajustar automaticamente a altura ao número de eventos-->
<!--                        contentHeight: 'auto', // Ajustar a altura do conteúdo do calendário para que caiba todos os eventos-->
<!--                        defaultView: 'agendaWeek',-->
<!--                        minTime: '08:00:00', // Hora de início do horário-->
<!--                        maxTime: '20:30:00', // Hora de término do horário-->
<!--                        slotDuration: '00:30:00', // Duração de cada slot de tempo (30 minutos)-->
<!--                        slotLabelFormat: 'h:mm A', // Formato do rótulo de tempo-->
<!--                        allDaySlot: false, // Não mostrar o slot de tempo para o dia inteiro-->
<!--                        events: blocoSelecionado, // Passar a lista de "blocos" como eventos do FullCalendar-->

<!--                        eventRender: function (event, element) {-->
<!--                            // Montar o título do evento com o nome da disciplina e a sala-->
<!--                            var title = event.title + ' - Sala ' + event.sala;-->
<!--                            // Adicionar um botão para ver mais detalhes-->
<!--                            var detailsButton = $('<button>').addClass('btn btn-sm btn-info').text('Ver detalhes');-->
<!--                            element.find('.fc-title').append(detailsButton);-->
<!--                            // Definir o título no elemento do evento-->
<!--                            element.find('.fc-title').text(title);-->
<!--                        },-->
<!--                        eventClick: function (event) {-->

<!--                            if ($('#event-details-modal').length > 0) {-->
<!--                                $('#event-details-modal').remove();-->
<!--                            }-->

<!--                            // Criar o modal com as informações do evento-->
<!--                            var modal = $('<div>').addClass('modal fade').attr('id', 'event-details-modal');-->
<!--                            var modalDialog = $('<div>').addClass('modal-dialog');-->
<!--                            var modalContent = $('<div>').addClass('modal-content');-->
<!--                            var modalHeader = $('<div>').addClass('modal-header').html('<h5 class="modal-title">' + event.title);-->
<!--                            var modalBody = $('<div>').addClass('modal-body').html('Professor: ' + event.prof + '<br>Número de inscrições: ' + event.nInscricoes + '<br>Tamanho da sala: ' + event.lotacao + '<br>Curso: ' + event.curso);-->
<!--                            modalContent.append(modalHeader, modalBody);-->
<!--                            modalDialog.append(modalContent);-->
<!--                            modal.append(modalDialog);-->

<!--                            // Adicionar o modal ao corpo da página e exibi-lo-->
<!--                            $('body').append(modal);-->
<!--                            modal.modal('show');-->
<!--                        }-->
<!--                    });-->

<!--                });-->
<!--            })-->
<!--        });-->

<!--    }-->

<!--    function criarBloco(curso, title, turno, prof, nInscricoes, diaSemana, horaInicio, horaFim, data, sala, lotacao) {-->
<!--        var start = data + 'T' + horaInicio + ':00';-->
<!--        var end = data + 'T' + horaFim + ':00';-->

<!--        return {-->
<!--            curso: curso,-->
<!--            title: title,-->
<!--            turno: turno,-->
<!--            prof: prof,-->
<!--            nInscricoes: nInscricoes,-->
<!--            diaSemana: diaSemana,-->
<!--            start: start,-->
<!--            end: end,-->
<!--            sala: sala,-->
<!--            lotacao: lotacao-->
<!--        };-->
<!--    }-->
<!--    handleCSVFile();-->

<!--   -->
<!--</script>-->
<!--</body>-->
<!--</html>-->
