function retval = trainSingleMulti (weights, desired,learning,activation,actDerivative,intermediateValues)
  layers = size(intermediateValues)(2);
  delta = actDerivative(intermediateValues{layers}) * (desired - intermediateValues{layers});
  weights{size(weights)(2)} = weights{size(weights)(2)} + learning*delta*addUmbral(intermediateValues{layers-1});
  for i = layers-1:-1:2
    deltaOld = delta;
    for j = 1 : size(weights{i-1})(1)
      delta = actDerivative(intermediateValues{i}(j)) * weights{i}(j+1) * deltaOld' ; 
      weights{i-1}(j,:) = weights{i-1}(j,:) + learning * delta * addUmbral(intermediateValues{i-1});
    endfor
  endfor
  retval = weights;
endfunction