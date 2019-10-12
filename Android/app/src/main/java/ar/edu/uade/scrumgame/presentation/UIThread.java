package ar.edu.uade.scrumgame.presentation;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
