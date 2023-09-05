package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.ufscar.dc.dsw.model.UsuarioLocadora;
import br.ufscar.dc.dsw.model.UsuarioGeral;
import br.ufscar.dc.dsw.model.Locacoes;
import br.ufscar.dc.dsw.model.UsuarioCliente;
//import br.ufscar.dc.dsw.repository.UsuarioLocadoraRepository;
//import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
//import br.ufscar.dc.dsw.repository.LocacoesRepository;
import br.ufscar.dc.dsw.service.spec.IUsuarioLocadoraService;
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;
import br.ufscar.dc.dsw.service.spec.ILocacoesService;

@RestController
public class UsuarioLocadoraRestController {

    @Autowired
    private IUsuarioLocadoraService usuarioLocadoraService;
    @Autowired
    private IUsuarioGeralService usuarioGeralService;
    @Autowired
    private ILocacoesService locacoesService;

    //Retorna lista de locadoras
    @GetMapping("/locadoras/{nomeCidade}")
    public List<String> getUsernamesCidade(@PathVariable String nomeCidade) {
        return usuarioLocadoraService.buscarNomePorCidade(nomeCidade);
    }


     // Retorna a locadora por id
    @GetMapping("/locadora/{id}")
    public ResponseEntity<UsuarioLocadora> getCombinedLocadoraById(@PathVariable int id) {
        UsuarioLocadora usuarioLocadora = (UsuarioLocadora) usuarioGeralService.buscarPorId((long) id);        
        return ResponseEntity.ok(usuarioLocadora);
    }

    @GetMapping("/locadoras")
    public ResponseEntity<List<UsuarioLocadora>> getUsuarioFinal() {
        List<UsuarioLocadora> usuariosLocadora = usuarioLocadoraService.buscarTodos();
        List<UsuarioLocadora> usuariosFinais = new ArrayList<>();

        for (UsuarioLocadora usuarioLocadora : usuariosLocadora) {

            usuariosFinais.add(usuarioLocadora);
        }

        return ResponseEntity.ok(usuariosFinais);

    }

   

    //Inserir locadora
    @PostMapping("/locadora")
    public ResponseEntity<UsuarioLocadora> insertLocadora(@RequestBody Map<String, String> requestData) {
        // Criação de um usuarioLocadora
        UsuarioLocadora usuarioLocadora = new UsuarioLocadora(
				requestData.get("cpf_cnpj"),
				requestData.get("email"),
				Integer.parseInt(requestData.get("hierarquia")),
				requestData.get("senha"),
				requestData.get("username"),
				requestData.get("cidade")
			);

   

        // Inserção caso não tenha ninguém com esse username e CPF
        if (usuarioLocadoraService.buscarPorCnpj(requestData.get("cpf_cnpj")) == null) {
            usuarioGeralService.salvar(usuarioLocadora);
            return ResponseEntity.ok(usuarioLocadora);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Deletar locadora
    @DeleteMapping("/locadora/{id}")
    public ResponseEntity<String> deleteLocadora(@PathVariable Long id) {

        UsuarioLocadora usuarioLocadora = (UsuarioLocadora) usuarioGeralService.buscarPorId((long) id);   
        List<Locacoes> locacoesList = locacoesService.buscarPorCnpj(usuarioLocadora.getCpfCnpj());

        if (usuarioLocadora != null) {
            for (Locacoes locacao : locacoesList) {
                locacoesService.excluir(locacao);
            }
            usuarioLocadoraService.excluir(usuarioLocadora);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Alterar locadora
    @PostMapping("/locadora/{id}")
    public ResponseEntity<UsuarioLocadora> updateLocadora(
            @PathVariable int id,
            @RequestBody Map<String, String> requestData) {

        String username = requestData.get("username");
        String email = requestData.get("email");
        String cidade = requestData.get("cidade");
        String senha = requestData.get("senha");
        

        UsuarioLocadora usuarioLocadora = (UsuarioLocadora) usuarioGeralService.buscarPorId((long) id);   

        if (usuarioLocadora != null) {
                usuarioLocadora.setUsername(username);
                usuarioLocadora.setEmail(email);
                usuarioLocadora.setSenha(senha);
                usuarioLocadora.setCidade(cidade);

                usuarioLocadoraService.salvar(usuarioLocadora);

                return ResponseEntity.ok(usuarioLocadora);
        }
    
        return ResponseEntity.notFound().build();
    }
}
