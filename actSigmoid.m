function retval = actSigmoid (X)
  retval = arrayfun(@(x) 1/(1+exp(-x)),X);
endfunction