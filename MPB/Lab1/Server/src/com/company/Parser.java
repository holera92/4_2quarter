package com.company;

import java.util.Arrays;
import java.util.Collections;

public class Parser {

    public static final String REQUEST_PREFIX = "Rq";
    public static final String RESPONSE_PREFIX = "Rp";

    public static ParseInfo deserialize(String message) {
        return switch (message.substring(0, message.indexOf(':'))) {
            case REQUEST_PREFIX -> deserializeRequest(message);
            case RESPONSE_PREFIX -> deserializeResponse(message);
            default -> throw new IllegalArgumentException("Illegal message prefix");
        };

    }

    private static ParseInfo deserializeRequest(String response) {
        return new ParseInfo(
                response.substring(0, response.indexOf(':')),
                Collections.emptyList(),
                Integer.valueOf(response.substring(response.indexOf(':') + 1))
        );
    }

    private static ParseInfo deserializeResponse(String response) {
        return new ParseInfo(
                response.substring(0, response.indexOf(':')),
                Arrays.stream(
                        response.substring(response.indexOf('[') + 1, response.indexOf(']')).split(",")
                ).map(Integer::valueOf).toList(),
                0
        );
    }

    public static String serialize(ParseInfo info) {
        var builder = new StringBuilder();

        switch (info.type()) {
            case RESPONSE_PREFIX -> serializeResponse(info, builder);
            case REQUEST_PREFIX -> serializeRequest(info, builder);
            default -> throw new IllegalArgumentException("Illegal message prefix");
        }

        return builder.toString();
    }

    private static void serializeResponse(ParseInfo info, StringBuilder builder) {
        builder.append(info.type());
        builder.append(':');
        serializeList(info, builder);
    }

    private static void serializeRequest(ParseInfo info, StringBuilder builder) {
        builder.append(info.type());
        builder.append(':');
        builder.append(info.numberOfValues());
    }

    private static void serializeList(ParseInfo info, StringBuilder builder) {

        builder.append('[');


        for (int i = 0; i < info.values().size() - 1; i++) {
            builder.append(info.values().get(i));
            builder.append(',');
        }

        builder.append(info.values().get(info.values().size() - 1));

        builder.append(']');
    }

}
