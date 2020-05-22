package ar.edu.uade.scrumgame.presentation.exception;

import android.content.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.domain.exception.UserAlreadyRegisteredException;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class ErrorMessageFactory {
    private static final List<Class<? extends FirebaseException>> FIREBASE_LOGIN_EXCEPTIONS = Arrays.asList(FirebaseAuthInvalidUserException.class, FirebaseAuthInvalidCredentialsException.class);

    private ErrorMessageFactory() {

    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);
        if (exception instanceof IOException) {
            message = context.getString(R.string.exception_message_file_not_found);
        }
        if (exception instanceof UserAlreadyRegisteredException || exception instanceof FirebaseAuthUserCollisionException) {
            message = context.getString(R.string.exception_message_user_already_registered);
        }
        if (FIREBASE_LOGIN_EXCEPTIONS.contains(exception.getClass())) {
            message = context.getString(R.string.exception_invalid_credentials);
        }
        return message;
    }
}
