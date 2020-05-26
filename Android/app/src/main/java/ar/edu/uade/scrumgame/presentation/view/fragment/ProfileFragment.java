package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import ar.edu.uade.scrumgame.BuildConfig;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.constants.UserGenderConstant;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.presenter.ProfilePresenter;
import ar.edu.uade.scrumgame.presentation.view.ProfileView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ProfileFragment extends BaseFragment implements ProfileView {
    public interface ProfileListener {
        void onLoggedOut();
    }

    @Inject
    ProfilePresenter profilePresenter;
    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;
    @BindView(R.id.name_tv)
    AppCompatTextView name;
    @BindView(R.id.profession_tv)
    AppCompatTextView profession;
    @BindView(R.id.age_tv)
    AppCompatTextView age;
    @BindView(R.id.location_tv)
    AppCompatTextView location;
    @BindView(R.id.game_taste_tv)
    AppCompatTextView gameTaste;
    @BindView(R.id.game_time_tv)
    AppCompatTextView gameTime;
    @BindView(R.id.log_out_button)
    Button logoutButton;
    @BindView(R.id.back_ib)
    ImageButton backButton;
    @BindView(R.id.information_chip)
    Chip information;
    @BindView(R.id.achievements_chip)
    Chip achievements;
    @BindView(R.id.profile_picture_iv)
    ImageView profilePicture;
    @BindView(R.id.app_version_tv)
    AppCompatTextView appVersion;
    private ProfileListener profileListener;

    public ProfileFragment() {
        this.setRetainInstance(true);
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ProfileListener) {
            this.profileListener = (ProfileListener) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LevelComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.profilePresenter.setView(this);
        if (savedInstanceState == null) {
            this.setUpViews();
            this.profilePresenter.initialize();
        }
    }

    private void setUpViews() {
        this.information.setOnCheckedChangeListener((buttonView, isChecked) -> ProfileFragment.this.changeSelectedChipStyle(this.information, isChecked));
        this.achievements.setOnCheckedChangeListener((buttonView, isChecked) -> ProfileFragment.this.changeSelectedChipStyle(this.achievements, isChecked));
        this.information.setSelected(true);
        this.information.setChecked(true);
    }

    private void changeSelectedChipStyle(Chip chip, boolean isChecked) {
        if (isChecked) {
            chip.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        } else {
            chip.setTextColor(ContextCompat.getColor(getActivity(), R.color.jelly));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.profileListener = null;
    }

    @Override
    public void showLoading() {
        this.progressLayout.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.progressLayout.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToast(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.profilePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.profilePresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.profilePresenter.destroy();
    }

    @OnClick(R.id.log_out_button)
    public void logout() {
        this.showAlert(getString(R.string.confirmation),
                getString(R.string.logout_confirmation),
                getActivity(),
                getString(R.string.logout),
                (dialog, which) -> ProfileFragment.this.profilePresenter.onLogout());
    }

    @OnClick(R.id.back_ib)
    public void goBack() {
        this.getActivity().onBackPressed();
    }

    @Override
    public void profileLoaded(UserModel userProfile) {
        this.setUpFields(userProfile);
    }

    @Override
    public void onLoggedOut() {
        if (this.profileListener != null) {
            this.profileListener.onLoggedOut();
        }
    }

    private void setUpFields(UserModel userProfile) {
        this.name.setText(userProfile.getName());
        this.profession.setText(userProfile.getProfession());
        this.age.setText(String.valueOf(userProfile.getAge()));
        this.location.setText(String.format(getString(R.string.location), userProfile.getCity(), userProfile.getState(), userProfile.getCountry()));
        this.gameTaste.setText(userProfile.getGameTasteLevel());
        this.gameTime.setText(userProfile.getGameTimeLevel());
        UserGenderConstant gender = UserGenderConstant.getGender(userProfile.getGender());
        int placeholderDrawable = gender.equals(UserGenderConstant.FEMALE) ? R.drawable.female_avatar : R.drawable.male_avatar;
        Picasso.get().load(placeholderDrawable).into(profilePicture);
        this.appVersion.setText(String.format(getString(R.string.version),BuildConfig.VERSION_NAME));
    }
}
