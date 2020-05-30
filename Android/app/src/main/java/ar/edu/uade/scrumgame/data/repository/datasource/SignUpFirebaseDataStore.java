package ar.edu.uade.scrumgame.data.repository.datasource;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.Emitter;
import io.reactivex.Observable;

class SignUpFirebaseDataStore implements SignUpDataStore {
    private static final String LOG_TAG = "SIGNUP";

    @Override
    public Observable<Void> signUp(String email, String password) {
        return Observable.create(emitter -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.getCurrentUser();
            mAuth.signOut();
            this.tryToSignUpWithCredentials(email, password, emitter);
        });
    }

    private void tryToSignUpWithCredentials(String email, String password, Emitter<Void> emitter) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                this.createUserWithCredentials(email, password, emitter);
            } else {
                emitter.onComplete();
            }
        });
    }

    private void createUserWithCredentials(String email, String password, Emitter<Void> emitter) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LOG_TAG, "createUserWithEmail:success");
                        emitter.onComplete();
                    } else {
                        Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                        try {
                            if (task.getException() != null)
                                throw task.getException();
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                });
    }
}

