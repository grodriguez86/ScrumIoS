package ar.edu.uade.scrumgame.data.repository.datasource;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Observable;

class FirebaseAuthDataStore implements ResetPasswordDataStore {

    private static final String LOG_TAG = "RESET_PASSWORD";

    @Override
    public Observable<Void> resetPassword(String email) {
        return Observable.create(emitter -> {
            try {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(task -> {
                            Log.d(LOG_TAG, "Send password reset email.");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(LOG_TAG, "Error send password reset email", e);
                            emitter.onError(e);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
