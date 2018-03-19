function p = learnXOR(p)
  p.learn([1,1], -1);
  p.learn([0,1], 1);
  p.learn([1,0], 1);
  p.learn([0,0], -1);
end
