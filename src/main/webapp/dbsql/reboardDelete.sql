--reboardDelete.sql
--exec reboardDelete();

create or replace procedure reboardDelete --프로시저 이름 
(
--매개변수
    p_groupNo number,
    p_step    number,
    p_no      number
)
is
--변수선언부
    cnt number;
    flag char;
begin
--처리할 내용
    --[1] 원본글인 경우
    if p_step=0 then
        --하위step에 답변이 존재하는지 체크
        select count(*) into cnt
        from reboard
        where groupno=p_groupNo;
        
        --1) 하위step에 답변이 존재하는 경우
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        --2) 하위step에 답변이 없는 경우
        else 
            delete from reboard
            where no=p_no;
        end if;
    
     --[2] 답변글인 경우         
    else
        delete from reboard
        where no=p_no;
        
        -- 원본의 delflag가 Y면서, 마지막 답글인지 체크.
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
        
        -- 다른 방법
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

        --바로 위의 로직을 간략화 한 것
--        select count(*) into cnt
--        from reboard
--        where groupno=p_groupNo;

--        if cnt=1 then
--            delete from reboard a
                -- 하나남은 글의 delflag가 Y라면 삭제
--            where exists (select 1 from reboard b
--                where a.no=b.no
--                and groupno=p_groupNo and delflag='Y' and step=0)
--        end if;
        
    end if;
    
    commit;

EXCEPTION
    WHEN OTHERS THEN
        raise_application_error(-20001, '답변형 게시판 글 삭제 실패!');
        ROLLBACK;
end;
