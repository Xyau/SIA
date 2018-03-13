function retval = trainSingle (weights, entry,desired,learning,activation)
  suma = suma(weights,entry);
  calc =activation(suma);
  deltas = [];
  for i = [1:size(desired)(2)]
    deltas = [deltas; learning*(desired(i) - calc(i)).*entry;];
  endfor
  retval = weights + deltas;
endfunction