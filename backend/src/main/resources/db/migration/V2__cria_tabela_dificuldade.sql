CREATE TABLE dificuldade (
    id UUID not null,
    id_area_conhecimento UUID not null,
    tema varchar(120) not null,
    descricao varchar(500),
    contexto varchar(500),
    objetivo varchar(500) not null,
    prioridade varchar(20) not null default 'BAIXA',
    status varchar(20) not null default 'PENDENTE',
    criado_em timestamp not null default current_timestamp,
    atualizado_em timestamp,

    constraint pk_dificuldade primary key (id),
    constraint fk_dificuldade_area_conhecimento foreign key (id_area_conhecimento) references area_conhecimento (id)
);
