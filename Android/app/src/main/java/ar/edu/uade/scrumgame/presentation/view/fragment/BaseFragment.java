package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Fragment;
import android.widget.Toast;

import ar.edu.uade.scrumgame.presentation.di.HasComponent;

public abstract class BaseFragment extends Fragment {
    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
