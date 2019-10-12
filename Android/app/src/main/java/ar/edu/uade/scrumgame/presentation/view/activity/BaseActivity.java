package ar.edu.uade.scrumgame.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.presentation.ScrumApplication;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.ApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.modules.ActivityModule;
import ar.edu.uade.scrumgame.presentation.navigation.Navigator;

public abstract class BaseActivity extends Activity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }


    protected ApplicationComponent getApplicationComponent() {
        return ((ScrumApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
