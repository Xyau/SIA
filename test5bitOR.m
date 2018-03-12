1;
bits = 10
learning_factor = 0.05
rounds = 50

E = buildBinaryEntries(bits)
f = @(x) [x(1)|x(2),x(3)|x(4),x(5)|x(6),x(7)|x(8),x(9)|x(10)]
S = buildBinaryOutput(E,f)
E = denormalizeWithU(E)
S = denormalize(S)
W = rand(size(S)(2),size(E)(2))

WN = train(W,E,S,learning_factor,rounds)

UntrainedOut = verify(W,E,S)
TrainedOut = verify(WN,E,S)