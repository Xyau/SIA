classdef Perceptron < handle

  properties (SetAccess = protected)

    learningRate;
    network = {};
    previousNetwork = {};
    % Activation function
    activation;
    diffActivation;
    % Costs
    costError = [];
    costIndex = 0;
    % Momentum
    variation = {};
    previousVariation = {};
    momentum;
    momentumEnabled = 1;
    % Adaptative learning rate
    learningRateIncrement = 0.1;
    learningRateDecrement = 0.01;

  end

%===============================================================================

  methods

    function this = Perceptron(learningRate, network, activation, diffActivation, momentum)
      this.learningRate = learningRate;
      this.activation = activation;
      this.diffActivation = diffActivation;
      this.network = network
      this.momentum = momentum;
      for i = 1:size(network)(2)
        this.variation{i} = zeros(size(network{i}));
      end
    end

    function output = result(this, input)
      [V, h] = this.propagate(input);
      output = V{end};
    end

    function learn(this, input, expectedOutput)
      this.previousNetwork = this.network;
      this.previousVariation = this.variation;

      [V, h] = this.propagate(input);
      this.backpropagate(V, h, expectedOutput);
    end

    function learnAll(this)
      [patterns , expected] = load_data('../Descargas/terrain06.data');
      patterns
      for j = 1:100
        printf('%d\n',j);
        fflush(stdout);
        for i = 1:size(patterns)(1)
          this.learn(patterns(i,:), expected(i));
        end
          aux = this.getError(patterns,expected)
          this.costError = [this.costError; aux];
                 fflush(stdout);

            if (aux < 0.001)
            break;
            end
      end
    end

    function learnWithError(this)
      [patterns , expected] = load_data('../Descargas/terrain06.data');
      for i = 1:size(patterns)(1)
        this.learn(patterns(i,:), expected(i,:));
      end
      this.costError = [this.costError; this.getError(patterns,expected)];

      if size(this.costError)(2) > 2
        if this.costError(end) < this.costError(end - 1)
          this.learningRate += this.learningRateIncrement;
          this.momentumEnabled = 1;
        elseif
          this.learningRate -= this.learningRateDecrement * this.learningRate;
          this.momentumEnabled = 0;
          this.undo();
        end
      end
    end

    function [XY,Z] = testAll(this)
      XY = Perceptron.buildXYEntries();
      Z = [];
      for i = 1:size(XY )(1)
        Z = [Z;this.result(XY(i,:))];
      end
    end

  end

  methods (Access = private)

    function [V,h] = propagate(this, input)
      V{1} = input;
      h{1} = input;
      for i = 1:size(this.network)(2)
        h{i+1} = (this.network{i}*(Perceptron.addThreshold(V{i}))')';
        V{i+1} = this.activation((this.network{i}*(Perceptron.addThreshold(V{i}))'))';
      end
    end

    function backpropagate(this, V, h, expectedOutput)
      deltas = this.calculateDeltas(V, h, expectedOutput);
      for k = 1:size(this.network)(2)
        V{k} = Perceptron.addThreshold(V{k});
        this.variation{k} = this.learningRate * deltas{k}' * V{k} + ...
                this.momentum * this.variation{k} * this.momentumEnabled;
        this.network{k} += this.variation{k};
      end
    end

    function deltas = calculateDeltas(this, V, h, expectedOutput)
      layers = size(this.network)(2);
      deltas{layers} = ((1 - V{layers+1}.**2)).*(expectedOutput - V{layers+1});
      for k = layers:-1:2
        weights = Perceptron.removeThreshold(this.network{k});
        deltas{k-1} = ((1 - V{k}.**2)).*(deltas{k}*weights);
      end
    end


    function plotCost = getThisError(this,patterns,output)
      e = 0;
      for i = 1 : size(patterns)(1)
        e += (output(i) - this.result(patterns(i,:)))** 2;
      end
      e /= 2* size(patterns)(1) ;
      plotCost = e;
    end

    function e = getError(this,patterns,output)
      e = 0;
      for i = 1 : size(patterns)(1)
        e += (output(i) - this.result(patterns(i,:)))** 2;
      end
      e /= 2* size(patterns)(1);
    end

    function undo(this)
      this.variation = this.previousVariation;
      this.network = this.previousNetwork;
    end

  end

%===============================================================================

  methods (Static)

    function ret = addThreshold(input)
      ret = [ones(size(input)(1),1).*-1,input];
    end

    function [ans] = removeThreshold(weights)
      ans = [];
      for i = 1 : size(weights)(1)
          ans = [ans;weights(i,2:end)];
      end
    end

    function retval = buildBinaryEntries (n)
      X = [];
      for i = [0:2^n-1]
         row = [];
         for j = [0:n-1]
            row = horzcat(row,bitget(i,n-j));
         end
         X = vertcat(X,row);
      end
      retval = X;
    end

    function retval = buildXYEntries ()
      X = [];
      for i = [-3.5:0.1:3.5]
         for j = [-3.5:0.1:3.5]
          X = [X;[i,j]];
         end
      end
      retval = X;
    end

  end

end
