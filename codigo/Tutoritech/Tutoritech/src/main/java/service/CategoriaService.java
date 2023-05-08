package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.CategoriaDAO;
import model.Categoria;
import spark.Request;
import spark.Response;


public class CategoriaService {

	private CategoriaDAO categoriaDAO = new CategoriaDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	
	
	public CategoriaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Categoria(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Categoria(), orderBy);
	}

	
	public void makeForm(int tipo, Categoria categoria, int orderBy) {
		String nomeArquivo = "catform.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umaCategoria = "";
		if(tipo != FORM_INSERT) {
			umaCategoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/categoria/list/1\">Nova Categoria</a></b></font></td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t</table>";
			umaCategoria += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/categoria/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Categoria";
				nome = "aplicativos de banco, redes sociais, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + categoria.getID();
				name = "Atualizar Categoria (ID " + categoria.getID() + ")";
				nome = categoria.getNome();
				buttonLabel = "Atualizar";
			}
			umaCategoria += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umaCategoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t</table>";
			umaCategoria += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umaCategoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Categoria (ID " + categoria.getID() + ")</b></font></td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t\t<tr>";
			umaCategoria += "\t\t\t<td>&nbsp;Nome: "+ categoria.getNome() +"</td>";
			umaCategoria += "\t\t</tr>";
			umaCategoria += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UMA-CATEGORIA>", umaCategoria);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Categorias</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/categoria/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/categoria/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Categoria> categorias;
		if (orderBy == FORM_ORDERBY_ID) {                 	categorias = categoriaDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		categorias = categoriaDAO.getOrderByNome();
		} else {											categorias = categoriaDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Categoria p : categorias) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/categoria/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/categoria/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCategoria('" + p.getID() + "', '" + p.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CATEGORIA>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		
		String resp = "";
		
		Categoria categoria = new Categoria(-1, nome);
		
		if(categoriaDAO.insert(categoria) == true) {
            resp = "Categoria (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Categoria (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Categoria categoria = (Categoria) categoriaDAO.get(id);
		
		if (categoria != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, categoria, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Categoria " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Categoria categoria = (Categoria) categoriaDAO.get(id);
		
		if (categoria != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, categoria, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Categoria " + id + " não encontrado.";
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
		Categoria categoria = categoriaDAO.get(id);
        String resp = "";       

        if (categoria != null) {
        	categoria.setNome(request.queryParams("nome"));
        	categoriaDAO.update(categoria);
        	response.status(200); // success
            resp = "Categoria (ID " + categoria.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Categoria (ID \" + categoria.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id").substring(1));
        Categoria categoria = categoriaDAO.get(id);
        String resp = "";       

        if (categoria != null) {
            categoriaDAO.delete(id);
            response.status(200); // success
            resp = "Categoria (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Categoria (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}