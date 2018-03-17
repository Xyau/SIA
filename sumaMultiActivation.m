function [V,h] = sumaMultiActivation (weightsMulti, entries,activation)
  V = {};
  h = {};
  h{1} = entries;
  V{1} = entries;
  for i = 1:size(weightsMulti)(2)
    h{i+1} = (weightsMulti{i}*(addUmbral(h{i}))')';
    V{i+1} = activation((weightsMulti{i}*(addUmbral(V{i}))')');
  endfor
  
endfunction