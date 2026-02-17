import model.repository.impl.CountryRepository;
import model.repository.impl.TeamRepository;
import model.repository.impl.TournamentRepository;
import model.repository.ITeamRepository;
import model.service.loading.impl.JsonEntityLoader;
import presenter.ILangService;
import presenter.IPresenter;
import presenter.impl.JsonLangService;
import presenter.Scene;
import presenter.impl.DefaultPresenter;
import presenter.impl.JsonWidgetFactory;
import presenter.impl.interfaces.IWidgetFileFactory;
import presenter.impl.widget.Widget;
import view.impl.DefaultView;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        URL configsURL = Main.class.getClassLoader().getResource("configs");

        if (configsURL == null) {
            throw new RuntimeException("Configs URL not found!");
        }

        Path path = Path.of(configsURL.toURI()).getParent();

        IWidgetFileFactory widgetFactory = new JsonWidgetFactory(path);

        Map<String, Map<Integer, Widget>> scenes = new HashMap<>();
        for (Scene scene : Scene.values()) {
            scenes.put(scene.name(), widgetFactory.construct(
                Path.of("configs/scenes/" + scene.name().toLowerCase() + "/widgets")
            ));
        }

        ITeamRepository repository = new TeamRepository();

        JsonEntityLoader loader = new JsonEntityLoader(Path.of(configsURL.toURI()), new CountryRepository(), repository, new TournamentRepository());

        loader.loadClubsAndLeagues(Path.of("teams"));

        ILangService langService = new JsonLangService(Path.of(configsURL.toURI()));

        IPresenter presenter = new DefaultPresenter(
            new DefaultView(path),
            repository,
            scenes,
            langService);

        long lastTime = System.nanoTime();

        double deltaTime = 0;

        while (presenter.run(deltaTime)) {

            long currentTime = System.nanoTime();

            deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

            lastTime = currentTime;
        }
    }
}