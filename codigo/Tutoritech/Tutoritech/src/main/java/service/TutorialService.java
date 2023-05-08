package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;

import dao.TutorialDAO;
import model.Categoria;
import model.Tutorial;
import spark.Request;
import spark.Response;

public class TutorialService {

	private TutorialDAO tutorialDAO = new TutorialDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_TITULO = 2;
	private final int FORM_ORDERBY_CAT = 3;
	
	
	public TutorialService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Tutorial(), FORM_ORDERBY_TITULO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Tutorial(), orderBy);
	}

	
	public void makeForm(int tipo, Tutorial tutorial, int orderBy) {
		String nomeArquivo = "tutform.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umTutorial = "";
		if(tipo != FORM_INSERT) {
			umTutorial += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/tutorial/list/1\">Novo Tutorial</a></b></font></td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t</table>";
			umTutorial += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/tutorial/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Tutorial";
				descricao = "youtube, whatsapp, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + tutorial.getID();
				name = "Atualizar Tutorial (ID " + tutorial.getID() + ")";
				descricao = tutorial.getTitulo();
				buttonLabel = "Atualizar";
			}
			umTutorial += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umTutorial += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td>&nbsp;Titulo: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ descricao +"\"></td>";
			umTutorial+= "\t\t\t<td>Texto: <input class=\"input--register\" type=\"text\" name=\"texto\" value=\""+ tutorial.getTexto() +"\"></td>";
			umTutorial += "\t\t\t<td>Url: <input class=\"input--register\" type=\"url\" name=\"url\" value=\""+ tutorial.getUrl() +"\"></td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td>&nbsp;catId: <select name=\"catid\">";
			List<Categoria> categorias = tutorialDAO.getCats();
			for( Categoria c : categorias) {
						  umTutorial += "\t\t\t\t<option value=\""+ c.getID() +"\">" + c.getNome() + "</option>";
			}
			umTutorial += "\t\t\t</select>";		  
			// umTutorial += "\t\t\t<td>&nbsp;catId: <input class=\"input--register\" type=\"text\" name=\"catid\" value=\""+ tutorial.getCatid() + "\"></td>";
			umTutorial += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t</table>";
			umTutorial += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umTutorial += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + tutorial.getID() + ")</b></font></td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td>&nbsp;Titulo: "+ tutorial.getTitulo() +"</td>";
			umTutorial += "\t\t\t<td>Texto: "+ tutorial.getTexto() +"</td>";
			umTutorial += "\t\t\t<td>URL: "+ tutorial.getUrl() +"</td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t\t<tr>";
			umTutorial += "\t\t\t<td>&nbsp;Catid: "+ tutorial.getCatid()+ "</td>";
			umTutorial += "\t\t\t<td>&nbsp;</td>";
			umTutorial += "\t\t</tr>";
			umTutorial += "\t</table>";			
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-TUTORIAL>", umTutorial);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Tutoriais</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/tutorial/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/tutorial/list/" + FORM_ORDERBY_TITULO + "\"><b>Titulo</b></a></td>\n" +
        		"\t<td><a href=\"/tutorial/list/" + FORM_ORDERBY_CAT + "\"><b>Cat</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		List<Tutorial> tutorials;

		if (orderBy == FORM_ORDERBY_ID) {                 	tutorials = tutorialDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_TITULO) {		tutorials = tutorialDAO.getOrderByTitulo();
		} else if (orderBy == FORM_ORDERBY_CAT) {			tutorials = tutorialDAO.getOrderByCatid();
		} else {											tutorials = tutorialDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Tutorial p : tutorials) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getTitulo() + "</td>\n" +
            		  "\t<td>" + tutorialDAO.getCat(p.getCatid()).getNome() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/tutorial/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/tutorial/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteTutorial('" + p.getID() + "', '" + p.getTitulo() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-TUTORIAL>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String texto = request.queryParams("texto");
		String url = request.queryParams("url");
		int catid = Integer.parseInt(request.queryParams("catid"));
		
		String resp = "";
		
		Tutorial tutorial = new Tutorial(-1, titulo, texto, url, catid);
		
		if(tutorialDAO.insert(tutorial) == true) {
            resp = "Tutorial (" + titulo + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Tutorial (" + titulo + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Tutorial tutorial = (Tutorial) tutorialDAO.get(id);
		
		if (tutorial != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, tutorial, FORM_ORDERBY_TITULO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Tutorial " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Tutorial tutorial = (Tutorial) tutorialDAO.get(id);
		
		if (tutorial != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, tutorial, FORM_ORDERBY_TITULO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Tutorial " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Tutorial tutorial = tutorialDAO.get(id);
        String resp = "";       

        if (tutorial != null) {
        	tutorial.setTitulo(request.queryParams("titulo"));
        	tutorial.setTexto(request.queryParams("texto"));
        	tutorial.setUrl(request.queryParams("url"));
        	tutorial.setCatid(Integer.parseInt(request.queryParams("catid")));
        	tutorialDAO.update(tutorial);
        	response.status(200); // success
            resp = "Tutorial (ID " + tutorial.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Tutorial (ID \" + tutorial.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id").substring(1));
        Tutorial tutorial = tutorialDAO.get(id);
        String resp = "";       

        if (tutorial != null) {
            tutorialDAO.delete(id);
            response.status(200); // success
            resp = "Tutorial (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Tutorial (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}