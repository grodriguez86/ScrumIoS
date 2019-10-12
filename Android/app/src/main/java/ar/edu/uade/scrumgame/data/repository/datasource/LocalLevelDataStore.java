package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.data.entity.ContentEntity;
import ar.edu.uade.scrumgame.data.entity.LevelEntity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class LocalLevelDataStore implements LevelDataStore {
    private static final String LEVELS_FILE_NAME = "data/db.json";
    private Context context;

    @Inject
    LocalLevelDataStore(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<LevelEntity>> levelList() {
        return Observable.create(emitter -> {
            ContentEntity databaseContent = new Gson().fromJson(getAssetsFileContent(LEVELS_FILE_NAME, emitter), ContentEntity.class);
            emitter.onNext(databaseContent.getLevels());
            emitter.onComplete();
        });
    }

    private String getAssetsFileContent(String levelsFileName, ObservableEmitter<List<LevelEntity>> emitter) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(levelsFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            emitter.onError(e);
        }
        return json;
    }
}
