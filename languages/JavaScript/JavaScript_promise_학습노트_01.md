## promise 복습 중에 궁금한점 정리

<br>

####  fetch 함수를 사용하는 promise
```javascript
fetch(url, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)  // data는 전송할 데이터
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));

```

<br>

####  fetch 함수를 사용코드를 async/await 문법으로 표현하기
```javascript
async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST', 
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    return response.json(); // 응답 데이터를 JSON 형식으로 파싱
}

postData('https://example.com/answer', { answer: 42 })
    .then(data => {
        console.log(data); // 처리된 응답 데이터
    })
    .catch(error => {
        console.error('Error:', error);
    });

```

<br>

#### async/await 문법을 간략히 말하자면..
* promise과 같은 기능을 함
* promise를 동기함수 형식으로 표현함(익숙하고 일반적인)
  * 그래서 예외 처리를 직관적으로 할 수 있음(try-catch문)
* async는 이게 비동기 함수라는걸 명시
* await가 붙은게 비동기 내용


<br>

#### async function안에 await 여러 개 사용할 수 있을까?
* **순차적**으로 사용하는 방법
```javascript
async function exampleAsyncFunction() {
    try {
        const result1 = await someAsyncFunction1();
        console.log(result1);

        const result2 = await someAsyncFunction2();
        console.log(result2);

        const result3 = await someAsyncFunction3();
        console.log(result3);

        // 이곳에서 다른 작업 수행 가능
    } catch (error) {
        console.error(error);
    }
}

exampleAsyncFunction();

```
* **병렬적**으로 사용하는 방법
```javascript
async function exampleAsyncFunction() {
    try {ㅎ
        const [result1, result2, result3] = await Promise.all([
            someAsyncFunction1(),
            someAsyncFunction2(),
            someAsyncFunction3()
        ]);

        console.log(result1);
        console.log(result2);
        console.log(result3);

        // 이곳에서 다른 작업 수행 가능
    } catch (error) {
        console.error(error);
    }
}

exampleAsyncFunction();

```











