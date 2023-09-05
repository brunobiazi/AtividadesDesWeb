package br.ufscar.dc.dsw.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.ufscar.dc.dsw.model.UsuarioGeral;
import java.util.List; // Add this import for List
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import br.ufscar.dc.dsw.model.UsuarioGeral;


@SuppressWarnings("unchecked")
public interface UsuarioGeralRepository extends JpaRepository<UsuarioGeral, Long> {
    @Query(value = "Select username from UsuarioGeral ug inner join UsuarioLocadora ul on ug.id = ul.id", nativeQuery = true)
    List<String> comercialpartners();

    @Query(value = "select username from UsuarioGeral ug inner join UsuarioCliente uc on ug.id = uc.id", nativeQuery = true)
    List<String> allclients();

    @Modifying
    @Query("UPDATE UsuarioGeral u SET u.username = :username, u.email = :email, u.hierarquia = :hierarquia, u.senha = :senha WHERE u.cpf_cnpj = :cpf_cnpj")
    void updateUsuarioGeralData(@Param("cpf_cnpj") String cpfCnpj, @Param("username") String username, @Param("email") String email,
                                @Param("hierarquia") int hierarquia, @Param("senha") String senha);

    UsuarioGeral findByUsername(String username);

    @Query("SELECT u FROM UsuarioGeral u WHERE u.cpf_cnpj = :cpf_cnpj")
    UsuarioGeral findByCpfCnpj(@Param("cpf_cnpj") String cpf_cnpj);
   
    @Query("SELECT u FROM UsuarioGeral u WHERE u.id = :id")
    Optional<UsuarioGeral> findById(@Param("id") Long id);

    @Query("SELECT DISTINCT u FROM UsuarioGeral u JOIN UsuarioLocadora l ON u.cpf_cnpj = l.cpf_cnpj")
    List<UsuarioGeral> findByCnpjInUsuarioLocadora();

    @Query("SELECT COUNT(l) > 0 FROM UsuarioGeral u JOIN UsuarioLocadora l ON u.cpf_cnpj = l.cpf_cnpj WHERE u.username = :username")
    boolean existsByUsernameInUsuarioLocadora(@Param("username") String username);


    @Query("SELECT u.cpf_cnpj FROM UsuarioGeral u WHERE u.username = :username")
    String findCnpjByUsername(@Param("username") String username);

    void delete(UsuarioGeral usuario);

    // Add the saveUsuarioLGeral method
    UsuarioGeral save(UsuarioGeral usuario);


}
