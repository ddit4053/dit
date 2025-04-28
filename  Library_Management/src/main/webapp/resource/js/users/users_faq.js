document.addEventListener("DOMContentLoaded", function() {
    // FAQ 항목 토글 기능
    const faqQuestions = document.querySelectorAll('.faq-question');
    
    faqQuestions.forEach(question => {
        question.addEventListener('click', function() {
            // 해당 FAQ 항목의 활성화 상태 토글
            const faqItem = this.parentElement;
            faqItem.classList.toggle('active');
            
            // 아이콘 변경 (+ 또는 ×)
            const toggleIcon = this.querySelector('.toggle-icon');
            if (faqItem.classList.contains('active')) {
                toggleIcon.textContent = '+';
            } else {
                toggleIcon.textContent = '+';
            }
        });
    });
    
    // 카테고리 필터링 기능
    const categoryItems = document.querySelectorAll('.category-item');
    const faqItems = document.querySelectorAll('.faq-item');
    
    categoryItems.forEach(item => {
        item.addEventListener('click', function() {
            // 활성화된 카테고리 변경
            categoryItems.forEach(cat => cat.classList.remove('active'));
            this.classList.add('active');
            
            // 선택된 카테고리에 맞는 FAQ만 표시
            const selectedCategory = this.getAttribute('data-category');
            
            faqItems.forEach(faq => {
                if (selectedCategory === 'all' || faq.getAttribute('data-category') === selectedCategory) {
                    faq.style.display = 'block';
                } else {
                    faq.style.display = 'none';
                    // 숨길 때 열려있는 FAQ 닫기
                    faq.classList.remove('active');
                    const toggleIcon = faq.querySelector('.toggle-icon');
                    toggleIcon.textContent = '+';
                }
            });
        });
    });
    
    // 검색 기능
    const faqSearch = document.getElementById('faqSearch');
    const faqSearchBtn = document.getElementById('faqSearchBtn');
    
    function searchFaq() {
        const searchText = faqSearch.value.toLowerCase();
        
        if (searchText.trim() === '') {
            // 검색어가 없으면 현재 선택된 카테고리에 맞는 모든 FAQ 표시
            const activeCategory = document.querySelector('.category-item.active');
            const selectedCategory = activeCategory.getAttribute('data-category');
            
            faqItems.forEach(faq => {
                if (selectedCategory === 'all' || faq.getAttribute('data-category') === selectedCategory) {
                    faq.style.display = 'block';
                } else {
                    faq.style.display = 'none';
                }
            });
            return;
        }
        
        // 검색어에 맞는 FAQ만 표시
        faqItems.forEach(faq => {
            const questionText = faq.querySelector('.question-text').textContent.toLowerCase();
            const answerText = faq.querySelector('.answer-content').textContent.toLowerCase();
            
            if (questionText.includes(searchText) || answerText.includes(searchText)) {
                faq.style.display = 'block';
            } else {
                faq.style.display = 'none';
                // 숨길 때 열려있는 FAQ 닫기
                faq.classList.remove('active');
                const toggleIcon = faq.querySelector('.toggle-icon');
                toggleIcon.textContent = '+';
            }
        });
        
        // 검색 시 '전체' 카테고리 활성화
        categoryItems.forEach(cat => cat.classList.remove('active'));
        document.querySelector('[data-category="all"]').classList.add('active');
    }
    
    // 검색 버튼 클릭 시 검색 실행
    faqSearchBtn.addEventListener('click', searchFaq);
    
    // 엔터 키 입력 시 검색 실행
    faqSearch.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchFaq();
        }
    });
});