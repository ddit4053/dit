

	$(function() {
		  $('.book-card').on('click', function() {
		    const bookno = $(this).data('bookno');
		    console.log(bookno);
		    location.href = `${contextPath}/books/detail?bookNo=${bookno}`;
		  });
	});
