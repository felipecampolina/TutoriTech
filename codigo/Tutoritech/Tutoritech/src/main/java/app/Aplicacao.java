package app;

import static spark.Spark.*;

import java.util.HashMap;
import java.security.*;

import model.Admin;
import model.Usuario;
import service.CategoriaService;
import service.FrontService;
import service.AdminService;
import service.TutorialService;
import service.UsuarioService;


public class Aplicacao  {
	
	private static FrontService 	 frontService     = new FrontService();
	private static CategoriaService  categoriaService = new CategoriaService();
	private static AdminService      adminService     = new AdminService();
	private static TutorialService   tutorialService  = new TutorialService();
	private static UsuarioService    usuarioService   = new UsuarioService();
	
	public static HashMap<String, Usuario> users  = new HashMap<>();
	public static HashMap<String, Admin>   admins = new HashMap<>();
	
    public static void main(String[] args) throws Exception {
    	
    	port(6789);
        
        staticFiles.location("/public");
 // -------------------------------------------------------- LOGIN/LOGOUT ADM
        
        get("frontend/logoutadm", (request, response) -> frontService.logoutAdm(request, response));
        
        after("frontend/logoutadm", (request, response) -> { response.redirect("/frontend/loginAdm/login"); });
        
        before("/menuadm", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (admins.get(chave) == null )
            	response.redirect("/frontend/loginAdm/login");
        });
        
        before("/categoria/*", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (admins.get(chave) == null )
            	response.redirect("/frontend/loginAdm/login");
        });
        
        before("/admin/*", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (admins.get(chave) == null )
            	response.redirect("/frontend/loginAdm/login");
        });
        
        before("/tutorial/*", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (admins.get(chave) == null )
            	response.redirect("/frontend/loginAdm/login");
        });
        
        before("/usuario/*", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (admins.get(chave) == null )
            	response.redirect("/frontend/loginAdm/login");
        });
        
 // -------------------------------------------------------- LOGIN/LOGOUT USUARIO
        
        before("frontend/cat-premium/index", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (users.get(chave) == null )
            	response.redirect("/frontend/inicio-login/login");
            });
        
        before("frontend/tipos-para-cada-categoria/:id", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
            if (users.get(chave) == null )
            	response.redirect("/frontend/inicio-login/login");
            });

        before("frontend/tutorial/:id", (request, response) -> {
        	String chave = request.userAgent() + " " + request.ip();
        	int id = Integer.parseInt(request.params(":id"));
        	if(  id != 8 && id != 9)
        		if (users.get(chave) == null )
        			response.redirect("/frontend/inicio-login/login");
            });
        
        before("frontend/inicio-login/inicio", (request, response) -> { frontService.logout(request, response); });
        
        before("frontend/inicio-login/login", (request, response) -> { frontService.logout(request, response); });
        
        before("frontend/compra/comprar", (request, response) -> { frontService.logout(request, response); });
        
        get("frontend/logout", (request, response) -> frontService.logout(request, response));
        
        after("frontend/logout", (request, response) -> { response.redirect("/frontend/inicio-login/inicio"); });
        
 // -------------------------------------------------------- FrontEnd
        
        get("frontend/tut-gratuitos/index", (request, response) -> frontService.tut_gratuito(request, response));
        
        get("frontend/tutorial/:id", (request, response) -> frontService.tutorial(request, response));
        
        get("frontend/cat-premium/index", (request, response) -> frontService.cat_premium(request, response));
        
        get("frontend/tipos-para-cada-categoria/:id", (request, response) -> frontService.tipos_para_cada_categoria(request, response));
        
        get("frontend/compra/comprar", (request, response) -> frontService.makeCadastro(request, response));
        
        post("frontend/compra/comprar", (request, response) -> frontService.cadastrar(request, response));
        
        get("frontend/inicio-login/login", (request, response) -> frontService.makeLogin(request, response));
        
        post("frontend/inicio-login/login", (request, response) -> frontService.tryLogin(request, response));
        
        get("frontend/inicio-login/inicio", (request, response) -> frontService.makeInicio(request, response));
        
        get("frontend/EntreEmContato/contato", (request, response) -> frontService.makeContato(request, response));
        
        get("frontend/loginAdm/login", (request, response) -> frontService.makeLoginAdm(request, response));
        
        post("frontend/loginAdm/login", (request, response) -> frontService.tryLoginAdm(request, response));
        
        get("/menuadm", (request, response) -> frontService.makeMenuAdm(request, response));
        
 // -------------------------------------------------------- Categoria 

        
        post("/categoria/insert", (request, response) -> categoriaService.insert(request, response));

        get("/categoria/:id", (request, response) -> categoriaService.get(request, response));
        
        get("/categoria/list/:orderby", (request, response) -> categoriaService.getAll(request, response));

        get("/categoria/update/:id", (request, response) -> categoriaService.getToUpdate(request, response));
        
        post("/categoria/update/:id", (request, response) -> categoriaService.update(request, response));
           
        get("/categoria/delete/:id", (request, response) -> categoriaService.delete(request, response));
        
 // -------------------------------------------------------- Admin
        
        post("/admin/insert", (request, response) -> adminService.insert(request, response));

        get("/admin/:nome", (request, response) -> adminService.get(request, response));
        
        get("/admin/list/:orderby", (request, response) -> adminService.getAll(request, response));

        get("/admin/update/:nome", (request, response) -> adminService.getToUpdate(request, response));
        
        post("/admin/update/:nome", (request, response) -> adminService.update(request, response));
           
        get("/admin/delete/:nome", (request, response) -> adminService.delete(request, response));
        
 // -------------------------------------------------------- Tutorial
        
        post("/tutorial/insert", (request, response) -> tutorialService.insert(request, response));

        get("/tutorial/:id", (request, response) -> tutorialService.get(request, response));
        
        get("/tutorial/list/:orderby", (request, response) -> tutorialService.getAll(request, response));

        get("/tutorial/update/:id", (request, response) -> tutorialService.getToUpdate(request, response));
        
        post("/tutorial/update/:id", (request, response) -> tutorialService.update(request, response));
           
        get("/tutorial/delete/:id", (request, response) -> tutorialService.delete(request, response));
        
 // -------------------------------------------------------- Usuario
        
        post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));

        get("/usuario/:email", (request, response) -> usuarioService.get(request, response));
        
        get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));

        get("/usuario/update/:email", (request, response) -> usuarioService.getToUpdate(request, response));
        
        post("/usuario/update/:email", (request, response) -> usuarioService.update(request, response));
           
        get("/usuario/delete/:email", (request, response) -> usuarioService.delete(request, response));
             
        
    }
}