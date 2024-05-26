import java.util.*;
import java.sql.Connection;

import model.Usuario;
import model.dao.ConexaoFactory;
import model.dao.IUsuarioDAO;
import model.dao.UsuarioDAO;
import model.reposirory.*;
import services.UsuarioService;

public class MvcApp {
    public static void main(String[] args) throws Exception {

        /*
         * Este conjunto de instruções inicializaram as dependencias 
         * para o uso do serviço de contatos utilizando o repositório
         * com o SGBD MySQL.
         */
        Connection conexao = ConexaoFactory.getConexao();
        IUsuarioDAO dao = new UsuarioDAO(conexao);
        UsuarioRepository reposirory = new UsuarioRepositoryMySQLImpl(dao);

        UsuarioMemoriaRepositoryImpl repo = new UsuarioMemoriaRepositoryImpl();
        UsuarioService service = new UsuarioService(repo);
        
        /*
         * Utilize as leituras em console se preferir.
         * Scanner in = new Scanner(System.in);
         * System.out.println("Digite o nome: ");
         * String nome = in.nextLine();
         * System.out.println("Digite o e-mail: ");
         * String email = in.nextLine();
         * System.out.println("Digite o senha: ");
         * String senha = in.nextLine();
         */
        

        Usuario u1 = new Usuario(null, "Nome", "email@email.com", "123edc");

         // Chamada do metodo de persistencia
        // TODO: Descomente o trecho abaixo para persisitir em baco de dados e consulte o banco de dados
        //service.salvar(u1);

        //TODO: Criar mais 2 usuarios.
        Usuario u2 = new Usuario(null, "Joseph", "joseph@gmail.com", "@123senha");
        Usuario u3 = new Usuario(null, "Josephina", "josephphina@email.com", "@321senha");
        //TODO: Exibir os usuarios cadastrados
        System.out.println("Buscar todos os usuários");
        List<Usuario> usuarios = service.obterTodos();
        usuarios.forEach(u -> System.out.println(u));
        // TODO: Remover o primeiro usuario criado.
        service.excluir(service.buscarPorEmail(u1.getEmail()).getId());
        // TODO: Buscar e exibir o segundo usuario criado com base no e-mail
        System.out.println("Buscar Por Email");
        System.out.println(service.buscarPorEmail(u2.getEmail()));
        // TODO: Exibir os usuarios cadastrados
        usuarios.clear();
        System.out.println("Buscar todos os usuários");
        usuarios = service.obterTodos();
        usuarios.forEach(u -> System.out.println(u));
        // TODO: Altere o repositório MySQL pelo repositório em memória    
    }
}
