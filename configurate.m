function p = configurate(filename)

  [func, learningRate, momentum] = textread(filename, '%s %f %f', 1);
  sizes = dlmread(filename, '%d %d', 1, 0)
  if func{1} == 'tanh'
    f = @(x) tanh(x);
    f_prima = @(x) 1 - tanh(x).^2;
  elseif func{1} == 'exp'
    f = @(x) sigmf(x, [2, 0]);
    f_prima = @(x) 2 * x .* (1 - x);
  else
    printf('%s\n', 'Error, no valid function name');
    return;
  end
  network = {};
  for i = 1:size(sizes)(1)
    network{i} = unifrnd(-0.5,0.5,sizes(i,1),sizes(i,2));
  end

  p = Perceptron(learningRate, network, f, f_prima, momentum);

end
