//유데미 강의 484~

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


