package gustavbarbosaa.backend.repositories;

import gustavbarbosaa.backend.domains.Dificuldade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DificuldadeRepository extends JpaRepository<Dificuldade, UUID> {
}
