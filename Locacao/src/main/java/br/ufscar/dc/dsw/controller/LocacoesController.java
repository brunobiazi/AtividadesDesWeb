package br.ufscar.dc.dsw.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.ufscar.dc.dsw.model.Locacoes;
import br.ufscar.dc.dsw.model.UsuarioGeral;


import br.ufscar.dc.dsw.repository.LocacoesRepository; 
//import br.ufscar.dc.dsw.repository.UsuarioLocadoraRepository;
//import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
import br.ufscar.dc.dsw.service.spec.ILocacoesService;
//import br.ufscar.dc.dsw.service.spec.IUsuarioLocadoraService; COLOQUEI AQUI MAS PERCEBI QUE NÃO É CHAMADO EM NENHUM MOMENTO, POR ISSO COMENTEI
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;


import java.security.Principal;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService; 

import javax.servlet.http.HttpSession;


@Controller
public class LocacoesController {

    @Autowired
    private ILocacoesService locacoesService;

    //@Autowired
    private LocacoesRepository locacoesRepository;

    @Autowired
    private IUsuarioGeralService usuarioGeralService;
    //private UsuarioGeralRepository usuarioGeralRepository;

    //@Autowired
    //private IUsuarioLocadoraService usuarioLocadoraService;
    //private UsuarioLocadoraRepository usuarioLocadoraRepository;

    @GetMapping("/testelocacoes")
    public String getAllUsuarios(Model model) {
        //List<Locacoes> usuarios = locacoesRepository.findAll();
        List<Locacoes> usuarios = locacoesService.buscarTodos();
        model.addAttribute("usuarios", usuarios);
        return "testelocacoes";
    }

    @GetMapping("/listalocacoes")
    public String listRentals(Model model, HttpSession session) {
        // Check if the user is logged in
        UsuarioGeral user = (UsuarioGeral) session.getAttribute("user");
        if (user == null) {
            return "redirect:/loginpage";
        }

        // Retrieve locacoes based on the loggedInUser from the database
        List<Locacoes> locacoes = locacoesService.buscarPorCnpjouCpf(user.getCpfCnpj());
        model.addAttribute("user", user); // Use 'user' object instead of 'loggedInUser'
        model.addAttribute("locacoes", locacoes);

        return "listalocacoes"; // Return the Thymeleaf template name
    }




    @GetMapping("/locar")
    public String locar(Model model, HttpSession session, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("user", session.getAttribute("user"));
        // Retrieve the list of usernames from usuariogeral that match cnpj in usuariolocadora
        //List<UsuarioGeral> usuariosGeral = usuarioGeralRepository.findByCnpjInUsuarioLocadora(); //PRECISA IMPLEMENTAR NO DAO -> SERVICE -> ISERVICE -> TRAZER PARA CÁ
        List<UsuarioGeral> usuariosGeral = usuarioGeralService.buscarPorCnpjemUsuarioLocadora();
        model.addAttribute("usuariosGeral", usuariosGeral);
        model.addAttribute("error", error);
        return "cadastrarlocacao";
    }
    
    private LocalDateTime truncateToHour(LocalDateTime dateTime) {
        return dateTime.withMinute(0).withSecond(0).withNano(0);
    }

    @PostMapping("/CadastrarLocacao")
    public String cadastrarLocacao(@RequestParam String cidade,
                                @RequestParam String datetime,
                                HttpSession session) {

        UsuarioGeral user = (UsuarioGeral) session.getAttribute("user");
        if (user == null) {
            return "redirect:/loginpage";
        }
        //if (usuarioGeralRepository.existsByUsernameInUsuarioLocadora(user.getUsername())) {
        if (usuarioGeralService.existeNomeemUsuarioLocadora(user.getUsername())) {  
            // Handle the error (e.g., show a message to the user).
            // For simplicity, let's redirect back to the form with an error message.
            return "redirect:/locar?error=notAllowed";
        }

        // Convert datetime string to LocalDateTime object using a custom DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        datetime = datetime.replace("T", " ");
        System.out.println("EU MUDEI PARA " + datetime);
        LocalDateTime locacaoLocalDateTime = LocalDateTime.parse(datetime, formatter);

        if (locacaoLocalDateTime.isBefore(LocalDateTime.now())) {
            return "redirect:/locar?error=pastTime";
        }
        System.out.println("ENTRANDO LOCACAO");
        // Create the hourLocacao and hourDevolucao objects based on locacaoLocalDateTime
        LocalDateTime hourLocacao = truncateToHour(locacaoLocalDateTime);
        LocalDateTime hourDevolucao = hourLocacao.plusHours(1);
        
        System.out.println("SET loca LOCACAO");
        // Convert LocalDateTime objects to Calendar objects
        Date locacaoCalendar = Date.from(hourLocacao.atZone(ZoneId.systemDefault()).toInstant());
        
        System.out.println("SET devolve LOCACAO");
        Date devolucaoCalendar = Date.from(hourDevolucao.atZone(ZoneId.systemDefault()).toInstant());
        
        System.out.println("Pre cpf");
        // Check if the user already has a rental during the selected hour
        //long count = locacoesRepository.countByCpfAndTimeRange(user.getCpfCnpj(), locacaoCalendar.getTime(), devolucaoCalendar.getTime());
        long count = locacoesService.contaPorCpf(user.getCpfCnpj(), hourLocacao, hourDevolucao);
        if (count > 0) {
            return "redirect:/locar?error=alreadyRented";
        }
        
        // Retrieve the CNPJ of the locadora using the selected username
        String cnpj = cidade;
        
        System.out.println("Pre cnpj");
        // Check if the locadora already has a rental during the selected hour
        //count = locacoesRepository.countByCnpjAndTimeRange(cnpj, hourLocacao.getTime(), hourDevolucao.getTime());
        count = locacoesService.contaPorCnpj(cnpj, hourLocacao, hourDevolucao);
        if (count > 0) {
            return "redirect:/locar?error=notAvailable";
        }
        
        System.out.println("SAVINg " + hourLocacao.toString() + " " + hourDevolucao.toString());
        System.out.println(locacaoCalendar.toString());
        String hl = hourLocacao.toString().replace("T", " ");
        String hd = hourDevolucao.toString().replace("T", " ");

        // Save the Locacoes entity in the database
        Locacoes locacao = new Locacoes(user.getCpfCnpj(), cnpj, hl, hd);
        //locacoesRepository.save(locacao);
        System.out.println("pos SAVINg");
        locacoesService.salvar(locacao);
        System.out.println("all done");

        // Redirect to a success page or any other desired page
        return "redirect:/locar?error=success"; // Replace "successPage" with the desired success page URL.
    }

}
