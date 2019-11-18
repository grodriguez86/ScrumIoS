package ar.edu.uade.scrumgame.domain.repository;

import java.util.List;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.SubLevel;
import io.reactivex.Observable;

public interface LevelRepository {

    Observable<List<Level>> levels();

    Observable<Level> levelByCode(Integer code);

    Observable<SubLevel> subLevelByCode(String code);
}
