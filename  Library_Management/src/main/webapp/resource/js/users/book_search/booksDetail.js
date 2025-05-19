

  
  	function insertReview(){
  	  if (loggedInUserNo == null) {
          alert("로그인 후 리뷰를 등록할 수 있습니다.");
          return;
        }
  	  
  	  const rating = $('#rating').val();
      const reviewContent = $('#reviewContent').val();
      const bookNo = $('#bookNo').val();
      
      
      if (!rating || !reviewContent) {
		alert("별점과 리뷰를 모두 작성해주세요")
		return;
      }
      
      $.ajax({
        url: "detail/reviewInsert",
        type:'post',
        data:{
          rating: rating,
          revContent: reviewContent,
          bookNo: bookNo
          //userNo: userNo
        },
        success: function(res){
          alert("리뷰등록");
          $('#rating').val('');
          $('#reviewContent').val('');
          loadReviewList();
          resetReviewForm();
        },
        error: function(xhr) {
        }
      })
  	}
  	


  	function resetReviewForm() {
  	  // 별점 값 초기화
  	  document.getElementById('rating').value = '0';

  	  // 별 색 초기화
  	  const stars = document.querySelectorAll('.star');
  	  stars.forEach(star => star.classList.remove('filled'));

  	  // 텍스트 초기화
  	  document.getElementById('reviewContent').value = '';
  	}

  	
  
    function loadReviewList(){
      const bookNo = $('#bookNo').val();
      
      $.ajax({
        url: "detail/reviewList?bookNo=" + bookNo,
        type: 'get',
        dataType: 'json',
        success : function(list){
        	 let html = '';
             if(list.length == 0){
                 html = "<p>등록된 리뷰가 없습니다.</p>";
             } else {
                 list.forEach(function(item) {
                	  html += `
                		    <div style="margin-bottom:15px;">
                		      <div style="display: flex; align-items: center; gap: 10px;">
                		        <strong>별점: ${item.rating}점</strong>
                		  `;
                		  
                		  if (loggedInUserNo !== null && loggedInUserNo === item.userNo) {
                		    html += `
               		    	<button class="btn btn-edit editReview" data-reviewno="${item.revNo}">수정</button>
              		    	<button class="btn btn-delete deleteReview" data-reviewno="${item.revNo}">삭제</button>
                		    `;
                		  }
                		  
                		  html += `
                		      </div> <!-- flex 끝 -->
                		      <p>내용: ${item.revContent}</p>
                		      <small>작성일 : ${item.revDate}</small><br>
                		      <small>작성자 : ${item.name}</small>
                		      <hr>
                		    </div>
                		  `;
                 });
             }
             $('#reviewList').html(html);
        },
        error: function(xhr){
        }
      })
    }
    
    //모달창 띄우기
    function openModal(reviewNo,rating,revContent) {
    	 $('#editReviewNo').val(reviewNo);
    	  $('#editRating').val(rating);
    	  $('#editRevContent').val(revContent);
    	  $('#editReviewModal').show();
    }
    
    //모달창 닫기
    function closeModal() {
    	  $('#editReviewModal').hide();
   	}
    
    //리뷰 수정버튼 이벤트
    $(document).on('click','.editReview',function(){
   	 	const parentDiv = $(this).closest('div');
	   	const ratingText = parentDiv.find('strong').text(); // "별점: 5점" 이런식
	    const rating = ratingText.match(/\d+/)[0]; // 별점 숫자만 추출
	    const revContent = parentDiv.find('p').text().replace('내용: ', '');
	    const reviewNo = $(this).data('reviewno');
	    
	    console.log("수정 눌렀을때 no : "+reviewNo)
	    openModal(reviewNo, rating, revContent);
    	
    })
    
    //리뷰 수정
    function updateReview() {
    	  const reviewNo = $('#editReviewNo').val();
    	  const rating = $('#rating').val();
    	  const revContent = $('#editRevContent').val();
    	  
    	  if (!rating || !revContent) {
    		    alert("별점과 리뷰 내용을 모두 입력해주세요.");
    		    return;
   		  }
    	  
    	  $.ajax({
    		  url: "detail/reviewUpdate",
    		  type: "post",
    		  data: {
    			  revNo: reviewNo,
    			  rating: rating,
    			  revContent: revContent
    		  },
    		  success: function(res){
    			  alert("리뷰가 수정되었습니다.");
    			  closeModal();
    			  loadReviewList();
    		  },
    		  error: function(xhr){
    			  alert("수정 실패")
    		  }
    	  });
    }
    
    //리뷰삭제 
    $(document).on('click','.deleteReview',function(){
    	
    	const revNo = $(this).data('reviewno');
    	$.ajax({
    		url : "detail/reviewDelete",
    		type : "get",
    		data : {
    			revNo: revNo
    		},
    		success: function(res){
    			alert("리뷰 삭제");
  			  	loadReviewList();
    		}
    	})
    })
    
    function BookFavorite(){

    	const bookNo = $('#BookFavorite').val();
    	
    	if (userNo == null) {
            alert("로그인 후 이용할 수 있습니다.");
            return;
        }
        const btn = $('#BookFavorite');
        const icon = btn.find('i');

    	
     	$.ajax({
    		url: "detail/favoriteInsert",
    		type: "get",
    		data : {
    			userNo : userNo,
    			bookNo : bookNo
    		},
    		success : function(res){
    			if (res === "insert") {
                    alert("관심 도서 등록 완료!");
                    $('#BookFavorite')
			        btn.addClass("favorited");
			        icon.removeClass("fa-regular").addClass("fa-solid");
                    //.data("favorited", true);
                } else if (res === "delete") {
                    alert("관심 도서가 삭제되었습니다.");
                    btn.removeClass("favorited");
                    icon.removeClass("fa-solid").addClass("fa-regular");
                    //.data("favorited", false);
                } else {
                    alert("처리 실패");
                }
    		},
    		error: function(xhr){
    			alert(xhr.status);
    		}
    	}) 
    }
    
   function requestLoan() {

   	  const bookNo = $('#BookLoan').val();
   	  
   	  if (userNo == null) {
   		    alert("로그인 후 대출 신청이 가능합니다.");
   		    return;
  		  }
   	  
   	  $.ajax({
   		    url: "detail/loanInsert", 
   		    type: "post",
   		    data: {
   		      userNo: userNo,
   		      bookNo: bookNo
   		    },
   		    success: function(res) {
   		      if (res === "success") {
   		        alert("대출 신청이 완료되었습니다.");
   				$('#BookLoan')
			    .text("대출중");
   		      } else if (res === "notAvailable") {
   		        alert("대여 가능한 도서가 없습니다.");
   		      } else if (res === "alreadyLoaned") {
   		    	alert("이미 대출중인 도서입니다.");
   		    	$('#BookLoan')
			    .text("대출중");
   		      } else if (res === "suspended") {
   		    	alert("정지 상태에선 대출할 수 없습니다.");
   		      } else {
   		        alert("대출 신청 실패");
   		      }
   		    },
   		    error: function(xhr) {
   		      alert("오류 발생: " + xhr.status);
   		    }
   		  });
  		}
   
   function loadFavorite() {
	   

   	const bookNo = $('#BookFavorite').val();
	   
   	
   	const btn = $('#BookFavorite'); // 버튼 요소
   	const icon = btn.find('i');     // 버튼 안의 <i> 아이콘 요소

   	//관심도서 체크후 있으면 버튼 변경
   	$.ajax({
   		url : "detail/favoriteInsert",
   		type: 'post',
   		data: {
   			userNo : userNo,
   			bookNo : bookNo
   		},
   		success : function(res){
   			if (res === "exists") {
                   $('#BookFavorite')
			        btn.addClass("favorited");
			        icon.removeClass("fa-regular").addClass("fa-solid");
               } else {
                   $('#BookFavorite')
                 	 btn.removeClass("favorited");
                  	 icon.removeClass("fa-solid").addClass("fa-regular");
               }
   		},
   		error : function(xhr){
   			alert(xhr.status);
   		}
   	})
   }
   
   function requestReservation() {
		
		const bookNo = $('#bookNo').val();
		
		$.ajax({
	   		url : "detail/reserInsert",
	   		type: 'get',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success : function(res){
	   			if (res === "reserveInsert") {
	   				alert("예약성공");
	   				$('#BookLoan')
				    .text("예약중"); 
	                  
               } else if (res === "alreadyreserve"){
            	   alert("이미 예약중입니다.");
					
            	   
               } else {
            	   alert("예약실패");
               }
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
	}
    
   function loadReservation() {
	   const bookNo = $('#bookNo').val();
	   
	   	$.ajax({
	   		url : "detail/reserCheck",
	   		type: 'get',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success : function(res){
					if (res ==="reservation"){
						$('#BookLoan')
					    .text("예약신청")
					    .css("background-color", "#4CAF50")
					    .removeAttr('onclick')
					    .on('click', function(){
					    	requestReservation();
					    }); // 함수 이름만 전달
					}else if(res ==="alreadyreserve"){
						$('#BookLoan')
					    .text("예약중")
					    .css("background-color", "#4CAF50")
					    .removeAttr('onclick')
					    .on('click', function(){
					    	requestReservation();
					    }); // 함수 이름만 전달
					}
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
   }
   
   let isReserveVisible = false; // 예약리스트 보이게
   
   function reserveList(){
	   const bookNo = $('#bookNo').val();
	   
	   	if (userNo == null) {
	        alert("로그인 후 이용할 수 있습니다.");
	        return;
	    }
		if (isReserveVisible) {
			$("#reserveTableContainer").hide();
			isReserveVisible = false;
			return;
		}
	   
	   	$.ajax({
	   		url : "detail/reserCheck",
	   		type: 'post',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success: function(res) {
	   		    let html = '<table class="reserve-table" border="1">';
	   		    html += '<tr>';
	   		    html += '<th>예약 번호</th>';
	   		    html += '<th>예약 일자</th>';
	   		    html += '<th>상태</th>';
	   		    html += '<th>사용자 이름</th>';
	   		    html += '</tr>';

	   		    if (res.length === 0) {
	   		        html += '<tr><td colspan="5">예약 정보가 없습니다.</td></tr>';
	   		    } else {
	   		        res.forEach(function(item) {
	   		            html += '<tr>';
	   		            html += `<td>${item.reserveNo}</td>`;
	   		            html += `<td>${item.reserveDate}</td>`;
	   		            html += `<td>${item.reserveStatus}</td>`;
	   		            html += `<td>${item.name}</td>`;
	   		            html += '</tr>';
	   		        });

	   		    }

	   		    html += '</table>';

				$('#reserveTableContainer').html(html).show();
				isReserveVisible = true;
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
	   
   }

    
    $(document).ready(function() {
    loadReviewList(); // 리뷰 불러오기
    
    if(userNo != null){
    	loadReservation();// 대출인지 예약인지 체크하기
    	loadFavorite(); // 관심도서 체크하기
    }
    
    //별 클릭주고 색채우기
	  	const stars = document.querySelectorAll('.star');
	
	  	stars.forEach(star => {
	  	  star.addEventListener('click', function() {
	  	    // 선택된 별의 값 가져오기
	  	    const rating = this.getAttribute('data-value');
	  	    
	  	  	document.getElementById('rating').value = rating;
	  	    
	  	    // 선택된 별까지만 색을 채우기
	  	    stars.forEach(star => {
	  	      if (star.getAttribute('data-value') <= rating) {
	  	        star.classList.add('filled');
	  	      } else {
	  	        star.classList.remove('filled');
	  	      }
	  	    });
	  	  });
	  	});
    
    });
