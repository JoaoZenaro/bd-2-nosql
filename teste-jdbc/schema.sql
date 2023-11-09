CREATE DATABASE unoesc_crud_jdbc;
USE unoesc_crud_jdbc;

CREATE TABLE produto (
    id_prod INT AUTO_INCREMENT PRIMARY KEY,
    nome_prod VARCHAR(50),
    data_cadastro DATE,
    quantidade INT,
    preco DECIMAL(10, 2),
    observacao VARCHAR(50)
) engine=InnoDB;

INSERT INTO produto
VALUES (1, 'Notebook', '2023-01-01', 5, 666, "Observacao teste"),
    (2, 'Smartphone', '2023-10-30', 15, 1234.56, "Observacao teste"),
    (3, 'TV', '2023-04-01', 2, 4999.99, "Observacao teste"),
    (4, 'Smartband', '2023-04-01', 50, 499.99, "Observacao teste");

SELECT * FROM produto;