// 유데미 강의 400~

/*--------- async와 await ---------*/
//async와 await는 JavaScript의 비동기 프로그래밍을 더 쉽고 직관적으로 만들기 위해 도입된 구문

const fs = require('fs/promises');

//async를 앞에 붙임으로써 이 함수는 반환문(return)은 없지만 자동으로 프로미스를 반환함
async function  readFile(){  //async키워드는 다른 하나의 카워드(await)를 잠금해제해서 해당 함수 내세어 사용할 수 있음
    let fileData;

    //await : 프로미스를 반환하는 모든 메서드 앞에 추가 할 수 있음
    try{
        fileData = await fs.readFile('data.txt');
        //async와 await를 사용하면 try-catch구문 사용도 가능함
    }catch (error) {
        console.log(error);
    }
    //async await를 사용할 때 자바스크립트는 해당 프로미스에 then을 추가함
    //그리고 then에 전달되는 이 함수의 매개변수 값으로 여기서 얻을 수 있는 값을 제공함
    //해당값을 반환값으로 제공함(마치 우항이 비동기 작업인 것 처럼!)

    //12번째 줄이 완료될 때까지 중지됨
    console.log('File parsing done!');
    console.log(fileData.toString());
    console.log('Hi there!');
    console.log('promises chained');

}


readFile();
console.log(' ');