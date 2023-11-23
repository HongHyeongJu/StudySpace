// 유데미 강의 397~

/*--------- 동기코드 ---------*/

const fs = require('fs');

function  readFile(){
    let fileData;

    fileData = fs.readFileSync('data.txt');
    //동기 처리방식 덕분에 위의 파일을 모두 읽을 때 까지 아래의 코드 실행은 잠시 멈춘다


    console.log(fileData);
    //<Buffer 54 68 69 73 20 77 6f 72 6b 20 2d 20 64 61 74 61 20 66 72 6f 6d 20 74 68 65 20 74 65 78 74 20 66 69 6c 65 21>
    console.log(fileData.toString());
    //This work - data from the text file!
    console.log('Hi there!');
}

readFile();
console.log(' ');





/*--------- 비동기코드 & 콜백함수 ---------*/

function  readFile2(){
    let fileData;

    fileData = fs.readFile('data.txt',  function (error, fileData){
        console.log('File parsing done!');
        console.log(fileData.toString());
    });
    // 콜백변수를 보면 동기작업임을 알 수 있음.
    // 작업이 완료되면 추후의 어느 시점에서 실행되어야 하는 함수를 지정함


    console.log('Hi there!');
}

readFile2();
console.log(' ');




