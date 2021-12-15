package com.example.tomcat;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultImpl implements Result {

    private String result;

    public ResultImpl(String strOfNumbers) {
        Map<Boolean, List<Integer>> map = Arrays.stream(strOfNumbers.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.partitioningBy((num -> num % 2 == 0)));

        result = "Even: " + map.get(true).size() + " Odd: " + map.get(false).size();
    }

    public ResultImpl(Integer[] numbers) {
        int even = 0;
        int odd = 0;

        for (int number : numbers) {
            if (number % 2 == 0) {
                even++;
            } else {
                odd++;
            }

            result = "Even: " + even + " Odd: " + odd;
        }

    }

    public ResultImpl(List<Integer> numbers) {
        Map<Boolean, List<Integer>> map = numbers.stream()
                .collect(Collectors.partitioningBy((num -> num % 2 == 0)));

        result = "Even: " + map.get(true).size() + " Odd: " + map.get(false).size();
    }

    @Override
    public String getResult() {
        return String.valueOf(result);
    }
}
