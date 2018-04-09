classdef Configurator < handle

  properties (SetAccess = protected)
     eta;
     g;
     g_prima;
     beta;
     adaptative
     incrementRate;
     decrementRate;
     hidden_layers;
     neurons;
     momentum;
     mode;
     terrainPath;
     epsilon;
     cutCondition;
     trainRatio;
     seed;
     maxIterations;
   end

  methods

    function this = Configurator(g,beta,eta,momentum,adaptative,incrementRate,decrementRate,hidden_layers,neurons,mode,terrainPath,epsilon,cutCondition,trainRatio,seed,maxIterations)
      this.eta = eta;
      this.g = g;
      this.beta = beta;
      this.adaptative = adaptative;
      this.incrementRate = incrementRate;
      this.decrementRate = decrementRate;
      this.hidden_layers = hidden_layers;
      this.neurons = neurons;
      this.momentum = momentum;
      this.terrainPath=terrainPath;
      this.epsilon=epsilon;
      this.mode=mode;
      this.cutCondition=cutCondition;
      this.trainRatio=trainRatio;
      this.seed = seed;
      this.maxIterations = maxIterations;
    end

    function p = run(this)
      rand('seed',this.seed);
      if strcmp(this.g,'tanh')
        this.g = @(x) tanh(this.beta*x);
        this.g_prima = @(x) this.beta*(1 - x.^2);
      elseif strcmp(this.g,'exp')
        this.g = @(x) 1./(1+exp(- 2*this.beta*x));
        this.g_prima = @(x) 2*this.beta*x.*(1-x);
      else
        printf('%s\n', 'Error, no valid function name');
        return;
      end
      network = {};
      network{1} = unifrnd(-0.5,0.5,this.neurons(1),3);
      for i = 1:this.hidden_layers-1
        network{i+1} = unifrnd(-0.5,0.5,this.neurons(i+1),this.neurons(i)+1);
      end
      network{this.hidden_layers+1} = unifrnd(-0.5,0.5,1,this.neurons(this.hidden_layers)+1);

      p = Perceptron(this.eta, network, this.g, this.g_prima, this.momentum,this.terrainPath,this.epsilon,this.cutCondition,this.incrementRate,this.decrementRate,this.trainRatio,this.maxIterations);

      if strcmp(this.mode,'incremental')
        if this.adaptative == 1
          p.learnIncrementalAdaptative;
        else
          p.learnIncremental;
        end
      else
         if this.adaptative == 1
          p.learnBatchAdaptative;
        else
          p.learnBatch;
        end
      end
      #plotField;
    end

  end
end
