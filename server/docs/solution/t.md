




select sum(num), policy_group, min(p), sum(num * p),
case policy_group 
when 100 then sum(num) div 3 * 10 + sum(num) mod 3 * min(p)
when 101 then sum(num * p) - sum(num) div 2 * max(p)
else -1 end D
from test_p group by policy_group














N:3,Y:10(10元3件)
+1(加一元多一件)
B:4,F:1(买4免1)
D:0.5(5折)
D:0.9,2:0.8(单件9折，第二件8折)
D:0.9,2+:0.8(单件9折，第二件及以上8折)
1:0.9,2:0.8(第一件9折，任两件8折)
1:0.9,2+:0.8(第一件9折，任两件及以上8折)
E:10,-3(满10元立减3元)
* 价低者免，价低者折