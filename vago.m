p = helper
p.learnAll;
[XY,Z] = p.testTrainedPoints;
[XYo , Zo] = load_data('../../../Downloads/terrain06.txt');
XYo = XYo(1 : floor(0.9 * size(XYo)(1)),:);
Zo = Zo(1 : floor(0.9 * size(Zo)(1)),:);
max(abs(Z .- Zo))
min(abs(Z .- Zo))
plot3(XYo(:,1),XYo(:,2),Zo,'.','color','red');
hold on 
plot3(XY(:,1),XY(:,2),Z,'.');