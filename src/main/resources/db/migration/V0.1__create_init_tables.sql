create table public.customer
(
    id serial constraint customer_pk primary key,
    email varchar(64) not null,
    name varchar(64) not null,
    phone varchar(16) not null,
    address varchar(256) not null,
    birthday  varchar,
    gender integer not null,
    auth_id uuid not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    deleted_at timestamp
);

comment on table public.customer is '회원 테이블';
comment on column public.customer.id is '회원 고유 ID,PK';
comment on column public.customer.email is '로그인용 이메일';
comment on column public.customer.name is '회원 이름';
comment on column public.customer.phone is '회원 연락처';
comment on column public.customer.address is '회원 주소';
comment on column public.customer.gender is '회원 성별';
comment on column public.customer.auth_id is '회원 인증용 ID';


