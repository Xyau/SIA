function [ans] = removeUmbral(weights)
  ans = [];
  for i = 1 : size(weights)(1)
      ans = [ans;weights(i,2:end)];
  endfor
endfunction