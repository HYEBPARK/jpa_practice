package start;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})}) // 유니크 제약조건 추가
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) // 제약조건 추가, 회원 이름은 필수로 입력되어야하고 10자를 초과하면 안 된다.
    private String username;

    private Integer age;


    // enum을 사용하려면 @Enumerated 으로 매핑해야함
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // @Temporal 을 사용해서 날짜 타입은 매핑해야함
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // CLOB, BLOB 매핑 가능
    @Lob
    private String description;


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

}
