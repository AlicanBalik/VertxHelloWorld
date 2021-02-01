package com.example.lastdemo.infrastructure.persistence.utils;

import com.example.lastdemo.domain.user.model.User;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ParserUtils {

    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private ParserUtils() {
    }

    public static Optional<User> toUserOptional(ResultSet resultSet) {
        return parseResultSet(resultSet, ParserUtils::getUserOptionalFromResultSet, Optional::empty);
    }

    public static User toUser(ResultSet resultSet) {
        return parseResultSet(resultSet, ParserUtils::getUserFromResultSet, () -> null);
    }

    private static Optional<User> getUserOptionalFromResultSet(ResultSet resultSet) {
        return Optional.of(getUserFromResultSet(resultSet));
    }

    private static User getUserFromResultSet(ResultSet resultSet) {
        JsonObject row = resultSet.getRows().get(0);
        return new User(row.getString("ID"), row.getString("PASSWORD"), row.getString("EMAIL"));
    }

    private static <T> T emptyIfNoResult(
            ResultSet resultSet, Supplier<T> emptySupplier, Supplier<T> supplier) {
        if (resultSet.getRows().isEmpty()) {
            return emptySupplier.get();
        } else {
            return supplier.get();
        }
    }

    private static <T> T parseResultSet(
            ResultSet resultSet, Function<ResultSet, T> function, Supplier<T> supplier) {
        if (!resultSet.getRows().isEmpty()) {
            return function.apply(resultSet);
        }
        return supplier.get();
    }

    public static String toTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(dateTimeFormatter) : null;
    }

    private static LocalDateTime fromTimestamp(String timestamp) {
        return timestamp != null ? LocalDateTime.parse(timestamp, dateTimeFormatter) : null;
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime != null
                ? localDateTime.format(DateTimeFormatter.ofPattern(pattern))
                : null;
    }
}
