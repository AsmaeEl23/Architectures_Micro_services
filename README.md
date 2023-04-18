<center><img src="images/ensetLOGO.png">
<h1>Architectures Micro-services <br> Spring Cloud<br></h1>
<h3>Manage bank accounts using Micro service.</h3>
<p><br><br>Asmae EL HYANI<br> Distributed System & Artificial Intelligence Master’s<br> ENSET Mohammedia</p>
</center>
<br><br><br>
<h2>Introduction</h2>
<p>a Spring Boot project with web dependencies, Spring Data JPA, H2, Lombok</p>
<center><img src="images/img.png"></center>
<p>First, creat spring project with Spring Data JPA, Spring web, H2 Database, Lombok, Spring for GraphQL, Swagger dependencies</p>
<ol type="1">
    <h3><li>JPA Bank Account</li></h3>
    <pre>@Entity
        @Data
        @NoArgsConstructor @AllArgsConstructor @Builder
        public class BankAccount {
            @Id
            private String id;
            private Date createdAt;
            private Double balance;
            private String currency;
            @Enumerated(EnumType.STRING)
            private AccountType type;
        }
    </pre>
    <h3><li>BankAccountRepository Interface</li></h3>
    <pre>
        public interface BankAccountRepository extends JpaRepository<'BankAccount,String> {
        }
    </pre>
    <h3><li>BankAccountServiceApplication</li></h3>
    <pre>
        @SpringBootApplication
        public class BankAccountServiceApplication {
            public static void main(String[] args) {
                SpringApplication.run(BankAccountServiceApplication.class, args);
            }
            @Bean
            CommandLineRunner start(BankAccountRepository bankAccountRepository){
                return args->{
                    for(int i=0;i<10;i++){
                        BankAccount bankAccount =BankAccount.builder()
                                .id(UUID.randomUUID().toString())
                                .type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT:AccountType.SAVING_ACCOUNT)
                                .balance(10000+Math.random()*9000)
                                .createdAt(new Date())
                                .currency("MAD")
                                .build();
                        bankAccountRepository.save(bankAccount);
                    }
                };
            }
        }
    </pre>
    <h3><li>Test</li></h3>
    <img src="images/img1.PNG">
    <h3><li>Restful web service(AccountRestController)</li></h3>
    <pre>
        @RestController
        public class AccountRestControler {
            private BankAccountRepository bankAccountRepository;
            public AccountRestControler(BankAccountRepository bankAccountRepository){
                this.bankAccountRepository=bankAccountRepository;
            }
            @GetMapping("/bankAccounts")
            public List<'BankAccount> bankAccounts(){
                return bankAccountRepository.findAll();
            }
            @GetMapping("/bankAccounts/{id}")
            public BankAccount bankAccount(@PathVariable String id){
                return bankAccountRepository.findById(id)
                        .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
            }
            @PostMapping("/bankAccounts")
            public BankAccount save(@RequestBody BankAccount bankAccount){
                if(bankAccount.getId()==null) bankAccount.setId(UUID.randomUUID().toString());
                return bankAccountRepository.save(bankAccount);
            }
            @PutMapping("/bankAccounts/{id}")
            public BankAccount update(@PathVariable String id,@RequestBody BankAccount bankAccount){
                BankAccount account=bankAccountRepository.findById(id).orElseThrow();
                if(bankAccount.getBalance()!=null)account.setBalance(bankAccount.getBalance());
                if(bankAccount.getCreatedAt()!=null)account.setCreatedAt(bankAccount.getCreatedAt());
                if(bankAccount.getType()!=null)account.setType(bankAccount.getType());
                if(bankAccount.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
                return bankAccountRepository.save(account);
            }
            @DeleteMapping("/bankAccounts")
            public void delete(@PathVariable String id){
                bankAccountRepository.deleteById(id);
            }
        }
    </pre>
    <h3><li>Test the web micro-service using Browser</li></h3>
    <p>Show all Bank accounts</p>
    <img src="images/img2.PNG">
    <p>Show Bank account by ID</p>
    <img src="images/img3.PNG">
    <h3><li>Test the web micro-service using a Postman client</li></h3>
    <ul type="circle">
        <h4><li>Get all bank accounts</li></h4>
            <img src="images/postman.PNG">
        <pre> @GetMapping("/bankAccounts")
        public List<'BankAccount> bankAccounts(){
            return bankAccountRepository.findAll();
        }</pre>
        <h4><li>Get bank account by ID</li></h4>
            <img src="images/postmanGetByID.PNG">
        <pre>@GetMapping("/bankAccounts/{id}")
        public BankAccount bankAccount(@PathVariable String id){
            return bankAccountRepository.findById(id)
                    .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
        }</pre>
        <h4><li>Save bank account</li></h4>
            <img src="images/postmanSave.PNG">
        <pre>@PostMapping("/bankAccounts")
        public BankAccount save(@RequestBody BankAccount bankAccount){
            if(bankAccount.getId()==null) bankAccount.setId(UUID.randomUUID().toString());
            return bankAccountRepository.save(bankAccount);
        }</pre>
        <h4><li>Update account</li></h4>
            <img src="images/postmanUpdate.PNG">
        <pre>@PutMapping("/bankAccounts/{id}")
        public BankAccount update(@PathVariable String id,@RequestBody BankAccount bankAccount){
            BankAccount account=bankAccountRepository.findById(id).orElseThrow();
            if(bankAccount.getBalance()!=null)account.setBalance(bankAccount.getBalance());
            if(bankAccount.getCreatedAt()!=null)account.setCreatedAt(bankAccount.getCreatedAt());
            if(bankAccount.getType()!=null)account.setType(bankAccount.getType());
            if(bankAccount.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
            return bankAccountRepository.save(account);
        }</pre>
        <h4><li>Delete bank account by ID</li></h4>
        <p>Before</p>
        <img src="images/delete.png">
        <p>Delete</p>
        <img src="images/postmanDelete.png">
        <p>After</p>
        <img src="images/delete2.png">
        <pre>@DeleteMapping("/bankAccounts")
        public void delete(@PathVariable String id){
            bankAccountRepository.deleteById(id);
        }</pre>
    </ul>
<h3><li>Swagger</li></h3>
<img src="images/swagger1.png"/>
<p>Add bank account using post method </p>
<img src="images/swgPost1.png">
<img src="images/swgPost2.png">
<p>Get the bank account that already added</p>
<img src="images/swgGet1.png">
<p>We can try other methods in the same way </p>

<h3><li>Spring Data Rest</li></h3>
<p>Add Spring data rest dependency, to create a generic web service</p>
<img src="images/swagger1.png"/>
<p>Add bank account using post method </p>

</ol>