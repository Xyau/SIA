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
    learningRateIncrement ;
    learningRateDecrement ;
    trainRatio ;
    epsilon ;
    cutCondition;
    terrainPath ;
  end

%===============================================================================

  methods

    function this = Perceptron(learningRate, network, activation, diffActivation, momentum,terrainPath,epsilon,cutCondition,learningRateIncrement,learningRateDecrement,trainRatio)
      this.learningRate = learningRate;
      this.activation = activation;
      this.diffActivation = diffActivation;
      this.network = network;
      this.terrainPath = terrainPath;
      this.epsilon = epsilon;
      this.cutCondition = cutCondition;
      this.learningRateIncrement = learningRateIncrement;
      this.learningRateDecrement = learningRateDecrement;
      this.trainRatio = trainRatio;
      this.momentum=momentum;
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

    function learnIncremental(this)
      [patterns , expected] = load_data(this.terrainPath);
      patterns_train = patterns(1 : floor(this.trainRatio * size(patterns)(1)),:);
      expected_train = expected(1 : floor(this.trainRatio * size(patterns)(1)),:);
      patterns_test = patterns(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      expected_test = expected(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      j = 0;
      do
        j++;
        printf('%d\n',j);
        fflush(stdout);
        for i = 1: size(patterns_train)(1)
          this.learn(patterns_train(i,:), expected_train(i));
        end
          aux = this.getError(patterns_train,expected_train)
          this.costError = [this.costError; aux];
          fflush(stdout);
      until (aux < this.cutCondition)
      this.testNetwork(patterns_test , expected_test);
    end

    function learnIncrementalAdaptative(this)

      [patterns , expected] = load_data(this.terrainPath);
      patterns_train = patterns(1 : floor(this.trainRatio * size(patterns)(1)),:);
      expected_train = expected(1 : floor(this.trainRatio * size(patterns)(1)),:);
      patterns_test = patterns(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      expected_test = expected(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      j = 0;
      do
        j++;
        printf('%d\n',j);
        fflush(stdout);
        for i = 1: size(patterns_train)(1)
          this.learn(patterns_train(i,:), expected_train(i));
        end
          aux = this.getError(patterns_train,expected_train)
          this.costError = [this.costError; aux];
          fflush(stdout);
           if size(this.costError)(2) > 2
              if this.costError(end) < this.costError(end - 1)
                this.learningRate += this.learningRateIncrement;
                this.momentumEnabled = 1;
              else
                this.learningRate -= this.learningRateDecrement * this.learningRate;
                this.momentumEnabled = 0;
                this.undo();
              end
            end
      until (aux < this.cutCondition)
      this.testNetwork(patterns_test , expected_test);
    end


    function learnBatch(this)
      [patterns , expected] = load_data(this.terrainPath);
      patterns_train = patterns(1 : floor(this.trainRatio * size(patterns)(1)),:);
      expected_train = expected(1 : floor(this.trainRatio * size(patterns)(1)),:);
      patterns_test = patterns(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      expected_test = expected(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      j = 0;
      do
        j++;
        printf('%d\n',j);
        fflush(stdout);
        [V, h] = this.propagate(patterns_train);
        deltas = this.calculateDeltas(V, h, expected_train);
        for k = 1:size(this.network)(2)
          V{k} = Perceptron.addThreshold(V{k});
          this.variation{k} = this.learningRate * deltas{k}' * V{k}+ ...
                this.momentum * this.variation{k} * this.momentumEnabled;
          this.network{k} += this.variation{k} / size(patterns_train)(1);
         end
          aux = this.getError(patterns_train,expected_train)
          this.costError = [this.costError; aux];
          fflush(stdout);
        until (aux < this.cutCondition)
        this.testNetwork(patterns_test , expected_test);
    end


    function learnBatchAdaptative(this)
      [patterns , expected] = load_data(this.terrainPath);
      patterns_train = patterns(1 : floor(this.trainRatio * size(patterns)(1)),:);
      expected_train = expected(1 : floor(this.trainRatio * size(patterns)(1)),:);
      patterns_test = patterns(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      expected_test = expected(floor(this.trainRatio * size(patterns)(1)+1):end,:);
      j = 0;
      do
        j++;
        printf('%d\n',j);
        fflush(stdout);
        [V, h] = this.propagate(patterns_train);
        deltas = this.calculateDeltas(V, h, expected_train);
        for k = 1:size(this.network)(2)
          V{k} = Perceptron.addThreshold(V{k});
          this.variation{k} = this.learningRate * deltas{k}' * V{k}+ ...
                this.momentum * this.variation{k} * this.momentumEnabled;
          this.network{k} += this.variation{k}/size(patterns_train)(1);
         end
          aux = this.getError(patterns_train,expected_train)
          this.costError = [this.costError; aux];
          fflush(stdout);
          if size(this.costError)(2) > 2
            if this.costError(end) < this.costError(end - 1)
              this.learningRate += this.learningRateIncrement;
              this.momentumEnabled = 1;
            else
              this.learningRate -= this.learningRateDecrement * this.learningRate;
              this.momentumEnabled = 0;
              this.undo();
            end
          end
        until (aux < this.cutCondition)
        this.testNetwork(patterns_test , expected_test);
    end

    function [XY,Z] = generalize(this)
      XY = Perceptron.buildXYEntries();
      Z = [];
      for i = 1:size(XY )(1)
        Z = [Z;this.result(XY(i,:))];
      end
    end


   function [XY,Z] = testTrainedPoints(this)
      [patterns , expected] = load_data(this.terrainPath);
      XY = patterns(1 : floor(this.trainRatio * size(patterns)(1)),:);
      Z = [];
      for i = 1:size(XY)(1)
        Z = [Z;this.result(XY(i,:))];
      end
    end

     function [XY,Z] = getOriginalData(this)
      [XY , Z] = load_data(this.terrainPath);
       XY = XY(1 : floor(this.trainRatio * size(XY)(1)),:);
       Z = Z(1 : floor(this.trainRatio * size(Z)(1)),:);
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
      deltas{layers} = (this.diffActivation(V{layers+1})).*(expectedOutput - V{layers+1});
      for k = layers:-1:2
        weights = Perceptron.removeThreshold(this.network{k});
        deltas{k-1} = (this.diffActivation(V{k})).*(deltas{k}*weights);
      end
    end



    function e = getError(this,patterns,output)
      e = 0;
      for i = 1 : size(patterns)(1)
        e += (output(i) - this.result(patterns(i,:)))** 2;
      end
      e /= 2* size(patterns)(1);
      pause(0);
      plot([this.costError;e]);
      refreshdata;
    end

     function e = testNetwork(this,patterns,output)
      s = 0;
      for i = 1 : size(patterns)(1)
        if abs(output(i) - this.result(patterns(i,:))) < this.epsilon
            s++;
        end
      end
      printf('%d de exito\n',s / size(patterns)(1));
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
