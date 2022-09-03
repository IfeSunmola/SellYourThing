// General Scripts file. The functions here are used in multiple htmls

/*
* This method disables sort select options on click, so it won't be sent to the url
* Fields that are disabled aren't shown in URL when a GET request is made
* */
function disableEmptyFields() {
    // disabling the fields so only the non-empty Request Params will be shown in url
    const citySelect = document.getElementById("citySelect");
    citySelect.disabled = citySelect.value === "0";

    const categorySelect = document.getElementById("categorySelect");
    categorySelect.disabled = categorySelect.value === "0";

    const order = document.getElementById("order");
    order.disabled = order.value === "new";

    const searchText = document.getElementById("searchText");
    searchText.disabled = searchText.value === "";
}

/*
* Any time a modal is closed, clear whatever was in it
* */
function clearModalOnFormClose() {
    $('.modal').on('hidden.bs.modal', function (e) {
        $('.modal-body').find("input").val("");
    })
}

clearModalOnFormClose();