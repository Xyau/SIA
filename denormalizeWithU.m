function retval = denormalizeWithUmbral (X)
  asd = zeros(size(X)(1),1);
  retval = arrayfun(@(x) x*2-1,[asd,X]);
endfunction