package br.ufscar.dc.dsw.controller;

 import br.ufscar.dc.dsw.dao.LocacaoDAO;
import br.ufscar.dc.dsw.dao.LocadoraDAO;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Locadora;
 import br.ufscar.dc.dsw.util.Erro;

 import java.io.IOException;
import java.util.HashMap;
 import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 @WebServlet(urlPatterns = { "/locadora/*" })
 public class locadoraController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Locale locale;
    ResourceBundle messages;
    String mensagemErro;

    private LocadoraDAO locadoraDao;

    @Override
    public void init() {
        locadoraDao = new LocadoraDAO();
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

        // Login usuario = (Login) request.getSession().getAttribute("usuarioLogado");
        // if (usuario == null) {
        //     Erro erro = new Erro("É necessário estar logado para acessar esta página.");
        //     request.setAttribute("mensagens", erro);
        //     response.sendRedirect("../views/login.jsp");
        //     return;
        // }

        // if (usuario.getLocadora() != null || usuario.getCliente().getAdmin() == 0) {
        //     Erro erro = new Erro();
        //     erro.add("Você não tem permissão para acessar esta página.");
        //     request.setAttribute("mensagens", erro);
        //     request.setAttribute("usuario", usuario);
        //     request.setAttribute("linkVoltar", "../locadora/lista");
        //     RequestDispatcher rd = request.getRequestDispatcher("/views/error.jsp");
        //     rd.forward(request, response);
        //     return;
        // }

        try {
            switch(action){
                case "/cadastro":
                    apresentaFormCadastro(request, response);
                    break;
                case "/edicao":
                    apresentaFormEdicao(request, response);
                    break;
                case "/insere":
                    insere(request, response);
                    break;
                case "/remove":
                    remove(request, response);
                    break;
                case "/atualiza":
                    atualiza(request, response);
                    break;
                case "/lista":
                    lista(request, response);
                    break;
                default:
                    Erro erro = new Erro();
                    mensagemErro = messages.getString("error.404");
                    erro.add(mensagemErro);
                    mensagemErro = messages.getString("error.page.not.found");
                    erro.add(mensagemErro);
                    request.setAttribute("mensagens", erro);
                    request.setAttribute("linkVoltar", "../locacao/lista");
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

    private void lista(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        List<Locadora> listaLocadora = locadoraDao.getAll();
        request.setAttribute("listaLocadora", listaLocadora);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listaLocadora.jsp");
        dispatcher.forward(request, response);
    }

    private Map<Long, String> getLocadora() {
        Map <Long,String> locadoras = new HashMap<>();
        for (Locadora locadora: new LocadoraDAO().getAll()) {
            locadoras.put(locadora.getCnpj(), locadora.getNome());
        }
        return locadoras;
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("locadoras", getLocadora());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioLocadora.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locadora locadora = locadoraDao.get(id);
        request.setAttribute("locadora", locadora);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioLocadora.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Long cnpj = Long.parseLong(request.getParameter("cnpj"));
        String nome = request.getParameter("nome");
        String cidade = request.getParameter("cidade");

        if(locadoraDao.verificaEmailDuplicado(email)){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.add.rental");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.email.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locadora/cadastro");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if(locadoraDao.verificaCnpjDuplicado(cnpj)){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.add.rental");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.cnpj.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locadora/cadastro");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }
 
        Locadora locadora = new Locadora(email, senha, cnpj, nome, cidade);
        locadoraDao.insert(locadora);
        response.sendRedirect("lista");
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(request.getParameter("id"));
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Long cnpj = Long.parseLong(request.getParameter("cnpj"));
        String nome = request.getParameter("nome");
        String cidade = request.getParameter("cidade");

        Locadora locadoraEmail = locadoraDao.getByEmail(email);
        Locadora locadoraCnpj = locadoraDao.getByCnpj(cnpj);
        if(locadoraEmail != null && !locadoraEmail.getId().equals(id)){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.update.rental");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.email.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locadora/edicao?id=" + id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if(locadoraCnpj != null && !locadoraCnpj.getId().equals(id)){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.update.rental");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.cnpj.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../locadora/edicao?id=" + id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        Locadora locadora = new Locadora(id, email, senha, cnpj, nome, cidade);
        locadoraDao.update(locadora);
        response.sendRedirect("lista");
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        Locadora locadora = new Locadora(id);
        //LocacaoDAO locacaoDao = new LocacaoDAO();
        //List<Locacao> locacoes = locacaoDao.getPorLocadora(locadora.getCnpj());
        // while(locacoes.size() > 0){
        //     Locacao locacao = locacoes.remove(0);
        //     locacaoDao.delete(locacao);
        //     locacoes = locacaoDao.getPorLocadora(locadora.getCnpj());
        // }
        locadoraDao.delete(locadora);
        response.sendRedirect("lista");
    }
}