function retval = verify(weights, entries,desired)
  retval = desired - sign(suma(weights,entries));
endfunction