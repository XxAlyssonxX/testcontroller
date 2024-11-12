$(document).ready(function () {
	loadGuests();

	$('#guestForm').submit(function (event) {
		event.preventDefault();
		const id = $('#guestid').val();
		if (id) {
			updateGuest(id);
		} else {
			addGuest(); 
		}
	});
});

function loadGuests() {
	$.getJSON('/quarto/', function (data) {
		$('#guestTableBody').empty();
		data.forEach(guest => {
			$('#guestTableBody').append(
				`<tr>
	<td>${guest.id}</td>
	<td>${guest.tipo}</td>
	<td>${guest.num}</td>
	<td>${guest.andar}</td>
	<td>
		<button class="btn btn-sm btn-warning" onclick="showEditGuestForm(${guest.id}, '${guest.tipo}', '${guest.num}', '${guest.andar}')">Edit</button>
		<button class="btn btn-sm btn-danger" onclick="deleteGuest(${guest.id})">Delete</button>
	</td>
	</tr>`
			);
		});
	});
}

function showAddGuestForm() {
	$('#formTitle').text('Adicionar Quarto');
	$('#guestid').val('');
	$('#guestTipoQuarto').val('');
	$('#guestNumQuarto').val('');
	$('#guestAndar').val('');
	$('#guestFormModal').show();
}

function showEditGuestForm(id, tipo, num, andar) {
	$('#formTitle').text('Editar Quarto');
	$('#guestid').val(id);
	$('#guestTipoQuarto').val(tipo);
	$('#guestNumQuarto').val(num);
	$('#guestAndar').val(andar);
	$('#guestFormModal').show();
}

function closeGuestForm() {
	$('#guestFormModal').hide();
}

function addGuest() {
	const guest = {
		tipo: $('#guestTipoQuarto').val(),
		num: $('#guestNumQuarto').val(),
		andar: $('#guestAndar').val(),
	};
	$.ajax({
		url: '/quarto/',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(guest),
		success: function () {
			closeGuestForm();
			loadGuests();
		}
	});
}

function updateGuest(id) {
	const guest = {
		tipo: $('#guestTipoQuarto').val(),
		num: $('#guestNumQuarto').val(),
		andar: $('#guestAndar').val()
	};
	$.ajax({
		url: `/quarto/${id}`,
		type: 'PUT',
		contentType: 'application/json',
		data: JSON.stringify(guest),
		success: function () {
			closeGuestForm();
			loadGuests();
		}
	});
}

function deleteGuest(id) {
	if (confirm('Realmente vai deletar?')) {
		$.ajax({
			url: `/quarto/${id}`,
			type: 'DELETE',
			success: function () {
				loadGuests();
			}
		});
	}
}
