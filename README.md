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
Currently, cutting 1000 short segments along a continuous line costs the same as cutting the single continuous line all at once.
Instead, the code should ensure that there are no edges which could be merged into a single edge, and
a cost overhead should be added to each distinct edge that must be cut.

The time spent between cuts could potentially be large, depending on the size of the material, time to reposition/reinitialize
the laser, the number of edges, and the order in which the edges are cut. The quote should optimize edge order, then charge based
on the additional down-time.

The process for finding the hull and its orientation would need to be used in building the machine instructions for the 
cut operation, and possibly in validating the submitted file (to ensure it fits on the maximum material size for instance).
The code for this should be broken out into a separate module used by all of these systems, and the final results passed to the quote algorithm.

The convex hull calculation and calipers method can in theory generate invalid points based on rounding errors.
There are techniques for avoiding these: http://www.geometrictools.com/Documentation/MinimumAreaRectangle.pdf

This could be implemented in C++ if speed becomes essential.