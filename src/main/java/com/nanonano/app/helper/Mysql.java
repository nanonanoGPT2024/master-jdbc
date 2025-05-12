package com.nanonano.app.helper;

import org.springframework.stereotype.Component;

@Component
public class Mysql {

    public static String searching(String[] fields) {
        StringBuilder concatBuilder = new StringBuilder("AND CONCAT(");

        for (int i = 0; i < fields.length; i++) {
            concatBuilder.append("IFNULL(")
                    .append(fields[i])
                    .append(", '')");

            if (i < fields.length - 1) {
                concatBuilder.append(", ' ', ");
            }
        }

        concatBuilder.append(") LIKE '%test%'");
        return concatBuilder.toString();
    }

    public static String andString(String field, String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder concatBuilder = new StringBuilder();
        concatBuilder.append(" and ")
                .append(field)
                .append(" = '")
                .append(value)
                .append("'");
        return concatBuilder.toString();
    }

    public static String andInteger(String field, String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder concatBuilder = new StringBuilder();
        concatBuilder.append(" and ")
                .append(field)
                .append(" = ")
                .append(value)
                .append(" ");
        return concatBuilder.toString();
    }

       public static String moreThan(String field, String value) {
        if(value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder concatBuilder = new StringBuilder();
        concatBuilder.append(" and ")
        .append(field)
        .append(" >= '")
        .append(value)
        .append("' ");
        return concatBuilder.toString();
    }

    public static String lessThan(String field, String value) {
        if(value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder concatBuilder = new StringBuilder();
        concatBuilder.append(" and ")
        .append(field)
        .append(" <= '")
        .append(value)
        .append("' ");
        return concatBuilder.toString();
    }

}
