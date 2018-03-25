classdef Configurator < handle

methods

    function this = Configurator(g,beta,eta,momentum,adaptative,incrementRate,decrementRate,hidden_layers,neurons)
      this.eta = eta;
      this.g = g;
      this.beta = beta;
      this.adaptative = adaptative
      this.incrementRate = incrementRate;
      this.decrementRate = decrementRate;
      this.hidden_layers = hidden_layers;
      this.neurons = neurons;
    end
    
    function weights = run()
      if g == 'tanh'
        g = @(x) tanh(beta*x);
        g_prima = @(x) beta*(1 - x.^2);
      elseif g{1} == 'exp'
        g = @(x) 1./(1+exp(-2*beta*h));
        g = @(x) 2*beta*x.*(1-x);
      else
        printf('%s\n', 'Error, no valid function name');
        return;
      end
      network = {};
      network{1} = unifrnd(-0.5,0.5,neurons(1),3);
      for i = 1:hidden_layers-2
        network{i} = unifrnd(-0.5,0.5,neurons(i+1),neurons(i)+1);
      end
      network{hidden_layers} = unifrnd(-0.5,0.5,1,neurons(hidden_layers)+1);

      p = Perceptron(eta, network, g, g_prima, momentum);
    end
  
 end 
    
   