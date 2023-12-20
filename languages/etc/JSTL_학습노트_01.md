### 12월 13일 (수)

<br>

#### JSTL
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/function" prefix="fn" %>
```
* prefix는 변경 가능하지만 규칙처럼 정해두고 쓰기

<br>


## core
* 조건문, 반복문
* ```<c:if test=""> 조건이 참일 때 실행되는 코드 </c:if>```
* if / choose-when / forEach/ set / out
* ```${  }``` EL태그 표현식? 백단에서 보내준 값 화면으로 가져올 때
  * String일 경우 '' 처리
  * null처리는 empty
  * ```${ list eq '123' }``` : equals
  * ```${ list ne '123' }``` : not equals
  * 문법 알고만 가세여
    * 크다 : gt
    * 크거나 같다 : ge
    * 작다 : lt
    * 작거나 같다 : le
* choose-when : 무조건 값이 있을 때 A나 B나 C
* forEach : 배열이나 리스트의 자료형이 들어와야 사용할 수 있음
  * 내가 반복 직접 주고 싶으면 ```<c:forEach begin=  end=  items="list" val="i"> ${array[0].name}} </c:forEach>```
* set : 변수 선언
  * 화면에 노출되지 않은 값 저장하고싶을 때
  * ```<c:set var="변수명" value="변수 값"></c:set>```
* out : 화면에 출력하는거
  * 이거 링크 표시할때 사용해봤다


<br>

## fmt
* 데이터 변환은 아님
* 화면에 노출되는 형식을 바꿔줌
```html
<fmt:formatDate value="23/12/13"  pattern="yyyy-MM-dd"/>

<fmt:formatNumber value="1000000000" pattern="###,###"/> 

```

<br>

## function
* 데이터를 변환함
* EL태그 안에서 작업함
* 화면에서 많인 텍스트 길이가 너무 길 때 일정 이상에서는 ...으로 보여주고 싶을 때
```html
<c:set var="변수명" value="변수 값"></c:set>

<c:if test="${fn:length(str) > 10}" ></c:if>
```



<br>

#### 화면 개발 개념
* 화면이 전환될 때 JSP, Java, xml 등의 파일이 오감
* 개발할 때 체크할 것
  * 1. 지금 다음 화면으로 이동할 때 그 화면에 보내줄 값이 있는가?
  * 2. 보내줄게 있다면? 무엇을 셋팅해서 보내줄 것인가
    * 안그러면 null등 JSP와 Controller갑의 데이터 전달에 대해 확실히 생각하기
* 생각하는 방법
  * **DB**를 기준으로 저장여부
  * **첫 화면** 볼 때 셋팅되어야 하는 값이 있는지

<br>


### 기타
* 값이 필요하지 않은 글등록 페이지 이동은 버튼에 onclick = location.href 속성을 바로 줘도됨

<br>

* **스크립트 단에서 보내는 방법**
* form으로 submit할 때 태그에 name 속성 꼭 필요함
* .value 쓰는 애들은 name 속성이 가능하다고 생각하면 됨
* 설명) input 처럼 내가 바꿀 수 있는 것들은. 입력갑. value 
* 값이 넘어가지 않는 경우
  * td/ div/ p 태그 등
  * <form></form> 태그의 범위를 벗어남
* text값을 보낼때
  * 강제로 보내거나
  * hidden속성을 가진 input태그를 안에 넣어서 보냄

<br>
  
* **JQuery를 이용한 데이터 제출**
  * 버튼 클릭했을 때
  * ```$("#frm").attr("action","이동할URL").attr("속성","값").attr("속성","값").submit();```
* 장점
  * 한 번 제출로 여러군데에 값을 보낼 수 있음

<br>
  
* 이후 DTO나 MAP<String, Object>으로 받기
  * MAP으로 매개변수 받을 때는 @RequestParam 붙이고
  * DTO는 @ModelAttribute
* return 값으로 ```redirect:```  이거나 ```forward:``` 는 url로 보내는 것
* MyBatis 문법
  * 파라미터는 1개만 보낼 수 있다
  * 그래서 DTO나 MAP 사용함
  * DML 언어 사용시 데이터 변경 결과를 int 값으로 반환해줌
* 위의 int 결과값은 사용자에게 알려주기 위한 조건값

<br>
  
* 앞으로의 과제
  * 쿼리 작성하기
* true 인것 배열로 submit하는게 핵심













