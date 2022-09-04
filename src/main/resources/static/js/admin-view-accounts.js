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
                    $('#disableForm').attr('action', '/admin/accounts/disable/' + account.accountId);
                    $('#disableEnableFormButton').html('Disable Account');
                    $('#disableEnableFormHeader').html('Disable this account?');
                }
                else {
                    $('#disableForm').attr('action', '/admin/accounts/enable/' + account.accountId);
                    $('#disableEnableFormButton').html('Enable Account');
                    $('#disableEnableFormHeader').html('Enable this account?');
                }
                prefill(account, 'disableEnable');
            });
            $('#disableAccountModal').modal('show');
        });
    });
}

function openDeleteModalAndPrefill() {
    $('document').ready(function () {
        $('.table #deleteAccountBtnId').on('click', function (event) {
            event.preventDefault();
            const href = $(this).attr('href');
            $.get(href, function (account) {
                $('#deleteForm').attr('action', '/admin/accounts/delete/' + account.accountId);
                prefill(account, 'delete');
            });
            $('#deleteAccountModal').modal('show');
        });
    });
}

function enableAccountDeleteButton(adminIdFromServer, adminEmailFromServer) {
    const deleteAccountButton = document.getElementById('deleteFormButton')
    const idField = document.getElementById('adminAccountId').value;
    const emailField = document.getElementById('adminEmail').value;

    if (idField === adminIdFromServer && emailField === adminEmailFromServer) {
        deleteAccountButton.disabled = false;
        return;
    }
    deleteAccountButton.disabled = true;
}

function clearModalOnFormClose() {
    $('#deleteAccountModal').on('hidden.bs.modal', function () { // clear the 'delete' form after it closes
        $('#deleteAccountForm').find("#confirmEmail").val("");
    });
}

openDisableModalAndPrefill();
openDeleteModalAndPrefill();
clearModalOnFormClose();
