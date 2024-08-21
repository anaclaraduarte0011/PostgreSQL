package com.ti2cc;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {

		DAO dao = new DAO();

		dao.conectar();

		while (true) {
			System.out.println("\nDigite o valor da ação a ser realizada:");
			System.out.println("1) Insere um elemento na tabela");
			System.out.println("2) Atualiza usuarios");
			System.out.println("3) Exclui usuarios");
			System.out.println("4) Mostra usuarios");
			System.out.println("Digite qualquer outro valor para sair desta operação");

			try (Scanner scanner = new Scanner(System.in)) {
				if (scanner.hasNextInt()) {
					int entrada = scanner.nextInt();

					Usuarios[] usuarios;
					switch (entrada) {
						case 1:

							// Mostra todos os usuários
							usuarios = dao.getUsuarios();
							System.out.println("==== Mostrando todos os usuarios === ");

							if (usuarios != null)
								for (Usuarios usuario : usuarios) {
									System.out.println("Codigo:" + usuario.getCodigo() + " | " +
											"Login:" + usuario.getLogin() + " | " +
											"Sexo:" + usuario.getSexo());
								}
							else
								System.out.println("\nNão foram encontrados registros de Usuários\n");

							// Insere um elemento na tabela
							if (dao.inserirUsuarios()) {
								System.out.println("Inserido com sucesso");
							}
							break;

						case 2:
							// Atualiza usuários
							Usuarios usuarioAtualizar = new Usuarios(11, "caio", "caio", 'M');
							usuarioAtualizar.setSenha("nova senha");
							dao.atualizarUsuarios(usuarioAtualizar);
							break;

						case 3:
							// Exclui usuários
							dao.excluirUsuarios();
							break;

						case 4:
							// Mostra todos os usuários
							usuarios = dao.getUsuarios();
							if (usuarios != null)
								for (Usuarios usuario : usuarios) {
									System.out.println("Codigo:" + usuario.getCodigo() + " | " +
											"Login:" + usuario.getLogin() + " | " +
											"Sexo:" + usuario.getSexo());
								}
							else
								System.out.println("\nNão foram encontrados registros de Usuários\n");
							break;

						default:
						scanner.close(); // Close the scanner before exiting
							return;
					}
				} else {
					scanner.close(); // Close the scanner before exiting
					return;
				}
			}
		}
	}
}