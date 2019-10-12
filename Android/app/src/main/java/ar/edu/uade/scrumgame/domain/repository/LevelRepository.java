package ar.edu.uade.scrumgame.domain.repository;

import java.util.List;

import ar.edu.uade.scrumgame.domain.Level;
import io.reactivex.Observable;

public interface LevelRepository {

    Observable<List<Level>> levels();
}
