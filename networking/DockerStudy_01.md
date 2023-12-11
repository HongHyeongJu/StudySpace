#### 유데미 강의 328~ 
* 교양 느낌으로 일단 들어본다.

<br>


### 간단한 도커 명령어 실행하기
```shell

$ docker --version 
// 도커 설치 확인


$ docker container run -d -p 5000:5000 in28min/hello-world-python:0.0.1.RELEASE
// docker container run는 컨테이너를 실행
// 실행은 포트 5000에서
// 컨테이너 이미지 이름은 in28min/hello-world-python
// 실행하려는 컨테이너 이미지 버전은 0.0.1.RELEASE 

```
* 찾을 수 없다고 하며 이미지를 다운로드함
* Pulling
* 브라우저로 이동해서 localhost:5000를 입력하면 파이썬을 설치하지 않았지만 REST API를 실행할 수 있음
* Docker덕분에 이제 명령어 하나로 전체 애플리케이션을 배포할 수 있게됨

<br>
  
### 백그라운드에서는 무슨 일이 일어나는걸까?
#### Docker의 배포 시스템
* 운영 체제 상관없음
* 애플리케이션에 빌드하기 위해 사용 중인 프로그래밍 언어 역시 상관없음
* 하드웨어 역시 상관없음
* 개발자는 Docker 이미지를 생성하고 나면 운영팀이 Docker 이미지를 간단한 명령어로 실행할 수 있음
* 기본적으로 Docker 이미지가 무엇을 포함하고 있는지에 관계없이, Docker 이미지가 주어진 후에는 동일한 방식으로 실행할 수 있음
```shell
$ docker container ls

// 생성된 컨테이너가 있다면 중지하기
$ docker container stop ID(10글자정도 이미지앞에있음)

clear
$ docker container ls

// 이번에는 자바 어플리케이션 실행해보기
$ docker container run -d -p 5000:5000 in28min/hello-world-java:0.0.1.RELEASE
// 로컬에서 이미지를 찾을 수 없는 경우, 해당 이미지를 다운로드

// 잠시후 이미지 풀링

$ docker container ls
// 자바 어플리케이션이 실행중임을 알 수 있음

// 컨테이너 중단하기
$ docker container stop f1
// f1만 입력해도 되는 것은  f1으로 시작하는 컨테이너가 단 하나이기 때문

$ docker container ls
// 이번에는 실행 중인 컨테이너가 없음을 알 수 있다

$ clear
// clear를 입력하면 애플리케이션 중단됨

```
* 같은 방식으로 Node.js도 가능함

<br>

### Docker가 제공하는 유연성
* Docker 이미지에 무엇이 있는지에 관계없이 개발자가 Docker 이미지를 생성하고 나면, 운영팀이 이미지를 같은 방식으로 실행할 수 있음
* 그저 이미지의 이름, 버전 등을 변경하기만 하면 됨.
* Docker 이미지에 무엇이 있는지에 관계없이 같은 방식으로 실행할 수 있음

<br>

### Docker 이미지가 작동하는 방식
* 우리가 실행하려고 하는 Docker 이미지는 애플리케이션을 실행하는 데 필요한 모든 것을 갖추고 있음
* 운영 체제를 갖고 있고, 애플리케이션 런타임도 갖고 있음
* (자바 애플리케이션을 실행하려면 JDK가 필요/ 파이썬 애플리케이션을 실행하려면 파이썬이 필요/ Node.js 애플리케이션을 실행하려면 Node.js가 필요)
* 이 모든 것이 Docker 이미지에 있음
* 애플리케이션 코드와 의존성 또한 Docker 이미지의 일부
* Docker 이미지를 갖고 있다면 Docker 컨테이너를 어디서나 같은 방식으로 실행할 수 있음
* 로컬 머신과 기업 데이터 센터, 클라우드에서도 가능
* **Docker 런타임이라는 것을 설치하기만 하면 된다.**

