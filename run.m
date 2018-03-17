function [] = run()
  E = buildBinaryEntries(3);
  a = {};
  a{1} = rand(8,4);
  a{2} = rand(5,9);
  a{3} = rand(1,6);


  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  

  pair = @(x) mod(x(1)+x(2)+x(3),2);
  pair_activated = @(x) f(mod(x(1)+x(2)+x(3),2));
  
  and = @(x) x(1) & x(2) & x(3);
  and_activated = @(x) f(x(1) & x(2) & x(3));
  
  for eta = 0.05:0.05:1
    for k = 1:10
      for i = 1 : size(E)(1)
        [V,h] = sumaMultiActivation(a,E(i,:),f);
        a = trainSingleMulti(a, pair_activated(E(i,:)),0.05,f,f_prima,V,h);
       endfor
    endfor
    correct =8;
    for i = 1 : size(E)(1)
        h = sumaMulti(a,E(i,:));      
        if (((h{size(V)(2)} > 0) -  pair(E(i,:))) != 0)
          correct -= 1;
        endif
    endfor
    if ((correct * 100 / 8) == 100)
      printf("eta %d -> %d\n",eta,correct * 100 / 8);
    endif
   endfor

endfunction