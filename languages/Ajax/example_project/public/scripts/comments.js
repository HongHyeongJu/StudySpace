//post-detail.ejs 의 코멘트 GET요청을 지우고 버튼에  id="load-comments-btn"  삽입

const loadCommnetsBtnElement = document.getElementById('load-comments-btn');
const commentsSectionalElement = document.getElementById('comments');


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
    //fetch의 매개변수는 get요청을 보내려는 URL. 기본이 GET요청
    const response = await fetch(`/posts/${postId}/comments`); //이 URL로 HTTP요청이 감
    const responseData = await response.json();

    const commentsListElement = createCommentsList(responseData);
    commentsSectionalElement.innerHTML = ''; //현재 있는 모든 콘텐츠 제거
    commentsSectionalElement.appendChild(commentsListElement);


}

loadCommnetsBtnElement.addEventListener('click', fetchCommentsForPost);