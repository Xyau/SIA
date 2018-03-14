function retval = trainEntries (weights, entries,desired,learning,activation,actDerivative)
  currWeights = weights;
  for i = [1:size(desired)(1)]
     desiredOutput = desired(i,:);
     entry = entries(i,:);
     currWeights = trainSingle(currWeights,entry,desiredOutput,learning,activation,actDerivative,@algDeltaRule);
  endfor
  retval = currWeights;
endfunction