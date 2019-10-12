package ar.edu.uade.scrumgame.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import ar.edu.uade.scrumgame.data.ApplicationTestCase;
import ar.edu.uade.scrumgame.data.entity.mapper.LevelEntityDataMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.LevelDataStoreFactory;
import ar.edu.uade.scrumgame.domain.Level;
import io.reactivex.observers.DisposableObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class LevelDataRepositoryTest extends ApplicationTestCase {
    private static final int SCRUM_LEVELS = 5;
    private LevelDataRepository levelDataRepository;

    @Before
    public void setUp() {
        levelDataRepository = new LevelDataRepository(new LevelDataStoreFactory(context()), new LevelEntityDataMapper());
    }

    @Test
    public void testJsonDBAccess() {
        levelDataRepository.levels().subscribe(new DisposableObserver<List<Level>>() {
            @Override
            public void onNext(List<Level> levels) {
                assertEquals(levels.size(), SCRUM_LEVELS);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
