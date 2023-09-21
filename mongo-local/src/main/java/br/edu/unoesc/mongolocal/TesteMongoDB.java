package br.edu.unoesc.mongolocal;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class TesteMongoDB {
	private static final Logger LOG = LoggerFactory.getLogger(TesteMongoDB.class);
	private static final String BANCO = "unoesc2023";
	private static final String CONEXAO = "mongodb://localhost:27017/";
	// docker run -d -p 27017:27017 --name mongodb mongo:latest
	
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

	private static void listarDocumentos(MongoCollection<Document> colecao) {	
		FindIterable<Document> cursor = colecao.find();
		
		System.out.println("=== Todos os documentos");
		listarDados(cursor);
	}
	
	private static void listarDados(FindIterable<Document> cursor) {
		try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
			while (cursorIterator.hasNext()) {
				System.out.println(cursorIterator.next());
			}
		}
		System.out.println();
	}
	
	private static void listarDocumentosComFiltro(MongoCollection<Document> colecao) {	
		// Filtra por parte do nome ("ano") e todo o sobrenome ("Silva")	
		Bson filter = and(Filters.regex("nome", "as", "i"),
                		  eq("sobrenome", "Ferreira"));
		
		List<Document> lista = colecao.find(filter).into(new ArrayList<>());
		System.out.println("=== Foram filtrados " + lista.size() + " documento(s)");
		lista.forEach(System.out::println);
		System.out.println();
	}
	
	private static void listarDocumentosCamposFiltro(MongoCollection<Document> colecao, String[] filtro) {
		System.out.print("=== Documentos parciais (nome e sobrenome) filtrados | ");
		System.out.println(filtro[0] + " = " + filtro[1]);
		
		Bson projecao = fields(include("nome", "sobrenome"), excludeId());
		FindIterable<Document> lista = colecao.find(Filters.eq(filtro[0], filtro[1])).projection(projecao);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try (final MongoCursor<Document> cursorIterator = lista.cursor()) {
			while (cursorIterator.hasNext()) {
				System.out.println(gson.toJson(cursorIterator.next()) + "\n");
			}
		}
	}

	private static void listarEnderecos(MongoCollection<Document> colecao) {
		System.out.println("=== Endereços");
		
		Bson projecao = fields(include("nome", "endereco"), excludeId());
		FindIterable<Document> cursor = colecao.find(and(gte("endereco.numero", 500), lte("endereco.numero", 700))).projection(projecao);
		
		listarDados(cursor);
	}
	
	private static void listarNascimentos(MongoCollection<Document> colecao) {
		System.out.println("=== Datas de nascimento");
		
		Bson projecao = fields(include("nome", "data_nascimento"), excludeId());
		FindIterable<Document> cursor = colecao.find(eq("data_nascimento", LocalDateTime.of(1990, 10, 15, 16, 30, 0))).projection(projecao);

		listarDados(cursor);
	}

	private static void listarHabilidades(MongoCollection<Document> colecao) {
		System.out.println("=== Habilidades");
		
		Bson filtro = Filters.elemMatch("habilidades", Filters.and(Filters.eq("nome", "espanhol"), Filters.eq("nível", "básico")));
		
		Bson projecao = Projections.fields(Projections.include("nome", "habilidades"), Projections.excludeId());

		FindIterable<Document> cursor = colecao.find(filtro).projection(projecao);
		
		listarDados(cursor);
	}

	private static void inserir(MongoCollection<Document> colecao) {
		/*
			{
			  "_id": {UUID},
			  "nome": "Herculano",
			  "sobrenome": "De Biasi",
			  "data_nascimento": "Jun 6, 1975, 2:00:00 AM",
			  "curso": {
			    "nome": "Ciência da Computação"
			  },
			  "notas": [
			    10,
			    9,
			    4.5
			  ],
			  "endereco": {
			    "rua": "Av.Barão do Rio Branco",
			    "numero": 530,
			    "bairro": "Centro"
			  },
			  "telefones": [
			    "(49) 9 9934-7105",
			    "(49) 3563-3233"
			  ],
			  "habilidades": [
			    {
			      "nome": "inglês",
			      "nível": "avançado"
			    },
			    {
			      "nome": "piano",
			      "nível": "básico"
			    }
			  ]
			}
	    */
		
		Document endereco = new Document("rua", "Av.Barão do Rio Branco")
				.append("numero", 530)
				.append("bairro", "Centro");
				
		List<String> telefones = Arrays.asList("(49) 9 9934-7105", "(49) 3563-3233");
		List<Document> habilidades = Arrays.asList(
				new Document("nome", "inglês")
					.append("nível", "avançado"),
				new Document("nome", "piano")
					.append("nível", "básico"));

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Document pessoa = new Document("_id", new ObjectId())
				.append("nome", "Herculano")
				.append("sobrenome", "De Biasi")
				.append("data_nascimento", LocalDateTime.of(1975, 6, 6, 2, 0, 0))
				.append("curso", new Document("nome", "Ciência da Computação"))
				.append("notas", Arrays.asList(10, 9, 4.5))
				.append("endereco", endereco) 
				.append("telefones", telefones)
				.append("habilidades", habilidades);

		colecao.insertOne(pessoa);
	}

	private static void listarUmDocumentoCompleto(MongoCollection<Document> colecao, String[] filtro) {
		Document doc;
		
		System.out.println("=== Documento completo");
		doc = colecao.find(eq(filtro[0], filtro[1])).first();
		
		if (doc != null) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gson.toJson(doc));
		} else {
			System.out.println("Documento buscado não foi encontrado!");
		}
		
		System.out.println();
	}

	public static void main(String[] args) {
		try {
			MongoClient mongoClient = MongoClients.create(CONEXAO);
			listarBancos(mongoClient);

			// MongoDatabase database = mongoClient.getDatabase(BANCO);
			// listarColecoes(database);

			// MongoCollection<Document> pessoas = database.getCollection("pessoas");
			// listarDocumentos(pessoas);
			
			// listarDocumentosComFiltro(pessoas);
			// listarDocumentosCamposFiltro(pessoas, new String[] { "sobrenome", "Silva" });

			// listarEnderecos(pessoas);
			// listarNascimentos(pessoas);

			// listarHabilidades(pessoas);

			// inserir(pessoas);
			// listarUmDocumentoCompleto(pessoas, new String[] { "nome", "Herculano" });
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
