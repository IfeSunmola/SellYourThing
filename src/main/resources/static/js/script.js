function disableEmptyFields() {
    const citySelect = document.getElementById("citySelect");
    citySelect.disabled = citySelect.value === "0";

    const categorySelect = document.getElementById("categorySelect");
    categorySelect.disabled = categorySelect.value === "0";

    const searchText = document.getElementById("searchText");
    searchText.disabled = searchText.value === "";
}