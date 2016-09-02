# plethora-challenge

To run,
To run against all inputs in succession, 

Overview:


assts/config.xml contains constants pertinent to the quote generation
assets/*.json files contain the data files which provide input edge/vertex data

Complexity:
Setup: O(N)
Chan Hull Generation: O(nlogh) (n=#points in vertex set, h=#points on hull)


Convex Hull Generation:

Rotating Calipers Smallest Bounding Rectangle Algorithm:

Possible additions:
The convex hull calculation and calipers method can in theory generate invalid points based on rounding errors.
There are techniques for avoiding these: http://www.geometrictools.com/Documentation/MinimumAreaRectangle.pdf
Implement in C++ if speed becomes essential.