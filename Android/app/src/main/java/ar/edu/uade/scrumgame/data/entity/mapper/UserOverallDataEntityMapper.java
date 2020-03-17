package ar.edu.uade.scrumgame.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.UserOverallDataEntity;
import ar.edu.uade.scrumgame.domain.UserOverallData;

@Singleton
public class UserOverallDataEntityMapper {
    @Inject
    public UserOverallDataEntityMapper() {
    }

    public UserOverallDataEntity userOverallDataToUserOverallDataEntity(UserOverallData userOverallData) {
        UserOverallDataEntity userOverallDataEntity = new UserOverallDataEntity();
        userOverallDataEntity.setpK(userOverallData.getpK());
        return userOverallDataEntity;
    }

    public UserOverallData userOverallDataEntityToUserOverallData(UserOverallDataEntity userOverallDataEntity) {
        UserOverallData userOverallData= new UserOverallData();
        userOverallData.setpK(userOverallDataEntity.getpK());
        return userOverallData;
    }
}
