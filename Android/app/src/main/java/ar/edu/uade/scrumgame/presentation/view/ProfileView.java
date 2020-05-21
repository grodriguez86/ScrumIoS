package ar.edu.uade.scrumgame.presentation.view;

import ar.edu.uade.scrumgame.presentation.models.UserModel;

public interface ProfileView extends LoadDataView {
    void profileLoaded(UserModel userProfile);

    void onLoggedOut();
}
