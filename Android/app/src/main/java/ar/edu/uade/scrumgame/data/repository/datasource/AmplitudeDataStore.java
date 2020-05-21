package ar.edu.uade.scrumgame.data.repository.datasource;

import com.amplitude.api.Amplitude;
import io.reactivex.Observable;
import org.json.JSONObject;

class AmplitudeDataStore implements LogEventDataStore {
    @Override
    public Observable<Void> logEvent(String eventName, JSONObject eventParams) {
        return Observable.create(emitter -> {
            Amplitude.getInstance().logEvent(eventName, eventParams);
            emitter.onComplete();
        });
    }
}
