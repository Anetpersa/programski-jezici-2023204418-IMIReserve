const reservationForm = document.getElementById('reservation-form');
const reservationTableBody = document.querySelector('#reservation-table tbody');
const cancelBtn = document.getElementById('cancel-btn');

if (!reservationForm || !reservationTableBody) {
    console.warn('Neki od potrebnih elemenata za reservation.js ne postoji na stranici.');
} else {
    let editingId = null;

    function loadReservations() {
        axios.get('/api/reservation')
            .then(rsp => {
                reservationTableBody.innerHTML = '';
                rsp.data.forEach(r => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
    <td>${r.id}</td>
    <td>${r.researcher}</td>
    <td>${r.instrument}</td>
    <td>${r.date}</td>
    <td>${formatDate(r.createdAt)}</td>
    <td>${formatDate(r.updatedAt)}</td>
    <td class="d-flex justify-content-center gap-2">
        <button class="btn btn-sm btn-warning edit-btn">Izmeni</button>
        <button class="btn btn-sm btn-danger delete-btn">Obriši</button>
    </td>
                    `;

                    const editBtn = tr.querySelector('.edit-btn');
                    if (editBtn) {
                        editBtn.addEventListener('click', () => {
                            editingId = r.id;
                            document.getElementById('reservation-id').value = r.id;
                            document.getElementById('researcher').value = r.researcher;
                            document.getElementById('instrument').value = r.instrument;
                            document.getElementById('date').value = r.date;
                        });
                    }

                    const deleteBtn = tr.querySelector('.delete-btn');
                    if (deleteBtn) {
                        deleteBtn.addEventListener('click', () => {
                            if (confirm('Da li ste sigurni da želite da obrišete ovu rezervaciju?')) {
                                axios.delete('/api/reservation/' + r.id)
                                    .then(() => loadReservations())
                                    .catch(err => showError(err.response?.data?.message));
                            }
                        });
                    }

                    reservationTableBody.appendChild(tr);
                });
            })
            .catch(err => showError(err.response?.data?.message));
    }

    reservationForm.addEventListener('submit', e => {
        e.preventDefault();

        const data = {
            researcher: document.getElementById('researcher').value,
            instrument: document.getElementById('instrument').value,
            date: document.getElementById('date').value
        };

        if (editingId) {
            axios.put('/api/reservation/' + editingId, data)
                .then(() => {
                    editingId = null;
                    reservationForm.reset();
                    loadReservations();
                })
                .catch(err => showError(err.response?.data?.message));
        } else {
            axios.post('/api/reservation', data)
                .then(() => {
                    reservationForm.reset();
                    loadReservations();
                })
                .catch(err => showError(err.response?.data?.message));
        }
    });

    if (cancelBtn) {
        cancelBtn.addEventListener('click', () => {
            editingId = null;
            reservationForm.reset();
        });
    }

    loadReservations();
}