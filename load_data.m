function [T,S] = load_data(file)
        M = dlmread(file)(2:end,:);
        XYZ = [M(:,1:end)];
        maxval = max(XYZ(:,3));
        minval = min(XYZ(:,3));
        XYZ(:,3) -= minval;
        XYZ(:,3) = XYZ(:,3) ./ (maxval-minval);
        rands = randperm(numel(XYZ(:,1)));
        j = 1;
        for i = 1:columns(rands)
          aux = XYZ(i,:);
          XYZ(i,:) = XYZ(j,:);
          XYZ(j,:) = aux;
          j += 1;
        end
        T = XYZ(:,1:end-1);
        S = XYZ(:,end);
end
