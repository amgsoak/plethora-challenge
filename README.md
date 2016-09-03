# plethora-challenge

To process every file in the assets/input directory, simply execute run.sh, run.bat, or plethora_challenge.jar (java -jar plethora_challenge.jar)
To run against a particular file in the assets/input directory: "java -jar plethora_challenge.jar fileName.json"

Overview:
assts/config.xml contains constants pertinent to the quote generation
assets/*.json files contain the data files which provide input edge/vertex data

Complexity:
Setup: O(N)
Chan Hull Generation: O(nlogh) (n=#points in vertex set, h=#points on hull)

Possible additions:
The code has a partial implementation of Chan's Convex Hull algorithm. It wasn't working for all cases, so it was left out.
With a little more time, it could be used to speed up the hull generation time from O(nlogn) to O(nlogh), where h is the size of the hull.

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

Java supplies the BigDecimal number class that can more easily handle precision, rather than using double.

This could be implemented in C++ if speed becomes essential.