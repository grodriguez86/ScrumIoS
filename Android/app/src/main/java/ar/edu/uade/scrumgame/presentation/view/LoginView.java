package ar.edu.uade.scrumgame.presentation.view;

public interface LoginView extends LoadDataView {

    void loggedIn();

    void navigateToSignupDetails();

    void goToSignup();

    void showResetPasswordSuccessAlert(String email);
}
