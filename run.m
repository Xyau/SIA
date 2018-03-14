function [] = run()
  E = buildBinaryEntries(3);
  a = {};
  a{1} = rand(2,4);
  a{2} = rand(1,3);
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x)**2;
  pair = @(x) mod(x(1)+x(2)+x(3),2);
  for k = 1:30
    for i = 1 : size(E)(1)
      V = sumaMultiActivation(a,E(i,:),f);
      a = trainSingleMulti(a, pair(E(i,:)),0.01,f,f_prima,V);
     endfor
  endfor
  for i = 1 : size(E)(1)
      V = sumaMulti(a,E(i,:)); 
      printf("%d %d %d -> %d\n",E(i,1),E(i,2),E(i,3),V{size(V)(2)} > 0);
  endfor

endfunction