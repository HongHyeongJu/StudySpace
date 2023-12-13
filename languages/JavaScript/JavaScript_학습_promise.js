// 유데미 강의 398~

/*--------- 프로미스 ---------*/
// 때때로 서로 의존하는 여러 비동기 작업이 있는 경우가 있음  ex) 1.파일읽기 -> 2.데이터 전달하기
// 서로 의존해야 하는 비동기 작업이 많을 수록 콜백 함수 내부에 더 많은 콜백 함수가 있을 수 있음(콜백함수 중첩)

const fs = require('fs/promises');

function  readFile(){
    let fileData;
/*    fs.readFile('data.txt'); 는 프로미스 객체를 반환함
    프로미스: 특정 속성와 메서드를 가지는 표준화된 객체
            then()메서드는 다시 함수, 익명함수, 미리 정의된 함수를 사용할 수 있음*/
    //성공사례를 처리하는 then()메서드, 프로미스에서 발생하는 오류를 처리하는 catch()메서드

    fs.readFile('data.txt').then(function (fileData){
        //이제 콜백함수를 readFile에 전달하지 않고 대신 then에 전달해서 readFile을 호출함
        console.log('File parsing done!');
        console.log(fileData.toString());
    }).then(function (){
        console.log('promises chained');

    });


    console.log('Hi there!');
}

readFile();
console.log(' ');

// try-catch의 try는 함수 호출 성공여부만 확인할 뿐, 결과가 성공했는지 여부는 확인하지 않음




/*--------- 콜백 함수를 사용한 비동기 함수의 예외 처리 ---------*/
function ex01(){
    fs.readFile('filename.txt', (error, data) => {

        if (error) {
            // 오류 처리
            console.error('Error reading file:', error);
            return;
        }
        // 파일 읽기 성공 처리
        console.log(data);

    });
}





/*--------- 프로미스 기반의 예외 처리 ---------*/
function ex02(){
    fs.promises.readFile('filename.txt')
    .then(data => {
        // 파일 읽기 성공 처리
        console.log(data);
    })
    .catch(err => {
        // 오류 처리
        console.error('Error reading file:', err);
    });


}