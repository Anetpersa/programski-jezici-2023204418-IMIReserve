const reservationForm = document.getElementById('reservation-form');
const reservationTableBody = document.querySelector('#reservation-table tbody');
const cancelBtn = document.getElementById('cancel-btn');
const researcherSelect = document.getElementById('researcherId');
const instrumentSelect = document.getElementById('instrumentId');

let researchers = [];
let instruments = [];
let editingId = null;

// Učitaj istraživače
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

// Učitaj instrumente
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

// Učitaj rezervacije
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
                    <td>${r.startTime}</td>
                    <td>${r.endTime}</td>
                    <td>${r.createdAt ? r.createdAt : ''}</td>
                    <td>${r.updatedAt ? r.updatedAt : ''}</td>
                    <td class="d-flex justify-content-center gap-2">
                        <button class="btn btn-sm btn-warning edit-btn">Izmeni</button>
                        <button class="btn btn-sm btn-danger delete-btn">Obriši</button>
                    </td>
                `;

                // Edit dugme
                tr.querySelector('.edit-btn').addEventListener('click', () => {
                    editingId = r.id;
                    document.getElementById('reservation-id').value = r.id;
                    researcherSelect.value = r.researcherId;
                    instrumentSelect.value = r.instrumentId;
                    document.getElementById('parameter').value = r.parameter;
                    document.getElementById('startTime').value = r.startTime;
                    document.getElementById('endTime').value = r.endTime;
                });

                // Delete dugme
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

// Submit (dodaj / izmeni)
reservationForm.addEventListener('submit', e => {
    e.preventDefault();

    const data = {
        researcherId: parseInt(researcherSelect.value),
        instrumentId: parseInt(instrumentSelect.value),
        parameter: document.getElementById('parameter').value,
        startTime: document.getElementById('startTime').value,
        endTime: document.getElementById('endTime').value
    };

    if (editingId) {
        axios.put('/api/reservation/' + editingId, data)
            .then(() => {
                editingId = null;
                reservationForm.reset();
                loadReservations();
            });
    } else {
        axios.post('/api/reservation', data)
            .then(() => {
                reservationForm.reset();
                loadReservations();
            });
    }
});

// Cancel dugme
cancelBtn.addEventListener('click', () => {
    editingId = null;
    reservationForm.reset();
});

// INIT
loadResearchers();
loadInstruments();
loadReservations();