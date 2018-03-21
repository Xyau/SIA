function [T,S] = load_data(file)
        M = dlmread(file)(2:end,:);
        XYZ = [M(:,1:end)];
        max = max(XYZ(:,3));
        min = min(XYZ(:,3));
        XYZ(:,3) -= min;
        XYZ(:,3) = XYZ(:,3) ./ (max-min);
        T = XYZ(:,1:end-1);
        S = XYZ(:,end);
end
