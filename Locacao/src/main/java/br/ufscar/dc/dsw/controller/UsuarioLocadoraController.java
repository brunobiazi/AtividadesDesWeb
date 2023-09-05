package br.ufscar.dc.dsw.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.ufscar.dc.dsw.model.UsuarioLocadora;
import br.ufscar.dc.dsw.model.UsuarioGeral;
import br.ufscar.dc.dsw.model.Locacoes;

//import br.ufscar.dc.dsw.repository.UsuarioLocadoraRepository;
//import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
//import br.ufscar.dc.dsw.repository.LocacoesRepository;
import br.ufscar.dc.dsw.service.spec.IUsuarioLocadoraService;
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;
import br.ufscar.dc.dsw.service.spec.ILocacoesService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioLocadoraController {

    @Autowired
    private IUsuarioLocadoraService usuarioLocadoraService;
    //private UsuarioLocadoraRepository usuarioLocadoraRepository;

    @Autowired 
    private IUsuarioGeralService usuarioGeralService;
    //private UsuarioGeralRepository usuarioGeralRepository;

    @Autowired
    private ILocacoesService locacoesService;
    //private LocacoesRepository locacoesRepository;
        

    @GetMapping("/testeusuariolocadora")
    public String getAllUsuarios(Model model) {
        //List<UsuarioLocadora> usuarios = usuarioLocadoraRepository.findAll(); 
        List<UsuarioLocadora> usuarios = usuarioLocadoraService.buscarTodos();
        model.addAttribute("usuarios", usuarios);
        return "testeclientelocadora";
    }

    @GetMapping("/buscarcidades")
    public String getDistincntCity(Model model) {
        //List<String> cidades = usuarioLocadoraRepository.findDistinctCidades();
        List<String> cidades = usuarioLocadoraService.buscarCidades();
        model.addAttribute("cidades", cidades);
        return "buscarcidades";
    }

    @PostMapping("/resultadocidades")
    public String resultadocidades(@RequestParam("cidade") String cidade, Model model) {
        //List<String> users = usuarioLocadoraRepository.findUsernamesByCidade(cidade);
        List<String> users = usuarioLocadoraService.buscarNomePorCidade(cidade);
        model.addAttribute("cidade", cidade);
        model.addAttribute("users", users);
        return "resultadocidades";
    }

    @GetMapping("/registrarlocadora")
    public String registrarlocadora(Model model) {
        return "registrarlocadora";
    }

    @GetMapping("/readlocadora")
    public String getUserInfo(@RequestParam String usuariolocadora, Model model) {
        // Fetch the user by username from the repository
        //UsuarioGeral user = usuarioGeralRepository.findByUsername(usuariolocadora);
        UsuarioGeral user = usuarioGeralService.buscarPorNome(usuariolocadora);
        //UsuarioLocadora locadora = usuarioLocadoraRepository.findByCnpj(user.getCpfCnpj());
        UsuarioLocadora locadora = usuarioLocadoraService.buscarPorCnpj(user.getCpfCnpj());
        model.addAttribute("user", user);
        model.addAttribute("locadora", locadora);
        return "placeinfo"; // Return the name of the Thymeleaf template (userinfo.html)

    }

    @PostMapping("/Insertlocadora")
    public ModelAndView insertlocadora(@RequestParam String username,
                                      @RequestParam String cpf_cnpj,
                                      @RequestParam String email,
                                      @RequestParam String cidade,
                                      @RequestParam String senha) {

        boolean insertSuccess = true;
        int hierarquiafinal = 0;

        UsuarioLocadora usuarioLocadora = new UsuarioLocadora(
            cpf_cnpj,
            email,
            hierarquiafinal,
            senha,
            username,
            cidade
        );
        //if (usuarioLocadoraRepository.findByCnpj(cpf_cnpj) == null){
        if (usuarioLocadoraService.buscarPorCnpj(cpf_cnpj) == null && usuarioGeralService.buscarPorNome(username) == null){
            //usuarioGeralRepository.save(usuarioGeral);
            usuarioGeralService.salvar(usuarioLocadora);
            //usuarioLocadoraRepository.save(usuarioLocadora);
            // usuarioLocadoraService.salvar(usuarioLocadora);
            insertSuccess = true;
        }
        else{
            insertSuccess = false;
        }

        // If the code reaches this point, the insert was successful
        ModelAndView successModelAndView = new ModelAndView("insertlocadora");
        successModelAndView.addObject("insertSuccess", insertSuccess);
        return successModelAndView;
    }

    @GetMapping("/deleteLocadora")
    public ModelAndView deleteLocadora(@RequestParam String usuariolocadora) {

        boolean insertSuccess = false;

        // Find the existing UsuarioGeral and UsuarioCliente objects to update
        //UsuarioGeral usuarioGeral = usuarioGeralRepository.findByUsername(usuariolocadora);
        UsuarioGeral usuarioGeral = usuarioGeralService.buscarPorNome(usuariolocadora);
        //UsuarioLocadora usuarioLocadora = usuarioLocadoraRepository.findByCnpj(usuarioGeral.getCpfCnpj());
        UsuarioLocadora usuarioLocadora = usuarioLocadoraService.buscarPorCnpj(usuarioGeral.getCpfCnpj());
        //List<Locacoes> locacoesList = locacoesRepository.findByCnpj(usuarioGeral.getCpfCnpj());
        List<Locacoes> locacoesList = locacoesService.buscarPorCnpj(usuarioGeral.getCpfCnpj());
        if (usuarioGeral != null && usuarioLocadora != null) {    
            for (Locacoes locacao : locacoesList) {
                //locacoesRepository.delete(locacao);
                locacoesService.excluir(locacao);
            }
            //usuarioLocadoraRepository.delete(usuarioLocadora);
            usuarioLocadoraService.excluir(usuarioLocadora);
            //usuarioGeralRepository.delete(usuarioGeral);
            usuarioGeralService.excluir(usuarioGeral);
            insertSuccess = true;
        }

        else {
            // If the user does not exist, handle the error (e.g., show an error message)
            insertSuccess = false;
        }

        // If the code reaches this point, the update was successful
        ModelAndView successModelAndView = new ModelAndView("insertcliente");
        successModelAndView.addObject("insertSuccess", insertSuccess);
        return successModelAndView;
    }

    @GetMapping("/editarlocadora")
    public String editarlocadora(@RequestParam String usuariolocadora, Model model) {
        // Fetch the user by username from the repository
        //UsuarioGeral usuarioGeral = usuarioGeralRepository.findByUsername(usuariolocadora);
        UsuarioGeral usuarioGeral = usuarioGeralService.buscarPorNome(usuariolocadora);
        //UsuarioLocadora usuarioLocadora = usuarioLocadoraRepository.findByCnpj(usuarioGeral.getCpfCnpj());
        UsuarioLocadora usuarioLocadora = usuarioLocadoraService.buscarPorCnpj(usuarioGeral.getCpfCnpj());

        // Pass the existing client data to the view
        model.addAttribute("usuarioGeral", usuarioGeral);
        model.addAttribute("usuarioLocadora", usuarioLocadora);
        return "editarlocadora"; // Return the name of the Thymeleaf template (editarcliente.html)
    }

    @PostMapping("/UpdateLocadora")
    public ModelAndView updateLocadora(@RequestParam String username,
                                    @RequestParam String cpf_cnpj,
                                    @RequestParam String email,
                                    @RequestParam String cidade,
                                    @RequestParam String senha) {

        boolean insertSuccess = false;

        // Find the existing UsuarioGeral and UsuarioLocadora objects to update
        //UsuarioGeral usuarioGeral = usuarioGeralRepository.findByCpfCnpj(cpf_cnpj);
        UsuarioGeral usuarioGeral = usuarioGeralService.buscarPorCpfCnpj(cpf_cnpj);
        //UsuarioLocadora usuarioLocadora = usuarioLocadoraRepository.findByCnpj(cpf_cnpj);
        UsuarioLocadora usuarioLocadora = usuarioLocadoraService.buscarPorCnpj(cpf_cnpj);

        if (usuarioGeral != null && usuarioLocadora != null) {
            if ((usuarioGeral.getUsername() != username && usuarioGeralService.buscarPorNome(username) == null) || usuarioGeral.getUsername().equalsIgnoreCase(username)){
                // Update the UsuarioGeral and UsuarioLocadora objects with new data
                usuarioGeral.setUsername(username);
                usuarioGeral.setEmail(email);
                usuarioGeral.setSenha(senha);
                usuarioGeral.setHierarquia(0);

                usuarioLocadora.setCidade(cidade);

                //usuarioGeralRepository.save(usuarioGeral);
                usuarioGeralService.salvar(usuarioGeral);
                //usuarioLocadoraRepository.save(usuarioLocadora);
                usuarioLocadoraService.salvar(usuarioLocadora);

                insertSuccess = true;
            }
            else{
                insertSuccess = false;
            }    
        } else {
            // If the locadora does not exist, handle the error (e.g., show an error message)
            insertSuccess = false;
        }
        // If the code reaches this point, the update was successful
        ModelAndView successModelAndView = new ModelAndView("insertlocadora");
        successModelAndView.addObject("insertSuccess", insertSuccess);
        return successModelAndView;
    }


}
