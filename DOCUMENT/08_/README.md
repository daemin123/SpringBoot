# TX 순서대충

---
# MYBATIS  + JPA TX  
---


TxConfig
---
> - <br>

|-|-|
|-|-|
|-|-|

```
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
        (
                basePackages ="com.example.demo.domain.repository",
                transactionManagerRef = "jpaTransactionManager"
        )
public class TxConfig {


    @Autowired
    private DataSource dataSource2;

    // 기본 TransactionManager
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager2() {
        //System.out.println("TX dataSrouce2 : " + dataSource2.toString());
        return new DataSourceTransactionManager(dataSource2);
    }


    //    JPA TransactionManager Settings
    @Bean(name="jpaTransactionManager")
    public JpaTransactionManager  jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource2);

        return transactionManager;
    }

}
```

---
#
---


TxTestService
---
> - <br>

|-|-|
|-|-|
|-|-|

```
@Service
@Slf4j
public class TxTestService {

    @Autowired
    private MemoMapper memoMapper;

    @Transactional(rollbackFor = SQLException.class,transactionManager = "dataSourceTransactionManager")
    public void addMemoTx(MemoDto dto) throws Exception	 {
        log.info("MemoService's addMemoTx Call! ");
        memoMapper.insert(dto);//01 정상INSERT
        throw new SQLException();
    }

    //JPA REPOSITORY
    @Autowired
    private MemoRepository memoRepository;

    @Transactional(rollbackFor = SQLException.class,transactionManager = "jpaTransactionManager")
    public void addMemoTx2(MemoDto dto) throws Exception{
        log.info("MemoService's addMemoTx2 Call!");
        Memo memo = new Memo();
        memo.setId(dto.getId());
        memo.setText(dto.getText());
        memoRepository.save(memo);
        throw new SQLException();
    }
}
```

---
#
---


TxTestServiceTest
---
> - <br>

|-|-|
|-|-|
|-|-|

```
@SpringBootTest
class TxTestServiceTest {

    @Autowired
    private TxTestService txTestService;
    @Test
    void t2() throws Exception {
        txTestService.addMemoTx(new MemoDto(1,"TEST1"));
    }

    //JPA TX
    @Test
    void t3() throws Exception {
        txTestService.addMemoTx2(new MemoDto(1,"TEST1"));
    }

}
```

---
# PROPAGATION(STS PPT 참고할것)
---


TMP
---
> - <br>

|-|-|
|-|-|
|-|-|

```
-
```

---
#
---


TMP
---
> - <br>

|-|-|
|-|-|
|-|-|

```
-
```

---
#
---


TMP
---
> - <br>

|-|-|
|-|-|
|-|-|

```
-
```

---
#
---


TMP
---
> - <br>

|-|-|
|-|-|
|-|-|

```
-
```

---
#
---

