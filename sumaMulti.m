function V = sumaMulti(weightsMulti, entries)
  V = {};
  V{1} = entries;
  for i = 1:size(weightsMulti)(2)
    V{i+1} = (weightsMulti{i}*(addUmbral(V{i}))')';
  endfor
  
endfunction