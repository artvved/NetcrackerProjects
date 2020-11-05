package repository;

import domain.contracts.Contract;

import java.util.Comparator;

public interface IRepositorySorter<Contract> {
    public Contract[] sort(domain.contracts.Contract[] contracts, int occupancy, Comparator<domain.contracts.Contract> comparator);
}
