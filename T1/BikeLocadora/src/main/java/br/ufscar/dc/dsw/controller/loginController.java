package br.ufscar.dc.dsw.controller;

 import br.ufscar.dc.dsw.dao.LocadoraDAO;
 import br.ufscar.dc.dsw.dao.ClienteDAO;
 import br.ufscar.dc.dsw.domain.Locadora;
 import br.ufscar.dc.dsw.domain.Cliente;
 import br.ufscar.dc.dsw.domain.Login;
 import br.ufscar.dc.dsw.util.Erro;

 import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 @WebServlet(urlPatterns = { "/auth/*" })
 public class loginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Locale locale;
    ResourceBundle messages;
    String mensagemErro;
    private LocadoraDAO locadoraDao;
    private ClienteDAO clienteDao;

    @Override
    public void init() {
        locadoraDao = new LocadoraDAO();
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

        try {
            switch(action){
                case "/login":
                    login(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                default:
                    erro(request, response);
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

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Erro erro = new Erro();
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        if(email == null || email.isEmpty()){
            mensagemErro = messages.getString("error.empty.email");

            erro.add(mensagemErro);
        }
        if(senha == null || senha.isEmpty()){
            mensagemErro = messages.getString("error.empty.password");

            erro.add(mensagemErro);
        }
        if(!erro.isExisteErros()){
            Login login = null;
            if(request.getParameter("tipo").equals("Cliente")){
                Cliente usuario = clienteDao.getByEmail(email);
                if(usuario != null && usuario.getSenha().equals(senha)){
                    login = new Login(null, usuario, "Cliente");
                } else {
                    mensagemErro = messages.getString("error.invalid.user");

                    erro.add(mensagemErro);
                }
            } else {
                Locadora usuario = locadoraDao.getByEmail(email);
                if(usuario != null && usuario.getSenha().equals(senha)){
                    login = new Login(usuario, null, "Locadora");
                } else {
                    mensagemErro = messages.getString("error.invalid.rental");
                    erro.add(mensagemErro);
                }
            }
            if(!erro.isExisteErros()){
                request.getSession().setAttribute("usuarioLogado", login);
                response.sendRedirect("../locadora/lista");
                return;
                
            }
        }
        request.getSession().invalidate();
        request.setAttribute("mensagens", erro);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/login.jsp");
        dispatcher.forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("../locadora/lista");
    }

    private void erro(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Erro erro = new Erro();
        mensagemErro = messages.getString("error.page.not.found");
        erro.add(mensagemErro);
        request.setAttribute("mensagens", erro);
        request.setAttribute("linkVoltar", "../views/login.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
        dispatcher.forward(request, response);
    }
 }
