
document.addEventListener('DOMContentLoaded', function() {
    const mainCategory = document.querySelector('select[name="mainCategory"]');
    const midCategory = document.querySelector('select[name="midCategory"]');
    const subCategory = document.querySelector('select[name="subCategory"]');

    function fetchCategories(parentId, targetSelect) {
        fetch(`${pageContext.request.contextPath}/books/categoryList?parentId=${parentId}`)
            .then(response => response.json())
            .then(data => {
                targetSelect.innerHTML = '<option value="">선택하세요</option>';
                data.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.categoryNo;
                    option.textContent = category.categoryName;
                    targetSelect.appendChild(option);
                });
            });
    }

    mainCategory.addEventListener('change', function() {
        const parentId = this.value;

        if (parentId) {
            fetchCategories(parentId, midCategory);
        } else {
            midCategory.innerHTML = '<option value="">선택하세요</option>';
            subCategory.innerHTML = '<option value="">선택하세요</option>';
        }
    });

    midCategory.addEventListener('change', function() {
        const parentId = this.value;

        if (parentId) {
            fetchCategories(parentId, subCategory);
        } else {
            subCategory.innerHTML = '<option value="">선택하세요</option>';
        }
    });

    // 초기 대분류 카테고리 로딩
    fetchCategories('', mainCategory);
    
	const form = document.querySelector('.search-form');
	const hiddenCategoryInput = document.getElementById('selectedCategoryId');
	
	form.addEventListener('submit', function(e) {
	    const mainVal = mainCategory.value;
	    const midVal = midCategory.value;
	    const subVal = subCategory.value;
	
	    let selected = '';
	    if (subVal) {
	        selected = subVal;
	    } else if (midVal) {
	        selected = midVal;
	    } else if (mainVal) {
	        selected = mainVal;
	    }
	
	    $('#selectedCategoryId').val(selected);
	});
});
