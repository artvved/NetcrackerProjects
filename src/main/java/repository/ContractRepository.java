package repository;

import domain.contracts.Contract;

import java.util.Arrays;
import java.util.Optional;


public class ContractRepository {
    private Contract[] contracts;

    /**
     * ContractRepository constructor
     * @param contracts  array of contracts
     */
    public ContractRepository(Contract[] contracts) {
        this.contracts = contracts;
    }

    /**
     * Method which provides opportunity to add new contract to repository using its id.
     * Method increases length of repository if needed.
     * @param contract contract
     */
    public void addById(Contract contract) {
        Long id = contract.getId();
        while (id >= contracts.length) {
            contracts = Arrays.copyOf(contracts, contracts.length * 2);
        }
        contracts[(int) (long) id] = contract;
    }

    /**
     * Method finds contract in repository using given id.
     * @param id id of the contract which we want to find in repository
     * @return Optional of Contract which we found or empty Optional
     */
    public Optional<Contract> getById(Long id) {
        if (id >= contracts.length || id < 0
                || contracts[(int) (long) id] == null) {
            return Optional.empty();
        } else
            return Optional.of(contracts[(int) (long) id]);
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
