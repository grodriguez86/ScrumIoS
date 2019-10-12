package ar.edu.uade.scrumgame.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable exception) {

    }
}
