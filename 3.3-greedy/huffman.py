import sys
from heapq import heapify, heappop, heappush

input_file = sys.argv[1]

with open(input_file) as f:
    weights = []
    depths = []
    for index, line in enumerate(f):
        weights.append((int(line), [index])) # (weight, indices) tuple
        depths.append(0)
        
heapify(weights) # in-place heapify

while len(weights) > 1:
    (lowest_weight, lowest_indices) = heappop(weights)
    (penultimate_weight, penultimate_indices) = heappop(weights)
    
    # Increment depths for all involved merged indices
    for index in lowest_indices:
        depths[index] += 1
    for index in penultimate_indices:
        depths[index] += 1
    
    new_depth = lowest_weight + penultimate_weight
    new_indices = lowest_indices + penultimate_indices
    heappush(weights, (new_depth, new_indices))

print('Maximum Encoding Length: ', max(depths))
print('Minimum Encoding Length: ', min(depths))