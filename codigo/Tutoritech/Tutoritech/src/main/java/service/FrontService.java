package service;

import java.util.List;
import java.util.Scanner;

import app.Aplicacao;

import java.io.File;

import dao.AdminDAO;
import dao.CategoriaDAO;
import dao.TutorialDAO;
import dao.UsuarioDAO;
import model.Admin;
import model.Categoria;
import model.Tutorial;
import model.Usuario;
import spark.Request;

import spark.Response;

public class FrontService {

	private TutorialDAO tutorialDAO = new TutorialDAO();
	private CategoriaDAO categoriaDAO = new CategoriaDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private AdminDAO adminDAO = new AdminDAO();
	private String form;
	private final int TUT_GRATUITOS = 1;
	private final int TUTORIAL = 2;
	private final int CAT_PREMIUM = 3;
	private final int TIPOS_P_CADA_CATEGORIA = 4;
	private final int CADASTRO = 5;
	private final int LOGIN = 6;
	private final int INICIO = 7;
	private final int CONTATO = 8;
	private final int LOGIN_ADM = 9;
	private final int MENU_ADM = 10;
	
	public FrontService() {
		makeForm();
	}
	
	public void makeForm() {
		makeForm(0);
	}

	public void makeForm( int PAGINA ) {
		if(PAGINA == TUT_GRATUITOS) {
			String nomeArquivo = "src/main/resources/public/frontend/tut-gratuitos/index.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		
			String botoes = "";

			Tutorial tutorialGrat = tutorialDAO.get(8);
			botoes += "<div class=\"col-12\">\n" +
					  "\t\t<a href=\"/frontend/tutorial/" + tutorialGrat.getID() + "\"><button class=\"btn-13 custom-btn\">"+ tutorialGrat.getTitulo() +"</button></a>\n" +
					  "\t\t\t</div>\n";
			form = form.replaceFirst("<inserir-bot>", botoes);
			
		} else if (PAGINA == TUTORIAL) {
			String nomeArquivo = "src/main/resources/public/frontend/tutorial/index.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
			
		} else if (PAGINA == CAT_PREMIUM) {
			String nomeArquivo = "src/main/resources/public/frontend/cat-premium/index.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		
			String botoes = "";
		
			List<Categoria> categorias = categoriaDAO.getOrderByNome();
			for(Categoria c : categorias) {
			botoes += "<div class=\"col-12\">\n" +
					  "\t\t<a href=\"/frontend/tipos-para-cada-categoria/" + c.getID() + "\"><button class=\"btn-13 custom-btn\">"+ c.getNome() +"</button></a>\n" +
					  "\t\t\t</div>\n";
			}
			form = form.replaceFirst("<inserir-bot>", botoes);
			
		} else if (PAGINA == TIPOS_P_CADA_CATEGORIA) {
			String nomeArquivo = "src/main/resources/public/frontend/tipos-para-cada-categoria/index.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
			
		} else if (PAGINA == CADASTRO) {
				String nomeArquivo = "src/main/resources/public/frontend/compra/compra.html";
				File arquivo = new File(nomeArquivo);
				form = "";
				try{
					Scanner entrada = new Scanner(arquivo);
					while(entrada.hasNext()){
						form += (entrada.nextLine() + "\n");
			    }
				entrada.close();
				}  catch (Exception e) { System.out.println(e.getMessage()); }
				
		} else if (PAGINA == LOGIN) {
			String nomeArquivo = "src/main/resources/public/frontend/inicio-login/login.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
			
		} else if (PAGINA == INICIO) {
			String nomeArquivo = "src/main/resources/public/frontend/inicio-login/inicio.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		} else if (PAGINA == CONTATO) {
			String nomeArquivo = "src/main/resources/public/frontend/EntreEmContato/contato.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		} else if (PAGINA == LOGIN_ADM) {
			String nomeArquivo = "src/main/resources/public/frontend/loginAdm/LoginAdm.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		} else if (PAGINA == MENU_ADM) {
			String nomeArquivo = "src/main/resources/public/menuadm.html";
			File arquivo = new File(nomeArquivo);
			form = "";
			try{
				Scanner entrada = new Scanner(arquivo);
				while(entrada.hasNext()){
					form += (entrada.nextLine() + "\n");
		    }
			entrada.close();
			}  catch (Exception e) { System.out.println(e.getMessage()); }
		}
	}
	
	public Object tut_gratuito(Request request, Response response) {
    	makeForm(TUT_GRATUITOS);
		return form;
	}
	
	public Object cat_premium(Request request, Response response) {
    	makeForm(CAT_PREMIUM);
    	String chave = request.userAgent() + " " + request.ip();
    	form = inserirSair(Aplicacao.users.get(chave));
		return form;
	}
	
	public Object makeCadastro (Request request, Response response) {
    	makeForm(CADASTRO);
		return form;
	}
	
	public Object makeLogin (Request request, Response response) {
    	makeForm(LOGIN);
		return form;
	}
	
	public Object makeLoginAdm (Request request, Response response) {
    	makeForm(LOGIN_ADM);
		return form;
	}
	
	public Object makeInicio (Request request, Response response) {
    	makeForm(INICIO);
		return form;
	}
	
	public Object makeContato (Request request, Response response) {
    	makeForm(CONTATO);
    	String chave = request.userAgent() + " " + request.ip();
    	form = inserirSair(Aplicacao.users.get(chave));
		return form;
	}
	
	public Object makeMenuAdm (Request request, Response response) {
    	makeForm(MENU_ADM);
		return form;
	}
	
	public Object tryLogin (Request request, Response response) {
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		String resp = "";
		Usuario usuario = usuarioDAO.getByPK(email);
		if(usuario != null ) {
			if(usuario.getSenha().equals(senha)) {
				String chave = request.userAgent() + " " + request.ip();
				Aplicacao.users.put(chave, usuario);
				resp = "Login feito";
				response.redirect("/frontend/cat-premium/index");
			} else {
				resp = "Senha incorreta";
				response.status(404);
			}
		} else {
			resp = "Não foi possivel encontrar nenhum usuario com esse nome";
			response.status(404);
		}
		makeForm(LOGIN);
    	return form.replaceFirst("//script", "window.onload = alert(\""+ resp +"\");");
	}
	
	public Object tryLoginAdm (Request request, Response response) {
		String nome = request.queryParams("nome");
		String senha = request.queryParams("senha");
		String resp = "";
		Admin admin = adminDAO.getByPK(nome);
		if(admin != null ) {
			if(admin.getSenha().equals(senha)) {
				String chave = request.userAgent() + " " + request.ip();
				Aplicacao.admins.put(chave, admin);
				resp = "Login feito";
				response.redirect("/menuadm");
			} else {
				resp = "Senha incorreta";
				response.status(404);
			}
		} else {
			resp = "Não foi possivel encontrar nenhum usuario com esse nome";
			response.status(404);
		}
		makeForm(LOGIN_ADM);
    	return form.replaceFirst("//script", "window.onload = alert(\""+ resp +"\");");
	}
	
	public Object tutorial(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Tutorial tutorial = tutorialDAO.get(id);
        String text = "";
		makeForm(TUTORIAL);
		text = "        <div class=\"row\">\r\n"
				+ "            <div class=\"col-12\">\r\n"
				+ "                <h1 id=\"title\">Tutorial: " + tutorial.getTitulo() + "</h1>\r\n"
				+ "            </div>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"row\">\r\n"
				+ "            <div class=\"col-12\" id=\"colTutorial\">\r\n"
				+ "                <div id=\"divVideo\">\r\n"
				+ "                    <iframe\r\n"
				+ "                        width=\"560\"\r\n"
				+ "                        height=\"315\"\r\n"
				+ "                        src=\"" + tutorial.getUrl() + "\"\r\n"
				+ "                        title=\"YouTube video player\"\r\n"
				+ "                        frameborder=\"0\"\r\n"
				+ "                        allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\"\r\n"
				+ "                        allowfullscreen\r\n"
				+ "                        class=\"w-100\"\r\n"
				+ "                    ></iframe>\r\n"
				+ "                </div>\r\n"
				+ "                <div id=\"textoTutorial\">\r\n"
				+ "                    <p>\r\n"
				+ "                        " + tutorial.getTexto() + "\r\n"
				+ "                    </p>\r\n"
				+ "                </div>\r\n"
				+ "            </div>\r\n"
				+ "        </div>";
		form = form.replaceFirst("<tutorial>", text);
    	String chave = request.userAgent() + " " + request.ip();
    	form = inserirSair(Aplicacao.users.get(chave));
		return form;
	}
	
	public Object cadastrar(Request request, Response response) {
		String email = request.queryParams("email");
		String nome = request.queryParams("nome");
		String telefone = request.queryParams("telefone");
		String senha = request.queryParams("senha");
		String cep = request.queryParams("cep");
		String cidade = request.queryParams("cidade");
		String rua = request.queryParams("rua");
		int numero = Integer.parseInt(request.queryParams("numero"));
		String complemento = request.queryParams("complemento");
		
		String resp = "";
		
		Usuario usuario = new Usuario(email, nome, telefone, senha, cep, cidade, rua, numero, complemento);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuario (" + nome + ") criado!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + nome + ") não criado!";
			response.status(404); // 404 Not found
		}
    	makeForm(CADASTRO);
    	return form.replaceFirst("//script", "window.onload = alert(\""+ resp +"\");");
	}
	
	public Object tipos_para_cada_categoria(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Categoria categoria = categoriaDAO.get(id);
        String botoes = "";
		makeForm(TIPOS_P_CADA_CATEGORIA);
		List<Tutorial> tutoriais = tutorialDAO.getOrderByTitulo();
		for(Tutorial t : tutoriais) {
			if(t.getCatid() == categoria.getID()) {
				botoes += "<div class=\"col-12\">\n" +
						  "\t\t<a href=\"/frontend/tutorial/" + t.getID() + "\"><button class=\"btn-13 custom-btn\">"+ t.getTitulo() +"</button></a>\n" +
						  "\t\t\t</div>\n";
			}
		}
		form = form.replaceFirst("<inserir-bot>", botoes);
    	String chave = request.userAgent() + " " + request.ip();
    	form = inserirSair(Aplicacao.users.get(chave));
		return form;
	}
	
	public String inserirSair (Usuario usuario) {
		if( usuario != null) {
			String sair = "<p> " + usuario.getNome() + "  | <a href=\"/frontend/logout\">Sair</a></p>";
			form = form.replaceFirst("<inserir-sair>", sair);
		}
		return form;
	}
	
	public Object logout (Request request, Response response) {
    	String chave = request.userAgent() + " " + request.ip();
		if(Aplicacao.users.remove(chave) != null)
			response.redirect("/frontend/inicio-login/inicio");
		return "";
	}
	
	public Object logoutAdm (Request request, Response response) {
    	String chave = request.userAgent() + " " + request.ip();
		if(Aplicacao.admins.remove(chave) != null)
			response.redirect("/frontend/loginAdm/login");
		return "";
	}
	
}