package com.company;

import java.util.List;

public record ParseInfo(
    String type,
    List<Integer> values,
    Integer numberOfValues
) {}

