const BASE_URL = '/admin/categories/'
function openEditCategoryModal() {
    $('document').ready(function () {
        $('.table .editButton').on('click', function (event) {
            event.preventDefault();
            sessionStorage.setItem("categoryName", $(this).closest("tr").find("#categoryName").text());
            $('#editCategoryModalHeader').html('Editing Category <span><strong>' + sessionStorage.categoryName + '</strong></span>');
            $('#editCategoryModal').modal('show');
        });
    });
}

function validateEditModalButton() {
    const saveEditsButton = document.getElementById("saveEditsButton");
    const categoryName = sessionStorage.categoryName;
    const oldCategoryField = document.getElementById("oldCategoryName").value;
    const newCategoryName = document.getElementById("newCategoryName").value;

    if (categoryName === oldCategoryField) {
        saveEditsButton.disabled = false;
        $('#editCategoryModalForm').attr('action', BASE_URL + 'edit/' + sessionStorage.categoryName + '/' + newCategoryName);
        return;
    }
    saveEditsButton.disabled = true;
}

function openDeleteCategoryModal() {
    $('document').ready(function () {
        $('.table .deleteButton').on('click', function (event) {
            event.preventDefault();
            sessionStorage.setItem("categoryName", $(this).closest("tr").find("#categoryName").text());
            $('#deleteCategoryModalHeader').html('Delete category: <span><strong>' + sessionStorage.categoryName + '</strong></span>? All the posts' +
                ' assoociated with this category will also be deleted.');
            $('#deleteCategoryModal').modal('show');
        });
    });
}

function validateDeleteModalButton() {
    const deleteButton = document.getElementById("deleteCategoryButton");

    const categoryName = sessionStorage.categoryName;
    const categoryField = document.getElementById("deleteCategoryName").value;

    const adminIdField = document.getElementById("adminAccountId").value;
    const adminIdFromServer = sessionStorage.authId;

    if (categoryName === categoryField && adminIdField === adminIdFromServer) {
        deleteButton.disabled = false;
        $('#deleteCategoryModalForm').attr('action', BASE_URL + 'delete/' + adminIdField + '/' + categoryName);
        return;
    }
    deleteButton.disabled = true;
}

openEditCategoryModal();
openDeleteCategoryModal();