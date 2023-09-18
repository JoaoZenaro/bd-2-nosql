package br.edu.unoesc.mongolocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class TesteMongoDB {
	private static final Logger LOG = LoggerFactory.getLogger(TesteMongoDB.class);
	private static final String BANCO = "unoesc2023";
	private static final String CONEXAO = System.getenv("MONGODB_URL"); //"mongodb://localhost:27017";
	
	private static void listarBancos(MongoClient mongoClient) {
		LOG.info("Conectado ao servidor 'localhost' com sucesso!\n");
		System.out.println("=== Lista dos bancos de dados no servidor local");
		
		mongoClient.listDatabaseNames().iterator().forEachRemaining(System.out::println);
		System.out.println();
	}

	private static void listarColecoes(MongoDatabase database) {
		LOG.info("Conectado ao banco de dados '{}' com sucesso!\n", BANCO);
		System.out.println("=== Lista de coleções no servidor local");
		
		MongoIterable<String> collections = database.listCollectionNames();		
		collections.forEach(System.out::println);
		System.out.println();
	}
	
	public static void main(String[] args) {
		try {
			MongoClient mongoClient = MongoClients.create(CONEXAO);
			listarBancos(mongoClient);

			MongoDatabase database = mongoClient.getDatabase(BANCO);
			listarColecoes(database);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
