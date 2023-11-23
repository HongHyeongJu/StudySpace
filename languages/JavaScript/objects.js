// 유데미 강의 394~

/*--------- 생성자함수/클래스 ---------*/

//객체 리터럴 표기법
const job0 = {
    title : 'Developer',
    location : 'New York',
    salary : 50000,
};

console.log(new Date().toISOString());
console.log(' ');



//동일한 모양을 가진 객체가 잠재적으로 필요한 경우
class Job {
    // 예약된 이름의 특별한 메서드.
    // 이 클래스의 구제척인 인스턴스 구성 방법과 나중에 new키워드와 함께 이 객체를 구성하는 방법을 자바스크립트에게 알려줌
    constructor(jobTitle, place, salary) {
        this.title = jobTitle;
        this.location = place;
        this.salary = salary;
    }

    describe(){
        console.log(`I'm a ${this.title}, I work in ${this.location} and I earn ${this.salary}.` );
    }

}

const developer = new Job('Developer','New York',50000);
console.log(developer);
developer.describe();






/*--------- 배열 비구조화 ---------*/
const input = ['apple', 'banana'];
const [firstFruit, secondFruit ] = input;
console.log(firstFruit);
console.log(secondFruit);
console.log(' ');



/*--------- 객체 비구조화 ---------*/
const jopjop = { title: 'Developv er', location: 'New York' };
const { title: jTitle}= jopjop;
console.log(jTitle)

//uuid 공식 문서에서도 객체 비구조화를 이용해 대입함
const { v4: uuidv4 } = require('uuid');
uuidv4();





