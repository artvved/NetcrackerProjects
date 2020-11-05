package repository;

import domain.contracts.Contract;

import java.util.Comparator;

public class BubbleRepositorySorter implements IRepositorySorter<Contract>{
    @Override
    public Contract[] sort(Contract[] contracts, int occupancy, Comparator<Contract> comparator) {
        for (int i = 0; i < occupancy; i++) {
            for (int j = 0; j < occupancy - i-1; j++) {
                if (comparator.compare(contracts[j], contracts[j + 1]) > 0){
                    Contract tmp=contracts[j];
                    contracts[j]=contracts[j+1];
                    contracts[j+1]=tmp;
                }
            }
        }
        return contracts;
    }



}
