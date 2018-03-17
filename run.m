function [] = run()
  E = buildBinaryEntries(3);
  a = {};
  a{1} = rand(3,4)*2-1;
  a{2} = rand(1,4)*2-1;

  f = @(x) x;
  f_prima = @(x) ones(size(x));
  xor = @(x) bitxor(bitxor(x(1),x(2)),x(3));
  xor_activated = @(x) f(bitxor(bitxor(x(1),x(2)),x(3)));
  
  pair = @(x) mod(x(1)+x(2)+x(3),2);
  pair_activated = @(x) f(mod(x(1)+x(2)+x(3),2));
  
  for eta = 0.05:0.05:1
    for k = 1:50
      for i = 1 : size(E)(1)
        [V,h] = sumaMultiActivation(a,E(i,:),f);
        a = trainSingleMulti(a, pair_activated(E(i,:)),0.05,f,f_prima,V,h);
       endfor
    endfor
    correct =8;
    for i = 1 : size(E)(1)
        h = sumaMulti(a,E(i,:)); 
        correct -= (h{size(V)(2)} > 0 -  pair(E(i,:)));
    endfor
    printf("eta %d -> %d\n",eta,correct * 100 / 8);
   endfor

endfunction