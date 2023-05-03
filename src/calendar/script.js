
    function criarBloco(uc, data, horaInicio, horaFim, sala, curso, turno, prof, nInscritos, lotacao) {
        var inicio = data + 'T' + horaInicio + ':00';
        var fim = data + 'T' + horaFim + ':00';

        return {
            uc: uc,
            inicio: inicio,
            fim: fim,
            sala: sala,
            curso: curso,
            turno:turno,
            prof: prof,
            nInscritos: nInscritos,
            lotacao: lotacao
        };
    }

        var blocos = [
                criarBloco('Disciplina A', '2023-05-01', '08:30', '09:00', 'D104', 'LEI', 'manha', 'Afonso', '5', '10'),
                criarBloco('Disciplina B', '2023-05-01', '09:30', '10:00', 'D104', 'LEI', 'manha', 'Afonso', '5', '10'),
                criarBloco('Disciplina C', '2023-05-02', '10:00', '11:00', 'D105', 'LCC', 'tarde', 'Rita', '7', '20'),
                criarBloco('Disciplina D', '2023-05-02', '11:30', '12:30', 'D105', 'LCC', 'tarde', 'Rita', '7', '20'),
                criarBloco('Disciplina E', '2023-05-03', '14:00', '16:00', 'D203', 'MEI', 'noite', 'Clara', '15', '30'),
                criarBloco('Disciplina F', '2023-05-04', '09:00', '10:00', 'D205', 'LGI', 'manha', 'Guilherme', '12', '15'),
                criarBloco('Disciplina G', '2023-05-05', '16:00', '17:00', 'D203', 'MIEC', 'tarde', 'Ana', '6', '25'),
                criarBloco('Disciplina H', '2023-05-06', '11:00', '12:00', 'D202', 'MIEC', 'manha', 'João', '9', '15'),
                criarBloco('Disciplina I', '2023-05-07', '15:00', '16:30', 'D105', 'LCC', 'tarde', 'Rita', '7', '20'),
                criarBloco('Disciplina J', '2023-05-08', '18:00', '20:00', 'D201', 'LEI', 'noite', 'Hélder', '20', '40')
                ];


   $('#calendar').fullCalendar({
    header: {
        left: 'prev, next today',
        center: 'uc',
        right: 'month,agendaWeek,agendaDay'
        },
        height: 'auto',
        contentHeight: 'auto',
        defaultView: 'agendaWeek',
        minTime:'08:00:00',
        maxTime: '20:30:00',
        slotDuration: '00:30:00',
        slotLabelFormat: 'h:mm A',
        allDaySlot: false,
        events: blocos,

        eventRender: function(event, element) {
        var uc = event.uc + ' - Sala ' + event.sala;
        var detalhes = $('<button>').addClass('btn btn-sm btn-info').text('Ver detalhes');
        element.find('fc.uc').append(detalhes);
        element.find('.fc-uc').text(uc);
        },
        eventClick: function(event) {
            if ($('#event-details-modal').length > 0) {
                $('#event-details-modal').remove();
            }
            var modal = $('<div>').addClass('modal fade').attr('id', 'event-details-modal');
            var modalDialog = $('<div>').addClass('modal-dialog');
            var modalContent = $('<div>').addClass('modal-content');
            var modalHeader = $('<div>').addClass('modal-header').html('<h5 class="modal-uc">' + event.uc);
            var modalBody = $('<div>').addClass('modal-body').html('Professor: ' + event.prof + '<br> Nº de Inscrições: ' + event.nInscritos + '<br>Lotação: ' + event.lotacao);
            modalContent.append(modalHeader, modalBody);
            modalDialog.append(modalContent);
            modal.append(modalDialog);

            $('body').append(modal);
            modal.modal('show');
        }
        });