--task 1
drop function ch_sal(integer, numeric);

create or replace function ch_sal(integer, numeric(7,2)) returns float as $$
declare
  begin_t integer = extract(microseconds from clock_timestamp());
begin
  if (select empno from emp where empno = $1) is null then
    raise exception 'no such employee with given id';
  end if;
  update emp set sal = $2 where empno = $1;
  return (extract(microseconds from clock_timestamp()) - begin_t) / 1000;
end;
$$ language plpgsql;

--explain analyse
select ch_sal(7900,100.0);


create or replace function raise_sal (integer) returns float as $$
declare
  begin_t integer = extract(microseconds from clock_timestamp());
  employee emp%ROWTYPE;
  exp integer;
begin
  select * into employee from emp where emp.empno = $1;
  if employee is null then
    raise exception 'no such employee';
  end if;
  exp = date_part('year', now()) - date_part('year', employee.hiredate);
  if exp >= 15 then
    update emp set sal = employee.sal * 1.15 where emp.empno = $1;
  else
    if exp >= 10 then
      update emp set sal = employee.sal * 1.1 where emp.empno = $1;
    else
      update emp set sal = employee.sal * 1.05 where emp.empno = $1;
    end if;
  end if;
  return (extract(microseconds from clock_timestamp()) - begin_t) / 1000;
end;
$$ language plpgsql;

select raise_sal(7369);


create or replace function create_n_depts (integer, varchar(13), varchar(14)) returns float as $$
declare
  begin_t integer = extract(microseconds from clock_timestamp());
begin
    for i in 1..$1 loop
      insert into dept (deptno, dname, loc)
        values ((select nextval('dept_deptno_seq')), concat($3, i), concat($2, i));
    end loop;
    return (extract(microseconds from clock_timestamp()) - begin_t) / 1000;
end;
$$ language plpgsql;

select create_n_depts(3,'ALASKA', 'COOLING');

--task 2
select ch_sal(7900,100.0);

--task 3
create or replace function public.raise_sal_5k () returns integer as $$
declare
  curs refcursor;
  employee emp%ROWTYPE;
begin
  open curs scroll for select * from emp;
  loop
    fetch curs into employee;
    exit when not found;
    if (date_part('year', now()) - date_part('year', employee.hiredate)) > 9 then
      update emp set sal = employee.sal + 5000
        where current of curs;
    end if;
  end loop;
  return 0;
end;
$$ language plpgsql;

select raise_sal_5k();

create or replace function public.inc_sal_v2 () returns integer as $$
declare
  employ emp%ROWTYPE;
begin
  for employ in (select * from public.emp) loop
    if (date_part('year', now()) - date_part('year', employ.hiredate)) > 9 then
      update emp set sal = employ.sal + 5000 where emp.empno = employ.empno;
    end if;
  end loop;
  return 0;
end;
$$ language plpgsql;

select inc_sal_v2();

--task 4
--Instead of packages, use schemas to organize your functions into groups.
--Since there are no packages, there are no package-level variables either.
-- This is somewhat annoying. You can keep per-session state in temporary tables instead.

create or replace function my_pack.foo () returns integer as $$
    begin
      raise notice 'I AM THE FOO';
      return 0;
    end;
$$ language plpgsql;

select my_pack.foo();

--task 5

drop trigger if exists emp_trigger on emp;
drop function if exists emp_trigger();

create function emp_trigger () returns trigger as $$
begin
  insert into emp_change (change_name, change_date, change_time, change_salary)
  values (tg_op, now(), current_time,new.sal);
  return new;
end;
$$ language plpgsql;

create trigger emp_trigger after insert or update or delete on emp
  for each row execute procedure emp_trigger();

do $$
begin
  insert into emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
  values (1,'new','job',null,now(),123,10,10);
  update emp set sal = 1000 where empno = 1;
  delete from emp where empno = 1;
end$$;