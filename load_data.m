function [T,S] = load_data(file)
        M = dlmread(file)(2:end,:);
        XYZ = [M(:,1:end)];
        maxval = max(XYZ(:,3));
        minval = min(XYZ(:,3));
        XYZ(:,3) -= minval;
        XYZ(:,3) = XYZ(:,3) ./ (maxval-minval);
        maxval = max(XYZ(:,2));
        minval = min(XYZ(:,2));
        XYZ(:,2) -= minval;
        XYZ(:,2) = XYZ(:,2) ./ (maxval-minval);
        maxval = max(XYZ(:,1));
        minval = min(XYZ(:,1));
        XYZ(:,1) -= minval;
        XYZ(:,1) = XYZ(:,1) ./ (maxval-minval);
        T = XYZ(:,1:end-1);
        S = XYZ(:,end);
end
