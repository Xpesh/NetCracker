-- task 1
select * from dept;

--task 2
select format('%s, %s', ename, job) emp, sal * 12 from emp;

--task 3
select ename, sal from emp where sal < 1500 or sal > 2850 order by ename;

--task 4
select ename, job, empno from emp where empno = 1111 or empno = 2222 or empno = 3333;

--task 5
select ename from emp where substring(ename, 1, 1) = 'a' or substring(ename, 1 ,1) = 'A';


--task 6
select ename, format('*%s', sal), empno from emp where length(ename) < 6;

--task 7
select ename,round((extract(days from (now() - hiredate)) / 30)::numeric, 0) from emp;

--task 8
select ename, sal, case when comm notnull then format('%s %s', comm, (sal + comm) * 12) end from emp;

--task 9
select ename, case when comm is null then 'No commissions' else to_char(comm, '99999FM') end from emp;

--task 10
select distinct job from emp where deptno = 30;

--task 11
select ename, job, emp.deptno from emp inner join dept on emp.deptno = dept.deptno where loc = 'DALLAS';

--task 12
select format('%s %s', employ.ename, employ.empno) employee, format('%s %s', mngr.ename, mngr.empno) manager
from emp as employ join emp as mngr using(deptno) where employ.job != 'MANAGER' and mngr.job = 'MANAGER';

--task 13
select dept.dname, dept.deptno, ename, empno  from emp join dept on emp.deptno = dept.deptno;

--task 14
select ename, deptno, sal, salgrade.grade from emp join salgrade on emp.sal < salgrade.hisal and emp.sal > salgrade.losal;

--task 15
select ename from emp where substring(ename, 2, 1) = '%';