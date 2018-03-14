function retval = actSigmoidDerivative (X)
  retval = arrayfun(@(x) actSigmoid(x)*(1-actSigmoid(x)),X);
endfunction