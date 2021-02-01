package com.example.lastdemo.application;

import com.example.lastdemo.domain.user.exception.EmailAlreadyExistsException;
import com.example.lastdemo.domain.user.exception.UserNotFoundException;
import com.example.lastdemo.domain.user.model.ModelValidator;
import com.example.lastdemo.domain.user.model.NewUser;
import com.example.lastdemo.domain.user.model.User;
import com.example.lastdemo.domain.user.model.UserRepository;
import com.example.lastdemo.domain.user.service.UserService;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public final class UserServiceImpl extends ApplicationService implements UserService {

    private final UserRepository userRepository;
    private final ModelValidator modelValidator;

    public UserServiceImpl(UserRepository userRepository, ModelValidator modelValidator) {
        this.userRepository = userRepository;
        this.modelValidator = modelValidator;
    }

    @Override
    public Single<User> create(NewUser newUser) {
        modelValidator.validate(newUser);
        User user = new User(UUID.randomUUID().toString(), newUser.getEmail(), newUser.getPassword());

//        user.setPassword(hashProvider.hashPassword(newUser.getPassword()));

        return this.validEmail(user.getEmail())
//                .andThen(validEmail(user.getEmail()))
                .andThen(
                        userRepository
                                .store(user)
                                .flatMap(
                                        persistedUser ->
                                                userRepository.findById(persistedUser.getId()).map(this::extractUser)));
    }

    private User extractUser(Optional<User> userOptional) {
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    private Completable validEmail(String email) {
        return this.isEmailAlreadyExists(email)
                .flatMapCompletable(
                        isEmailAlreadyExists -> {
                            if (isEmailAlreadyExists) {
                                return Completable.error(new EmailAlreadyExistsException(String.format("%s already exists.", email)));
                            }

                            return CompletableObserver::onComplete;
                        });
    }

    private Single<Boolean> isEmailAlreadyExists(String email) {
        return userRepository.countByEmail(email).map(this::isCountResultGreaterThanZero);
    }
}
