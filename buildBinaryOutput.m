function retval = buildBinaryOutput (X,f)
  output = [];
  for i = [1:size(X)(1)]
    output = [output;f(X(i,:))];
  endfor
  retval = output;
endfunction