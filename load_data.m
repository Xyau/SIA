function [T,S] = load_data(file)
        M = dlmread(file)(2:end,:);
        XYZ = [M(:,1:end)];
        maxval = max(XYZ(:,3));
        minval = min(XYZ(:,3));
        XYZ(:,3) -= minval;
        XYZ(:,3) = XYZ(:,3) ./ (maxval-minval);
        T = XYZ(:,1:end-1);
        S = XYZ(:,end);
end
