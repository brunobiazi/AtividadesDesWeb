package br.ufscar.dc.dsw.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import br.ufscar.dc.dsw.model.UsuarioCliente;
import br.ufscar.dc.dsw.repository.UsuarioClienteRepository;
import br.ufscar.dc.dsw.service.spec.IUsuarioClienteService;

@Service
@Transactional(readOnly = false)
public class UsuarioClienteService implements IUsuarioClienteService {

    @Autowired
    private UsuarioClienteRepository dao;

    public void atualizarDadosUsuario(String cpf_cnpj, String sexo, String telefone, String dataNascimento) {
        dao.updateUsuarioClienteData(cpf_cnpj, sexo, telefone, dataNascimento);
    }

    public void salvar(UsuarioCliente cliente) {
		dao.save(cliente);
	}

	public void excluir(UsuarioCliente cliente) {
		dao.delete(cliente);
	}

    @Transactional(readOnly = true)
    public UsuarioCliente buscarPorCpf(String cpf_cnpj) {
        return dao.findByCpf(cpf_cnpj);
    }

    @Transactional(readOnly = true)
    public List<UsuarioCliente> buscarTodos() {
        return dao.findAll();
    }

    
}