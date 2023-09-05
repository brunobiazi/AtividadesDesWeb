package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.ufscar.dc.dsw.model.UsuarioCliente;

//import br.ufscar.dc.dsw.repository.UsuarioClienteRepository; 
//import br.ufscar.dc.dsw.repository.LocacoesRepository; 
//import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
import br.ufscar.dc.dsw.service.spec.IUsuarioClienteService;
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;
import br.ufscar.dc.dsw.service.spec.ILocacoesService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.ufscar.dc.dsw.model.UsuarioGeral;
import br.ufscar.dc.dsw.model.Locacoes;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.ufscar.dc.dsw.service.impl.UsuarioClienteService;

@RestController
public class UsuarioClienteRestController {

    @Autowired
    private IUsuarioClienteService usuarioClienteService;
    @Autowired
    private IUsuarioGeralService usuarioGeralService;
    @Autowired
    private ILocacoesService locacoesService;

    // Busca todos os clientes
    // BD: usuarioCliente;
    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioCliente>> getAllUsuarios() {
        // Busca todos os usuáriosClientes e os armazena
        List<UsuarioCliente> usuariosClientes = usuarioClienteService.buscarTodos();

        // Cria uma lista para usuariosFinais, ou seja, usuarioCliente+usuarioGeral
        // Essa lista será a retornada pela função.
        List<UsuarioCliente> usuariosFinaisMap = new ArrayList<>();

        // Alimenta a mesma
        for (UsuarioCliente usuarioCliente : usuariosClientes) {

            usuariosFinaisMap.add(usuarioCliente);
        }

        return ResponseEntity.ok(usuariosFinaisMap);
    }

    // busca clientes 
    @GetMapping("/cliente/{id}")
    public ResponseEntity<UsuarioCliente> getCombinedClienteByCpf(@PathVariable int id) {
        UsuarioCliente usuarioCliente = (UsuarioCliente) usuarioGeralService.buscarPorId((long) id);        

        return ResponseEntity.ok(usuarioCliente);
    }

    // Insere um cliente
    @PostMapping("/cliente")
    public ResponseEntity<UsuarioCliente> insertCliente(@RequestBody Map<String, String> requestData) {

        // Criação de um usuarioCliente
        UsuarioCliente usuarioCliente = new UsuarioCliente(
				requestData.get("cpf_cnpj"),
				requestData.get("email"),
				Integer.parseInt(requestData.get("hierarquia")),
				requestData.get("senha"),
				requestData.get("username"),
				requestData.get("sexo"),
				requestData.get("telefone"),
				requestData.get("dataDeNascimento")
			);

        // Inserção caso não tenha ninguém com esse CPF
        if (usuarioClienteService.buscarPorCpf(requestData.get("cpf_cnpj")) == null) {
            usuarioGeralService.salvar(usuarioCliente);
            return ResponseEntity.ok(usuarioCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update
    @PostMapping("/cliente/{id}")
    public ResponseEntity<UsuarioCliente> updateCliente(@PathVariable int id, @RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String email = requestData.get("email");
        String sexo = requestData.get("sexo");
        String telefone = requestData.get("telefone");
        String dataDeNascimento = requestData.get("dataDeNascimento");
        String senha = requestData.get("senha");

        UsuarioCliente usuarioCliente = (UsuarioCliente) usuarioGeralService.buscarPorId((long) id);        

        if (usuarioCliente != null) {
            usuarioCliente.setSexo(sexo);
            usuarioCliente.setTelefone(telefone);
            usuarioCliente.setDataDeNascimento(dataDeNascimento);
            usuarioCliente.setUsername(username);
            usuarioCliente.setCpfCnpj(email);
            usuarioCliente.setSenha(senha);

            usuarioClienteService.salvar(usuarioCliente);

            return ResponseEntity.ok(usuarioCliente);
        }   
        
        return ResponseEntity.notFound().build();
    }

    //Deletar cliente
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Boolean> deleteCliente(@PathVariable Long id) {

        UsuarioCliente usuarioCliente = (UsuarioCliente) usuarioGeralService.buscarPorId((long) id);        
        List<Locacoes> locacoesList = locacoesService.buscarPorCpf(usuarioCliente.getCpfCnpj());

        if (usuarioCliente != null) {
            for (Locacoes locacao : locacoesList) {
                locacoesService.excluir(locacao);
            }
            usuarioClienteService.excluir(usuarioCliente);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
