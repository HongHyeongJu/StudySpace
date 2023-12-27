### K-MOOC 데이터베이스 보안 강의 청강
#### 6주차. 데이터베이스 관리 시스템 (DBMS) 보안

<br>
<br>


### 데이터베이스 관리자
* 역할
  * 시스템 권한 부여
  * 일방사용자 생성 및 권한부여
  * 백업
  * 감사
* 시스템 계정 별로 다른 수행 권한을 가짐
* DBMS별로 다른 계정 및 권한 종류를 가짐
  * 이번 강의에서는 대표적인 오라클과 MySQL 중심으로 학습함

<br>
<br>


## [ 관리자 계정 ]

### Oracle 관리자 계정
* 설치할 때 자동으로 생성되는 관리자 계정
  * SYS, SYSTEM, SYSBACKUP, SYSDG, SYSKM, SYSRAC
* SYS, SYSTEM
  * 시스템 관리 계정. 자동으로 DBA 롤이 부여됨
  * 데이터베이스 시스템 권한 포함. 관리자에게만 부여
* DBA role 시스템 권한 확인 명령문 ```select * from role_sys_privs where role='DBA''```
* SYS
  * 모든 권한을 가지고 있는 관리자 계정
  * 데이터 사전 소유
  * 데이터베이스 설치 시 SYSDBA 권한 자동 부여
* SYSTEM
  * SYS계저과 유사하지만 백업 및 복구, 데이터베이스 업그레이드는 불가함
* SYSBACKUP
  * 백업 및 복구 작업 수행 
* SYSDG
  * Data Guard 작업 수행
  * 하나 이상의 동기화된 대기 데이터베이스 생성 유지 관리하여 데이터 손상을 방지함
* **SYSKM**
  * Transparent Data Encryption(TDE) 작업 수행
  * 오라클에서 제공하는 데이터 암호화 기능 수행 가능
* SYSRAC
  * Real Application Clusters 
  * 오라클 에이전트가 오라클  RAC를 수행할 수 있도록 함




<br>
<br>




### MySQL 관리자 계정
* 데이터베이스 설치와 함께 Root 계정 자동 생성
  * Root 게정은 시스템 계정으로 모든 권한을 보유함
  * Root 계정명은 유추 가능하므로 다른 계정으로 변경 권장
  * 계정명 변경 명령문 ```update user set user='dbsec' where user='root';  flush privileges; ```



<br>
<br>


## [ 관리자 권한 ]





### Oracle의 권한들
* 대표적인 관리자 권한
  * SYSDBA(스키마 SYS)
  * SYSOPER(스키마 PUBLIC)
  * 데이터베이스 생성, 시작, 종료, 백업 또는 복구와 같은 고급 관리 작업을 수행하는데 필요한 관리 권한
  * 접속시 기본 스키마를 사용하여서 접속
* SYSDBA
  * 가장 강력한 관리자 권한
  * 사용자 데이터를 볼 수 있는 기능을 포함하여 대부분 작업 허용
  * RESTRICTED SESSTION 권한 소유함
  * 데이터베이스 생성, 시작, 종료, 백업 또는 복구와 같은 고급 관리 작업을 수행하는데 필요한 관리 권한
  * 서버 파라미터 파일 생성 권한
  * 데이터베이스를 ARCHIVELOG 모드로 변경
  * 데이터 복구
* SYSOPER
  * 사용자가 기본적인 데이터베이스 운영을 위한 작업 수행
  * 사용자 데이터를 볼 수 있는 기능 미제공
  * RESTRICTED SESSTION 권한 소유함
  * SYSDBA의 대부분 수행 가능하지만, 완전 복구만 가능함


<br>
<br>


### MySQL의 권한들
* ALL PRIVILEGES 권한
  * GRANT OPTION 권한을 제외한 모든 권한을 부여함
* MySQL의 특정
* CREATE USER 권한으로 다양한 기능 수행 가능함
  *ORACLE의 경우 각각의 권한이 필요했음









