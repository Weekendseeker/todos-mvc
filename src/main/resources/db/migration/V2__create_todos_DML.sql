INSERT INTO public.roles (id, role) VALUES (1, 'USER');

INSERT INTO public.accounts (id, username, password, active) VALUES (27, 'Marvin', '1', true);
INSERT INTO public.accounts (id, username, password, active) VALUES (28, 'George', '1', true);

INSERT INTO public.todos (id, description, isdone) VALUES (117, 'Just Do It!', false);
INSERT INTO public.todos (id, description, isdone) VALUES (118, 'Сходить куда нибудь', false);
INSERT INTO public.todos (id, description, isdone) VALUES (119, 'Приготовить ужин', false);
INSERT INTO public.todos (id, description, isdone) VALUES (120, 'Разработать модуль', false);
INSERT INTO public.todos (id, description, isdone) VALUES (121, 'Пробежка', false);
INSERT INTO public.todos (id, description, isdone) VALUES (122, 'Чтение Spring in Action', false);
INSERT INTO public.todos (id, description, isdone) VALUES (123, 'Ретроспектива', false);

INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (117, 27, 117);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (118, 27, 118);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (119, 27, 119);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (120, 27, 120);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (121, 28, 121);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (122, 28, 122);
INSERT INTO public.todos_in_account (id_row, id_account, id_plan) VALUES (123, 28, 123);

INSERT INTO public.user_role (user_id, role_id) VALUES (27, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (28, 1);
