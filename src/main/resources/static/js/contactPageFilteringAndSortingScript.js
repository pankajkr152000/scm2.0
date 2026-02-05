
let currentType = "all";
let currentPage = 0;
let sortBy = "name";
let dir = "ascending";

function loadContacts(page = 0) {
    currentPage = page;

    fetch(`/contacts/filter?type=${currentType}&page=${currentPage}&sortBy=${sortBy}&sortDirection=${sortDirection}`)
        .then(res => res.text())
        .then(html => {
            document.getElementById("contactsContainer").innerHTML = html;
        });
}

function changeSort(value) {
    sortBy = value;
    loadContacts(0);
}

function changeDirection(value) {
    dir = value;
    loadContacts(0);
}

function filterType(type) {
    currentType = type;
    loadContacts(0);
}
