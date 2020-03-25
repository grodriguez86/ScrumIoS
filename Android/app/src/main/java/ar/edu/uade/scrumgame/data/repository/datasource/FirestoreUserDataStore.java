package ar.edu.uade.scrumgame.data.repository.datasource;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.domain.User;
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
                        .addOnSuccessListener(userSaveSuccess -> {
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

    @Override
    public Observable<UserEntity> getUser(String userEmail) {
        return Observable.create(emitter -> {
            try {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("users")
                        .document(userEmail)
                        .get()
                        .addOnSuccessListener(userProgressInitSuccess -> {
                            Log.d(LOG_TAG, "User document fetched");
                            UserEntity userEntity = userProgressInitSuccess.getData() == null ?
                                    new UserEntity() :
                                    userProgressInitSuccess.toObject(UserEntity.class);
                            emitter.onNext(userEntity);
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(LOG_TAG, "Error fetching user document", e);
                            emitter.onError(e);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
