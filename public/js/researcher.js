const researcherForm = document.getElementById('researcher-form');
const researcherTableBody = document.querySelector('#researcher-table tbody');
const cancelBtn = document.getElementById('cancel-btn');

if (!researcherForm || !researcherTableBody) {
    console.warn('Neki od potrebnih elemenata za researcher.js ne postoji na stranici.');
} else {
    let editingId = null;

    // Fetch researchers and show in the table
    function loadResearchers() {
        axios.get('/api/researcher')
            .then(rsp => {
                researcherTableBody.innerHTML = '';
                rsp.data.forEach(r => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${r.id}</td>
                        <td>${r.firstName}</td>
                        <td>${r.lastName}</td>
                        <td>${r.email}</td>
                        <td>${r.phone}</td>
                        <td>${formatDate(r.createdAt)}</td>
                        <td>${formatDate(r.updatedAt)}</td>
                        <td class="d-flex justify-content-center gap-2">
                            <button class="btn btn-sm btn-warning edit-btn">Izmeni</button>
                            <button class="btn btn-sm btn-danger delete-btn">Obriši</button>
                        </td>
                    `;

                    // Edit button
                    const editBtn = tr.querySelector('.edit-btn');
                    if (editBtn) {
                        editBtn.addEventListener('click', () => {
                            editingId = r.id;
                            document.getElementById('researcher-id').value = r.id;
                            document.getElementById('first-name').value = r.firstName;
                            document.getElementById('last-name').value = r.lastName;
                            document.getElementById('email').value = r.email;
                            document.getElementById('phone').value = r.phone;
                        });
                    }

                    // Delete button
                    const deleteBtn = tr.querySelector('.delete-btn');
                    if (deleteBtn) {
                        deleteBtn.addEventListener('click', () => {
                            if (confirm('Da li ste sigurni da želite da obrišete ovog istraživača?')) {
                                axios.delete('/api/researcher/' + r.id)
                                    .then(() => loadResearchers())
                                    .catch(err => showError(err?.response?.data?.message || err.message));
                            }
                        });
                    }

                    researcherTableBody.appendChild(tr);
                });
            })
            .catch(err => showError(err?.response?.data?.message || err.message));
    }

    // Add/edit a researcher
    researcherForm.addEventListener('submit', e => {
        e.preventDefault();

        const data = {
            firstName: document.getElementById('first-name').value,
            lastName: document.getElementById('last-name').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value
        };

        if (editingId) {
            axios.put('/api/researcher/' + editingId, data)
                .then(() => {
                    editingId = null;
                    researcherForm.reset();
                    loadResearchers();
                })
                .catch(err => showError(err?.response?.data?.message || err.message));
        } else {
            axios.post('/api/researcher', data)
                .then(() => {
                    researcherForm.reset();
                    loadResearchers();
                })
                .catch(err => showError(err?.response?.data?.message || err.message));
        }
    });

    // Cancel button
    if (cancelBtn) {
        cancelBtn.addEventListener('click', () => {
            editingId = null;
            researcherForm.reset();
        });
    }

    loadResearchers();
}