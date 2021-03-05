package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.reactivex.Observable;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Iterator;

class AnalyticsDataStore implements LogEventDataStore {
    private Context context;

    @Inject
    AnalyticsDataStore(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Void> initialize() {
        return Observable.empty();
    }

    @Override
    public Observable<Void> logEvent(String eventName, JSONObject eventParams) {
        return Observable.create(emitter -> {
            try {
                Bundle bundle = new Bundle();
                Iterator<String> keys = eventParams.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = eventParams.getString(key);
                    bundle.putString(key, value);
                }
                FirebaseAnalytics.getInstance(this.context).logEvent(eventName, bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        });
    }
}
