--task 1
select min(sal), avg(sal), max(sal) from emp;

--task 2
select count(*) from emp where sal > 0;

--task 3
select deptno, sal from emp where sal >= 1000 and sal in ( select min(emp.sal) from emp group by deptno);
--task 4
select avg(sal) as avg_sal from emp inner join dept on emp.deptno = dept.deptno group by dept.deptno;

--task 5
select empno, ename from emp where mgr = (select mgr from emp where empno = 7698);

--task 6
select empno, ename, sal from emp where sal > (select avg(sal) from emp);

--task 7
select ename, deptno, sal from emp where deptno = any
                                         (select deptno from emp where comm > 0)
                                     and sal = any
                                         (select sal from emp where comm > 0);

--task 8
select ename, deptno, sal from emp where deptno = any
                                         (select deptno from emp where comm > 0)
                                      or sal = any
                                         (select sal from emp where comm > 0);
--task 9
select ename, empno from emp where sal > (select max(sal) from emp where job = 'CLERK');

--task 10
select count(empno) from emp where deptno = (select deptno from dept where loc = 'DALLAS');

--task 11
select count(empno) from emp where job = 'MANAGER';

--task 12
with recursive r as (
  select 1 as i, empno, mgr, ename from emp
  where empno = 7369

  union
  select i + 1 as i, emp.empno, emp.mgr, emp.ename
  from emp join r on r.mgr = emp.empno
) select i, empno, ename from r;

--task 13
with recursive r as (
  select 1 as i, empno, mgr, ename, deptno from emp
  where empno = 7369

  union
  select i + 1 as i, emp.empno, emp.mgr, emp.ename, emp.deptno
  from emp join r on r.mgr = emp.empno
) select i, empno, ename, r.deptno, dname from r join dept
  on r.deptno = dept.deptno;

--task 14
with main as
       (with recursive r as (
         select 1 as i, empno, mgr, ename, deptno from emp
         where empno = 7369

         union
         select i + 1 as i, emp.empno, emp.mgr, emp.ename, emp.deptno
         from emp join r on r.mgr = emp.empno
         ) select i, empno, ename, r.deptno, dname from r join dept
                                                               on r.deptno = dept.deptno)
select *, (array(select ename from main))[2:] from main;

-- СДЕЛАЙ 15