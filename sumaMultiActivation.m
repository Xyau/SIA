function V = sumaMultiActivation (weightsMulti, entries,activation)
  V = {};
  V{1} = entries;
  for i = 1:size(weightsMulti)(2)
    V{i+1} = activation((weightsMulti{i}*(addUmbral(V{i}))')');
  endfor
  
endfunction