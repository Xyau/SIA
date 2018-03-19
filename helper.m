function p = helper()
  f = @(x) tanh(x);
  f_prima = @(x) 1 - tanh(x).^2;
  p = Perceptron(0.1, [4,1], [3,5], f, f_prima);
  p.learn([1,1], -1);
end
