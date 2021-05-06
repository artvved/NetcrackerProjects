package domain.contracts;

import domain.clients.Client;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

public class Contract {
    private Long id;
    private int number;
    private LocalDate startDate;
    private LocalDate endDate;
    private Client client;


    public Contract() {

    }

    public Contract(Long id, int number, LocalDate startDate, LocalDate endDate, Client client) {
        this.id = id;
        this.number = number;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
