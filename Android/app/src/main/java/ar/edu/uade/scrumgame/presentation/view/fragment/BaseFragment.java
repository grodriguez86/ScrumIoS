package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import ar.edu.uade.scrumgame.presentation.di.HasComponent;

public abstract class BaseFragment extends Fragment {
    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(String title,String message, Context context, String positiveButtonText, MaterialDialog.SingleButtonCallback onPositiveClickListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveButtonText);
        builder.onPositive(onPositiveClickListener);
        builder.show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
