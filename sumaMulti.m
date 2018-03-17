function h = sumaMulti(weightsMulti, entries)
  h = {};
  h{1} = entries;
  for i = 1:size(weightsMulti)(2)
    h{i+1} = (weightsMulti{i}*(addUmbral(h{i}))')';
  endfor
  
endfunction