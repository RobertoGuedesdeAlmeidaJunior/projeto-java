package model.dao;

import java.util.*;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

    // TODO: Incluir dependencia de conexao
    private final Connection conexao;

    // TODO: Fazer inversão/injeção de dependencia
    public UsuarioDAO(Connection connection) {
        this.conexao = connection;
        init();
    }

    private void init() {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS fatec;\n";
        String createTable = "CREATE TABLE IF NOT EXISTS fatec.usuarios("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nome VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUEEsdra, "
                + "senha VARCHAR(100) NOT NULL);";

        try (Statement stm = conexao.createStatement()) {
            stm.execute(createDatabase);
            stm.execute(createTable);
        } catch (Exception e) {
            System.out.println("Erro ao criar a entidade usuarios. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public void salvar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO fatec.usuarios (nome, email, senha) "
                + "values ('%s', '%s', '%s')";

        try (Statement stm = conexao.createStatement()) {
            stm.execute(String.format(sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha()));
        } catch (Exception e) {
            System.out.println("Erro ao criar usuario. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Usuario user = new Usuario();
        String sql = "SELECT * FROM fatec.usuarios WHERE email = '%s'";

        try (Statement stm = conexao.createStatement();
             ResultSet resultado = stm.executeQuery(String.format(sql, email))) {

                while (resultado.next()) {
                    user.setId(resultado.getInt("id"));
                    user.setEmail(resultado.getString("email"));
                    user.setNome(resultado.getString("nome"));
                    user.setSenha(resultado.getString("senha"));
                }
        } catch (Exception e) {
            System.out.println("Erro ao encontrar o usuário. Erro: " + e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public void atualizar(Usuario usuario) {
        Usuario user = buscarPorEmail(usuario.getEmail());
        String sql = "UPDATE fatec.usuarios SET nome = '%s', email = '%s', senha = '%s' WHERE id = '%i'";

        try (Statement stm = conexao.createStatement()) {
            stm.executeUpdate(String.format(sql, user.getNome(), user.getEmail(), user.getSenha(), user.getId()));
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o usuário. Erro: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void excluir(Integer id) {
        String sql = "DELETE FROM fatec.usuarios WHERE id = " + id;

        try (Statement stm = conexao.createStatement()) {
            stm.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Erro ao deletar o usuário. Erro: " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM fatec.usuarios where email = ";
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stm = conexao.createStatement();
                ResultSet rst = stm.executeQuery(sql)) {
            while (rst.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rst.getString("nome"));
                usuario.setEmail(rst.getString("email"));
                usuario.setSenha(rst.getString("senha"));

                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception e) {
            System.out.println("Falha ao recuperar usuarios.");
        }

        return usuarios;
    }

}
