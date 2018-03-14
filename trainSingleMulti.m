function retval = trainSingle (weights, entry,desired,learning,activation,actDerivative,algorithm)
  suma = suma(weights,entry);
  out =activation(suma);
  deltas = [];
  for i = [1:size(desired)(2)]
    deltas = [deltas; learning*(algorithm(desired(i),out(i),suma(i),actDerivative)).*entry;];
  endfor
  retval = weights + deltas;
endfunction