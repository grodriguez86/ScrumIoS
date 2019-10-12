package ar.edu.uade.scrumgame.presentation.exception;

import android.content.Context;

import java.io.IOException;

import ar.edu.uade.scrumgame.R;

public class ErrorMessageFactory {

    private ErrorMessageFactory() {

    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);
        if (exception instanceof IOException) {
            message = context.getString(R.string.exception_message_file_not_found);
        }
        return message;
    }
}
