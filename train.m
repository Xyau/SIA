function retval = train (weights, entries,desired,learning,rounds)
  currWeights = weights
  prevWeights = weights;
  for i = [1:rounds]
     currWeights = trainEntries(currWeights,entries,desired,learning);
     if prevWeights == currWeights
       break
     else
       prevWeights = currWeights;
     endif
  endfor
  retval = currWeights;
endfunction