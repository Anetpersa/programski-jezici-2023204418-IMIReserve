// instrument.js

const instrumentForm = document.getElementById('instrument-form');
const instrumentTableBody = document.querySelector('#instrument-table tbody');
const cancelBtn = document.getElementById('cancel-btn');

if (!instrumentForm || !instrumentTableBody) {
    console.warn('Neki od potrebnih elemenata za instrument.js ne postoji na stranici.');
} else {
    let editingId = null;

    // Učitaj instrumente i prikaži u tabeli
    function loadInstruments() {
        axios.get('/api/instrument')
            .then(rsp => {
                instrumentTableBody.innerHTML = '';
                rsp.data.forEach(i => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${i.id}</td>
                        <td>${i.instrumentName}</td>
                        <td>${i.laboratory}</td>
                        <td>${formatDate(i.createdAt)}</td>
                        <td>${formatDate(i.updatedAt)}</td>
                        <td class="d-flex justify-content-center gap-2">
                            <button class="btn btn-sm btn-warning edit-btn">Izmeni</button>
                            <button class="btn btn-sm btn-danger delete-btn">Obriši</button>
                        </td>
                    `;

                    // Edit dugme
                    const editBtn = tr.querySelector('.edit-btn');
                    if (editBtn) {
                        editBtn.addEventListener('click', () => {
                            editingId = i.id;
                            document.getElementById('instrument-id').value = i.id;
                            document.getElementById('instrument-name').value = i.instrumentName;
                            document.getElementById('laboratory').value = i.laboratory;
                        });
                    }

                    // Delete dugme
                    const deleteBtn = tr.querySelector('.delete-btn');
                    if (deleteBtn) {
                        deleteBtn.addEventListener('click', () => {
                            if (confirm('Da li ste sigurni da želite da obrišete ovaj instrument?')) {
                                axios.delete('/api/instrument/' + i.id)
                                    .then(() => loadInstruments())
                                    .catch(err => showError(err?.response?.data?.message || err.message));
                            }
                        });
                    }

                    instrumentTableBody.appendChild(tr);
                });
            })
            .catch(err => showError(err?.response?.data?.message || err.message));
    }

    // Submit forma (dodaj / izmeni instrument)
    instrumentForm.addEventListener('submit', e => {
        e.preventDefault();

        const data = {
            instrumentName: document.getElementById('instrument-name').value,
            laboratory: document.getElementById('laboratory').value
        };

        if (editingId) {
            axios.put('/api/instrument/' + editingId, data)
                .then(() => {
                    editingId = null;
                    instrumentForm.reset();
                    loadInstruments();
                })
                .catch(err => showError(err?.response?.data?.message || err.message));
        } else {
            axios.post('/api/instrument', data)
                .then(() => {
                    instrumentForm.reset();
                    loadInstruments();
                })
                .catch(err => showError(err?.response?.data?.message || err.message));
        }
    });

    // Cancel dugme
    if (cancelBtn) {
        cancelBtn.addEventListener('click', () => {
            editingId = null;
            instrumentForm.reset();
        });
    }

    // INIT
    loadInstruments();
}