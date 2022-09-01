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

function enableDeleteButton(email) {
    const deleteAccountButton = document.getElementById("deleteAccountButton")
    const confirmEmail = document.getElementById("confirmEmail");
    deleteAccountButton.disabled = confirmEmail.value !== email;
}

function openModalAndPreFill() {
    $('document').ready(function () {
        $('.table .temp').on('click', function (event) {
            event.preventDefault();

            const href = $(this).attr('href');
            $.get(href, function (account) {
                //select accountId, change the value to the data gotten from the get request
                //name input attribute is used to do the mapping. Name attribute must be the same as the name in the result set
                $('#accountId').val(account.accountId);
                $('#role').val(account.role);
                $('#firstName').val(account.firstName);
                $('#lastName').val(account.lastName);
                $('#email').val(account.email);
                $('#dateOfBirth').val(account.dateOfBirth);
                $('#active').val(account.active);

            });
            $('#disableAccountModal').modal('show')
        })
    })
}

openModalAndPreFill()