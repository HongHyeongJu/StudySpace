//유데미 강의 481~

### POST요청 데이터 준비
* 새 댓글을 달 때마다 페이지 로딩 되지 않도록
```javascript
 const commentsFormElement = document.querySelector('#comments-form from');
//from엘리먼트는 해당 자바스크립트 코드가 있을 경우 제출될 때 이벤트도 같이 제출함


commentsFormElement.addEventListener('submit')
//브라우저가 자체 제출을 처리하기 전에 이 양식이 제출될 때마다 제출 이벤트가 발생함

```

(수정 부분)
function saveComment() {} 함수 작동 이전에
기본 브러우저 동작 억제하기 (기본브라우저 동작 = 자체적으로 요청 보내기)







<br>

### 발신 요청에 첨부되어야 하는 추가 메타데이터 정의
* {fetch()} 함수에 추가 구성
* {hearders: } 속성
  * 자바스크립트 객체로 설정해서 일부 추가 헤더, 발신 요청에 첨부되어야 하는 일부 추가 메타데이터를 정의할 수 있음
* comments.js 수정 : fetch의 headers 추가
````javascript
function saveComment(event) {
    event.preventDefault();
    const postId = commentsFormElement.dataset.postid;
    const enteredTitle = commentTitleElement.value();
    const enteredText = commentTextElement.value();

    const comment = {title: enteredTitle, text: enteredText};

    // console.log(enteredTitle); 확인
    fetch(`/posts/${postId}/comments`, {
        //두번째 매개변수는 다른 속성을 설정할 수 있는 객체
        method: 'POST',
        body: JSON.stringify(comment), //JSON 데이터를 받기 위해 app.js에  app.use(express.json());  추가함

        headers : {
            'Content-Type': 'application/json'  //이 요청이 일부 제이슨 데이터를 전달함

        }

    });

}
````


<br>

### 사용자 경험(UX) 개선
* 응답을 받으면 프로미스가 해결되도록 {saveComments}를 비동기 함수로 변환
* 비동기로 변환하는 이유
  * 실제로 여기에 관한 응답에 관심이 있지만, 응답을 받을 때까지 대기하는 데는 관심이 음
  * 그 다음 줄에서 실제로 다시 모든 댓글을 가져올 수 있기 때문에
````javascript
async function saveComment(event) {
    event.preventDefault();
    const postId = commentsFormElement.dataset.postid;
    const enteredTitle = commentTitleElement.value();
    const enteredText = commentTextElement.value();

    const comment = {title: enteredTitle, text: enteredText};

    // console.log(enteredTitle); 확인
    const response = await fetch(`/posts/${postId}/comments`, {
        //두번째 매개변수는 다른 속성을 설정할 수 있는 객체
        method: 'POST',
        body: JSON.stringify(comment), //JSON 데이터를 받기 위해 app.js에  app.use(express.json());  추가함

        headers : {
            'Content-Type': 'application/json'  //이 요청이 일부 제이슨 데이터를 전달함
        }
    });

}'
````
* fetchCommentsForPost 함수를 수동으로 트리거 하기
````javascript
async function saveComment(event) {
            ...
    // console.log(enteredTitle); 확인
    const response = await fetch(`/posts/${postId}/comments`, {
            ...
        }
    });

    fetchCommentsForPost();  //해당 모든 댓글에 관해 또 다른 {GET} 요청을 다시 보냄
            
}'
````


<br>

### 댓글이 없는 이 시나리오 처리
* {fetchCommentsForPost} 함수 수정
````
//해당 데이터, 댓글 데이터를 가져오기 위해 서버에  ajax 요청 보내기
async function fetchCommentsForPost(event){
    const postId = loadCommnetsBtnElement.dataset.postid;
    //fetch의 매개변수는 get요청을 보내려는 URL. 기본이 GET요청
    const response = await fetch(`/posts/${postId}/comments`); //이 URL로 HTTP요청이 감
    const responseData = await response.json();

    //댓글이 없는 이 시나리오 처리
    if(responseData && responseData.length > 0){
        const commentsListElement = createCommentsList(responseData);
        commentsSectionalElement.innerHTML = ''; //현재 있는 모든 콘텐츠 제거
        commentsSectionalElement.appendChild(commentsListElement);
    } else {
        commentsSectionalElement.firstElementChild.textContent = '댓글을 찾을 수 없습니다. 추가하시겠습니까?';
    }

}
````







