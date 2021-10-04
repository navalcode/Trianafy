package repository;

import model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public class PlaylistRepository extends JpaRepository<Playlist, Long> {
}
