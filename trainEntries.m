function retval = trainEntries (weights, entries,desired,learning)
  currWeights = weights;
  for i = [1:size(desired)(2)]
     desiredOutput = desired(i,:);
     entry = entries(i,:);
     currWeights = trainSingle(currWeights,entry,desiredOutput,learning);
  endfor
  retval = currWeights;
endfunction