function disableEmptyFields() {
    // disabling the fields so only the non empty Request Params will be shown in url
    const citySelect = document.getElementById("citySelect");
    citySelect.disabled = citySelect.value === "0";

    const categorySelect = document.getElementById("categorySelect");
    categorySelect.disabled = categorySelect.value === "0";

    const order = document.getElementById("order");
    order.disabled = order.value === "new";

    const searchText = document.getElementById("searchText");
    searchText.disabled = searchText.value === "";
}

function enableAccountDeleteButton(email) {
    const deleteAccountButton = document.getElementById('deleteAccountButton')
    const confirmEmail = document.getElementById('confirmEmail');
    deleteAccountButton.disabled = confirmEmail.value !== email;
}

function prefill(account, flag) {
    if (flag === 'disableEnable') {
        $('#disableAccountId').val(account.accountId);
        $('#disableRole').val(account.role);
        $('#disableFirstName').val(account.firstName);
        $('#disableLastName').val(account.lastName);
        $('#disableEmail').val(account.email);
        $('#disableDateOfBirth').val(account.dateOfBirth);
        $('#disableActive').val(account.active);
    }
    else {
        $('#deleteAccountId').val(account.accountId);
        $('#deleteRole').val(account.role);
        $('#deleteFirstName').val(account.firstName);
        $('#deleteLastName').val(account.lastName);
        $('#deleteEmail').val(account.email);
        $('#deleteDateOfBirth').val(account.dateOfBirth);
        $('#deleteActive').val(account.active);
    }
}

function openDisableModalAndPrefill() {
    $('document').ready(function () {
        $('.table .disableEnableAccountBtn').on('click', function (event) {
            event.preventDefault();
            const href = $(this).attr('href');
            $.get(href, function (account) {
                if (account.active === 'YES') {// grab id 'disableForm', replace the attribute 'action' with the 2nd param
                    $('#disableForm').attr('action', '/admin/' + account.accountId + '/disable');
                    $('#disableEnableFormButton').html('Disable Account')
                    $('#disableEnableFormHeader').html('Disable this account?')
                }
                else {
                    $('#disableForm').attr('action', '/admin/' + account.accountId + '/enable');
                    $('#disableEnableFormButton').html('Enable Account')
                    $('#disableEnableFormHeader').html('Enable this account?')
                }
                prefill(account, 'disableEnable');
            });
            $('#disableAccountModal').modal('show')
        })
    })
}

function openDeleteModalAndPrefill() {
    $('document').ready(function () {
        $('.table #deleteAccountBtnId').on('click', function (event) {
            event.preventDefault();
            const href = $(this).attr('href');
            $.get(href, function (account) {
                $('#deleteForm').attr('action', '/admin/' + account.accountId + '/delete');
                prefill(account, 'delete');
            });
            $('#deleteAccountModal').modal('show')
        })
    })
}

function enableAdminAccountDeleteButton(adminIdFromServer, adminEmailFromServer) {
    const deleteAccountButton = document.getElementById('deleteFormButton')
    const idField = document.getElementById('adminAccountId').value;
    const emailField = document.getElementById('adminEmail').value;

    if (idField === adminIdFromServer && emailField === adminEmailFromServer) {
        deleteAccountButton.disabled = false;
        return;
    }
    deleteAccountButton.disabled = true;
}

openDisableModalAndPrefill()
openDeleteModalAndPrefill();