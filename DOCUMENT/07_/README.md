# SPRINGBOOT


JPA개념
---
> <br>

|-|
|-|
|[JPA 기본](https://dev-coco.tistory.com/74)|

---
#
---

JPA Entity 관련 애노테이션 정리
---

```
@Entity
    해당 클래스가 JPA 엔티티임을 표시합니다.
    이 애노테이션을 사용하여 데이터베이스의 테이블과 매핑될 클래스를 정의합니다.
@Table
    엔티티와 매핑될 데이터베이스 테이블을 지정합니다.
    테이블 이름, 스키마, 인덱스, 고유 제약 조건 등을 설정할 수 있습니다.
@Id
    해당 필드가 엔티티의 주키(primary key)임을 표시합니다.
    주키는 엔티티를 식별하는 데 사용되며, 반드시 필요한 애노테이션입니다.
@GeneratedValue
    주키의 값이 자동으로 생성되는 경우 사용됩니다.
    자동 생성되는 값의 전략(strategy)을 설정할 수 있습니다.
@Column
    엔티티 클래스의 필드와 데이터베이스 테이블의 컬럼을 매핑합니다.
    컬럼의 이름, 길이, NULL 허용 여부, 고유 제약 조건 등을 설정할 수 있습니다.
@Temporal
    날짜 및 시간 타입의 필드를 매핑할 때 사용됩니다.
    DATE, TIME, TIMESTAMP 중 어떤 형식으로 매핑할지 지정합니다.
@OneToMany, @ManyToOne
    일대다 및 다대일 관계를 매핑합니다.
    일대다 관계는 한 엔티티가 여러 다른 엔티티와 관계를 가지는 경우에 사용됩니다.
    다대일 관계는 여러 엔티티가 하나의 엔티티와 관계를 가지는 경우에 사용됩니다.
@OneToOne
    일대일 관계를 매핑합니다.
    양쪽 엔티티 중 한 쪽에서만 참조가 필요한 경우에 사용됩니다.
@ManyToMany
    다대다 관계를 매핑합니다.
    연결 테이블이 필요하며, 두 엔티티 간의 매핑을 설정합니다.
@JoinColumn
    관계 매핑에서 외부 키(Foreign Key)를 지정합니다.
    기본적으로 연관 관계의 주 키(primary key)를 참조하지만, 다른 컬럼을 참조하도록 설정할 수 있습니다.
@Transient
    해당 필드가 영속성 컨텍스트에 저장되지 않는 임시 필드임을 표시합니다.
    데이터베이스에 매핑되지 않으며, 엔티티의 상태에 영향을 주지 않습니다.
```

---
#
---

GeneratedValue's strategy <br>
---

```
GenerationType.AUTO
    기본값으로, 하이버네이트가 데이터베이스에 맞게 가장 적합한 전략을 선택합니다.
    일반적으로 IDENTITY, SEQUENCE, TABLE 중 하나를 선택하여 사용됩니다.
GenerationType.IDENTITY
    주키 값을 데이터베이스에 의해 자동으로 생성합니다.
    일반적으로 데이터베이스의 AUTO_INCREMENT(또는 IDENTITY) 컬럼을 사용합니다.
    MySQL, PostgreSQL 등에서 자주 사용됩니다.
    GenerationType.SEQUENCE
    데이터베이스의 시퀀스(sequence)를 사용하여 주키 값을 생성합니다.
    데이터베이스마다 시퀀스를 지원하는 경우에 사용됩니다.
    Oracle과 같은 데이터베이스에서 주로 사용됩니다.
GenerationType.TABLE
    키 생성 전용 테이블을 사용하여 주키 값을 생성합니다.
    키 생성용 테이블에서 값을 가져와 사용합니다.
    데이터베이스가 시퀀스를 지원하지 않거나 다중 서버 환경에서 사용될 때 유용합니다.
GenerationType.SEQUENCE, GenerationType.TABLE 조합
    일부 데이터베이스는 시퀀스를 지원하지 않거나, 다중 서버 환경에서 시퀀스의 동기화 문제가 발생할 수 있습니다. 이 경우, 시퀀스와 키 생성용 테이블을 조합하여 사용합니다.

```

---
#
---

함수명명 규칙
---
> BookRepository <br>
```
//    // 책 이름으로 책 검색
//    List<Book> findByBookname(String bookname);
//    // 출판사로 책 검색
//    List<Book> findByPublisher(String publisher);
//    // ISBN으로 책 검색
//    Book findByIsbn(String isbn);
//    // 책 코드 범위로 책 검색
//    List<Book> findByBookcodeBetween(long start, long end);
//
//    // 책 이름이나 출판사로 책 검색
//    List<Book> findByBooknameOrPublisher(String bookname, String publisher);
//
//    // 책 이름이나 출판사로 책 검색, 정렬하여 가져오기
//    List<Book> findByBooknameOrPublisherOrderByBooknameAsc(String bookname, String publisher);
//
//    // 책 이름에 특정 문자열이 포함된 책 검색
//    List<Book> findByBooknameContaining(String keyword);
//
//    // 출판사가 특정 문자열로 시작하는 책 검색
//    List<Book> findByPublisherStartingWith(String prefix);
//
//    // 책 이름으로 책 개수 가져오기
//    int countByBookname(String bookname);
//
//    // 출판사로 책 개수 가져오기
//    int countByPublisher(String publisher);
//
//    // 책 이름으로 책 삭제
//    void deleteByBookname(String bookname);
```

---
#
---


관계 Mapping 관련 애노테이션
---
> <br>

```
@OneToOne:
    양방향 또는 단방향의 일대일 관계를 매핑합니다.
    mappedBy 속성을 사용하여 연관된 엔티티의 필드를 지정할 수 있습니다.

@OneToMany:
    일대다 관계를 매핑합니다.
    주로 @JoinColumn을 사용하여 외래 키를 매핑하며, mappedBy 속성을 사용하여 양방향 매핑을 지정할 수 있습니다.
@ManyToOne:
    다대일 관계를 매핑합니다.
    주로 @JoinColumn을 사용하여 외래 키를 매핑하며, 양방향 매핑을 위해 mappedBy 속성을 사용할 수 있습니다.
@ManyToMany:
    다대다 관계를 매핑합니다.
    중간 테이블이 필요하며, @JoinTable을 사용하여 중간 테이블을 지정하고 각 엔티티의 외래 키를 매핑합니다.
@JoinColumn:
    외래 키를 매핑할 때 사용됩니다. 주로 @ManyToOne 또는 @OneToOne 관계에서 사용되며, 해당 엔티티의 외래 키를 지정합니다.
@JoinTable:
    다대다 관계에서 사용되며, 중간 테이블을 매핑할 때 사용됩니다. @ManyToMany 관계에서 사용되며, 중간 테이블과 각 엔티티의 외래 키를 매핑합니다.
@PrimaryKeyJoinColumn:
    상속 관계 매핑 시 부모 엔티티의 기본 키 컬럼을 자식 엔티티의 기본 키 컬럼으로 사용할 때 사용됩니다.
@JoinColumnOrFormula:
    외래 키 매핑을 위해 일반적인 @JoinColumn과 SQL 수식을 사용할 때 선택할 수 있게 해줍니다.
@MapKeyJoinColumn:
    @MapKey 애노테이션과 함께 @OneToMany 또는 @ManyToMany 매핑에서 사용되며, 맵의 키를 위한 외래 키를 지정합니다.
@OrderColumn:
    순서가 있는 컬렉션인 List나 Map을 매핑할 때 사용됩니다. 순서를 유지하기 위한 컬럼을 지정합니다.
```


JPQL 정리
---
>  <br>

|-|
|-|
|[JPQL](https://hstory0208.tistory.com/entry/JPA-JPQL%EC%9D%B4%EB%9E%80-%EC%82%AC%EC%9A%A9%EB%B0%A9%EB%B2%95-%EA%B8%B0%EB%B3%B8-%EB%AC%B8%EB%B2%95-%EC%B4%9D-%EC%A0%95%EB%A6%AC)|
|[JPQL](https://jang8584.tistory.com/282)|

---
#
---

복합키 구성
---
|-|
|-|
|[바로가기](https://woo-chang.tistory.com/43)|




