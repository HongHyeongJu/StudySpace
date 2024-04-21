-- 코드를 입력하세요
SELECT a.DATETIME	
from (select ROW_NUMBER() OVER (ORDER BY DATETIME desc) AS row_num, 
      DATETIME from ANIMAL_INS ) a
where row_num = 1;