```
[한글자막] Spring Boot 3 & Spring Framework 6 마스터 (2023 Java 최신)
유데미 331~
```

### Docker 용어
* **Docker 이미지**
  * 애플리케이션의 특정 버전을 나타내는 패키지
  * 운영 체제, 소프트웨어, 코드, 의존성 등 애플리케이션이 필요한 모든 것을 포함함
* **Docker 레지스트리**
  * Docker 이미지를 저장하는 곳
  * Docker 허브는 가장 인기 있는 Docker 레지스트리 중 하나
  * 각 클라우드 플랫폼은 Docker 레지스트리도 제공
  * Docker 레지스트리 내부에는 Docker 저장소를 만들 수 있음
* **Docker 저장소**
  * 특정 앱, 특정 마이크로서비스, 특정 소프트웨어에 대한 Docker 이미지를 갖고 있음
  * 저장소에는 태그가 여러가지 있음
  * 각 태그는 Docker 이미지와 관련 있음
* **Docker 컨테이너**
  * Docker 이미지의 런타임 인스턴스
  * Docker 이미지를 가지고 여러 컨테이너를 실행할 수 있음
* **Dockerfile**
  * Docker 이미지를 생성하는 데 필요한 지침을 포함하는 파일



<br>

<br>

## ``` ver.1```
### Docker 이미지의 생성
*  Dockerfile을 사용해야함
  * Docker 이미지를 생성하는 데 필요한 지침을 포함하는 파일 
* 예시 [1]
---
```shell
## Dockerfile - 1 - Creating Docker Images
FROM openjdk:18.0-slim
COPY target/*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/app.jar"]
```
---
* 위의 Dockerfile은 4개의 간단한 지침을 가지고 있음
* ```FROM```
  * 베이스 이미지를 설정
  * 모든 Docker 이미지는 베이스 이미지부터 시작
  * openjdk:18.0-slim
    * 여기서 사용하는 베이스 이미지
  * openjdk
    * 저장소
  * 18.0-slim
      * 태그
* 스프링 부트 프로젝트를 빌드하면 애플리케이션 JAR가 타겟 폴더에 생성됨
* ```COPY```
  * 새 파일이나 디렉토리를 복사해서 이미지로 가져오는 것
  * target/
    * 로컬 머신의 타겟 폴더에서 
  * *.jar
    * JAR 파일을 복사해서 
  * app.jar
    * 특정 이름을 붙여 
  * Docker 이미지에 가져와야함
* 그다음 컨테이너가 런타임에서 수신하도록 설정된 포트에 대해 Docker에게 알림
* ```EXPOSE```
  * 5000
    * 생성하는 애플리케이션을 포트 5000에서 수신하도록 설정
    * 포트 5000을 노출하는 것
* ENTRYPOINT
  * 컨테이너를 실행할 때 실행할 명령어를 설정해야함
  * 컨테이너가 Docker 이미지에서 생성되면 애플리케이션을 실행할 수 있어야 함
  * ["java","-jar","/app.jar"]
    * 3가지 명령어

<br>


### 지침 적용 방법
* 프로젝트 상위 레벨에 새로운 파일 생성
* 파일이름 : Dockerfile
* 지침 붙여넣기
* FROM openjdk:18.0-slim <br>
COPY target/*.jar app.jar  <br>
EXPOSE 5000  <br>
ENTRYPOINT ["java","-jar","/app.jar"]  <br>
* 저장
* 의존성 재주입 및 Run As

<br>

### Dockerfile을 이용한 컨테이너 생성
* 터미널을 열고 현재 폴더로 이동
* 맥이나 리눅스에서 ls를, 윈도우에서는 dir 명령어를 사용하면 Dockerfile을 확인할 수 있음
* 이미지 빌드 전 Docker 실행여부 확인하기
* ```docker --version```  //버전 확인
* ```docker container ls```  // 현재 실행중인 컨테이너
* 이제 Docker 이미지를 빌드할 차례
```shell
$ docker build -t in28min/hello-world-docker:v1 .
```
* ```docker build -t``` + 저장소이름 + 태그 + **마침표**
* ```$ clear```를 입력하고 다시 ```$ docker image list```
* 가장 중요한 것은 가장 최신에 생성된 이미지(시간을 보면 알 수 있음)
* 생성된 Docker 이미지 실행하기
  * ```$ docker run -d -p 5000:5000 in28min/hello-world-docker:v1```
* ```$ docker container ls```로 현재 실행 중인 컨테이너를 확인


<br>

### 위의 실행 방식의 올바르지 않은 사항
* 로컬 머신에 무언가를 빌드해서 Docker 이미지로 복사해서 가져오는 방식은 비추천
  * 로컬 머신에서 어떤 작업을 수행하고, 그것을 복사해서 로컬 이미지로 가져오면 다른 사람의 머신에서 실행했을 때 출력이 다소 달라질 가능성이 있기 때문
* 따라서 Docker 이미지를 생성할 때마다 전체 빌드 프로세스가 Docker 이미지 내부에서 이루어져야 함!
* [1]JAR 파일을 Docker 이미지의 일부로 빌드 -> [2]Docker 이미지의 일부로 실행
  * 이 작업을 수행할 때 빌드에는 많은 시간이 소요되지만 이후 보완함



## ```ver2```
* 자바 파일을 빌드해야함 -> 자바 파일을 빌드하려면 Maven이 필요함
* 이러한 이유로 특정 베이스 이미지를 사용함
---
```
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18.0-slim
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]

```
---
* ```FROM maven:3.8.6-openjdk-18-slim AS build```
  * openjdk와 Maven을 합친 것
  * Maven을 갖게 되면 mvn clean package를 실행할 수 있음
* ``` WORKDIR /home/app    COPY . /home/app ```
  * 전체 디렉토리, 즉 전체 파일이 컨테이너 이미지 내부에서 복사되어 이 폴더로 이동함
* ```RUN mvn -f /home/app/pom.xml clean package```
  * mvn에는 빌드할 pom.xml로서 -f/home/app/pom.xml을 입력함
  * pom.xml을 사용하여 Docker 이미지 내부에서 빌드하는 것
* ```AS build```
  * 위의 4줄(첫번째 단락을 "빌드단계 라고 함"
* ```COPY --from=build /home/app/target/*.jar app.jar```
  * 빌드 단계에서 복사함
  * 이 폴더에서 복사함
  * 타겟폴더는 JAR파일이 생성되는 곳
* JAR 파일을 첫 번째 단계의 일부로 빌드하고, 이것의 출력이 JAR 파일이 됨
* 두번째 단계의 일부로 빌드한 이 JAR 파일을 사용하고
* 이것을 복사해서 두 번째 이미지인 app.jar로 가져감

<br>

### 새 이미지를 사용하는 이유
* 새 이미지를 사용하는 이유, Maven 이미지를 사용하여 실행하지 않는 이유
  * 문제는 Maven 이미지에 openjdk 외에도 다른 많은 것들이 포함될 수 있다는 것
  * 컨테이너를 실행할 때 가급적 작은 컨테이너 이미지를 써야 함
  * 이것이 새 이미지를 생성하고, JAR 파일을 복사하고, 실행하는 이유

<br>

## ```ver.3```
### Docker 이미지의 빌드 시간을 개선하는 방법
* 빌드에는 상당한 시간이 소요되는 문제점을 해결하기
  * [1] Docker가 레이어링이라는 것을 사용한다는 점
    * 각 명령어가 별도의 레이어를 생성할 수 있음
    * Docker가 하는 일은 레이어를 최대한 다시 사용하는 것
  * [2] 변경되지 않는 모든 중요한 사항이 빌드를 시작할 때 존재한다
    * JAR를 빌드하려면 스프링 부트 애플리케이션 클래스가 필요함
---
```
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java	/home/app/src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18.0-slim
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
```
---
* 우선 pom.xml을 복사하고, 
* 스프링 부트 애플리케이션을 복사한 다음
* mvn clean package를 실행함
* (내의견)자주 변경되는 소스와 변경이 적은 소스를 구분하는 것이 중요한가보다
  * 자주 변경되는 코드 때문에 2개의 파일을 복사하고 빌드를 먼저 트리거 하는 것
  * 이 빌드를 트리거하고 나면 모든 파일을 다시 복사하고, 또 다른 빌드를 트리거함
  * 장점
    * 스프링부트실행 자바 파일에서 아무런 사항도 변경하지 않는다면 
    * 이 5개의 레이어가 다시 사용되고 다음 단계에서 mvn clean package만 실행됨
* Docker는 모든 레이어를 캐시하고, 그것을 다시 사용하려고 한다는 사실이 중요함
* COPY./pom.xml을 별도의 단계로 갖고 있음
  * 스프링 부트 애플리케이션은 별도의 단계를 갖고 있기 때문에, 
  * 레이어 3으로 빌드되고,  레이어 4로, 이것은 또 다른 레이어인 레이어 5로 빌드되는 것을 확인할 수 있음
* 중요한 점
  * pom.xml에 아무런 변경 사항이 없다면, 
  * 애플리케이션 파일 자체에 아무런 변경 사항이 없다면 첫 5개의 레이어를 캐싱할 수 있음
  * 변경하려는 모든 것이 컨트롤러, 저장소 클래스, 또는 애플리케이션에 있는 다른 클래스일 경우 빌드가 매우 빨라짐









