$(document).ready(function() {
	$("#buttonAdd2Wishlist").on("click", function(evt) {
		addToWishlist();
	});
});

function addToWishlist() {
	quantity = $("#quantity" + productId).val();
	url = contextPath + "wishlist/add/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		showModalDialog("Shopping wishlist", response);
	}).fail(function() {
		showErrorModal("Error while adding product to shopping wishlist.");
	});
}