<h1>**🐳Kotlin Spring Boot API Boilerplate**</h1>

<h2>💡 프로젝트 개요 </h2>
- 이 프로젝트는 Kotlin과 Spring Boot를 기반으로 구축된 백엔드 API 서비스의 기본 구조(Boilerplate)입니다. 
Spring Boot 3.x, JPA, Flyway를 사용하여 데이터베이스 마이그레이션을 관리하며, 로컬 개발 환경에서는 Docker Compose를 통해 PostgreSQL 데이터베이스와 Redis를 자동으로 실행하도록 설정되어 있습니다.

<h2>🛠️ 프로젝트 구성 및 기술 스택 </h2>

|분류| 기술 스택             | 버전/설명                                                                         |
|------|-------------------|-------------------------------------------------------------------------------|
|언어| Kotlin            | JVM 21                                                                        |
|프레임워크| Spring Boot       | 3.5.x 이상                                                                      |
|빌드 도구| Gradle            | Kotlin DSL                                                                    |
|데이터베이스| PostgreSQL        | postgres:17 <span style="background-color: #dcffe4">  (Docker Compose)</span> |
|DB 마이그레이션| Flyway            | 11.9.1                                                                        |
|ORM| Spring Data JPA   | -                                                                             |
|API 문서| Springdoc OpenAPI | Swagger UI 제공                                                                 |



<h2> 📂 주요 파일 및 디렉토리 </h2>

| 파일/디렉토리                                                                               | 설명                                         | 
|---------------------------------------------------------------------------------------|--------------------------------------------|
| <span style="background-color: #f0f0f0;"> build.gradle.kts                            | Gradle 빌드 설정 파일 (Kotlin DSL). 주요 종속성 및 태스크 정의. |
| <span style="background-color: #f0f0f0;"> src/main/kotlin/                            | Kotlin 소스 코드.                              |
| <span style="background-color: #f0f0f0;"> src/main/resources/                         |애플리케이션 리소스 및 설정 파일.|
| <span style="background-color: #f0f0f0;"> src/main/resources/application-local.yml    |로컬 개발 환경 전용 설정. (DB, Redis 등)|
| <span style="background-color: #f0f0f0;"> src/main/resources/db/migration             |Flyway 마이그레이션 스크립트(.sql) 저장 경로.|
| <span style="background-color: #f0f0f0;"> docker-compose.yml             |로컬 DB 및 Redis 컨테이너 정의 파일.|


<h2> ⚙️ 필요사항 </h2>

- 로컬에서 개발 환경을 구축하고 실행하려면 다음 도구들이 반드시 설치되어 있어야 합니다.
1. Java Development Kit (JDK) 21 이상
2. Docker


<h2>🚀 시작하는 방법 </h2>

1. 프로젝트 클론
2. Docker Compose 파일 확인
- 프로젝트 루트 디렉토리에 다음 컨테이너를 정의하는 docker-compose.yml 파일이 있는지 확인하십시오.
```
# docker-compose.yml (핵심 설정)
services:
  redis:
    image: redis:alpine
    restart: always
    ports:
      - "16379:6379"
  postgres:
    image: postgres:17
    restart: always
    ports:
      - "15432:5432"
    environment:
      POSTGRES_USER: ludin
      POSTGRES_PASSWORD: ludinPW
      POSTGRES_DB: ludin
    volumes:
      - postgres_data:/var/lib/postgresql/data

# ... (생략)
```
3. 로컬 환경 설정 (application-local.yml)
- src/main/resources/application-local.yml 파일이 Docker Compose 설정과 일치하는지 확인합니다.
```
# application-local.yml (핵심 설정)
# Local

spring:
  application:
    name: ludin-kopring-api
  config:
    activate:
      on-profile: local

  datasource:
    driver-class-name: org.postgresql.Driver
    write:
      jdbc-url: jdbc:postgresql://localhost:15432/{dbName}
      username: {DB_ID}
      password: {DB_PASSWORD}
    read:
      jdbc-url: jdbc:postgresql://localhost:15432/{dbName}
      username: {DB_ID}
      password: {DB_PASSWORD}

  flyway:
    url: jdbc:postgresql://localhost:15432/{dbName}
    user: {DB_ID}
    password: {DB_PASSWORD}
    enabled: true
    validate-on-migrate: true
    locations: classpath:db/migration
    clean-disabled: false      # ✅ 개발 중 DB 초기화 허용
    out-of-order: true         # ✅ 과거 버전 파일 허용
    baseline-on-migrate: true  # ✅ flyway_schema_history가 없을 때 baseline 설정

  docker:
    compose:
      enabled: true
      lifecycle-management: start_only
      skip:
        in-tests: false
# ... (생략)
```

<h2>▶️ 실행 방법 (Execution)</h2>

<h3>1. 개발 환경 실행 (DB 자동 실행 포함)</h3>
- Gradle의 bootRun 태스크를 사용하여 애플리케이션을 실행하면, Spring Boot의 내장 Docker Compose 기능이 자동으로 백그라운드에서 PostgreSQL과 Redis 컨테이너를 시작합니다.
```
# 로컬 프로파일을 활성화하여 실행합니다.
./gradlew bootRun
```

<h3>실행 과정:</h3>

1. Spring Boot가 시작됩니다. 
2. docker.compose.enabled: true 설정에 따라 docker-compose.yml 파일이 실행되어 DB 컨테이너(15432 포트)가 시작됩니다. 
3. Flyway 마이그레이션이 자동으로 수행됩니다 (DB 스키마 생성). 
4. 애플리케이션이 시작됩니다.

<h3>2. DB 마이그레이션 (선택적)</h3>
- 애플리케이션 시작 시 Flyway가 자동으로 마이그레이션을 수행하지만, 수동으로 마이그레이션하거나 클린(초기화)할 수 있습니다.

| 명령어                                                             |설명|
|-----------------------------------------------------------------|---|
| <span style="background-color: #f0f0f0;">./gradlew flywayMigrate |Flyway 스크립트(db/migration)를 실행하여 DB를 최신 상태로 마이그레이션합니다.|
| <span style="background-color: #f0f0f0;">./gradlew flywayClean  |DB 내의 모든 테이블과 데이터를 삭제합니다. (주의: 개발 환경에서만 사용하세요.)|

<h2>📋 기타 설정 및 주의사항</h2>
1. Flyway 설정
   - 개발 편의성을 위해 Flyway에 다음과 같은 설정이 추가되어 있습니다.
     - clean-disabled: false: 로컬 개발 중 DB 초기화(flywayClean)를 허용합니다. 
     - out-of-order: true: 과거 버전의 마이그레이션 파일이 나중에 발견되어도 허용합니다.
     - baseline-on-migrate: true: 마이그레이션 기록 테이블이 없을 때 현재 상태를 기준으로 베이스라인을 설정합니다.

2. Docker Compose 수동 관리
   - 만약 Spring Boot의 자동 관리가 아닌, 수동으로 DB 컨테이너를 관리하고 싶다면 다음 명령어를 사용합니다.
```
# DB/Redis 컨테이너 수동 실행
docker compose up -d

# DB/Redis 컨테이너 수동 종료
docker compose down
```