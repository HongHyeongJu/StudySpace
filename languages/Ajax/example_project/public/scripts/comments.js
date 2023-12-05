//post-detail.ejs 의 코멘트 GET요청을 지우고 버튼에  id="load-comments-btn"  삽입

const loadCommnetsBtnElement = document.getElementById('load-comments-btn');
const commentsSectionalElement = document.getElementById('comments');
const commentsFormElement = document.querySelector('#comments-form from');
//from엘리먼트는 해당 자바스크립트 코드가 있을 경우 제출될 때 이벤트도 같이 제출함
const commentTitleElement = document.getElementById('title');
const commentTextElement = document.getElementById('text');


//댓글 목록 준비하기
function createCommentsList(comments){
    const commentListElement = document.createElement('ol');

    for(const comments of comments){
        const commentElement = document.createElement('li');
        commentElement.innerHTML = `
            <article class="comment-item">
              <h2>${comments.title}</h2>
              <p>${comments.text}</p>
            </article>
        `;

        commentListElement.appendChild(commentElement);
    }
}



//해당 데이터, 댓글 데이터를 가져오기 위해 서버에  ajax 요청 보내기
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


loadCommnetsBtnElement.addEventListener('click', fetchCommentsForPost);
commentsFormElement.addEventListener('submit', saveComment);





