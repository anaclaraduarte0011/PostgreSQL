package com.ti2cc;

import java.sql.*;
import java.util.Scanner;

public class DAO {
	private Connection conexao;

	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "postgres";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "postgres";
		String password = "2307123";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirUsuarios() {
		boolean status = false;
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.println("Digite o código do usuário:");
			int codigo = scanner.nextInt();

			scanner.nextLine(); // Consuma a nova linha pendente

			System.out.println("Digite o login do usuário:");
			String login = scanner.nextLine();

			System.out.println("Digite a senha do usuário:");
			String senha = scanner.nextLine();

			System.out.println("Digite o sexo do usuário:");
			String sexo = scanner.nextLine();

			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuarios (codigo, login, senha, sexo) "
					+ "VALUES (" + codigo + ", '" + login + "', '" + senha + "', '" + sexo + "');");
			st.close();
			status = true;

			scanner.close(); // Feche o scanner
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarUsuarios(Usuarios usuarios) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuarios SET login = '" + usuarios.getLogin() + "', senha = '"
					+ usuarios.getSenha() + "', sexo = '" + usuarios.getSexo() + "'"
					+ " WHERE codigo = " + usuarios.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirUsuarios() {
		boolean status = false;
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.println("Digite o código do usuário a ser excluído:");
			int codigo = scanner.nextInt();

			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuarios WHERE codigo = " + codigo);
			st.close();
			status = true;

			scanner.close(); // Feche o scanner
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Usuarios[] getUsuarios() {
		Usuarios[] usuarios = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
			if (rs.next()) {
				rs.last();
				usuarios = new Usuarios[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					usuarios[i] = new Usuarios(rs.getInt("codigo"), rs.getString("login"),
							rs.getString("senha"), rs.getString("sexo").charAt(0));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
}