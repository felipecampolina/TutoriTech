package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.AdminDAO;
import model.Admin;
import spark.Request;
import spark.Response;


public class AdminService {

	private AdminDAO adminDAO = new AdminDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_NOME  = 1;
	private final int FORM_ORDERBY_SENHA = 2;
	
	public AdminService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Admin(), FORM_ORDERBY_NOME);
	}
	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Admin(), orderBy);
	}

	
	public void makeForm(int tipo, Admin admin, int orderBy) {
		String nomeArquivo = "admform.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umAdmin = "";
		if(tipo != FORM_INSERT) {
			umAdmin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/admin/list/1\">Novo Administrador</a></b></font></td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t</table>";
			umAdmin += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/admin/";
			String name, descnome, descsenha, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Administrador";
				descnome = "admin, admin123, ...";
				descsenha = "12345, senha@123, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + admin.getNome();
				name = "Atualizar Admin (Nome: " + admin.getNome() + " Senha: " + admin.getSenha() + ")";
				descnome = admin.getNome();
				descsenha = admin.getSenha();
				buttonLabel = "Atualizar";
			}
			umAdmin += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umAdmin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td>&nbsp;Nome:  <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ descnome +"\"></td>";
			umAdmin += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"text\" name=\"senha\" value=\""+ descsenha +"\"></td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t</table>";
			umAdmin += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umAdmin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Admin (Nome " + admin.getNome() + ")</b></font></td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t\t<tr>";
			umAdmin += "\t\t\t<td>&nbsp;Nome: "+ admin.getNome() +"</td>";
			umAdmin += "\t\t\t<td>Senha: "+ admin.getSenha() +"</td>";
			umAdmin += "\t\t</tr>";
			umAdmin += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-ADMIN>", umAdmin);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Admins</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/admin/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/admin/list/" + FORM_ORDERBY_SENHA + "\"><b>Senha</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Admin> admins;
		if (orderBy == FORM_ORDERBY_NOME) {             admins = adminDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_SENHA) {		admins = adminDAO.getOrderBySenha();
		} else {									    admins = adminDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Admin p : admins) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getSenha() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/admin/" + p.getNome() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/admin/update/" + p.getNome() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteAdmin('" + p.getNome() + "', '" + p.getSenha() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-ADMIN>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String senha = request.queryParams("senha");
		
		String resp = "";
		
		Admin admin = new Admin(nome, senha);
		
		if(adminDAO.insert(admin) == true) {
            resp = "Admin (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Admin (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		String nome = request.params(":nome");		
		Admin admin = (Admin) adminDAO.getByPK(nome);
		
		if (admin != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, admin, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Admin " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String nome = request.params(":nome");
		Admin admin = (Admin) adminDAO.getByPK(nome);
		
		if (admin != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, admin, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Admin " + nome + " não encontrado.";
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
        String nome = request.params(":nome");
		Admin admin = adminDAO.getByPK(nome);
        String resp = "";
        
        if (admin != null) {
        	admin.setNome(request.queryParams("nome"));
        	admin.setSenha(request.queryParams("senha"));
        	adminDAO.update(admin);
        	response.status(200); // success
            resp = "Admin (Nome " + admin.getNome() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Admin (Nome \" + admin.getNome() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object delete(Request request, Response response) {
        String nome = request.params(":nome").substring(1);
        Admin admin = adminDAO.getByPK(nome);
        String resp = "";       

        if (admin != null) {
            adminDAO.delete(nome);
            response.status(200); // success
            resp = "Admin (" + nome + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Admin (" + nome + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}