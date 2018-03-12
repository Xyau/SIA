function retval = trainSingle (weights, entry,desired,learning)
  calc =sign(suma(weights,entry));
  deltas = [];
  for i = [1:size(desired)(2)]
    deltas = [deltas; learning*(desired(i) - calc(i)).*entry;];
  endfor
  retval = weights + deltas;
endfunction