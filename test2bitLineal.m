1;
bits = 4
learning_factor = 0.02
rounds = 200
activation = @actSigmoid
activationDerivative = @actSigmoidDerivative
#activation = @(x) x
#activationDerivative = 1
E = buildBinaryEntries(bits)
f = @(x) [x(1)&x(2),x(3)&x(4)]
S = buildBinaryOutput(E,f)
E = addUmbral(E)
W = rand(size(S)(2),size(E)(2))

WN = train(W,E,S,learning_factor,rounds,activation,activationDerivative)

UntrainedError = verify(W,E,S,activation)
TrainedError = verify(WN,E,S,activation)