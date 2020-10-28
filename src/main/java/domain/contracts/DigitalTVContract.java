package domain.contracts;

import domain.clients.Client;
import domain.contracts.util.TVChannel;

import java.util.Date;
import java.util.List;

public class DigitalTVContract extends Contract {
    private List<TVChannel> channelsPackage;

    public DigitalTVContract() {
    }

    public DigitalTVContract(Long id,int number, Date startDate, Date endDate, Client client, List<TVChannel> channelsPackage) {
        super(id, number, startDate, endDate, client);
        this.channelsPackage = channelsPackage;
    }

    public List<TVChannel> getChannelsPackage() {
        return channelsPackage;
    }

    public void setChannelsPackage(List<TVChannel> channelsPackage) {
        this.channelsPackage = channelsPackage;
    }
}
