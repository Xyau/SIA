function retval = trainSingleMulti (weights, desired,learning,activation,actDerivative,V,h)
  layers = size(V)(2);
  delta = actDerivative(h{layers}) * (desired - V{layers});
  weights{size(weights)(2)} = weights{size(weights)(2)} + learning*delta*addUmbral(V{layers-1});
  for i = layers-1:-1:2
     aux = removeUmbral(weights{i})';
     delta = actDerivative(h{i}) .* (aux * delta')'; 
     weights{i-1} = weights{i-1}+ learning * delta' * addUmbral(V{i-1});
  endfor
  retval = weights;
endfunction