function p = helper()
  a = {}
  a{1} = unifrnd(-0.5,0.5,40,3);
  a{2} = unifrnd(-0.5,0.5,1,41);

  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.09, a, f, f_prima, 0);
  %0.005 y 0.9 vs 0.1 y 0
end

p = helper
f = @(x) xor(x(1),x(2))
p.learnIterativeAdaptative(f)
