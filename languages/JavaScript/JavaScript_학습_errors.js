// 유데미 강의 391~

// node.js에서만 작동하는 부분이지만 공부~
// 함수는 오류가 발생하면 실행을 중지하고, 그 오류는 함수가 호출된 장소로 전달됨express는 라우트 핸들러 함수를 호출함.
// 그리고 라우트 핸들러 중 하나에서 오류를 수신하면, (오류가 있음을 확인한 다음)기본 오류 처리 미들웨어를 실행함
//

// try-catch구문은 전체적으로 발생할 수 있는 오류를 매우 자세하게 처리하는데 매우 유용한 구조
// 어떤 상황에서 오류를 예상할 수 있는 곳만, 필요한 만큼만 래핑하기

const fs = require('fs');

function  readFile(){
    try{
        const fileData = fs.readFileSync('data.json');
    } catch {
      console.log('An error occurred!');
    }

    console.log('Hi there!');
}

readFile();



/* 오류 데이터 & 사용자 지정 오류 발생 */

//오류가 발생하면 항상 해당 오류에 대한 추가 정보(예: 오류로 이어지는 메시지 및 일련의 단계)가 포함된 일부 데이터(일반적으로 객체)를 얻습니다.
//다음과 같이 해당 객체/데이터를 확보할 수 있습니다.

try {
    somethingThatMightFail();
} catch (error) { // accept an "error parameter" after catch
    console.log(error.message);
}

//catch 후에 오류 데이터를 매개변수처럼 받아들일 수 있습니다(엄밀히 말하자면 함수가 아님에도 불구하고 말이죠).
//그 데이터가 정확히 무엇인지(예: message 속성이 있는 객체)는 오류를 일으킨 함수/메소드에 따라 다릅니다.
//여러분의 오류를 던질 수도 있습니다.

/*
function doSomething() {
    // do something ...
    throw { message: 'Something went wrong! };
}
*/

//이건 조금 더 발전되었지만 결국 오류를 일으키는 경우에 이러한 모든 내장 함수와 메서드가 하는 일입니다.


// 스코프지정(범위지정) : 단순히 변수, 상수 및 함수가 특정 위치에만 사용될 수 있음을 의미함