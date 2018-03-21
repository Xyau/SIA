function p = helper()
  a = {}
  a{1} = unifrnd(-0.5,0.5,12,3);
  a{2} = unifrnd(-0.5,0.5,13,13);
  a{3} = unifrnd(-0.5,0.5,1,14);
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.01, a, f, f_prima);
  costerror = [];
end

f = @(x) xor(x(1),x(2),x(3))
