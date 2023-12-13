유데미 484~

<br>

### 오류처리(서버측 & 기술)
* 기술적인 이유로 요청이 실패하면 ( 400이나 500 상태 코드가 아니라) 기술적인 문제를 가지고 있는 것.
* 그러면 이 {fetch();} 함수는 오류를 발생함
* {async/await}를 사용할 때 {try/catch}를 사용해 오류 작업을 수행할 수 있음
* 코드 수정
```javascript
[기존 코드]
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

    if(response.ok){
        fetchCommentsForPost(); //해당 모든 댓글에 관해 또 다른 {GET} 요청을 다시 보냄
    } else {
        alert('댓글을 전송할 수 없습니다.');

    }

}
```
* 오류처리 추가
```javascript
async function saveComment(event) {
    event.preventDefault();
    const postId = commentsFormElement.dataset.postid;
    const enteredTitle = commentTitleElement.value();
    const enteredText = commentTextElement.value();

    const comment = {title: enteredTitle, text: enteredText};

    try {
        // console.log(enteredTitle); 확인
        const response = await fetch(`/posts/${postId}/comments`, {
            //두번째 매개변수는 다른 속성을 설정할 수 있는 객체
            method: 'POST',
            body: JSON.stringify(comment), //JSON 데이터를 받기 위해 app.js에  app.use(express.json());  추가함

            headers : {
                'Content-Type': 'application/json'  //이 요청이 일부 제이슨 데이터를 전달함
            }
        });

        if(response.ok){
            fetchCommentsForPost(); //해당 모든 댓글에 관해 또 다른 {GET} 요청을 다시 보냄
        } else {
            alert('댓글을 전송할 수 없습니다.');  //이 처리도 결국은 최소한 응답이 있는 경우에만 신제로 실행되어야함
        }
    } catch (error){
        //기술적인 오류가 발생한 걍우
        alert('요청을 보낼 수 없습니다 - 나중에 다시 시도하세요!');
    }

}
```
* catch블럭 실험을 위해서 브라우저의 개발자도구 - 네트워크 - No throttling을 선택해서 오프라인 상태로 가장함

<br>
<br>

### 댓글 가져오는 함수도 수정
* fetchCommentsForPost
* 여기서 구문 분석을 계속하고 페이지에 무언가를 출력하기 전에 응답을 받았는지 확인하기
* {!response.ok}가 거짓인지 확인하고 , 그렇다면 이 경우 다른 코드가 실행되지 않도록 반환
* 아래 코드를 중간에 추가
```javascript
    if(!response.ok){
        alert('댓글 가져오기에 실패했습니다!');
        return;
    }
```
* 그리고 기술적인 오류를 포착하기 위해서 try-catch블록으로 감싸기
* 아래는 수정완료한 코드
```javascript
async function fetchCommentsForPost(event){
    const postId = loadCommnetsBtnElement.dataset.postid;

    try {
        //fetch의 매개변수는 get요청을 보내려는 URL. 기본이 GET요청
        const response = await fetch(`/posts/${postId}/comments`); //이 URL로 HTTP요청이 감

        if(!response.ok){
            alert('댓글 가져오기에 실패했습니다!');
            return;
        }

        const responseData = await response.json();

        //댓글이 없는 이 시나리오 처리
        if(responseData && responseData.length > 0){
            const commentsListElement = createCommentsList(responseData);
            commentsSectionalElement.innerHTML = ''; //현재 있는 모든 콘텐츠 제거
            commentsSectionalElement.appendChild(commentsListElement);
        } else {
            commentsSectionalElement.firstElementChild.textContent = '댓글을 찾을 수 없습니다. 추가하시겠습니까?';
        }

    } catch (error){
        alert('댓글 가져오기에 실패했습니다!');
    }


}
```
* 이러한 오류 처리로 문제가 발생했을 때 더 나은 사용자 경험을 제공할 수 있음