package domain.contracts;

import domain.clients.Client;

import java.util.Date;

public class Contract {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Client client;

    public Contract() {

    }

    public Contract(Long id, Date startDate, Date endDate, Client client) {
        this.id = id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
