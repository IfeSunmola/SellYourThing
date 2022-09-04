function openDeletePostModal() {
    $('document').ready(function () {
        $('.table .deletePostButton').on('click', function (event) {
            event.preventDefault();
            sessionStorage.setItem("postId", $(this).closest("tr").find(".postIdTable").text());
            $('#deletePostForm').attr('action', '/admin/posts/delete/' + sessionStorage.postId);
            $('#deletePostModal').modal('show');
        });
    });
}

function enablePostDeleteButton(adminIdFromServer, adminEmailFromServer) {
    const postId = sessionStorage.postId
    const postInfoHref = '/admin/posts/find/' + postId;

    const deletePostFormButton = document.getElementById('deletePostFormButton')

    const adminIdField = document.getElementById('adminAccountId').value;
    const adminEmailField = document.getElementById('adminEmail').value;

    const postIdField = document.getElementById('postId').value;
    const postPriceField = document.getElementById('postPrice').value;

    $.get(postInfoHref, function (postDeleteVerify) {
        const postPrice = postDeleteVerify.postPrice;
        if (adminIdField === adminIdFromServer && adminEmailField === adminEmailFromServer
            && postIdField === postId && postPriceField === postPrice) {
            deletePostFormButton.disabled = false;
            return;
        }
        deletePostFormButton.disabled = true;
    });
}

openDeletePostModal();