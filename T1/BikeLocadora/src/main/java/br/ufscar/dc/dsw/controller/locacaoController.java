package br.ufscar.dc.dsw.controller;
import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.LocadoraDAO;
import br.ufscar.dc.dsw.dao.LocacaoDAO;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Login;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.util.Erro;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 @WebServlet(urlPatterns = { "/locacao/*" })
 public class locacaoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LocadoraDAO locadoraDao;
    private LocacaoDAO locacaoDao;
    private ClienteDAO clienteDao;
    Locale locale;
    ResourceBundle messages;
    String mensagemErro;
    String assunto;
    String corpo;

    @Override
    public void init() {
        locadoraDao = new LocadoraDAO();
        locacaoDao = new LocacaoDAO();
        clienteDao = new ClienteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        locale = request.getLocale();
        messages = ResourceBundle.getBundle("message", locale);

        String action = request.getPathInfo();
        if (action == null) {
            action = "";
        }

        Login usuario = (Login) request.getSession().getAttribute("usuarioLogado");
        request.setAttribute("usuario", usuario);

        try {
            switch(action){
                case "/cadastro":
                    autorizacao(request, response);
                    apresentaFormCadastro(request, response);
                    break;
                case "/edicao":
                    autorizacao(request, response);
                    apresentaFormEdicao(request, response);
                    break;
                case "/insere":
                    autorizacao(request, response);
                    insere(request, response);
                    break;
                case "/remove":
                    autorizacao(request, response);
                    remove(request, response);
                    break;
                case "/atualiza":
                    autorizacao(request, response);
                    atualiza(request, response);
                    break; 
                case "/lista":
                    lista(request, response);
                    break;
                case "/listaPorLocadora":
                    listaPorLocadora(request, response);
                    break;
                case "/listaPorCliente":
                    listaPorCliente(request, response);
                    break;
                // case "/cancelar":
				//     cancelarLocacao(request, response);
				//     break;
                default:
                    Erro erro = new Erro();
                    mensagemErro = messages.getString("error.404");
                    erro.add(mensagemErro);
                    mensagemErro = messages.getString("error.page.not.found");
                    erro.add(mensagemErro);
                    request.setAttribute("mensagens", erro);
                    request.setAttribute("linkVoltar", "../locadora/lista");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
                    dispatcher.forward(request, response);
            }
        } catch (RuntimeException | IOException | ServletException e){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.processing");
            erro.add(mensagemErro);
            erro.add(e.getMessage());
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locadora/lista");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            throw new ServletException(e);
        }
    }

    private Map<Long, String> getLocadora() {
        Map <Long,String> locadoras = new HashMap<>();
        for (Locadora locadora: new LocadoraDAO().getAll()) {
            locadoras.put(locadora.getCnpj(), locadora.getNome());
        }
        return locadoras;
    }

    private void lista(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        List<Locacao> listaLocacao = locacaoDao.getAll();
        List<Locadora> listaLocadora = locadoraDao.getAll();
        request.setAttribute("listaLocacao", listaLocacao);
        request.setAttribute("listaLocadora", listaLocadora);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listaLocacao.jsp");
        dispatcher.forward(request, response);
    }

    private void listaPorLocadora(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        Login usuario = (Login) request.getSession().getAttribute("usuarioLogado");
        List<Locacao> listaLocacao = locacaoDao.getPorLocadora(usuario.getLocadora().getCnpj());   
        request.setAttribute("listaLocacao", listaLocacao);
        request.setAttribute("filtrado", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listaLocacao.jsp");
        dispatcher.forward(request, response);
    }

        private void listaPorCliente(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        Login usuario = (Login) request.getSession().getAttribute("usuarioLogado");
        List<Locacao> listaLocacao = locacaoDao.getPorCliente(usuario.getCliente().getCpf());   
        request.setAttribute("listaLocacao", listaLocacao);
        request.setAttribute("filtrado", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listaLocacao.jsp");
        dispatcher.forward(request, response);
    }

 /*   private Map<Long, String> getLocacao() {
        Map <Long,String> locacaos = new HashMap<>();
        for (Locacao locacao: new LocacaoDAO().getAll()) {
            locacaos.put(locacao.getId(), locacao.getCidade());
        }
        return locacaos;
    }*/

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("locadoras", getLocadora());       
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioLocacao.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locacao locacao = locacaoDao.get(id);
        request.setAttribute("locadoras", getLocadora()); 
        request.setAttribute("locacao", locacao);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioLocacao.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Long locadoraCnpj = Long.parseLong(request.getParameter("locadora"));
        String clienteCpf = request.getParameter("cpf");
        String dataLocacao = request.getParameter("date");
        String horarioLocacao = request.getParameter("time");
        int ativo = Integer.parseInt(request.getParameter("ativo"));
        

        Locacao locacao = new Locacao(locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
        
        boolean locacaoValida = locacaoDao.validarLocacao(locacao);
    
        if (locacaoValida) {

            locacaoDao.insert(locacao);
            //Envio de emails
             // Recupere os parâmetros da solicitação, como o endereço de e-mail do destinatário e a mensagem.
            Locadora locadora = locadoraDao.getByCnpj(locadoraCnpj);
            String destinatarioLocadora = locadora.getEmail();
            Cliente cliente = clienteDao.getByCpf(clienteCpf);
            String destinatarioUsuario = cliente.getEmail();
            assunto = messages.getString("rental.subject");
            corpo = messages.getString("rental.subject");

            // Configure as propriedades do servidor de e-mail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.seuservidoremail.com"); // Altere para o host SMTP do seu provedor de e-mail.
            props.put("mail.smtp.port", "587"); // Porta SMTP do seu provedor de e-mail (587 é comum para TLS).
            props.put("mail.smtp.auth", "true"); // Habilita autenticação SMTP.
            props.put("mail.smtp.starttls.enable", "true"); // Habilita TLS (opcional).

            // Configure as credenciais de autenticação do remetente.
            String remetente = "locacaoemail@locacao.email.com"; // Altere para o seu endereço de e-mail.
            String senha = "locacaoemail"; // Altere para a sua senha.

            // Crie uma sessão de e-mail com base nas propriedades e nas credenciais.
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remetente, senha);
                }
            });

            try {
                // Crie uma mensagem de e-mail.
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(remetente));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarioLocadora));
                message.setSubject(assunto);
                message.setText(corpo);

                // Envie a mensagem de e-mail.
                //Transport.send(message);

                // Redirecione para uma página de confirmação após o envio bem-sucedido.
                //response.sendRedirect("listaPorCliente");
                
                message.setFrom(new InternetAddress(remetente));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarioUsuario));
                message.setSubject(assunto);
                message.setText(corpo);
                //Transport.send(message);

            } catch (MessagingException e) {
                // Em caso de erro, você pode redirecionar para uma página de erro ou fazer outra ação apropriada.
                e.printStackTrace();
                //response.sendRedirect("listaPorCliente");
            }
            response.sendRedirect("listaPorCliente");
        } else {
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.rent");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locacao/listaPorCliente");
            RequestDispatcher  dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // locacaoDao.insert(locacao);
        // response.sendRedirect("listaPorCliente");
                
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(request.getParameter("id"));
        Long locadoraCnpj = Long.parseLong(request.getParameter("locadora"));
        String clienteCpf = request.getParameter("cpf");
        String dataLocacao = request.getParameter("date");
        String horarioLocacao = request.getParameter("time");
        int ativo = Integer.parseInt(request.getParameter("ativo"));
 
        Locacao locacao = new Locacao(id, locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
        locacaoDao.update(locacao);
        listaPorLocadora(request, response);
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));

        Locacao locacao= new Locacao(id);
        locacaoDao.delete(locacao);
        listaPorLocadora(request, response);
    }

    private void autorizacao(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        Login usuario = (Login) request.getSession().getAttribute("usuarioLogado");
        if (usuario == null) {
            response.sendRedirect("../views/login.jsp");
            return;
        }
        if (usuario.getCliente() == null) {
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.permission");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("usuario", usuario);
            request.setAttribute("linkVoltar", "../locadora/lista");
            RequestDispatcher rd = request.getRequestDispatcher("/views/error.jsp");
            rd.forward(request, response);
            return;
        }
    }

    // private void cancelarLocacao(HttpServletRequest request, HttpServletResponse response)
	// 			throws ServletException, IOException {
	// 		Long id = Long.parseLong(request.getParameter("id"));
	// 		Locacao locacao = locacaoDao.get(id);
	// 		LocalDate dataLocacao = LocalDate.parse(locacao.getDataLocacao());
    //         LocalTime horarioLocacao = LocalTime.parse(locacao.getHorarioLocacao());
	// 		if (dataLocacao.isBefore(LocalDate.now())) {
	// 			Erro erro = new Erro("Não é possível cancelar ums locacao que já ocorreu.");
	// 			request.setAttribute("mensagens", erro);
	// 			listaPorCliente(request, response);
	// 			return;
	// 		}
	// 		// dataLocacao = dataLocacao.minusDays(5);
	// 		// if (dataLocacao.isBefore(LocalDate.now())) {
	// 		// 	Erro erro = new Erro("Só é possível cancelar uma locacao com no mínimo 5 dias de antecedência.");
	// 		// 	request.setAttribute("mensagens", erro);
	// 		// 	listaPorCliente(request, response);
	// 		// 	return;
	// 		// }
	// 		locacaoDao.updateAtivo(id, 0);
	// 		listaPorCliente(request, response);
    // }
    
}