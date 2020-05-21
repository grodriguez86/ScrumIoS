package ar.edu.uade.scrumgame.data.repository.datasource;

import io.reactivex.Observable;
import org.json.JSONObject;

public interface LogEventDataStore {
    Observable<Void> logEvent(String eventName, JSONObject eventParams);
}
