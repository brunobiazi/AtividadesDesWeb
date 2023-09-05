package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Login;
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

 @WebServlet(urlPatterns = { "/cliente/*" })
 public class clienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ClienteDAO clienteDao;
    Locale locale;
    ResourceBundle messages;
    String mensagemErro;

    @Override
    public void init() {
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
        if (usuario == null) {
            Erro erro = new Erro("É necessário estar logado para acessar esta página.");
            request.setAttribute("mensagens", erro);
            response.sendRedirect("../views/login.jsp");
            return;
        }

        if (usuario.getLocadora() != null || usuario.getCliente().getAdmin() == 0) {
            Erro erro = new Erro();
            erro.add("Você não tem permissão para acessar esta página.");
            request.setAttribute("mensagens", erro);
            request.setAttribute("usuario", usuario);
            RequestDispatcher rd = request.getRequestDispatcher("/views/error.jsp");
            rd.forward(request, response);
            return;
        }

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
            request.setAttribute("linkVoltar", "../cliente/lista");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            throw new ServletException(e);
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        List<Cliente> listaCliente = clienteDao.getAll();
        request.setAttribute("listaCliente", listaCliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listaCliente.jsp");
        dispatcher.forward(request, response);
    }

    private Map<Long, String> getCliente() {
        Map <Long,String> clientes = new HashMap<>();
        for (Cliente cliente: new ClienteDAO().getAll()) {
            clientes.put(cliente.getId(), cliente.getNome());
        }
        return clientes;
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("clientes", getCliente());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Cliente cliente = clienteDao.get(id);
        request.setAttribute("cliente", cliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/formularioCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String nascimento = request.getParameter("nascimento");
        int admin = Integer.parseInt(request.getParameter("admin"));

        if(clienteDao.verificaEmailDuplicado(email)) {
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.add.user");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.email.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../cliente/cadastro");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if(clienteDao.verificaCpfDuplicado(cpf)){
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.add.user");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.cpf.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../cliente/cadastro");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }
 
        Cliente cliente = new Cliente(email, nome, senha, cpf, telefone, sexo, nascimento, admin);
        clienteDao.insert(cliente);
        response.sendRedirect("lista");
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(request.getParameter("id"));
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String nascimento = request.getParameter("nascimento");
        int admin = Integer.parseInt(request.getParameter("admin"));

        Cliente clienteEmail = clienteDao.getByEmail(email);
        Cliente clienteCpf = clienteDao.getByCpf(cpf);

        if(clienteEmail != null && clienteEmail.getId() != id) {
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.update.user");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.email.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../cliente/edicao?id=" + id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if(clienteCpf != null && clienteCpf.getId() != id) {
            Erro erro = new Erro();
            mensagemErro = messages.getString("error.update.user");
            erro.add(mensagemErro);
            mensagemErro = messages.getString("error.cpf.already");
            erro.add(mensagemErro);
            request.setAttribute("mensagens", erro);
            request.setAttribute("linkVoltar", "../cliente/edicao?id=" + id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
            return;
        }
 
        Cliente cliente = new Cliente(id, email, nome, senha, cpf, telefone, sexo, nascimento, admin);
        clienteDao.update(cliente);
        response.sendRedirect("lista");
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        Cliente cliente= new Cliente(id);
        // LocacaoDAO locacaoDao = new LocacaoDAO();
        // locacaoDao.deleteAllByCliente(cliente.getCpf());
        clienteDao.delete(cliente);
        response.sendRedirect("lista");
    }

}