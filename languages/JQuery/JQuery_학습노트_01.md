### 2023.12.05.화


<br>
<br>

## script - jquery 필기
* HTML태그들은 화면에 UI요소를 보여주고 입력은 가능하지만 데이터 처리는 안됨.
* HTML 태그와 데이터 처리를 위해서는 스크립트가 필요함. javaScript,jquery
* jquery를 사용하기 위해서는 선언 필요함
````
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script> 
````

<br>

### 제이쿼리 기본문법
````javascript
$().  //제이쿼리 기본 문법
$("selector").api();
````

<br>

### 코드 작성 준비
* 옛날 방법
````javascript
$(document).ready(function() {
  // 여기에 jQuery 코드 작성
});
````
* 간결하고 일반적인 방법
````javascript
$(function() {
  // 여기에 jQuery 코드 작성
});
````
* 모든 파일은 위에서 부터 읽기 때문에 <body>태그 일기 전에 태그를 사용하는 스크립트가 있다면 온전히 로딩 못됨.
* 대신 function안에 가둬놓으면 바로 실행되지 않음
* 자바스크립트의 function은 jquery의 function과 분리해서 사용해야 함(주의)


<br>

### 태그 제어
* **제어하고 싶은 태그 찾기**
  * id 이용하기: $("태그의_아이디")
  * class 이용하기: $("태그의_클래스") 
  * name 이용하기: $("태그의_네임") 
  * 태그 이용하기: $("태그_종류") 
  * 속성 이용하기: $("[속성=속성값]") 
````html
<script>
    $(function() {
    
        $("태그의_아이디"): $("#infoBtn")
    
        $("태그의_클래스"): $(".btnClass")
    
        $("태그의_네임"): $("[name=infoBtn]")
    
        $("태그_종류"): $("input")
    
        $("[속성=속성값]"): $("disabled=disabled")   $("type=button")
    
    };
</script>
<body>
    <input type="button" name="infoBtn" id="infoBtn" class="btbClass" disabled="disabled"></input>
</body>
````
<br>

* **속성이 없는 태그의 제어 방법**
```html
	<table border = "1">
		<tr>
			<th></th>
		</tr>
		<tr>
			<td>aaa</td>
			<td>111</td>
			<td>qwer</td>
			<td>zxcv</td>
			<td>123qqq</td>
		</tr>
	</table>
```
* 공백 이용하기
```
$("table tr td")
```
* 꺽쇠 이용하기
```
$("table > tr > td")
```
* 태그의 종류와 순서(배열 인덱스) 이용하기
* 같은 태그들은 배열로 인식한다.
* 중간에 추가되지 않을 것을 기대해야함. 인덱스를 이용하기때문에
```
$("td").eq().text();
```
<br>

* **태그 구분을 위한 팁**
* id를 선택할 때 태그 종류를 같이 써줄 수 있음
```javascript
$("#userId")  //동일
$("input#userId")  //동일
```





<br>

### value값 
````javascript
<body>
    <input type="text" name="userId" id="userId" class="userIdfo" ></input>
</body>
````
* $(#userId).val();  //괄호가 비어있는건 태그의 값을 가져오는 것
* $(#userId).val("newUserId");  //괄호 안에 값을 주면 이것으로 대체하는 것


<br>

### value와 text
* val()  text()
* value: 입력해서 변경 가능한 값 ex) input, 
* text: 태그안의 값을 수정할 수 없는 것 ex) table의 td
* select태그의 option태그는 value,text 둘 다 가능함
* 예시
````html
  	<select>
		<option value="code01">강아지</option>
		<option value="code02">고양이</option>
		<option value="code03">앵무새</option>
	</select>

````


<br>

### 이벤트 함수 추가하기
* 이벤트함수의 이름에서 on을 제외하고 사용하기
```javascript
$(#btn).click(function (){
});
```
* on에 동작방식과 함수 추가하기
```javascript
$(#btn).on("click", function (){
    $(#chk).attr("checked", "checked")  //속성추가
});
```
* 속성 추가는 .attr("추가할 속성", "해당 속성의 값")
```
$(#chk).attr("checked", "checked")  //속성추가
```


<br>

### 태그 특징
* 변경불가
  * disabled: 회색으로 변경되며 비활성화(="disabled")
  * readonly: 클릭만 안됨(="readonly")
  * checked: 체크(="checked")
* 커서 옮기기
  * .focus()
* 덮어쓰기
  * .html()
  * 요소 내부의 내용을 완전히 대체하고 새로운 내용으로 덮어쓰는 것
* 추가하기
  * .append()
  * 선택한 요소 내부의 마지막 자식 요소로 추가됨



<br>

### 사용자를 배려하는 로직
* 입력이 틀렸을 때 해당 input칸으로 focus해주기
* 중복값을 입력했을 때 삭제+focus
* 중복확인이 끝나면 disable속성과 함께 수정 버튼 생성해주기



<br>
<br>
<br>






#### 다른 강의 필기
* $("셀렉터").html(oo); // html은 덮어쓰기 (굳이 remove, append로 할 필요 없음)
* $("셀렉터").append(oo); // append은 추가하기 
