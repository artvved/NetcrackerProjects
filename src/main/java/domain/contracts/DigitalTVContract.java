package domain.contracts;

import domain.clients.Client;
import domain.contracts.util.TVChannel;
import domain.contracts.util.TVChannelEntity;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DigitalTVContract extends Contract {
    private List<TVChannelEntity> channelsPackage;

    public DigitalTVContract() {
    }

    public DigitalTVContract(List<TVChannelEntity> channelsPackage) {
        this.channelsPackage = channelsPackage;
    }

    public DigitalTVContract(Long id, int number, LocalDate startDate, LocalDate endDate, Client client, List<TVChannelEntity> channelsPackage) {
        super(id, number, startDate, endDate, client);
        this.channelsPackage = channelsPackage;
    }



    public List<TVChannelEntity> getChannelsPackage() {
        return channelsPackage;
    }

    public void setChannelsPackage(List<TVChannelEntity> channelsPackage) {
        this.channelsPackage = channelsPackage;
    }
}
