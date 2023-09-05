package br.ufscar.dc.dsw.service.spec;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import br.ufscar.dc.dsw.model.Locacoes;

public interface ILocacoesService {

    void salvar(Locacoes locacao);

    void excluir(Locacoes locacao);

    List<Locacoes> buscarPorCpf(String cpf);

    List<Locacoes> buscarPorCnpj(String cnpj);

    List<Locacoes> buscarTodos();

    long contaPorCpf(String cpf, LocalDateTime startTime, LocalDateTime endTime);

    long contaPorCnpj(String cnpj, LocalDateTime startTime, LocalDateTime endTime);
    
    List<Locacoes> buscarPorCnpjouCpf(String document);
}