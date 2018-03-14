function retval = algDeltaRule (desired,out,suma,actDerivative)
  retval = (desired-out).*actDerivative(suma);
endfunction