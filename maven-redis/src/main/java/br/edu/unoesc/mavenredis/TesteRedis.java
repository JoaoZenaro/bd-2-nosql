package br.edu.unoesc.mavenredis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class TesteRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis-server", 6379);

        System.out.println(jedis.ping());
        System.out.println(jedis.echo("Olá Redis!\n"));

        System.out.println(jedis.set("universidade", "Unoesc"));
        System.out.println(jedis.get("universidade"));

        if (jedis.del("universidade") == 1) {
            System.out.println("Chave excluida com sucesso!");
        }

        if (jedis.get("universidade") == null) {
            System.out.println("Chave não existe!");
        }

        Map<String, String> aluno = new HashMap<>();
        aluno.put("nome", "Gabriel Marafon");
        aluno.put("curso", "Computação");
        jedis.hset("aluno:01", aluno);

        Map<String, String> aluno2 = new HashMap<>();
        aluno2.put("nome", "João Marcelo Zenaro");
        aluno2.put("curso", "Computação");
        jedis.hset("aluno:02", aluno2);

        Map<String, String> aluno3 = new HashMap<>();
        aluno3.put("nome", "Victor Soligo");
        aluno3.put("curso", "Computação");
        jedis.hset("aluno:03", aluno3);

        System.out.println(jedis.hgetAll("aluno:01"));
        System.out.println(jedis.hgetAll("aluno:02"));
        System.out.println(jedis.hgetAll("aluno:03"));

        jedis.close();
    }
}