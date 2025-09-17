// global.js
/**
 * Formatira ISO datum u uniforman format YYYY-MM-DD HH:mm
 * @param {string} iso - ISO string datuma
 * @returns {string} - formatiran datum ili 'N/A'
 */
function formatDate(iso) {
    if (!iso) return 'N/A';
    const date = new Date(iso);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2,'0');
    const day = String(date.getDate()).padStart(2,'0');
    const hours = String(date.getHours()).padStart(2,'0');
    const minutes = String(date.getMinutes()).padStart(2,'0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

/**
 * Prikazuje grešku korisniku
 * @param {string} msg - poruka o grešci
 */
function showError(msg) {
    alert(msg || 'Došlo je do greške!');
}