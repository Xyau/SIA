function retval = medidaError (desired,out)
  retval = 0.5*sum((desired-out).^2);
endfunction