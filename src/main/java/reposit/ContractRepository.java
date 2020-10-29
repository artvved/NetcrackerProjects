package reposit;

import domain.contracts.Contract;

import java.util.Arrays;
import java.util.Optional;


public class ContractRepository {
    private Contract[] contracts;

    public void addById( Contract contract) {
        Long id=contract.getId();
        if (id >= contracts.length) {
            contracts = Arrays.copyOf(contracts, contracts.length * 2);
        }
        contracts[(int) (long) id] = contract;
    }

    public Optional<Contract> getById(Long id) {
        if (id >= contracts.length || id < 0
                || contracts[(int) (long) id] == null) {
            return Optional.empty();
        } else
            return Optional.of(contracts[(int) (long) id]);
    }

    public void deleteById(Long id){
        if (id < contracts.length && id >= 0)
            contracts[(int)(long)id]=null;
    }

}
