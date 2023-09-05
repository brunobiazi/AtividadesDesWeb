INSERT INTO
    usuariocliente (cpf, data_de_nascimento, sexo, telefone)
VALUES
    (
        '02096725526',
        '1999-06-16',
        'Feminino',
        '(79) 99175-4690'
    );

INSERT INTO
    usuariogeral (cpf_cnpj, email, hierarquia, senha, username)
VALUES
    (
        '02096725526',
        'nayrakalinesv@gmail.com',
        1,
        'kaline',
        'kaline'
    );

INSERT INTO
    usuariocliente (cpf, data_de_nascimento, sexo, telefone)
VALUES
    (
        '22222222222',
        '1992-11-10',
        'Masculino',
        '(31) 2222-2222'
    );

INSERT INTO
    usuariogeral (cpf_cnpj, email, hierarquia, senha, username)
VALUES
    (
        '22222222222',
        'usuario2@exemplo.com',
        0,
        'senha2',
        'usuario2'
    );

INSERT INTO
    usuariolocadora (cnpj, cidade)
VALUES
    ('12345678901234', 'São Paulo');

INSERT INTO
    usuariogeral (cpf_cnpj, email, hierarquia, senha, username)
VALUES
    (
        '12345678901234',
        'usuarioLocadora1@exemplo.com',
        1,
        'senhal1',
        'usuariolocadora1'
    );

INSERT INTO
    locacoes (cnpj, cpf, horadevolucao, horalocacao)
VALUES
    (
        '12345678901234',
        '02096725526',
        '2023-08-07 18:00:00',
        '2023-09-07 10:00:00'
    );