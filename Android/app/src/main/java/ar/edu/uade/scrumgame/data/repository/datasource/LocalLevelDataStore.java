package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.data.entity.ContentEntity;
import ar.edu.uade.scrumgame.data.entity.InfoGameEntity;
import ar.edu.uade.scrumgame.data.entity.LevelEntity;
import ar.edu.uade.scrumgame.data.entity.SubLevelEntity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class LocalLevelDataStore implements LevelDataStore {
    private Context context;

    @Inject
    LocalLevelDataStore(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<LevelEntity>> levelList() {
        return Observable.create(emitter -> {
            ContentEntity databaseContent = new Gson().fromJson(getAssetsFileContent(this.context.getString(R.string.game_file), emitter), ContentEntity.class);
            emitter.onNext(databaseContent.getLevels());
            emitter.onComplete();
        });
    }

    @Override
    public Observable<LevelEntity> levelByCode(Integer code) {
        return Observable.create(emitter -> {
            ContentEntity databaseContent = new Gson().fromJson(getAssetsFileContent(this.context.getString(R.string.game_file), emitter), ContentEntity.class);
            for (LevelEntity levelEntity : databaseContent.getLevels()) {
                if (levelEntity.getCode().equals(code)) {
                    emitter.onNext(levelEntity);
                    emitter.onComplete();
                    return;
                }
            }
            emitter.onError(new RuntimeException("Level not found."));
        });
    }

    @Override
    public Observable<SubLevelEntity> subLevelByCode(String code) {
        return Observable.create(emitter -> {
            SubLevelEntity subLevelEntity = getSublevel(code);
            if (subLevelEntity != null) {
                emitter.onNext(subLevelEntity);
                emitter.onComplete();
                return;
            }
            emitter.onError(new RuntimeException("SubLevel not found."));
        });
    }

    @Override
    public Observable<List<InfoGameEntity>> infoGamesBySubLevelCode(String subLevelCode) {
        return Observable.create(emitter -> {
            SubLevelEntity subLevelEntity = getSublevel(subLevelCode);
            if (subLevelEntity != null) {
                emitter.onNext(subLevelEntity.getInfoGame());
                emitter.onComplete();
                return;
            }
            emitter.onError(new RuntimeException("SubLevel not found."));
        });
    }

    private SubLevelEntity getSublevel(String code) {
        int levelCode = Integer.parseInt(code.split("\\.")[0]);
        LevelEntity level = this.levelByCode(levelCode).blockingFirst();
        for (SubLevelEntity subLevelEntity : level.getSublevels()) {
            if (subLevelEntity.getCode().equals(code)) {
                return subLevelEntity;
            }
        }
        return null;
    }

    private String getAssetsFileContent(String levelsFileName, ObservableEmitter emitter) {
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
