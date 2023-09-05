package br.ufscar.dc.dsw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import br.ufscar.dc.dsw.model.UsuarioCliente;
import br.ufscar.dc.dsw.model.UsuarioLocadora;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.model.Locacoes;
import br.ufscar.dc.dsw.repository.LocacoesRepository;
//import br.ufscar.dc.dsw.repository.UsuarioLocadoraRepository;
//import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
import br.ufscar.dc.dsw.service.spec.ILocacoesService;
//import br.ufscar.dc.dsw.service.spec.IUsuarioLocadoraService; COLOQUEI AQUI MAS PERCEBI QUE NÃO É CHAMADO EM NENHUM MOMENTO, POR ISSO COMENTEI
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;

@RestController
public class LocacoesRestController {

    @Autowired
    private ILocacoesService locacoesService;

    // @Autowired
    private LocacoesRepository locacoesRepository;

    @Autowired
    private IUsuarioGeralService usuarioGeralService;
    // private UsuarioGeralRepository usuarioGeralRepository;

    // @Autowired
    // private IUsuarioLocadoraService usuarioLocadoraService;
    // private UsuarioLocadoraRepository usuarioLocadoraRepository;

    //Retorna a lista de locacoes
    @GetMapping("/locacoes")
    public ResponseEntity<List<Locacoes>> getLocacoes() {

        List<Locacoes> locacoes = locacoesService.buscarTodos();
      
        return ResponseEntity.ok(locacoes);
    }

    //Retorna a lista de locacoes por id
    @GetMapping("/locacoes/{id}")
    public ResponseEntity<Locacoes> getLocacoesById(@PathVariable String id) {

        List<Locacoes> locacoesList = locacoesService.buscarTodos();

        return ResponseEntity.ok(locacoesList.get(Integer.parseInt(id)-1));
    }

    //Retorna a lista de locacoes por cliente de id
    @GetMapping("/locacoes/clientes/{id}")
    public ResponseEntity<List<Locacoes>>getLocacoesByClienteId(@PathVariable int id) {
        UsuarioCliente usuarioCliente = (UsuarioCliente) usuarioGeralService.buscarPorId(id);
        return ResponseEntity.ok(locacoesService.buscarPorCpf(usuarioCliente.getCpfCnpj()));
    }

    //Retorna a lista de locacoes por locadora de id
    @GetMapping("/locacoes/locadoras/{id}")
    public ResponseEntity<List<Locacoes>>getLocacoesByLocadoraId(@PathVariable int id) {
        UsuarioLocadora usuarioLocadora = (UsuarioLocadora) usuarioGeralService.buscarPorId(id);
        return ResponseEntity.ok(locacoesService.buscarPorCnpj(usuarioLocadora.getCpfCnpj()));
    }

}
