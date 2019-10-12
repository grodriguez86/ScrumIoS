package ar.edu.uade.scrumgame.data;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import java.io.File;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = ApplicationStub.class, sdk = 18)
public abstract class ApplicationTestCase {

  @Rule public TestRule injectMocksRule = (base, description) -> {
    MockitoAnnotations.initMocks(ApplicationTestCase.this);
    return base;
  };

  public static Context context() {
    return ApplicationProvider.getApplicationContext();
  }

}
