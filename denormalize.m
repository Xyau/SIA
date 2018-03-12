function retval = denormalize(X)
   retval = arrayfun(@(x) x*2-1,X);
endfunction