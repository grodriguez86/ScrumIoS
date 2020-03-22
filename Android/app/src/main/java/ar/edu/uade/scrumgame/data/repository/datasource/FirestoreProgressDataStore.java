package ar.edu.uade.scrumgame.data.repository.datasource;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import io.reactivex.Observable;
import io.realm.Realm;

class FirestoreProgressDataStore implements RemoteProgressDataStore {

    private static final String LOG_TAG = "FB_PROG_REPO";

    @Override
    public Observable<Void> saveProgress(ProgressEntity progressEntity) {
        return Observable.create(emitter -> {
            try {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    emitter.onError(new RuntimeException("TODO"));
                    return;
                }
                String currentUserEmail = currentUser.getEmail();
                String levelKey = String.format("level_%d", progressEntity.getLevelId());
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection(String.format("users/%s/levels", currentUserEmail))
                        .document(levelKey)
                        .set(progressEntity)
                        .addOnSuccessListener(userProgressInitSuccess -> {
                            Log.d(LOG_TAG, "UserProgress DocumentSnapshot written");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(LOG_TAG, "Error adding progress document", e);
                            emitter.onError(e);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
