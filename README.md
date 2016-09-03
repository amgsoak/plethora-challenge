# Laser Cut Quote - Plethora Challenge

### How to Run
To process every file in the "assets/input" directory, simply execute run.sh, run.bat, or plethora_challenge.jar (java -jar plethora_challenge.jar)
To run against a particular file in the assets/input directory: "java -jar plethora_challenge.jar fileName.json"

### Overview
assets/config.xml contains constants pertinent to the quote generation
assets/*.json files contain the data files which provide input edge/vertex data
Config data is loaded into the ConfigValues class.
The InputFileData class will load and parse the data, and supply the vertices that are candidates for the hull.
The QuoteGenerator class will use the vertices to generate hull points using the implementation of Graham Scan found in ActiveAlgs.grahamHullGenerator.
Then the containing rect will be calculated using the implementation of Rotating Calipers found in ActiveAlgs.minRectGenerator.
Using the values in the config file, the quote will be generated from this minimum rect and a simple calculation of total cutting time.

I'm using modified third party code for the basic graham scan and rotating calipers algorithms.
https://github.com/bkiers/RotatingCalipers
https://github.com/bkiers/GrahamScan

### Complexity
N = Total Vertices
H = Number of Hull Vertices
Setup: O(N)
Graham Scan: O(NlogN)
Rotating Calipers: O(H)
Final Complexity: O(NlogN)

### Possible Additions
The code has a partial implementation of Chan's Convex Hull algorithm. It wasn't working for all cases, so it was left out.
With a little more time, it could be used to speed up the hull generation time from O(nlogn) to O(nlogh), where h is the size of the hull.

This could be implemented in C++ if speed becomes essential.

The convex hull calculation and calipers method can in theory generate invalid points based on rounding errors.
There are techniques for avoiding these: http://www.geometrictools.com/Documentation/MinimumAreaRectangle.pdf

Currently, cutting 1000 short segments along a continuous line costs the same as cutting the single continuous line all at once.
Instead, the code should ensure that there are no edges which could be merged into a single edge, and
a cost overhead should be added to each distinct edge that must be cut.

The time spent between cuts could potentially be large, depending on the size of the material, time to reposition/reinitialize
the laser, the number of edges, and the order in which the edges are cut. The quote should optimize cut/edge order, then charge based
on the additional down-time.

The process for finding the hull and its orientation would need to be used in building the machine instructions for the 
cut operation, and possibly in validating the submitted file (to ensure it fits on the maximum material size for instance).
The code for this should be broken out into a separate module used by all of these systems, and the final results passed to the quote algorithm.

Java supplies the BigDecimal number class that can more easily handle precision, rather than using double.

More validation of input data. Depending on the level of validation when the data "is automatically converted to a 2D profile",
this may not be necessary.

Unit testing could be done with a more robust (probably 3rd party) system.