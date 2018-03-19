function p = helper()
  a = {}
  a{1} = rand(4,4);
  a{2} = rand(4,5);
  a{3} = rand(1,5);
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.1, a, f, f_prima);
  costerror = [];
end

f = @(x) xor(x(1),x(2),x(3))
