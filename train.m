function retval = train (weights, entries,desired,learning,rounds, activation,actDerivative)
  currWeights = weights;
  prevWeights = weights;
  for i = [1:rounds]
     currWeights = trainEntries(currWeights,entries,desired,learning,activation,actDerivative);
     if prevWeights == currWeights
       break
     else
       prevWeights = currWeights;
     endif
  endfor
  retval = currWeights;
endfunction