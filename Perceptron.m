classdef Perceptron < handle

  properties (SetAccess = protected)

    learningRate;
    network = {};
    activation;
    diffActivation;
    costError = [];
    costIndex = 0;

  end

%===============================================================================

  methods

    function this = Perceptron(learningRate, network, activation, diffActivation)
      this.learningRate = learningRate;
      this.activation = activation;
      this.diffActivation = diffActivation;
      this.network = network
    end

    function output = result(this, input)
      [V, h] = this.propagate(input);
      output = V{end};
    end

    function learn(this, input, expectedOutput)
      [V, h] = this.propagate(input);
      this.backpropagate(V, h, expectedOutput);
    end

    function learnAll(this)
      [patterns , expected] = load_data('../Descargas/terrain06.txt');
      for i = 1:size(patterns)(1)
        this.learn(patterns(i,:), expected(i));
        % this.costError = [this.costError; this.getError(patterns,expected)];
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
        this.network{k} += this.learningRate * deltas{k}' * V{k};
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
