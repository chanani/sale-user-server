# Discount User Server


---

## 🔧 Tech Stack

### Stack
* **Java**: 21
* **Spring Boot**: 3.4.4
* **Gradle**: 8.13
* **ORM**: JPA, QueryDSL
* **Database**: MySQL 8.0.33
* **Authentication**: JWT
* **Documentation**: Swagger 3


### 📚 API Documentation

프로젝트를 실행한 후, 아래 링크에서 **Swagger UI**를 통해 모든 API 문서를 확인할 수 있습니다.

* **Swagger UI**: [http://localhost:11001/sale-user-be/user/swagger-ui/index.html](http://localhost:11001/sale-user-be/user/swagger-ui/index.html)

### Package Structure

```
com.vlast.socialmetrics
├── controller/         # API 컨트롤러
├── domain/             # 비즈니스 로직 & 서비스
├── entity/             # JPA 엔티티
├── infra/              # 인프라 및 크롤링
├── scheduler/          # 스케줄링
└── global/             # 공통 설정 & 유틸리티
```

