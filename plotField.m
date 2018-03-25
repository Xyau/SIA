[XY,Z] = p.generalize();
XYZ=[XY(:,1),XY(:,2),Z];
tri = delaunay(XYZ(:,1),XYZ(:,2));

trisurf(tri,XYZ(:,1),XYZ(:,2),XYZ(:,3))

hold on
