package domain.clients;

import domain.clients.util.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * @author artvved
 */
@Builder
public class Client {

    private Long id;
    private String firstName;
    private String lastName;        //fio
    private String patronymic;
    private Gender gender;
    private LocalDate birthDate;
    private Long passport;



    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String patronymic, Gender gender, LocalDate birthDate, Long passport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.passport = passport;
    }
    public Client(Long id, String firstName, String lastName,  Gender gender, LocalDate birthDate, Long passport) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassport() {
        return passport;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }

    public int getAge(){

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(passport, client.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}
