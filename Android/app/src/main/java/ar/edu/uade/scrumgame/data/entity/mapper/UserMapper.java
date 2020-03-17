package ar.edu.uade.scrumgame.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.domain.User;

@Singleton
public class UserMapper {
    @Inject
    public UserMapper() {

    }

    public UserEntity userToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(user.getAge());
        userEntity.setCity(user.getCity());
        userEntity.setCountry(user.getCountry());
        userEntity.setGameTasteLevel(user.getGameTasteLevel());
        userEntity.setGameTimeLevel(user.getGameTimeLevel());
        userEntity.setGender(user.getGender());
        userEntity.setMail(user.getMail());
        userEntity.setName(user.getName());
        userEntity.setProfession(user.getProfession());
        userEntity.setState(user.getState());
        userEntity.setUid(user.getUid());
        return userEntity;
    }

    public User userEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setAge(userEntity.getAge());
        user.setCity(userEntity.getCity());
        user.setCountry(userEntity.getCountry());
        user.setGameTasteLevel(userEntity.getGameTasteLevel());
        user.setGameTimeLevel(userEntity.getGameTimeLevel());
        user.setGender(userEntity.getGender());
        user.setMail(userEntity.getMail());
        user.setName(userEntity.getName());
        user.setProfession(userEntity.getProfession());
        user.setState(userEntity.getState());
        user.setUid(userEntity.getUid());
        return user;
    }

}
