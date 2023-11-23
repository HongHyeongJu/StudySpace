// 유데미 강의 390~

/*------------ 기본값과 참조값 ------------*/
const hobbies= ['studying', 'overwatch'];
//배열이나 객체를 저장할때는 실제 값이 아닌 참조(배열에 대한 포인터)를 저장함

const age= 32;  //값 자체가 저장됨
//const 숫자는 많은 메모리를 차지하는 복잡한 객체가 아니기 때문에 불필요하게 객체를 복사하는 것을 피하기 때문에 바꿀 수 없음

hobbies.push('reading'); //배열의 주소는 변경되지 않음

// hobbies = ['new', 'array'];  // 허용하지 않음. const이기 때문에 새로운 배열 주소를 넣을 수 없다

console.log(hobbies);





const person={ age: 32 };

function gerAdultYears( p ){
    p.age -= 18;
    return p.age;
}

console.log(person);
console.log(gerAdultYears(person));
console.log(person);  //const person은 객체를 참조하기 때문에 해당 객체 값이 바로 윗줄에서 함수로 인해 변했음
console.log();



const person2={ age: 32 };

function gerAdultYears2( p ){
    p.age -= 18;
    return p.age;
}

console.log(person2);
console.log(gerAdultYears( { age: person2.age} ));  //메모리에 완전히 새로운 장소에 완전 새로운 객체를 생성
console.log(gerAdultYears( {...person2} ));  //매개변수로 전달한 ...person2는 원래 객체를 가지고 새로운 객체를 생성하기 때문에 더이상 원래 객체를 건들지 않음
console.log(person2);
console.log();
