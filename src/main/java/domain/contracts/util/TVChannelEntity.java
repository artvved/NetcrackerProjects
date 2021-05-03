package domain.contracts.util;

import domain.contracts.Contract;
import lombok.Builder;

@Builder
public class TVChannelEntity {
    private Long id;
    private TVChannel name;
    private Contract contract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TVChannel getName() {
        return name;
    }

    public void setName(TVChannel name) {
        this.name = name;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
