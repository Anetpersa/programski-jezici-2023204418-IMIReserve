// global.js

/**
 * Formatira ISO datum u srpski lokalni format
 * @param {string} iso - ISO string datuma
 * @returns {string} - formatiran datum ili 'N/A'
 */
function formatDate(iso) {
    if (!iso) return 'N/A';
    return new Date(iso).toLocaleString('sr-RS', {
        year: '2-digit',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Prikazuje grešku korisniku
 * @param {string} msg - poruka o grešci
 */
function showError(msg) {
    alert(msg || 'Došlo je do greške!');
}