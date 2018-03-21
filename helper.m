function p = helper()
  a = {}
  a{1} = unifrnd(-0.5,0.5,12,3);
  a{2} = unifrnd(-0.5,0.5,12,13);
  a{3} = unifrnd(-0.5,0.5,1,13);
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.005, a, f, f_prima, 0.9);
  costerror = [];
end

p = helper
f = @(x) xor(x(1),x(2))
p.learnWithError(f)
