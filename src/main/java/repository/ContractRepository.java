package repository;

import domain.contracts.Contract;


import java.util.*;
import java.util.function.Predicate;


public class ContractRepository {
    private Contract[] contracts;
    private IRepositorySorter<Contract> sorter;
    /**
     * index of next free field in array of contracts
     */
    private int pointer = 0;

    /**
     * @return count of occupied places in array of contracts
     */
    public int getOccupancy() {
        return pointer;
    }

    /**
     * ContractRepository constructor
     *
     * @param contracts array of contracts
     */
    public ContractRepository(Contract[] contracts, IRepositorySorter<Contract> sorter) {
        this.contracts = contracts;
        this.sorter = sorter;
    }

    public ContractRepository() {
    }

    /**
     * Method which provides opportunity to add new contract to repository.
     * Method increases length of repository if needed.
     *
     * @param contract contract
     */
    public void add(Contract contract) {

        if (pointer >= contracts.length) {
            contracts = Arrays.copyOf(contracts, contracts.length * 2);
        }
        contracts[(int) (long) pointer] = contract;
        pointer++;
    }

    /**
     * Method finds contract in repository using given id.
     *
     * @param id id of the contract which we want to find in repository
     * @return Optional of Contract which we found or empty Optional
     */
    public Optional<Contract> getById(Long id) {
        for (int i = 0; i < pointer; i++) {
            Contract cur = contracts[i];
            if (cur.getId().equals(id))
                return Optional.of(cur);
        }
        return Optional.empty();
    }

    /**
     * Returns all contracts in repository
     *
     * @return all contracts in repository
     */
    public Contract[] getAll() {
        return contracts;
    }

    /**
     * Method which provides opportunity to delete contract in repository using given id
     *
     * @param id id of the contract which we want to delete in repository
     */
    public void deleteById(Long id) {
        for (int i = 0; i < pointer; i++) {
            Contract cur = contracts[i];
            if (cur.getId().equals(id)) {
                shift(i);
            }
        }
    }

    /**
     * finds repository elements which satisfy the search condition
     *
     * @param predicate predicate usage is to differ search conditions
     * @return new repository with contracts which satisfy the search condition
     */
    public ContractRepository find(Predicate<Contract> predicate) {
        ContractRepository contractRepo = new ContractRepository(new Contract[pointer], sorter);
        for (int i = 0; i < pointer; i++) {
            Contract cur = contracts[i];
            if (predicate.test(cur)) {
                contractRepo.add(cur);
            }
        }
        return contractRepo;
    }

    /**
     * sorts repository in certain way using certain comparison pattern
     *
     * @param comparator  comparator is used to transfer certain criterion of comparison
     */
    public void sort(Comparator<Contract> comparator) {
        sorter.sort(contracts, pointer, comparator);
    }

    private void shift(int pos) {
        int innerPointer = pointer;
        if (pointer >= contracts.length) {
            innerPointer--;
        }
        for (int i = pos; i < innerPointer; i++) {
            contracts[i] = contracts[i + 1];
        }
        contracts[innerPointer] = null;
        pointer--;

    }

}
