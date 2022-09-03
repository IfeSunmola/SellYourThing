function enableAccountDeleteButton(email) {
    const deleteAccountButton = document.getElementById('deleteAccountButton')
    const confirmEmail = document.getElementById('confirmEmail');
    deleteAccountButton.disabled = confirmEmail.value !== email;
}

function clearModalOnFormClose(){
    $('#deleteAccountModal').on('hidden.bs.modal', function (e) { // clear the 'delete' form after it closes
        $('#deleteAccountForm').find("#confirmEmail").val("");
    });
}

clearModalOnFormClose();