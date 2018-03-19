function p = helper()
  a = {}
  a{1} = unifrnd(-1,1,8,3);
  a{2} = unifrnd(-1,1,3,9);
  a{3} = unifrnd(-1,1,1,4);
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.005, a, f, f_prima);
  costerror = [];
end

f = @(x) xor(x(1),x(2),x(3))
