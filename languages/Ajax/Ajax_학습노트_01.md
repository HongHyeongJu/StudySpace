### 변경점만 요약 정리
템플릿 버튼에 id와 data속성 추가
*** 유데미 강의 478~


````html
<!--         <form action="/posts/<%= post._id %>/comments" method="GET"> -->

          <button id="load-comments-btn" class="btn btn-alt" data-postid="<%= post._id %>">Load Comments</button>  <!--postid EJS로 주입하기-->
````

<br>
<br>

javascript 파일 추가

````javascript
//post-detail.ejs 의 코멘트 GET요청을 지우고 버튼에  id="load-comments-btn"  삽입

const loadCommnetsBtnElement = document.getElementById('load-comments-btn');

//해당 데이터, 댓글 데이터를 가져오기 위해 서버에  ajax 요청 보내기
async function fetchCommentsForPost(event){
    const postId = loadCommnetsBtnElement.dataset.postid;
    //fetch의 매개변수는 get요청을 보내려는 URL. 기본이 GET요청
    const response = await fetch(`/posts/${postId}/comments`); //이 URL로 HTTP요청이 감
    const responseData = await response.json();
    console.log(responseData);

}

loadCommnetsBtnElement.addEventListener('click', fetchCommentsForPost);

````

<br>
<br>

blog.js 파일 수정

```javascript

router.get('/posts/:id/comments', async function (req, res) {
  const postId = new ObjectId(req.params.id);
  // const post = await db.getDb().collection('posts').findOne({ _id: postId });
  const comments = await db
    .getDb()
    .collection('comments')
    .find({ postId: postId }).toArray();

  // return res.render('post-detail', { post: post, comments: comments });
  // 원시데이터를 얻기 위해 변경
  res.json({comments: comments});
});

```



<br>
<br>

======= 댓글 섹션의 내용을 수동으로 교체하여 코멘트 내용을 가저온 데이터로 바꾸기 =======
* 기존의 EJS 지우기
* DOM 변경하는 js코드로 변경하기. 응답을 받고 화면에 표시되는 항목 변경하기
````javascript
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



````
추가하기

```javascript
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

```



<br>
<br>

*** 유데미 강의 484~





































