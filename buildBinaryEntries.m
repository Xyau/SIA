function retval = buildBinaryEntries (n)
  X = [];
  for i = [0:2^n-1]
     row = [];
     for j = [0:n-1]
        row = horzcat(row,bitget(i,n-j));
     endfor
     X = vertcat(X,row);
  endfor
  retval = X;
endfunction