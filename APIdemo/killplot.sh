
mypids=`ps aux | grep plot | awk {'print $2'}` 

for i in $mypids
do
  kill -9  $i
done



