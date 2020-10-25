package domain;

import java.util.Date;

/**
 * @author artvved
 */
public class Client {

    private Long id;
    private String firstName;
    private String lastName;        //fio
    private String patronymic;
    private Gender gender;
    private Date birthDate;
    private int passport;



    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String patronymic, Gender gender, Date birthDate, int passport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.passport = passport;
    }
    public Client(Long id, String firstName, String lastName,  Gender gender, Date birthDate, int passport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.passport = passport;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPassport() {
        return passport;
    }

    public void setPassport(int passport) {
        this.passport = passport;
    }
}
