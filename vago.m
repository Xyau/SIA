p = helper
p.learnIterative;
[XY,Z] = p.testTrainedPoints;
[XYo , Zo] = p.getOriginalData;
max(abs(Z .- Zo))
min(abs(Z .- Zo))
plot3(XYo(:,1),XYo(:,2),Zo,'.','color','red');
hold on
plot3(XY(:,1),XY(:,2),Z,'.');
