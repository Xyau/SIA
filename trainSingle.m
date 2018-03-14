function retval = trainSingle (weights, entry,desired,learning,activation,algorithm)
  suma = suma(weights,entry);
  out =activation(suma);
  deltas = [];
  for i = [1:size(desired)(2)]
    deltas = [deltas; learning*(algorithm(desired(i),out(i),suma)).*entry;];
  endfor
  retval = weights + deltas;
endfunction