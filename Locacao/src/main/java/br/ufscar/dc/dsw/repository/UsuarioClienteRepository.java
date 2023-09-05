package br.ufscar.dc.dsw.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.ufscar.dc.dsw.model.UsuarioCliente;
import br.ufscar.dc.dsw.model.UsuarioGeral;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Date;



public interface UsuarioClienteRepository extends JpaRepository<UsuarioCliente, Long> {
    @Query("SELECT u FROM UsuarioCliente u WHERE u.cpf_cnpj = :cpf_cnpj")
    UsuarioCliente findByCpf(@Param("cpf_cnpj") String cpf_cnpj);
    
    @Modifying
        @Query("UPDATE UsuarioCliente u SET u.sexo = :sexo, u.telefone = :telefone, u.dataDeNascimento = :dataNascimento WHERE u.cpf_cnpj = :cpf_cnpj")
        void updateUsuarioClienteData(@Param("cpf_cnpj") String cpf_cnpj, @Param("sexo") String sexo,
                                    @Param("telefone") String telefone, @Param("dataNascimento") String dataNascimento);
    
    void delete(UsuarioCliente cliente);

    
    UsuarioCliente save(UsuarioCliente cliente);

    List<UsuarioCliente> findAll();
}
