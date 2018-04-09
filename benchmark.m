config;
runNetwork;
p = ans;
inc = p.costError;

momentum=0.9;
runNetwork;
p = ans;
incMom = p.costError;

adaptative=1;
momentum=0;
runNetwork;
p = ans;
incAda = p.costError;

momentum=0.9;
runNetwork;
p = ans;
incMomAda = p.costError;


plot(inc,'color','blue');
hold on
plot(incMom,'color','red')
plot(incAda,'color','green')
plot(incMomAda,'color','magenta')