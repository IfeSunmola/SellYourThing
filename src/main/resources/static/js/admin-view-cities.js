const BASE_URL = '/admin/cities/'

function openEditCityModal() {
    $('document').ready(function () {
        $('.table .editButton').on('click', function (event) {
            event.preventDefault();
            sessionStorage.setItem("cityName", $(this).closest("tr").find("#cityName").text());
            $('#editCityModalHeader').html('Editing City <span><strong>' + sessionStorage.cityName + '</strong></span>');
            $('#editCityModal').modal('show');
        });
    });
}

function validateEditModalButton() {
    const saveEditsButton = document.getElementById("saveEditsButton");
    const cityName = sessionStorage.cityName;
    const oldCityField = document.getElementById("oldCityName").value;
    const newCityName = document.getElementById("newCityName").value;

    if (cityName === oldCityField) {
        saveEditsButton.disabled = false;
        $('#editCityModalForm').attr('action', BASE_URL + 'edit/' + sessionStorage.cityName + '/' + newCityName);
        return;
    }
    saveEditsButton.disabled = true;
}

function openDeleteCityModal() {
    $('document').ready(function () {
        $('.table .deleteButton').on('click', function (event) {
            event.preventDefault();
            sessionStorage.setItem("cityName", $(this).closest("tr").find("#cityName").text());
            $('#deleteCityModalHeader').html('Delete City: <span><strong>' + sessionStorage.cityName + '</strong></span>? All the posts' +
                ' assoociated with this city will also be deleted.');
            $('#deleteCityModal').modal('show');
        });
    });
}

function validateDeleteModalButton() {
    const deleteButton = document.getElementById("deleteCityButton");

    const cityName = sessionStorage.cityName;
    const cityField = document.getElementById("deleteCityName").value;

    const adminIdField = document.getElementById("adminAccountId").value;
    const adminIdFromServer = sessionStorage.authId;

    if (cityName === cityField && adminIdField === adminIdFromServer) {
        deleteButton.disabled = false;
        $('#deleteCityModalForm').attr('action', BASE_URL + 'delete/' + adminIdField + '/' + cityName);
        return;
    }
    deleteButton.disabled = true;
}

openEditCityModal();
openDeleteCityModal();