-- fill users
insert into public.user
select i                                               as id,
       concat('test', i)                               as name,
       date(concat('2000-05-', lpad(i::text, 2, '0'))) as date_of_birth,
       md5('12345')                                    as password
from generate_series(1, 25) i;
select setval('user_id_seq', 25, true);

-- fill accounts
insert into public.account
select i as id,
       i as user_id,
       b as initial_balance,
       b as balance
from (select i, round(random() * 10000) / 100 b from generate_series(1, 25) i) as q;
select setval('account_id_seq', 25, true);

-- fill emails
insert into public.email_data
select i                              as id,
       i                              as user_id,
       concat('test', i, '@mail.com') as email
from generate_series(1, 25) i;
select setval('email_data_id_seq', 25, true);

-- fill phones
insert into public.phone_data
select i                                          as id,
       i                                          as user_id,
       concat('122233344', LPAD(i::text, 2, '0')) as phone
from generate_series(1, 25) i;
select setval('phone_data_id_seq', 25, true);
