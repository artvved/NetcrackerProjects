package domain.contracts;

import domain.clients.Client;

import java.util.Date;

public class WiredInternetContract extends Contract {
    private int maxInternetSpeed;

    public WiredInternetContract() {
    }

    public WiredInternetContract(Long id, int number, Date startDate, Date endDate, Client client, int maxInternetSpeed) {
        super(id, number, startDate, endDate, client);
        this.maxInternetSpeed = maxInternetSpeed;
    }

    public int getMaxInternetSpeed() {
        return maxInternetSpeed;
    }

    public void setMaxInternetSpeed(int maxInternetSpeed) {
        this.maxInternetSpeed = maxInternetSpeed;
    }
}
