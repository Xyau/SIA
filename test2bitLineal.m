1;
bits = 4
learning_factor = 0.02
rounds = 5
activation = @(x) x

E = buildBinaryEntries(bits)
f = @(x) [x(1)&x(2),x(3)&x(4)]
S = buildBinaryOutput(E,f)
E = addUmbral(E)
W = rand(size(S)(2),size(E)(2))

WN = train(W,E,S,learning_factor,rounds,activation)

UntrainedOut = verify(W,E,S,activation)
TrainedOut = verify(WN,E,S,activation)