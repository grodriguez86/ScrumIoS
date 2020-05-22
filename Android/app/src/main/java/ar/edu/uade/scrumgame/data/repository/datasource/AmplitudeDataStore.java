package ar.edu.uade.scrumgame.data.repository.datasource;

import android.app.Application;
import android.content.Context;
import ar.edu.uade.scrumgame.BuildConfig;
import com.amplitude.api.Amplitude;
import io.reactivex.Observable;
import org.json.JSONObject;

import javax.inject.Inject;

class AmplitudeDataStore implements LogEventDataStore {
    private Context context;

    @Inject
    AmplitudeDataStore(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Void> initialize() {
        return Observable.create(emitter -> {
            Amplitude.getInstance().initialize(this.context, BuildConfig.AMPLITUDE_API_KEY).enableForegroundTracking((Application) this.context);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Void> logEvent(String eventName, JSONObject eventParams) {
        return Observable.create(emitter -> {
            Amplitude.getInstance().logEvent(eventName, eventParams);
            emitter.onComplete();
        });
    }
}
