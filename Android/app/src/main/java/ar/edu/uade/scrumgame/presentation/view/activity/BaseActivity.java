package ar.edu.uade.scrumgame.presentation.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.ScrumApplication;
import ar.edu.uade.scrumgame.presentation.di.components.ApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.modules.ActivityModule;
import ar.edu.uade.scrumgame.presentation.navigation.Navigator;
import com.afollestad.materialdialogs.MaterialDialog;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = ContextCompat.getDrawable(activity,R.drawable.gradient_background_logo);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((ScrumApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void showAlert(String title, String message, Context context, String positiveButtonText, MaterialDialog.SingleButtonCallback onPositiveClickListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveButtonText);
        builder.onPositive(onPositiveClickListener);
        builder.show();
    }
}
