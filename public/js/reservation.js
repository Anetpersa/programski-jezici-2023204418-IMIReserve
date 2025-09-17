const reservationForm = document.getElementById('reservation-form');
const reservationTableBody = document.querySelector('#reservation-table tbody');
const cancelBtn = document.getElementById('cancel-btn');
const researcherSelect = document.getElementById('researcherId');
const instrumentSelect = document.getElementById('instrumentId');
const startTimeInput = document.getElementById('startTime');
const endTimeInput = document.getElementById('endTime');

let researchers = [];
let instruments = [];
let editingId = null;

// Fetch researchers
function loadResearchers() {
    axios.get('/api/researcher')
        .then(rsp => {
            researchers = rsp.data;
            researcherSelect.innerHTML = '';
            researchers.forEach(r => {
                const option = document.createElement('option');
                option.value = r.id;
                option.textContent = `${r.firstName} ${r.lastName}`;
                researcherSelect.appendChild(option);
            });
        });
}

// Fetch instruments
function loadInstruments() {
    axios.get('/api/instrument')
        .then(rsp => {
            instruments = rsp.data;
            instrumentSelect.innerHTML = '';
            instruments.forEach(i => {
                const option = document.createElement('option');
                option.value = i.id;
                option.textContent = i.instrumentName;
                instrumentSelect.appendChild(option);
            });
        });
}

// Fetch reservations and show in the table
function loadReservations() {
    axios.get('/api/reservation')
        .then(rsp => {
            reservationTableBody.innerHTML = '';
            rsp.data.forEach(r => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${r.id}</td>
                    <td>${r.researcherName}</td>
                    <td>${r.instrumentName}</td>
                    <td>${r.parameter}</td>
                    <td>${formatDate(r.startTime)}</td>
                    <td>${formatDate(r.endTime)}</td>
                    <td>${r.createdAt ? formatDate(r.createdAt) : ''}</td>
                    <td>${r.updatedAt ? formatDate(r.updatedAt) : ''}</td>
                    <td class="d-flex justify-content-center gap-2">
                        <button class="btn btn-sm btn-warning edit-btn">Izmeni</button>
                        <button class="btn btn-sm btn-danger delete-btn">Obriši</button>
                    </td>
                `;

                // Edit button
                tr.querySelector('.edit-btn').addEventListener('click', () => {
                    editingId = r.id;
                    document.getElementById('reservation-id').value = r.id;
                    researcherSelect.value = r.researcherId;
                    instrumentSelect.value = r.instrumentId;
                    document.getElementById('parameter').value = r.parameter;
                    startTimeInput.value = r.startTime ? r.startTime.substring(0, 16) : '';
                    endTimeInput.value = r.endTime ? r.endTime.substring(0, 16) : '';
                    if (startTimeInput.value) endTimeInput.min = startTimeInput.value;
                });

                // Delete button
                tr.querySelector('.delete-btn').addEventListener('click', () => {
                    if (confirm('Da li ste sigurni da želite da obrišete ovu rezervaciju?')) {
                        axios.delete('/api/reservation/' + r.id)
                            .then(() => loadReservations());
                    }
                });

                reservationTableBody.appendChild(tr);
            });
        });
}

// Date validation
startTimeInput.addEventListener('change', () => {
    if (startTimeInput.value) {
        endTimeInput.min = startTimeInput.value;
        if (endTimeInput.value && endTimeInput.value < startTimeInput.value) {
            endTimeInput.value = startTimeInput.value;
        }
    } else {
        endTimeInput.min = '';
    }
});

endTimeInput.addEventListener('change', () => {
    if (startTimeInput.value && endTimeInput.value < startTimeInput.value) {
        alert('Vreme završetka ne može biti pre vremena početka!');
        endTimeInput.value = startTimeInput.value;
    }
});

// Add/edit a reservation
reservationForm.addEventListener('submit', e => {
    e.preventDefault();

    if (!startTimeInput.value || !endTimeInput.value) return;

    if (endTimeInput.value < startTimeInput.value) {
        alert('Vreme završetka ne može biti pre vremena početka!');
        return;
    }

    const data = {
        researcherId: parseInt(researcherSelect.value),
        instrumentId: parseInt(instrumentSelect.value),
        parameter: document.getElementById('parameter').value,
        startTime: startTimeInput.value,
        endTime: endTimeInput.value
    };

    if (editingId) {
        axios.put('/api/reservation/' + editingId, data)
            .then(() => {
                editingId = null;
                reservationForm.reset();
                endTimeInput.min = '';
                loadReservations();
            });
    } else {
        axios.post('/api/reservation', data)
            .then(() => {
                reservationForm.reset();
                endTimeInput.min = '';
                loadReservations();
            });
    }
});

// Cancel button
cancelBtn.addEventListener('click', () => {
    editingId = null;
    reservationForm.reset();
    endTimeInput.min = '';
});

loadResearchers();
loadInstruments();
loadReservations();