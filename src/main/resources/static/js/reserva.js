$(document).ready(function () {
    loadReservas();
    loadQuartos();
    loadHospedes();

    $('#reservaForm').submit(function (event) {
        event.preventDefault();
        const id = $('#reservaId').val();
        if (id) {
            updateReserva(id);
        } else {
            addReserva();
        }
    });
});

function loadReservas() {
    $.getJSON('/reserva/', function (data) {
        $('#reservaTableBody').empty();
        data.forEach(reserva => {
            $('#reservaTableBody').append(
                `<tr>
                    <td>${reserva.id}</td>
                    <td>${reserva.disponibilidade}</td>
                    <td>${reserva.quarto.tipo} - ${reserva.quarto.num}</td>
                    <td>${reserva.hospede.name}</td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="showEditReservaForm(${reserva.id}, '${reserva.disponibilidade}', ${reserva.quarto.id}, ${reserva.hospede.id})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="deleteReserva(${reserva.id})">Deletar</button>
                    </td>
                </tr>`
            );
        });
    });
}

function loadQuartos() {
    $.getJSON('/quarto/', function (data) {
        $('#reservaQuarto').empty();
        data.forEach(quarto => {
            $('#reservaQuarto').append(
                `<option value="${quarto.id}">${quarto.tipo} - ${quarto.num}</option>`
            );
        });
    });
}

function loadHospedes() {
    $.getJSON('/hospede/', function (data) {
        $('#reservaHospede').empty();
        data.forEach(hospede => {
            $('#reservaHospede').append(
                `<option value="${hospede.id}">${hospede.name}</option>`
            );
        });
    });
}

function showAddReservaForm() {
    $('#formTitle').text('Adicionar Reserva');
    $('#reservaId').val('');
    $('#reservaDisponibilidade').val('');
    $('#reservaQuarto').val('');
    $('#reservaHospede').val('');
    $('#reservaFormModal').show();
}

function showEditReservaForm(id, disponibilidade, quartoId, hospedeId) {
    $('#formTitle').text('Editar Reserva');
    $('#reservaId').val(id);
    $('#reservaDisponibilidade').val(disponibilidade);
    $('#reservaQuarto').val(quartoId);
    $('#reservaHospede').val(hospedeId);
    $('#reservaFormModal').show();
}

function closeReservaForm() {
    $('#reservaFormModal').hide();
}

function addReserva() {
    const reserva = {
        disponibilidade: $('#reservaDisponibilidade').val(),
        quarto: { id: $('#reservaQuarto').val() },
        hospede: { id: $('#reservaHospede').val() }  
    };
    $.ajax({
        url: '/reserva/',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(reserva),
        success: function () {
            closeReservaForm();
            loadReservas();
        }
    });
}

function updateReserva(id) {
    const reserva = {
        disponibilidade: $('#reservaDisponibilidade').val(),
        quarto: { id: $('#reservaQuarto').val() },
        hospede: { id: $('#reservaHospede').val() }  
    };
    $.ajax({
        url: `/reserva/${id}`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(reserva),
        success: function () {
            closeReservaForm();
            loadReservas();
        }
    });
}

function deleteReserva(id) {
    if (confirm('Realmente deseja deletar?')) {
        $.ajax({
            url: `/reserva/${id}`,
            type: 'DELETE',
            success: function () {
                loadReservas();
            }
        });
    }
}
