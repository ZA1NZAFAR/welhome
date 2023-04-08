package fr.efrei.database_service.tools;

import java.math.BigDecimal;
import java.sql.Date;

public class Tools {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    //check long
    public static boolean isNullOrEmpty(Long aLong) {
        return aLong == null || aLong == 0;
    }

    public static boolean isNullOrEmpty(float aFloat) {
        return aFloat == 0 || aFloat == 0.0;
    }

    public static boolean isNullOrEmpty(Date date) {
        return date == null;
    }

    public static boolean isNullOrEmpty(boolean bool) {
        return bool;
    }

    public static boolean isNullOrEmpty(BigDecimal price) {
        return price == null || price.compareTo(BigDecimal.ZERO) == 0;
    }
}
