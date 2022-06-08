--reboardDelete.sql
--exec reboardDelete();

create or replace procedure reboardDelete --���ν��� �̸� 
(
--�Ű�����
    p_groupNo number,
    p_step    number,
    p_no      number
)
is
--���������
    cnt number;
    flag char;
begin
--ó���� ����
    --[1] �������� ���
    if p_step=0 then
        --����step�� �亯�� �����ϴ��� üũ
        select count(*) into cnt
        from reboard
        where groupno=p_groupNo;
        
        --1) ����step�� �亯�� �����ϴ� ���
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        --2) ����step�� �亯�� ���� ���
        else 
            delete from reboard
            where no=p_no;
        end if;
    
     --[2] �亯���� ���         
    else
        delete from reboard
        where no=p_no;
        
        -- ������ delflag�� Y�鼭, ������ ������� üũ.
        select delflag into flag
        from reboard
        where groupno=p_groupNo and step=0;
        
        select count(*) into cnt
        from reboard
        where groupno=p_groupNo;
        
        if flag='Y' and cnt<2 then
            delete from reboard
            where groupno=p_groupNo and step=0;
        end if;
        
        -- �ٸ� ���
--        select count(*) into cnt
--        from reboard
--        where groupno=p_groupNo;
--        
--        if cnt=1 then
--            select count(*) into cnt from reboard
--            where group=p_groupNo and delflag='Y' and step=0;
--            
--            if cnt=1 then
--                delete from reboard
--                where groupno=p_groupNo; 
--        end if;

        --�ٷ� ���� ������ ����ȭ �� ��
--        select count(*) into cnt
--        from reboard
--        where groupno=p_groupNo;

--        if cnt=1 then
--            delete from reboard a
                -- �ϳ����� ���� delflag�� Y��� ����
--            where exists (select 1 from reboard b
--                where a.no=b.no
--                and groupno=p_groupNo and delflag='Y' and step=0)
--        end if;
        
    end if;
    
    commit;

EXCEPTION
    WHEN OTHERS THEN
        raise_application_error(-20001, '�亯�� �Խ��� �� ���� ����!');
        ROLLBACK;
end;
