package ar.edu.uade.scrumgame.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.models.UserOverallDataModel;

@PerActivity
public class UserDataMapper {

    @Inject
    UserDataMapper() {
    }

    public User userModelToUser(UserModel userModel) {
        User user = new User();
        user.setAge(userModel.getAge());
        user.setCity(userModel.getCity());
        user.setCountry(userModel.getCountry());
        user.setGameTasteLevel(userModel.getGameTasteLevel());
        user.setGameTimeLevel(userModel.getGameTimeLevel());
        user.setGender(userModel.getGender());
        user.setMail(userModel.getMail());
        user.setName(userModel.getName());
        user.setProfession(userModel.getProfession());
        user.setState(userModel.getState());
        user.setUid(userModel.getUid());
        return user;
    }

    public UserModel userToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setAge(user.getAge());
        userModel.setCity(user.getCity());
        userModel.setCountry(user.getCountry());
        userModel.setGameTasteLevel(user.getGameTasteLevel());
        userModel.setGameTimeLevel(user.getGameTimeLevel());
        userModel.setGender(user.getGender());
        userModel.setMail(user.getMail());
        userModel.setName(user.getName());
        userModel.setProfession(user.getProfession());
        userModel.setState(user.getState());
        userModel.setUid(user.getUid());
        return userModel;
    }

    public Progress progressModelToProgress(ProgressModel progressModel) {
        Progress progress = new Progress();
        progress.setSublevelID(progressModel.getSublevelID());
        progress.setActualGame(progressModel.getActualGame());
        progress.setTutorialCompleted(progressModel.isTutorialCompleted());
        progress.setStatus(progressModel.getStatus());
        progress.setBlocked(progressModel.isBlocked());
        progress.setpK(progressModel.getpK());
        progress.setLevelId(progressModel.getLevelId());
        progress.setTotalGames(progressModel.getTotalGames());
        return progress;
    }

    public ProgressModel progressToProgressModel(Progress progress) {
        ProgressModel progressModel = new ProgressModel();
        progressModel.setSublevelID(progress.getSublevelID());
        progressModel.setActualGame(progress.getActualGame());
        progressModel.setTutorialCompleted(progress.isTutorialCompleted());
        progressModel.setStatus(progress.getStatus());
        progressModel.setBlocked(progress.isBlocked());
        progressModel.setpK(progress.getpK());
        progressModel.setLevelId(progress.getLevelId());
        progressModel.setTotalGames(progress.getTotalGames());
        return progressModel;
    }

    public Collection<ProgressModel> progressToProgressModel(Collection<Progress> progressList) {
        List<ProgressModel> progressModelList = new ArrayList<>();
        for (Progress progress: progressList)
            progressModelList.add(this.progressToProgressModel(progress));
        return progressModelList;
    }

    public Collection<Progress> progressModelToProgress(Collection<ProgressModel> progressModelList) {
        List<Progress> progressList = new ArrayList<>();
        for (ProgressModel progressModel: progressModelList)
            progressList.add(this.progressModelToProgress(progressModel));
        return progressList;
    }

    public UserOverallData userOverallDataModelToUserOverallData(UserOverallDataModel userOverallDataModel) {
        UserOverallData userOverallData = new UserOverallData();
        userOverallData.setCurrentAvailableLevel(userOverallDataModel.getCurrentAvailableLevel());
        userOverallData.setpK(userOverallDataModel.getpK());
        return userOverallData;
    }

    public UserOverallDataModel userOverallDataToUserOverallDataModel(UserOverallData userOverallData) {
        UserOverallDataModel userOverallDataModel = new UserOverallDataModel();
        userOverallDataModel.setCurrentAvailableLevel(userOverallData.getCurrentAvailableLevel());
        userOverallDataModel.setpK(userOverallData.getpK());
        return userOverallDataModel;
    }
}
