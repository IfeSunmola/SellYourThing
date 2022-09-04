function enableAccountDeleteButton(email) {
    const deleteAccountButton = document.getElementById('deleteAccountButton')
    const confirmEmail = document.getElementById('confirmEmail');
    deleteAccountButton.disabled = confirmEmail.value !== email;
}
