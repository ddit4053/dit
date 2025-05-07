<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 리스트</title>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<style>

	.main-content-area {
	    width: 100%;
	    //padding: 10px;
	    //box-sizing: border-box;
	}
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    header h1 {
        text-align: center;
        color: #4e342e;
        margin-bottom: 30px;
    }
    
    .table-container {
        width: 100%;
        border-radius: 10px;
        overflow-x: auto; /* 가로 스크롤 방지 또는 자동 처리 */
    }

    table {
 		width: 100%;
        table-layout: fixed;
        border-collapse: collapse;
        background-color: #ffffff;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        border-radius: 8px;
        font-size: 14px; /* 글자 살짝 줄이기 */
    }

    thead {
        background-color: #8d6e63;
        color: white;
    }

    thead th {
	    padding: 10px;
	    text-align: center;
	    white-space: normal; /* ✅ 줄바꿈 허용 */
	    overflow-wrap: break-word; /* ✅ 단어 단위로 줄바꿈 */
	    word-break: break-word;    /* ✅ 길거나 끊기 어려운 단어도 줄바꿈 */
	    border-bottom: 1px solid #ddd;
    }

    tbody td {
	    padding: 10px;
	    text-align: center;
	    white-space: normal; /* ✅ 줄바꿈 허용 */
	    overflow-wrap: break-word; /* ✅ 단어 단위로 줄바꿈 */
	    word-break: break-word;    /* ✅ 길거나 끊기 어려운 단어도 줄바꿈 */
	    border-bottom: 1px solid #ddd;
    }

    tbody tr:hover {
        background-color: #f1f1f1;
    }

    img {
        border-radius: 4px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.2);
    }
	  td button {
	    padding: 6px 12px;
	    font-size: 13px;
	    white-space: nowrap;
	    background-color: #8d6e63;   
	    color: white;                   /* 흰색 텍스트 */
	    border: none;
	    border-radius: 4px;
	    cursor: pointer;
	    transition: background-color 0.2s ease;
	    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	
	td button:hover {
	    background-color: #c62828;      /* hover 시 더 진한 빨강 */
	}
	    /* 제목과 저자에 너비 지정 */
	th:nth-child(1), td:nth-child(1) { width: 6%; }   /* 도서번호 */
	th:nth-child(2), td:nth-child(2) { width: 14%; }  /* 제목 */
	th:nth-child(3), td:nth-child(3) { width: 10%; }  /* ISBN */
	th:nth-child(4), td:nth-child(4) { width: 9%; }   /* 출판일 */
	th:nth-child(5), td:nth-child(5) { width: 8%; }   /* 표지 */
	th:nth-child(6), td:nth-child(6) { width: 7%; }   /* 상태 */
	th:nth-child(7), td:nth-child(7) { width: 10%; }  /* 저자 */
	th:nth-child(8), td:nth-child(8) { width: 10%; }  /* 출판사 */
	th:nth-child(9), td:nth-child(9) { width: 8%; }   /* 카테고리 */
	th:nth-child(10), td:nth-child(10) { width: 10%; } /* 등록일 */
	th:nth-child(11), td:nth-child(11) { width: 8%; }  /* 버튼 */
	
	/* 구체적인 선택자로 영역을 제한 */
	.table-container .pagination {
	    text-align: center;
	    margin-top: 30px;
	}
	
	.table-container .page-btn {
	    display: inline-block;
	    margin: 0 5px;
	    padding: 8px 14px;
	    background-color: #eee;
	    border-radius: 6px;
	    text-decoration: none;
	    color: #333;
	    transition: background-color 0.3s;
	}
	
	.table-container .page-btn:hover {
	    background-color: #ccc;
	}
	
	.table-container .page-btn.active {
	    background-color: #8d6e63;
	    color: white;
	    font-weight: bold;
	}
</style>
<script type="text/javascript">
		$(document).ready(function() {
		    // 페이지 로드 후 기본적으로 '보유 도서' 리스트 로드
		    loadBooks("available");
		
		    // select box 변경 시 데이터 로드
		    $("#listSelector").change(function() {
		        const optionValue = $(this).val();
		        loadBooks(optionValue); // 선택된 값에 맞는 도서 리스트 로드
		    });
		});

		// select box 변경 시 데이터 로드
		$("#listSelector").change(function () {
		    const optionValue = $(this).val();
		    loadBooks(optionValue, 1); // 페이지 1로 초기화
		});

		// 페이지 이동 시 호출될 함수
		function goToPage(pageNumber) {
		    const type = $("#listSelector").val();
		    loadBooks(type, pageNumber);
		}
		
		
		// 도서 로드 함수 (페이지 추가)
		function loadBooks(type, page = 1) {
		    $.ajax({
		        url: "${pageContext.request.contextPath}/admin/books/listall",
		        type: "GET",
		        data: { type: type, page: page }, // ✅ 페이지 전달
		        success: function(response) {
		            const books = response.books;
		            const totalPages = response.totalPages;
					const isDeletedList = (type === "deleted"); // 삭제 목록 여부 확인

		            $("tbody").empty();
		            books.forEach(function(book) {
		                const title = book.title.split('-')[0];
		                const row = `
		                    <tr class="bookList" data-bookno="\${book.bookNo}">
			                    <td>\${book.bookNo}</td>
			                    <td>\${title}</td>
			                    <td>\${book.isbn}</td>
			                    <td>\${book.pubDate}</td>
			                    <td><img src="\${book.cover}" alt="cover" width="60px"/></td>
			                    <td>\${book.bookStatus}</td>
			                    <td>\${book.author}</td>
			                    <td>\${book.publisher}</td>
			                    <td>\${book.categoryId}</td>
			                    <td>\${book.insertDate}</td>
			                    <td>
			                        \${isDeletedList ? '' : `<button onclick="bookDelete(event, \${book.bookNo})">삭제</button>`}
			                    </td>
	                		</tr>
		                `;
		                $("tbody").append(row);
		            });

		            // ✅ 페이징 처리
		           let paginationHtml = "";
					const blockSize = 5; // 블록 당 페이지 수
					const currentBlock = Math.floor((page - 1) / blockSize);
					const startPage = currentBlock * blockSize + 1;
					let endPage = startPage + blockSize - 1;
					if (endPage > totalPages) endPage = totalPages;
					
					// 이전 블록
					if (startPage > 1) {
					    paginationHtml += `<button class="page-btn" onclick="goToPage(\${startPage - 1})">&laquo;</button>`;
					}
					
					// 페이지 버튼
					for (let i = startPage; i <= endPage; i++) {
					    if (i === page) {
					        paginationHtml += `<button class="page-btn active" onclick="goToPage(\${i})">\${i}</button>`;
					    } else {
					        paginationHtml += `<button class="page-btn" onclick="goToPage(\${i})">\${i}</button>`;
					    }
					}
					
					// 다음 블록
					if (endPage < totalPages) {
					    paginationHtml += `<button class="page-btn" onclick="goToPage(\${endPage + 1})">&raquo;</button>`;
					}
					$(".pagination").html(paginationHtml);

		            $("tr.bookList").click(function () {
		                const bookNo = $(this).data("bookno");
		                window.location.href = "${pageContext.request.contextPath}/admin/books/detailList?bookNo=" + bookNo;
		            });
		        },
		        error: function () {
		            alert("데이터를 불러오는 중 오류가 발생했습니다.");
		        }
		    });
		}


	
	    // 삭제 기능
	function bookDelete(event, bookNo) {
        event.stopPropagation(); // 상위 tr 클릭 방지
        
        if (!confirm("정말 이 도서를 삭제하시겠습니까?")) {
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/admin/books/delete?bookNo=" + bookNo,
            type: "GET",
            success: function(res) {
                if (res === "true") {
                    alert("삭제가 완료되었습니다.");
                    loadBooks($("#listSelector").val()); // 현재 선택된 리스트를 다시 로드
                } else {
                    alert("삭제 불가: 대여중인 책이 존재합니다.");
                }
            },
            error: function(xhr, status, error) {
                alert("삭제 중 오류가 발생했습니다: " + error);
            }
        });
    }
</script>
</head>
<body>
   <header>
	    <div style="display: flex; justify-content: space-between; align-items: center;">
	        <h1>도서 리스트</h1>
	        <div class="select-wrapper">
	            <select id="listSelector" style="padding: 6px; font-size: 14px;">
	                <option value="available">보유 도서</option>
	                <option value="deleted">삭제 도서</option>
	            </select>
	        </div>
	    </div>
	</header>

	<div class="table-container">
	    <table>
	        <thead>
	            <tr>
	                <th>도서번호</th>
	                <th>제목</th>
	                <th>ISBN</th>
	                <th>출판일</th>
	                <th>표지</th>
	                <th>상태</th>
	                <th>저자</th>
	                <th>출판사</th>
	                <th>카테고리</th>
	                <th>등록일</th>
	                <th></th>
	            </tr>
	        </thead>
	        <tbody>
	            <!-- Ajax로 로드된 도서 리스트가 여기에 동적으로 추가됩니다. -->
	        </tbody>
	    </table>
	      <div class="pagination"></div> <!-- ✅ 추가 -->
	</div>
</body>
</html>
