package domain.contracts;

import domain.clients.Client;

import java.time.LocalDate;
import java.util.Date;

public class CellularCommunicationContract extends Contract {
    private int minutes;
    private int megabytes;
    private int sms;

    public CellularCommunicationContract() {

    }

    public CellularCommunicationContract(Long id, int number, LocalDate startDate, LocalDate endDate, Client client, int minutes, int megabytes, int sms) {
        super(id,number, startDate, endDate, client);
        this.minutes = minutes;
        this.megabytes = megabytes;
        this.sms = sms;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMegabytes() {
        return megabytes;
    }

    public void setMegabytes(int megabytes) {
        this.megabytes = megabytes;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }
}
