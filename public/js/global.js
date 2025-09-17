// Format ISO date and time to YYYY-MM-DD HH:mm format
function formatDate(iso) {
    if (!iso) return 'N/A';
    const date = new Date(iso);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

// Displays an error to a user
function showError(msg) {
    alert(msg || 'Došlo je do greške!');
}