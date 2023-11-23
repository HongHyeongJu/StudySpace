// 유데미 강의 386~


/*------------ 함수, 기본 매개변수 ------------*/

//함수의 매개변수는 비선택적 매개변수, 선택적 매개변수 순서로 위치해야함
function greetUser(greetingPrefix, userName='user') {
    console.log(greetingPrefix+' '+userName+'!!');
}

greetUser('Hi', 'Max');
greetUser('Hello');
console.log(' ');









/*------------ 나머지 매개변수(Rest 매개변수), 스프레드 연산자(확산 연산자) ------------*/

function sumUp(num1, num2, num3=0){
    return num1+num2+num3;
}

console.log(sumUp(1,2,));
console.log(sumUp(1,2,));
console.log(' ');


// Rest매개변수(나머지 매개변수) 표현법 ... 붙이기
function sumUp2(...numbers){
    let result=0;
    for(const number of numbers){
        result += number;
    }
    return result;

}

console.log(sumUp2(1,5,10,11,20,41));
//함수의 매개변수를 나머지 매개변수로 표현했기 때문에 나열된 숫자들은 배열로 묶여 전달된다.
//자바스크립트 내부적으로 배열로 래핑되는것
//console.log(sumUp2([1,3,5,7,8]));


//스프레드 연산자 ...는 개별 값 목록을 병합해서 배열로 수집함
const inputNumbers = [1,5,10,11,20,41];

console.log(sumUp2(...inputNumbers));   //반대로 스프레드 연산자로 전달하면 개별 값 목록으로 전달됨
console.log(' ');









/*------------ 함수는 객체 ------------*/
console.log(sumUp2);
console.dir(sumUp2);
console.log(' ');
//함수는 일급객체 이기 때문에 속성와 메소드 가질 수 있음
//또한 속성을 저장하거나 업데이트 할 수 있음
//함수는 객체이지만, 실행될 수 있음!!





/*------------ 템플릿 리터럴 작업 ------------*/
function greetUser2(greetingPrefix, userName='user') {
    // console.log(greetingPrefix+' '+userName+'!!');
    console.log(`${greetingPrefix}  ${userName} !!`);
    //백틱과 동적 값을 전달하는 placeHolder를 이용해서 더 간격해짐
}
greetUser2('gogo');
console.log(' ');


