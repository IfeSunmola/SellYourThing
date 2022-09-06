$('#imageUpload').on('change', function () {
    const size = (this.files[0].size / 1000 / 1000).toFixed(2);
    const imageErrorP = $("#imageErrorP");
    if (size > 1) {
        $('#imageUpload').val('');
        imageErrorP.html('Image must be less than 1MB');
        imageErrorP.show();
    }
    else {
        imageErrorP.hide();
    }
});