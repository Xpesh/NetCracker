--task 1
create or replace function ch_sal(integer, numeric(7,2)) returns boolean as $$
  begin
    if (select empno from emp where empno = $1) is null then
      raise notice 'no such employee with given id';
      return false;
    end if;
    update emp set sal = $2 where empno = $1;
    return true;
  end;
  $$ language plpgsql;

 select ch_sal(7499,99999);

do language plpgsql $$
  begin
    if (select empno from emp where empno = 7369) is null then
      raise notice 'no such employee with given id';
    else
      update emp set sal = 99999 where empno = 7369;
    end if;
  end;
$$;

--task 2
do language plpgsql $$
  declare
    employ_id integer = 7934;
    employee emp%ROWTYPE;
    exp integer;
  begin
    select * into employee from emp where emp.empno = employ_id;
    if employee is null then
      raise notice 'no such employee';
      return;
    end if;
    exp = date_part('year', now()) - date_part('year', employee.hiredate);
    if exp >= 15 then
      update emp set sal = employee.sal * 1.15 where emp.empno = employ_id;
    else
      if exp >= 10 then
        update emp set sal = employee.sal * 1.1 where emp.empno = employ_id;
      else
        update emp set sal = employee.sal * 1.05 where emp.empno = employ_id;
      end if;
    end if;
  end;
$$;

--task 3
do language plpgsql $$
  declare
    dept_amount integer = 3;
    location varchar(13) = 'ALASKA';
    dept_name varchar(14) = 'COOLING';
  begin
    for i in 1..dept_amount loop
      insert into dept (deptno, dname, loc)
        values ((select nextval('dept_deptno_seq')), concat(dept_name, i), concat(location, i));
    end loop;
  end;
$$;