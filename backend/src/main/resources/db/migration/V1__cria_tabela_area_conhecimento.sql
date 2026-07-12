CREATE TABLE area_conhecimento (
    id UUID not null,
    nome varchar(120) not null,
    descricao varchar(500),
    ativo boolean not null default true,
    criado_em timestamp not null default current_timestamp,
    atualizado_em timestamp,

    constraint pk_area_conhecimento primary key (id)
);

create unique index uk_area_conhecimento_nome_lower
on area_conhecimento (lower(trim(nome)));
