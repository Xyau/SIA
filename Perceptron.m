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

    function learnAll(this, f)
      % [patterns , expected] = load_data('../Descargas/terrain06.txt');
      patterns = buildBinaryEntries(size(this.network{1})(2) - 1);
      for i = 1:size(patterns)(1)
        this.learn(patterns(i,:), f(patterns(i,:)));
        % this.learn(patterns(i,:), expected(i));
        % this.costError = [this.costError; this.getError(patterns,expected)];
      end
    end

    function learnWithError(this, f)
      patterns = buildBinaryEntries(size(this.network{1})(2) - 1);
      for i = 1:size(patterns)(1)
        expected = [];
        for j = 1:size(patterns)(1)
          expected = [expected, f(patterns)];
        end
        this.learn(patterns(i,:), expected(i));
        this.costError = [this.costError; this.getError(patterns,expected)];

        if size(this.costError)(2) > 2
          if this.costError(end) > this.costError(end - 1)
            this.learningRate += this.learningRateIncrement;
            this.momentumEnabled = 1;
          elseif
            this.learningRate -= this.learningRateDecrement * this.learningRate;
            this.momentumEnabled = 0;
            this.undo();
          end
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
        if (i == size(this.network)(2))
            V{i+1} = h{i+1};
        else
            V{i+1} = this.activation((this.network{i}*(Perceptron.addThreshold(V{i}))'))';
        end
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
      deltas{layers} = ones(size(h{layers+1})).*(expectedOutput - V{layers+1});
      for k = layers:-1:2
        weights = Perceptron.removeThreshold(this.network{k});
        deltas{k-1} = this.diffActivation(h{k}).*(deltas{k}*weights);
      end
    end

    function plotCost = getError(this,patterns,output)
      e = 0;
      for i = 1 : size(patterns)(1)
        e += (output(i) - this.result(patterns(i,:)))** 2;
      end
      e /= 2* size(patterns)(1);
      this.costIndex += 1;
      plotCost = [this.costIndex , e];
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
      for i = [-3:0.1:3]
         for j = [-3:0.1:3]
          X = [X;[i,j]];
         end
      end
      retval = X;
    end

  end

end
