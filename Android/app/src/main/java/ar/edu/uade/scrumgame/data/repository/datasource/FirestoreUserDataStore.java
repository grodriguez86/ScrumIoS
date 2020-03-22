package ar.edu.uade.scrumgame.data.repository.datasource;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.UserEntity;
import io.reactivex.Observable;

class FirestoreUserDataStore implements RemoteUserDataStore {

    private static final String LOG_TAG = "FB_PROG_REPO";

    @Override
    public Observable<Void> saveUser(UserEntity userEntity) {
        return Observable.create(emitter -> {
            try {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("users")
                        .document(userEntity.getMail())
                        .set(userEntity)
                        .addOnSuccessListener(userProgressInitSuccess -> {
                            Log.d(LOG_TAG, "UserEntity DocumentSnapshot written");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(LOG_TAG, "Error adding user document", e);
                            emitter.onError(e);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
