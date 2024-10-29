`brute-force-medians.py` contains a simple list-based approach to median maintenance. It has constant-time median selection (since it's a sorted list), but relies on linear-time insertion. (Note that even if sped up with binary search for faster insertion point index searching, moving the elements up the array is still linear time).

`heap-medians.py` uses the more efficient heap-based algorithm for median-maintenance, inserting a number and returning the new median in logarithmic time.

Usage: `python3 heap-medians num-list.txt`
