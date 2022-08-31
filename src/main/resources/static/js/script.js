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