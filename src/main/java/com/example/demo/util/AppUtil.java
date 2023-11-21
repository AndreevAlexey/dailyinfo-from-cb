package com.example.demo.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Arrays;

@UtilityClass
public class AppUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";


    public static <E extends Enum<E>> boolean checkRequestParam(Class<E> enumObject, String type) {
        for(Enum<E> val: enumObject.getEnumConstants()) {
            if (val.name().equalsIgnoreCase(type))
                return true;
        }
        return false;
    }
    public static <E extends Enum<E>> String makeErrorMessage(String param, E[] values) {
        return
                "Неверно указан параметр " + param + "! Возможные значения: " + Arrays.toString(values);
    }

    public static boolean checkDatePeriod(LocalDate periodFrom, LocalDate periodTo) {
        return
                periodFrom != null && periodTo != null && periodFrom.isBefore(periodTo.plusDays(1));
    }

    public static boolean checkString(String str) {
        return
                str != null && !str.isEmpty() && !str.isBlank();
    }

}
