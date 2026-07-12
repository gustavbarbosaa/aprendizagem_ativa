package gustavbarbosaa.backend.repositories;

import gustavbarbosaa.backend.domains.AreaConhecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AreaConhecimentoRepository extends JpaRepository<AreaConhecimento, UUID> {
    List<AreaConhecimento> findAllByAtivoTrue();

    boolean existsByNomeIgnoreCaseAndAtivoTrue(String nome);

    boolean existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(String nome, UUID id);
}
