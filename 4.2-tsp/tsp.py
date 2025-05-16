import sys
import math
import numpy as np

if len(sys.argv) != 2:
    print("Usage: python tsp.py <data_file>")
    sys.exit(1)

data_file = sys.argv[1]

cities = []

# Read the file
with open(data_file, 'r') as file:
    for line in file:
        (x,y) = line.split(' ')
        cities.append((float(x),float(y)))

num_cities = len(cities)
num_destinations = num_cities - 1 # exclude source
num_subsets = 2 ** num_destinations # we don't need to include the source in our subsets
# first index: destinations, all cities minus source
# second index: subset (as a bitmask), from 0-2^n-1

source_city = cities[0]

# note that the second city (non-source) will have index 0
solutions = np.ndarray((num_destinations, num_subsets))
solutions.fill(np.nan)
        
def euclidian_distance(city1, city2):
    (x,y) = city1
    (z,w) = city2
    return math.sqrt((x-z)**2 + (y-w)**2)

def get_optimal_path(dest_num, subset_bitmask):
    if not np.isnan(solutions[dest_num][subset_bitmask]):
        return solutions[dest_num][subset_bitmask]
    
    dest_num_bitmask = 1 << dest_num
    dest_city = cities[dest_num+1]
        
    # If destination not in subset, we have no path
    # This also covers the subset_size == 0 case
    if subset_bitmask & dest_num_bitmask == 0:
        solutions[dest_num][subset_bitmask] = np.inf
        return np.inf
    
    subset_size = subset_bitmask.bit_count()
        
    # If direct path, use euclidian distance
    if subset_size == 1:
        path_length = euclidian_distance(source_city, dest_city)
        solutions[dest_num][subset_bitmask] = path_length
        return path_length
    
    # If two-node path, only a single path still possible
    if subset_size == 2:
        inner_node_num = (subset_bitmask ^ dest_num_bitmask).bit_length() - 1
        inner_node_city = cities[inner_node_num+1]
        path_length = euclidian_distance(source_city, inner_node_city) + euclidian_distance(inner_node_city, dest_city)
        solutions[dest_num][subset_bitmask] = path_length
        return path_length
    
    # bitmask without final destination
    subset_prime = subset_bitmask ^ dest_num_bitmask
    # list of destination indices
    inner_nodes = tuple(i for i in range(subset_prime.bit_length()) if subset_prime & (1 << i))
    
    best_path = np.inf
    
    for penultimate_node in inner_nodes:
        subpath_length = get_optimal_path(penultimate_node, subset_prime)
        potential_path = subpath_length + euclidian_distance(cities[penultimate_node+1],dest_city)
        if potential_path < best_path:
            best_path = potential_path
        
    solutions[dest_num][subset_bitmask] = best_path
    return best_path

print(f'Loaded Cities ({len(cities)}): {cities}')

for dest_num in range(num_destinations):
    for subset_bitmask in range(num_subsets):
        
        dest_num_bitmask = 1 << dest_num
        dest_city = cities[dest_num+1]
        
        # If destination not in subset, we have no path
        # This also covers the subset_size == 0 case
        if subset_bitmask & dest_num_bitmask == 0:
            solutions[dest_num][subset_bitmask] = np.inf
            continue
        
        subset_size = subset_bitmask.bit_count()
            
        # If direct path, use euclidian distance
        if subset_size == 1:
            path_length = euclidian_distance(source_city, dest_city)
            solutions[dest_num][subset_bitmask] = path_length
            continue
        # If two-node path, only a single path still possible
        if subset_size == 2:
            inner_node_num = (subset_bitmask ^ dest_num_bitmask).bit_length() - 1
            inner_node_city = cities[inner_node_num+1]
            path_length = euclidian_distance(source_city, inner_node_city) + euclidian_distance(inner_node_city, dest_city)
            solutions[dest_num][subset_bitmask] = path_length
            continue
        
        # bitmask without final destination
        subset_prime = subset_bitmask ^ dest_num_bitmask
        # list of destination indices
        inner_nodes = tuple(i for i in range(subset_prime.bit_length()) if subset_prime & (1 << i))
        
        best_path = np.inf
        
        for penultimate_node in inner_nodes:
            subpath_length = get_optimal_path(penultimate_node, subset_prime)
            potential_path = subpath_length + euclidian_distance(cities[penultimate_node+1],dest_city)
            if potential_path < best_path:
                best_path = potential_path
            
        solutions[dest_num][subset_bitmask] = best_path

# Final loop: find best path of all penultimate paths
all_dest_set = 2**num_destinations - 1
best_path = np.inf

for dest_num in range(num_destinations):
    subpath_length = get_optimal_path(dest_num, all_dest_set)
    potential_path = subpath_length + euclidian_distance(source_city, cities[dest_num+1])
    if potential_path < best_path:
        best_path = potential_path
        
print(best_path)