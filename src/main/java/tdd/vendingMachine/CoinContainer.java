package tdd.vendingMachine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

class CoinContainer {

    private final Map<Coin, Integer> coins = new HashMap<>();

    public void add(Coin coin) {
        coins.putIfAbsent(coin, 0);
        coins.put(coin, coins.get(coin) + 1);
    }

    public BigDecimal getTotalAmount() {
        return coins.entrySet()
            .stream()
            .map(e -> e.getKey().getValue().multiply(BigDecimal.valueOf(e.getValue())))
            .reduce(ZERO, BigDecimal::add);
    }

    public List<Coin> getAsList(){
        return coins.entrySet()
            .stream()
            .flatMap(e -> createCoinStream(e.getKey(), e.getValue()))
            .collect(toList());
    }

    private Stream<Coin> createCoinStream(Coin coin, int numberOfCoins){
        return IntStream.range(0, numberOfCoins).mapToObj(i -> coin);
    }
}
