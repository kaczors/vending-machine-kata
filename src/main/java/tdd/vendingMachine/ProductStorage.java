package tdd.vendingMachine;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ProductStorage {

    private Map<Integer, Shelf> shelfsByNumber = newHashMap();

    public void addProduct(int shelfNumber, Product product) {
        shelfsByNumber.putIfAbsent(shelfNumber, Shelf.empty());
        shelfsByNumber.get(shelfNumber).addProduct(product);
    }

    public Shelf getShelf(Integer shelfNumber){
        return shelfsByNumber.getOrDefault(shelfNumber, new Shelf());
    }

}
