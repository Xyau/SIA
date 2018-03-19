classdef Perceptron < handle

  properties (SetAccess = protected)

    learningRate;
    network = {};
    activation;
    diffActivation;

  end

%===============================================================================

  methods

    function this = Perceptron(learningRate, sizes, numOfNeurons, activation, diffActivation)
      this.learningRate = learningRate;
      this.activation = activation;
      this.diffActivation = diffActivation;
      for i = 1:size(sizes)(2)
        this.network{i} = unifrnd(-1, 1, sizes(i), numOfNeurons(i));
      end
    end

    function output = result(this, input)
      output = input;
      for i = 1:size(this.network)(2)
        output = (this.network{i}*(Perceptron.addThreshold(output))')';
      end
    end

    function learn(this, input, expectedOutput)
      output = this.propagate(input);
      this.backpropagate(output, expectedOutput);
    end

  end

  methods (Access = private)

    function output = propagate(this, input)
      output{1} = input;
      for i = 1:size(this.network)(2)
        output{i+1} = this.activation((this.network{i}*(Perceptron.addThreshold(output{i}))'))';
      end
    end

    function backpropagate(this, output, expectedOutput)
      deltas = this.calculateDeltas(output, expectedOutput);
      for k = 1:size(this.network)(2)
        output{k} = Perceptron.addThreshold(output{k});
        this.network{k} += this.learningRate * deltas{k}' * output{k} ;
      end
    end

    function deltas = calculateDeltas(this, output, expectedOutput)
      layers = size(this.network)(2);
      deltas{layers} = this.diffActivation(output{layers+1}).*(expectedOutput - output{layers+1});
      for k = layers:-1:2
        weights = Perceptron.removeThreshold(this.network{k});
        deltas{k-1} = this.diffActivation(output{k}).*(deltas{k}*weights);
      end
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
      endfor
    endfunction

  end

end
