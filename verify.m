function retval = verify(weights, entries,desired,activation)
  retval = medidaError(desired,activation(suma(weights,entries)));
endfunction