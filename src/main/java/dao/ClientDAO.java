package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Client;

public class ClientDAO {

	private Connection connection;
	private String sql;

	public ClientDAO() throws SQLException {
		connection = DatabaseConnection.createConnection();
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createClient(Client client) {
		sql = "INSERT INTO cliente (nome,login,senha,data_nascimento,nacionalidade) VALUES (?,?,?,?,?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, client.getNome());
			stmt.setString(2, client.getLogin());
			stmt.setString(3, client.getSenha());
			stmt.setString(5, client.getNacionalidade());

			Date data = Date.valueOf(client.getDataNascimento());

			stmt.setDate(4, data);

			stmt.executeUpdate();

			System.out.println("Cliente criado com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Client findClient(int id) {
		Client client = null;
		sql = "SELECT * FROM cliente WHERE id=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet r = stmt.executeQuery();

			if (r.next()) {
				client = new Client();
				client.setId(r.getInt("id"));
				client.setNome(r.getString("nome"));
				client.setLogin(r.getString("login"));
				client.setSenha(r.getString("senha"));
				client.setDataNascimento(r.getDate("data_nascimento").toLocalDate());
				client.setNacionalidade(r.getString("nacionalidade"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;
	}
	
	
	
	public List<Client> findClients() {

		String sql = "SELECT * FROM cliente";

		List<Client> clients = new ArrayList<Client>();

		ResultSet r = null;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			r = stmt.executeQuery();

			while (r.next()) {
				Client client = new Client();
				client = new Client();
				client.setId(r.getInt("id"));
				client.setNome(r.getString("nome"));
				client.setLogin(r.getString("login"));
				client.setSenha(r.getString("senha"));
				client.setDataNascimento(r.getDate("data_nascimento").toLocalDate());
				client.setNacionalidade(r.getString("nacionalidade"));

				clients.add(client);
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return clients;

	}
	
	
	

	public void updateClient(Client client) {
		sql = "UPDATE cliente SET nome = ?, login = ?, senha = ?, data_nascimento = ?,  nacionalidade = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, client.getNome());
			stmt.setString(2, client.getLogin());
			stmt.setString(3, client.getSenha());
			stmt.setString(5, client.getNacionalidade());

			Date sqlDate = Date.valueOf(client.getDataNascimento());

			stmt.setDate(4, sqlDate);
			stmt.setInt(6, client.getId());

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteClient(int id) {
		sql = "DELETE FROM cliente WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();

			System.out.println("Cliente Deletado com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

