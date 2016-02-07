package tdd.vendingMachine;

import com.google.common.collect.Ordering;
import tdd.vendingMachine.validation.exception.CantGiveTheChangeException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static tdd.vendingMachine.util.NumberUtils.isEqualByComparingTo;

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

    public List<Coin> getAsList() {
        return coins.entrySet()
            .stream()
            .flatMap(e -> createCoinStream(e.getKey(), e.getValue()))
            .collect(toList());
    }

    private Stream<Coin> createCoinStream(Coin coin, int numberOfCoins) {
        return IntStream.range(0, numberOfCoins).mapToObj(i -> coin);
    }

    public void transferAllCoinsTo(CoinContainer coinOutputTry) {
        getAsList().forEach(coinOutputTry::add);
        coins.clear();
    }

    public void transferCoinsByAmountTo(BigDecimal amount, CoinContainer coinsDestination) {
        removeCoinsByAmount(amount).forEach(coinsDestination::add);
    }

    private Collection<Coin> removeCoinsByAmount(BigDecimal amount) {
        Map<Coin, Integer> bestMatch = new HashMap<>();
        BigDecimal bestMatchAmount = BigDecimal.ZERO;

        List<Coin> possibleCoinsInDescendingOrder = Ordering.from(Coin.DESCENDING_VALUE_ORDER).sortedCopy(coins.keySet());

        for (Coin coin : possibleCoinsInDescendingOrder) {
            int coinCount = coins.get(coin);

            int neededCount = amount.subtract(bestMatchAmount).divideToIntegralValue(coin.getValue()).intValueExact();
            int actualCount = neededCount >= coinCount ? coinCount : neededCount;

            bestMatch.put(coin, actualCount);
            bestMatchAmount = bestMatchAmount.add(coin.getValue().multiply(new BigDecimal(actualCount)));

            if (isEqualByComparingTo(bestMatchAmount, amount)) {
                break;
            }
        }

        if (!isEqualByComparingTo(bestMatchAmount, amount)) {
            throw new CantGiveTheChangeException();
        }

        return removeCoins(bestMatch);
    }

    private Collection<Coin> removeCoins(Map<Coin, Integer> coinsToRemove) {
        return coinsToRemove.entrySet().stream()
            .flatMap(e -> {
                Coin coin = e.getKey();
                int count = e.getValue();

                coins.merge(coin, count, (currentCount, subtrahend) -> currentCount - subtrahend);
                return createCoinStream(coin, count);
            })
            .collect(toList());
    }
}
