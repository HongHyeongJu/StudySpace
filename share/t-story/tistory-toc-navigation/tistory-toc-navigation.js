/*
* 해당 페이지가 '글' 형식일 때만 네비게이터 표시하기
* */


// 현재 페이지의 URL
let currentUrl = window.location.href;

// 티스토리 글 페이지 URL 패턴: 숫자만 있는 패턴
let postPagePattern = /^https:\/\/labmate-dev\.tistory\.com\/\d+$/;

// 글일 경우에에
if (postPagePattern.test(currentUrl)) {
    //네비게이터 div만들기
    makeNaviDiv();

    //해당 div 안에 링크태그 만들기
    fillTagNavigationDiv();

    //네비게이터 내용물이 없을 경우 숨기기
    hideNaviDiv();

} else {

    //스킨 편집할 일 있을 때 아래 주석 풀고 css 등을 고치세요!
/*    //네비게이터 div만들기
    makeNaviDiv();
    //해당 div 안에 링크태그 만들기
    fillTagNavigationDiv();*/
}



/*
* 네이게이터 div 만들기
* */
function makeNaviDiv() {

    // <div class="content-wrap"> 태그 가져오기
    let contentWrap = document.querySelector('.content-wrap');


    if (contentWrap) {
        // <div id="tag-navigation" class="tag-navigation"> 태그 생성
        let tagNavigationDiv = document.createElement('div');
        tagNavigationDiv.id = 'tag-navigation';
        tagNavigationDiv.className = 'tag-navigation';

        // <div class="content-wrap"> 태그의 마지막 자식으로 추가
        contentWrap.appendChild(tagNavigationDiv);
    }
}



/*
* div 태그 채우기
* 조건 : 본문에서 제목1(h1), 제목2(h2), 제목3(h3), 제목1(h1), blockquote(그 중에서 data-ke-style="style2"인 것)
* */
function fillTagNavigationDiv() {
    // contents_style 클래스를 가진 div 요소 선택
    let contentsDiv = document.querySelector('.contents_style');


    // h1, h2, h3, blockquote 태그들을 선택
    let headingsAndBlockquotes = contentsDiv.querySelectorAll('h1, h2, h3, blockquote');

    // 찾은 태그들 위에서부터 반복하면서 링크 만들기. <a><li></li></a> 형태로 만들 예정
    headingsAndBlockquotes.forEach(function (element, index) {

        //링크들이 들어갈 div
        let naviDivElement = document.getElementById('tag-navigation');

        // 각 요소의 텍스트 내용 가져오기
        let textContent = element.textContent.trim();

        // 요소가 비어있으면 링크 안만듦
        if (textContent == "") {
            return;
        }

        // blockquote 태그인 경우 data-ke-style 값 확인
        if (element.tagName == 'BLOCKQUOTE') {
            let dataKeyStyle = element.getAttribute('data-ke-style');
            if (dataKeyStyle !== 'style2') {
                return; // data-ke-style이 style2가 아니면 건너뜀
            }
        }

        // 목차 항목을 생성하고 추가하기
        let listItem = document.createElement('li');  //<li></li>
        let link = document.createElement('a');   //<a></a>
        link.href = '#navipass-' + index;
        listItem.textContent = textContent;

        // 링크 아래 여백 주기 (마지막 요소 빼고)
        listItem.style.marginBottom = '10px';
        if (index == headingsAndBlockquotes.length - 1) {
            listItem.style.marginBottom = '0px';
        }

        link.appendChild(listItem);       //결과:      <a><li></li></a>
        naviDivElement.appendChild(link); //결과: <div><a><li></li></a></div>

        // 각 요소에 고유한 ID 부여
        element.id = 'navipass-' + index;
    });
}


// 요소 없는 div 숨기기
function hideNaviDiv() {
    // #tag-navigation 내부의 <a> 태그 개수를 확인
    let linkCount = $("#tag-navigation a").length;

    // 만약 <a> 태그가 하나도 없으면 #tag-navigation를 숨깁니다.
    if (linkCount === 0) {
        $("#tag-navigation").hide();
    }
}

