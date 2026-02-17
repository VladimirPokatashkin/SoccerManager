package model.service.loading;

import java.io.IOException;
import java.nio.file.Path;

public interface IEntityLoader {
	void loadClubsAndLeagues(Path configPath) throws IOException;
	void loadTournaments(Path configPath) throws IOException;
}