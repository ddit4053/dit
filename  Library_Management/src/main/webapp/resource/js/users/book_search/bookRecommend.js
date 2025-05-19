
    $(function() {
        $('.book-card').on('click', function() {
            const bookno = $(this).data('bookno');
            console.log(bookno);
            location.href = `${pageContext.request.contextPath}/books/detail?bookNo=${bookno}`;
        });
    });