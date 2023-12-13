package main.com.kilchevski;

import java.util.*;

public class PuttingIntoPractice {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println("=======================================================================================");
        System.out.println("1. Найти все транзакции за 2011 год и отсортировать их по сумме (от меньшей к большей).");

        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .forEach(System.out::println);

        System.out.println("=======================================================================================");
        System.out.println("2. Вывести список неповторяющихся городов, в которых работают трейдеры.");

        transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);

        System.out.println("=======================================================================================");
        System.out.println("3. Найти всех трейдеров из Кембриджа и отсортировать их по именам.");

        transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);

        System.out.println("=======================================================================================");
        System.out.println("4. Вернуть строку со всеми именами трейдеров, отсортированными в алфавитном порядке.");

        transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .map(StringBuilder::new)
                .reduce((sb, sb1) -> sb.append(", ").append(sb1)) // or simple StringBuilder::append (without delimiter)
                .ifPresent(System.out::println);

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Затраты времени при количестве эелементов String 100_000 через String и StringBuilder:");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(String.valueOf(i));
        }

        long time1 = System.currentTimeMillis();

        list.stream()
                .map(StringBuilder::new)
                .reduce((sb, sb1) -> sb.append(", ").append(sb1));

        long time2 = System.currentTimeMillis();

        System.out.println(" - StringBuilder::append. Time: " + (time2 - time1));

        long time3 = System.currentTimeMillis();

        list.stream()
                .reduce(String::concat);
        long time4 = System.currentTimeMillis();

        System.out.println(" - String::concat. Time: " + (time4 - time3));

        System.out.println("=======================================================================================");
        System.out.println("5. Выяснить, существует ли хоть один трейдер из Милана.");

        boolean isMilanTraderExists = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        System.out.println("   Результат: " + isMilanTraderExists);

        System.out.println("=======================================================================================");
        System.out.println("6. Вывести суммы всех транзакций трейдеров из Кембриджа.");

        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(tr -> System.out.println(tr.getValue()));

        System.out.println("=======================================================================================");
        System.out.println("7. Какова максимальная сумма среди всех транзакций?");

        transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()
                .ifPresent(System.out::println);

        System.out.println("=======================================================================================");
        System.out.println("8. Найти транзакцию с минимальной суммой.");

        transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue))
                .ifPresent(System.out::println);

        System.out.println("=======================================================================================");
    }
}