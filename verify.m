function retval = verify(weights, entries,desired,activation)
  retval = desired - activation(suma(weights,entries));
endfunction