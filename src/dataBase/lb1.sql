drop table if exists emp;
drop table if exists dept;
drop table if exists salgrade;
drop sequence if exists dept_deptno_seq;
drop table if exists emp_change;

create table dept(
                   DEPTNO numeric(2) primary key,
                   DNAME varchar(14),
                   LOC varchar(13)
);

create sequence dept_deptno_seq as integer
minvalue 1
maxvalue 99;

alter sequence dept_deptno_seq owned by dept.DEPTNO;

create table emp(
                  EMPNO serial primary key,
                  ENAME varchar(10),
                  JOB varchar(9),
                  MGR numeric(4),
                  HIREDATE date,
                  SAL numeric(7, 2),
                  COMM numeric(7, 2),
                  DEPTNO numeric(2) references dept(DEPTNO)
);

create table salgrade(
                       GRADE integer,
                       LOSAL integer,
                       HISAL integer
);

insert into dept values (10, 'ACCOUNTING', 'NEW YORK');
insert into dept values (20, 'RESEARCH',   'DALLAS');
insert into dept values (30, 'SALES',      'CHICAGO');
insert into dept values (40, 'OPERATIONS', 'BOSTON');

insert into emp values (7369, 'SMITH',  'CLERK',     7902, TO_DATE('17-DEC-1980', 'DD-MON-YYYY'),  800, null, 20);
insert into emp values (7499, 'ALLEN',  'SALESMAN',  7698, TO_DATE('20-FEB-1981', 'DD-MON-YYYY'), 1600,  300, 30);
insert into emp values (7521, 'WARD',   'SALESMAN',  7698, TO_DATE('22-FEB-1981', 'DD-MON-YYYY'), 1250,  500, 30);
insert into emp values (7566, 'JONES',  'MANAGER',   7839, TO_DATE('2-APR-1981', 'DD-MON-YYYY'),  2975, null, 20);
insert into emp values (7654, 'MARTIN', 'SALESMAN',  7698, TO_DATE('28-SEP-1981', 'DD-MON-YYYY'), 1250, 1400, 30);
insert into emp values (7698, 'BLAKE',  'MANAGER',   7839, TO_DATE('1-MAY-1981', 'DD-MON-YYYY'),  2850, null, 30);
insert into emp values (7782, 'CLARK',  'MANAGER',   7839, TO_DATE('9-JUN-1981', 'DD-MON-YYYY'),  2450, null, 10);
insert into emp values (7788, 'SCOTT',  'ANALYST',   7566, TO_DATE('09-DEC-1982', 'DD-MON-YYYY'), 3000, null, 20);
insert into emp values (7839, 'KING',   'PRESIDENT', null, TO_DATE('17-NOV-1981', 'DD-MON-YYYY'), 5000, null, 10);
insert into emp values (7844, 'TURNER', 'SALESMAN',  7698, TO_DATE('8-SEP-1981', 'DD-MON-YYYY'),  1500,    0, 30);
insert into emp values (7876, 'ADAMS',  'CLERK',     7788, TO_DATE('12-JAN-1983', 'DD-MON-YYYY'), 1100, null, 20);
insert into emp values (7900, 'JAMES',  'CLERK',     7698, TO_DATE('3-DEC-1981', 'DD-MON-YYYY'),   950, null, 30);
insert into emp values (7902, 'FORD',   'ANALYST',   7566, TO_DATE('3-DEC-1981', 'DD-MON-YYYY'),  3000, null, 20);
insert into emp values (7934, 'MILLER', 'CLERK',     7782, TO_DATE('23-JAN-1982', 'DD-MON-YYYY'), 1300, null, 10);

insert into salgrade values (1,  700, 1200);
insert into salgrade values (2, 1201, 1400);
insert into salgrade values (3, 1401, 2000);
insert into salgrade values (4, 2001, 3000);
insert into salgrade values (5, 3001, 9999);

create table emp_change (
                          change_name varchar(6),
                          change_date date,
                          change_time time with time zone,
                          change_salary numeric(7, 2)
);