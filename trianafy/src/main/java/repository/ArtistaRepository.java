package repository;

import model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ArtistaRepository extends JpaRepository<Artista,Long> {


}
