INSERT INTO public.cities ("name") VALUES
       ('Moscow'),
       ('Saint Petersburg'),
       ('Kazan');

INSERT INTO public.roles ("name") VALUES
      ('Developer'),
      ('Tester'),
      ('QA'),
      ('PM');

INSERT INTO public.users ("name",city_id) VALUES
      ('Alex', 2),
      ('Cynthia', 3),
      ('Hank', 1),
      ('Stuart', 2),
      ('Eric', 1);

INSERT INTO public.users_roles (user_id,role_id) VALUES
      (1, 1),
      (1, 4),
      (2, 2),
      (3, 1),
      (4, 3),
      (5, 2);