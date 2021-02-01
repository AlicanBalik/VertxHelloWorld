package com.example.lastdemo.infrastructure.persistence;

import com.example.lastdemo.domain.user.model.User;
import com.example.lastdemo.domain.user.model.UserRepository;
import com.example.lastdemo.infrastructure.persistence.statement.Statement;
import com.example.lastdemo.infrastructure.persistence.statement.UserStatements;
import com.example.lastdemo.infrastructure.persistence.utils.ParserUtils;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJDBC extends JDBCRepository implements UserRepository {

    private final JDBCClient jdbcClient;
    private final UserStatements userStatements;

    public UserRepositoryJDBC(JDBCClient jdbcClient, UserStatements userStatements) {
        this.jdbcClient = jdbcClient;
        this.userStatements = userStatements;
    }

    @Override
    public Single<User> store(User user) {
        Statement<JsonArray> createUserStatement = userStatements.create(user);
        return jdbcClient
                .rxUpdateWithParams(createUserStatement.sql(), createUserStatement.params())
                .map(updateResult -> user);
    }

    @Override
    public Single<Optional<User>> findUserByEmail(String email) {
        Statement<JsonArray> findByEmailStatement = userStatements.findByEmail(email);
        return jdbcClient
                .rxQueryWithParams(findByEmailStatement.sql(), findByEmailStatement.params())
                .map(ParserUtils::toUserOptional);
    }

    @Override
    public Single<Long> countByEmail(String email) {
        Statement<JsonArray> countByEmailStatement = userStatements.countBy("email", email);
        return jdbcClient
                .rxQueryWithParams(countByEmailStatement.sql(), countByEmailStatement.params())
                .map(this::getCountFromResultSet);
    }

    @Override
    public Single<Optional<User>> findById(String id) {
        Statement<JsonArray> findByIdStatement = userStatements.findById(id);
        return jdbcClient
                .rxQueryWithParams(findByIdStatement.sql(), findByIdStatement.params())
                .map(ParserUtils::toUserOptional);
    }

}
