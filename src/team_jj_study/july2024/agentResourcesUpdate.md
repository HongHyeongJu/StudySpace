<br>

* 수정할 태그마다 각자의 특성과 해당 코드로 태그 id를 만들어 준다
    * ex) onoff_특정ID
    * ex) cpu_특정ID, ram_특정ID, hdd_특정ID

<br>

* 기기 리소스 5초마다 갱신하기 js 코드
```JSP
<div class="swiper theme-slider" data-swiper='{"autoplay":false,"spaceBetween":5,"loop":false,"loopedSlides":5,"slideToClickedSlide":true,"slidesPerView": 3}'>
        	<div class="swiper-wrapper">
            <c:forEach items="${VO리스트}" var="VO" >
	        	<div class="col-md-4 col-12 swiper-slide">
	                <div class="card shadow-sm border-info border-1 py-3 d-flex align-items-center" >
	                    <h5 class="card-title pt-2">
	                        <span>${VO.속성}</span>
	                        <c:if test="${VO.속성 eq 'ON'}">
	                        	<span class="ms-2 badge badge-phoenix badge-phoenix-success" id="onOff_${VO.속성">ON</span>
	                        </c:if>
							<c:if test="${VO.속성 eq 'OFF'}">
								<span class="ms-2 badge badge-phoenix badge-phoenix-danger" id="onOff_${VO.속성}">OFF</span>
	                        </c:if>
	                    </h5>
	                    <p class="card-text">
	                        <span>CPU : </span><span class="longer-text-skip-span-5vw" id="cpu_${VO.속성}">${VO.속성} %</span>
	                        <span>RAM : </span><span class="longer-text-skip-span-5vw ps-2" id="ram_${VO.속성}">${VO.속성} %</span>
	                        <span>HDD : </span><span class="longer-text-skip-span-5vw ps-2" id="hdd_${VO.속성}">${VO.속성} %</span>
	                    </p>
	                    <button type="button" class="btn btn-subtle-primary border-gray-300 me-1 mb-1 logBtn" data-cd="${VO.속성}" data-nm="${VO.속성}" >로그보기</button>
	                </div>
	            </div>
            </c:forEach>
            </div>
        </div>
		<div class="swiper-nav">
			<div class="swiper-button-next"><span class="fas fa-chevron-right nav-icon"></span></div>
			<div class="swiper-button-prev"><span class="fas fa-chevron-left nav-icon"></span></div>
		</div>
```

<br>

* js에서 5초마다 장비 데이터를 업데이트 하기
```javascript
	// 5초마다 장비 업데이트
	setInterval(function() {
		searchAgent();
	}, 5000);
```

<br>

* 리소스 조회해오는 ajax
```javascript

//장비로그 조회
function searchAgent(){

	$AJAX.get('/agentLog/agentList.json'
		, ''
		, { //ajax 옵션
			headers:{ "Content-Type" : "application/json" }	//전송 헤더
			,dataType:'json'								//데이터 타입
			,success:function(result) {
				let agentList = result.resultData.agentList;
				console.log(agentList);
				if(agentList.length > 0){
					agentList.forEach(agentVo => {
						//list의 agentCd값으로 id="PCR0000001_OnOff"를 찾아서 ON/ OFF 값에 따라 class와 innerText수정
						const agentCd = agentVo.agentCd;
						const onOffElement = $('#onOff_' + agentCd);
						if (onOffElement.length) {
							if (agentVo.network == 'ON') {
								onOffElement.removeClass('badge-phoenix-danger').addClass('badge-phoenix-success').text('ON');
							} else {
								onOffElement.removeClass('badge-phoenix-success').addClass('badge-phoenix-danger').text('OFF');
							}
						}
						//list의 agentCd값으로 CPU,RAM,HDD innerText값 수정
						$('#cpu_' + agentCd).text(agentVo.cpu);
						$('#ram_' + agentCd).text(agentVo.ram);
						$('#hdd_' + agentCd).text(agentVo.hdd);
					});
				}
			}
		}
	);

}
```

<br>

* 과제용이었기 때문에 여기에 기록하고 본 프로젝트에서는 지울 예정