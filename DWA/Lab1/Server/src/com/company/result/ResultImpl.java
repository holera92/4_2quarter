package com.company.result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultImpl implements Result {

    String result;

    public ResultImpl(String strOfNumbers) {
        var map = Arrays.stream(strOfNumbers.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.partitioningBy((num -> num % 2 == 0)));

        result = "Even: " + map.get(true).size() + " Odd: " + map.get(false).size();
    }

    public ResultImpl(Integer[] numbers) {
        var even = 0;
        var odd = 0;

        for (var number : numbers) {
            if (number % 2 == 0) {
                even++;
            } else {
                odd++;
            }

            result = "Even: " + even + " Odd: " + odd;
        }

    }

    public ResultImpl(List<Integer> numbers) {
        var map = numbers.stream()
                .collect(Collectors.partitioningBy((num -> num % 2 == 0)));

        result = "Even: " + map.get(true).size() + " Odd: " + map.get(false).size();
    }

    @Override
    public String getResult() {
        return String.valueOf(result);
    }
}
