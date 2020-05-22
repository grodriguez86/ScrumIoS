package ar.edu.uade.scrumgame.domain.repository;

import io.reactivex.Observable;
import org.json.JSONObject;

public interface LogEventRepository {

    Observable<Void> logEvent(String eventName, JSONObject eventParams);

    Observable<Void> initialize();
}
