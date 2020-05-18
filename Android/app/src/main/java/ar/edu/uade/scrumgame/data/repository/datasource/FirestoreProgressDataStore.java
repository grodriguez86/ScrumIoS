package ar.edu.uade.scrumgame.data.repository.datasource;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import io.reactivex.Observable;

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

    @Override
    public Observable<List<ProgressEntity>> getProgressList() {
        return Observable.create(emitter -> {
            try {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    emitter.onError(new RuntimeException("TODO"));
                    return;
                }
                String currentUserEmail = currentUser.getEmail();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("users")
                        .document(currentUserEmail)
                        .collection("levels")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<ProgressEntity> progressEntityList = task.getResult()
                                            .toObjects(ProgressEntity.class);
                                    emitter.onNext(progressEntityList);
                                    emitter.onComplete();
                                } else {
                                    Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                    emitter.onError(task.getException());
                                }
                            }
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<Void> saveProgressList(List<ProgressEntity> progressEntityList) {
        return Observable.create(emitter -> {
            try {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    emitter.onError(new RuntimeException("TODO"));
                    return;
                }
                String currentUserEmail = currentUser.getEmail();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                WriteBatch writeBatch = firebaseFirestore.batch();
                CollectionReference levelsCollection = firebaseFirestore.collection(String.format("users/%s/levels", currentUserEmail));
                for (ProgressEntity progressEntity: progressEntityList) {
                    @SuppressLint("DefaultLocale") String levelKey = String.format("level_%d", progressEntity.getLevelId());
                    levelsCollection
                        .document(levelKey)
                        .set(progressEntity);
                }
                writeBatch.commit()
                        .addOnCompleteListener(userProgressInitSuccess -> {
                            Log.d(LOG_TAG, "UserProgress DocumentSnapshots written");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(LOG_TAG, "Error adding progress documents", e);
                            emitter.onError(e);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<ProgressEntity> getProgressEntity(Integer levelCode) {
        throw new RuntimeException("NOT IMPLEMENTED"); // TODO
    }
}
