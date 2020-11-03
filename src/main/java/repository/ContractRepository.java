package repository;

import domain.contracts.Contract;


import java.util.Arrays;
import java.util.Optional;


public class ContractRepository {
    private Contract[] contracts;
    private int pointer=0;

    /**
     * ContractRepository constructor
     * @param contracts  array of contracts
     */
    public ContractRepository(Contract[] contracts) {
        this.contracts = contracts;
    }

    /**
     * Method which provides opportunity to add new contract to repository.
     * Method increases length of repository if needed.
     * @param contract contract
     */
    public void addById(Contract contract) {

        if (pointer >= contracts.length) {
            contracts = Arrays.copyOf(contracts, contracts.length * 2);
        }
        contracts[(int) (long) pointer] = contract;
        pointer++;
    }

    /**
     * Method finds contract in repository using given id.
     * @param id id of the contract which we want to find in repository
     * @return Optional of Contract which we found or empty Optional
     */
    public Optional<Contract> getById(Long id) {
        for (int i = 0; i <= pointer; i++) {
                Contract cur=contracts[i];
            if (cur.getId().equals(id))
                return Optional.of(cur);
        }
        return Optional.empty();
    }

    /**
     * Returns all contracts in repository
     * @return all contracts in repository
     */
    public Contract[] getAll(){
        return contracts;
    }

    /**
     * Method which provides opportunity to delete contract in repository using given id
     * @param id id of the contract which we want to delete in repository
     */
    public void deleteById(Long id) {
        if (id < contracts.length && id >= 0)
            contracts[(int) (long) id] = null;
    }

}
