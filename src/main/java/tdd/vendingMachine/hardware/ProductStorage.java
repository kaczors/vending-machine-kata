package tdd.vendingMachine.hardware;

import tdd.vendingMachine.product.Product;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ProductStorage {

    private Map<Integer, Shelf> shelvesByNumber = newHashMap();

    public void addProduct(int shelfNumber, Product product) {
        shelvesByNumber.putIfAbsent(shelfNumber, Shelf.empty());
        shelvesByNumber.get(shelfNumber).addProduct(product);
    }

    public Shelf getShelf(Integer shelfNumber){
        return shelvesByNumber.getOrDefault(shelfNumber, Shelf.empty());
    }

}
