package br.ufscar.dc.dsw.repository;

import java.util.List; // Import List from java.util
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.model.UsuarioCliente;
import br.ufscar.dc.dsw.model.UsuarioLocadora;

public interface UsuarioLocadoraRepository extends JpaRepository<UsuarioLocadora, Long> {
    @Query(value = "SELECT DISTINCT cidade FROM UsuarioLocadora", nativeQuery = true)
    List<String> findDistinctCidades();

    @Query(value = "SELECT username FROM UsuarioGeral ug JOIN UsuarioLocadora ul ON ug.id = ul.id WHERE ul.cidade  = ?1", nativeQuery = true)
    List<String> findUsernamesByCidade(String cidade);

    @Query("SELECT u FROM UsuarioLocadora u WHERE u.cpf_cnpj = :cpf_cnpj")
    UsuarioLocadora findByCnpj(@Param("cpf_cnpj") String cpf_cnpj);

    void delete(UsuarioLocadora locadora);

    UsuarioLocadora save(UsuarioLocadora locadora);

    List<UsuarioLocadora> findAll();
}
